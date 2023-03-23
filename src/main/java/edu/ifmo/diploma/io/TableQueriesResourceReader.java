package edu.ifmo.diploma.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifmo.diploma.exception.ContextLoadException;
import edu.ifmo.diploma.model.RegexTime;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TableQueriesResourceReader {
    private static final String TABLE_QUERY_REGEXP = "query_regexp";
    private static final Logger log = LoggerFactory.getLogger(TableQueriesResourceReader.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Reader reader;

    private TableQueriesResourceReader(InputStream inputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public static TableQueriesResourceReader fromInputStream(InputStream inputStream) {
        try {
            return new TableQueriesResourceReader(inputStream);
        } catch (IOException e) {
            log.error("Resource file can't found", e);
            throw new ContextLoadException(ContextLoadException.EXCEPTION_WHILE_READING_FILE, e);
        }
    }

    public Set<RegexTime> readResource() {
        JsonNode jsonNode = readTree();

        Set<RegexTime> queries = StreamSupport
                .stream(jsonNode.path(TABLE_QUERY_REGEXP).spliterator(), false)
                .map(node -> Try.of(() -> objectMapper.treeToValue(node, RegexTime.class))
                        .getOrElseThrow(() -> new IllegalArgumentException("Bad file")))
                .collect(Collectors.toSet());
        log.debug("Read queries {}", queries);
        return queries;
    }

    private JsonNode readTree() {
        try {
            return objectMapper.readTree(reader);
        } catch (IOException e) {
            log.error("Resource can't be read ", e);
            throw new ContextLoadException(ContextLoadException.EXCEPTION_WHILE_READING_FILE, e);
        }
    }
}
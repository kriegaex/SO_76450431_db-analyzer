package edu.ifmo.diploma.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifmo.diploma.exception.ContextLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TableQueriesResourceReader {
    private static final String TABLE_QUERY_REGEXP = "query_regexp";
    private static final Logger log = LoggerFactory.getLogger(TableQueriesResourceReader.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Reader reader;

    private TableQueriesResourceReader(Path path) throws IOException {
        this.reader = Files.newBufferedReader(path);
    }

    public static TableQueriesResourceReader fromFile(Path path) {
        try {
            return new TableQueriesResourceReader(path);
        } catch (IOException e) {
            log.error("Resource file with path {} can't found", path, e);
            throw new ContextLoadException(ContextLoadException.EXCEPTION_WHILE_READING_FILE, e);
        }
    }

    public Set<String> readResource() {
        JsonNode jsonNode = readTree();

        return StreamSupport
                .stream(jsonNode.path(TABLE_QUERY_REGEXP).spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toSet());
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
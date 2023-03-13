package edu.ifmo.diploma.io;

import edu.ifmo.diploma.exception.ContextLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties loadProperties(String resourceFileName) {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName)) {
            configuration.load(inputStream);
            return configuration;
        } catch (IOException e) {
            log.error("there is an error while load property by name {}", resourceFileName, e);
            throw new ContextLoadException(ContextLoadException.EXCEPTION_WHILE_LOAD_PROPERTIES, e);
        }
    }
}
package edu.ifmo.diploma.exception;

public class ContextLoadException extends RuntimeException {

    public static final String EXCEPTION_WHILE_READING_FILE = "File reading exception";
    public static final String EXCEPTION_WHILE_LOAD_PROPERTIES = "Loading properties exception";

    public ContextLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
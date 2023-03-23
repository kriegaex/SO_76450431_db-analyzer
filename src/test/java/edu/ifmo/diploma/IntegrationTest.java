package edu.ifmo.diploma;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Testcontainers
public abstract class IntegrationTest {
    private static final String DB_ANALYZER_REPORT_CONTEXT_FLUSH_SECONDS = "DB_ANALYZER_REPORT_CONTEXT_FLUSH_SECONDS";

    protected int flushPeriod() {
        return Integer.parseInt(System.getenv(DB_ANALYZER_REPORT_CONTEXT_FLUSH_SECONDS)) + 1;
    }
    protected void writeToFile(String content) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        Files.writeString(Paths.get(
                new File(String.format("reports/db-report-%s.html", LocalDateTime.now().format(formatter))).toURI()
        ), content);
    }
}

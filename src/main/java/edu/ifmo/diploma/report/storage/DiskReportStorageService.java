package edu.ifmo.diploma.report.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiskReportStorageService implements ReportStorageService {

    private final String packageName;
    private final DateTimeFormatter dateTimeFormatterThreadLocal;

    public DiskReportStorageService(String packageName, DateTimeFormatter dateTimeFormatterThreadLocal) {
        this.packageName = packageName;
        this.dateTimeFormatterThreadLocal = dateTimeFormatterThreadLocal;
    }

    @Override
    public void store(String content) {
        try {
            String formattedDate = LocalDateTime.now().format(dateTimeFormatterThreadLocal);
            File file = new File(String.format("%s/db-report-%s.html", packageName, formattedDate));
            Files.writeString(Paths.get(file.toURI()), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

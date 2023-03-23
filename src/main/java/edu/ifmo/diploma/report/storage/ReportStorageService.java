package edu.ifmo.diploma.report.storage;

@FunctionalInterface
public interface ReportStorageService {
    void store(String content);
}

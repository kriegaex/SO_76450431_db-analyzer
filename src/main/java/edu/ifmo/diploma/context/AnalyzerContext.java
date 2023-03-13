package edu.ifmo.diploma.context;

import edu.ifmo.diploma.model.DbAnalyzerModel;
import edu.ifmo.diploma.model.ExplainResult;
import edu.ifmo.diploma.model.enumeration.DatabasePlatform;
import edu.ifmo.diploma.io.PropertiesLoader;
import edu.ifmo.diploma.io.TableQueriesResourceReader;
import edu.ifmo.diploma.report.config.FreemarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AnalyzerContext {

    private static final Logger log = LoggerFactory.getLogger(AnalyzerContext.class);
    private static final String APP_PROPERTIES = "app.properties";
    private static final String FILE_NAME_PROPERTY = "db.analyzer.resource.file.name";
    private static final String PLATFORM_PROPERTY = "db.analyzer.platform";
    private static final String REPORT_FLUSH_PROPERTY = "db.analyzer.report.context.flush.seconds";
    private static final String REPORT_PACKAGE_PROPERTY = "db.analyzer.report.path";

    private static volatile AnalyzerContext instance;

    private final Set<Pattern> queryPatterns;
    private final DatabasePlatform platform;
    private final DbAnalyzerModel dbAnalyzerModel;
    private final FreemarkerService freemarkerService;
    private final String reportPackagePath;

    private AnalyzerContext() throws IOException, URISyntaxException {
        Properties properties = PropertiesLoader.loadProperties(APP_PROPERTIES);
        Path path = Paths.get(Thread.currentThread()
                .getContextClassLoader()
                .getResource(properties.getProperty(FILE_NAME_PROPERTY))
                .toURI());
        queryPatterns = TableQueriesResourceReader.fromFile(path).readResource()
                .stream()
                .map(Pattern::compile)
                .collect(Collectors.toSet());
        platform = DatabasePlatform.valueOf(properties.getProperty(PLATFORM_PROPERTY));
        dbAnalyzerModel = new DbAnalyzerModel();
        freemarkerService = new FreemarkerService();
        reportPackagePath = properties.getProperty(REPORT_PACKAGE_PROPERTY);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::flushContext,
                        0,
                        Long.parseLong(properties.getProperty(REPORT_FLUSH_PROPERTY)),
                        TimeUnit.SECONDS);
    }

    public static AnalyzerContext getInstance() {
        if (instance == null) {
            synchronized (AnalyzerContext.class) {
                if (instance == null) {
                    try {
                        instance = new AnalyzerContext();
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    log.debug("Context initialized");
                }
            }
        }
        return instance;
    }

    public void addExplainResult(String sql, ExplainResult result) {
        dbAnalyzerModel.addResult(sql, result);
        log.debug("Added query {} to analyze", sql);
    }

    private void flushContext() {
        freemarkerService.createReport(reportPackagePath, dbAnalyzerModel);
        dbAnalyzerModel.clear();
        log.debug("Context flushed at {}", ZonedDateTime.now());
    }

    public boolean isQueryInAnalysis(String query) {
        return queryPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(query).find());
    }

    public DatabasePlatform getPlatform() {
        return platform;
    }
}
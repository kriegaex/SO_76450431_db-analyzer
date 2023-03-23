package edu.ifmo.diploma.context;

import edu.ifmo.diploma.model.DbAnalyzerModel;
import edu.ifmo.diploma.model.ExplainResult;
import edu.ifmo.diploma.model.RegexTime;
import edu.ifmo.diploma.model.enumeration.DatabasePlatform;
import edu.ifmo.diploma.io.PropertiesLoader;
import edu.ifmo.diploma.io.TableQueriesResourceReader;
import edu.ifmo.diploma.proccessor.QueryAnalyzerProcessor;
import edu.ifmo.diploma.report.config.FreemarkerService;
import edu.ifmo.diploma.report.storage.ReportStorageService;
import io.vavr.control.Try;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static edu.ifmo.diploma.proccessor.QueryAnalyzerProcessor.*;

public class AnalyzerContext {

    private static final Logger log = LoggerFactory.getLogger(AnalyzerContext.class);
    private static final String APP_PROPERTIES = "app.properties";
    private static final String FILE_NAME_PROPERTY = "db.analyzer.resource.file.name";
    private static final String PLATFORM_PROPERTY = "db.analyzer.platform";
    private static final String REPORT_FLUSH_PROPERTY = "db.analyzer.report.context.flush.seconds";
    private static final String BASE_PACKAGE = "db.analyzer.base.package";

    private static volatile AnalyzerContext instance;

    private static ReportStorageService reportStorageService;
    private static Collection<QueryAnalyzerProcessor> analyzerProcessors;

    private final Map<String, Long> queryPatterns;
    private final DatabasePlatform platform;
    private String basePackage;
    private final DbAnalyzerModel dbAnalyzerModel;
    private final FreemarkerService freemarkerService;
    private final long flushPeriod;

    public static AnalyzerContext getInstance(ReportStorageService service,
                                              Collection<QueryAnalyzerProcessor> analyzerProcessors) {
        if (instance == null) {
            synchronized (AnalyzerContext.class) {
                if (instance == null) {
                    try {
                        if (Objects.isNull(service)) {
                            throw new IllegalArgumentException("Provide correct report storage service");
                        }
                        reportStorageService = service;
                        AnalyzerContext.analyzerProcessors = CollectionUtils.isEmpty(analyzerProcessors)
                                ? defaultProcessorChain()
                                : analyzerProcessors;
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


    public boolean isQueryInAnalysis(String query) {
        return queryPatterns.entrySet().stream()
                .anyMatch(entry -> Pattern.compile(entry.getKey()).matcher(query).find());
    }

    public Long getCriticalTimeForQuery(String query) {
        return queryPatterns.entrySet().stream()
                .filter(entry -> Pattern.compile(entry.getKey()).matcher(query).find())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This query not in analysis"));
    }

    public DatabasePlatform getPlatform() {
        return platform;
    }

    public String getBasePackage() {
        return basePackage;
    }

    void addExplainResult(String sql, ExplainResult result) {
        result.setCriticalTime(getCriticalTimeForQuery(sql));
        if (result.getCriticalTime() < result.getExecutionTime()) {
            log.debug("Query {} is critical", sql);
            analyzerProcessors.forEach(processor -> processor.analyze(sql, result));
        }
        result.getPlan().replaceIfNodeTypeOuter();
        dbAnalyzerModel.addResult(sql, result);
        log.debug("Added query {} to analyze", sql);
    }

    private AnalyzerContext() throws IOException, URISyntaxException {
        Properties properties = Try.of(() -> PropertiesLoader.loadProperties(APP_PROPERTIES))
                .getOrElse(new Properties());
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(getVariable(properties, FILE_NAME_PROPERTY));
        queryPatterns = TableQueriesResourceReader.fromInputStream(inputStream).readResource()
                .stream()
                .collect(Collectors.toMap(RegexTime::getRegex, RegexTime::getMs));
        platform = DatabasePlatform.valueOf(getVariable(properties, PLATFORM_PROPERTY));
        dbAnalyzerModel = new DbAnalyzerModel();
        basePackage = getVariable(properties, BASE_PACKAGE);
        freemarkerService = new FreemarkerService();
        flushPeriod = Long.parseLong(getVariable(properties, REPORT_FLUSH_PROPERTY));
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::flushContext,
                        0,
                        flushPeriod,
                        TimeUnit.SECONDS);
    }

    private static String getVariable(Properties properties, String property) {
        return Optional
                .ofNullable(properties.getProperty(property))
                .orElseGet(() -> System.getenv(property.replace(".", "_")));
    }

    private void flushContext() {
        if (!dbAnalyzerModel.isEmpty()) {
            synchronized (this) {
                if (!dbAnalyzerModel.isEmpty()) {
                    LocalDateTime now = LocalDateTime.now();
                    dbAnalyzerModel.setFrom(now.minusSeconds(flushPeriod));
                    dbAnalyzerModel.setTo(now);
                    String report = freemarkerService.createReport(dbAnalyzerModel);
                    reportStorageService.store(report);
                    dbAnalyzerModel.clear();
                    log.debug("Context flushed at {}", ZonedDateTime.now());
                }
            }
        }
    }
}
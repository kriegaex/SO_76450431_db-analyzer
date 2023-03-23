package edu.ifmo.diploma.report.config;

import edu.ifmo.diploma.model.DbAnalyzerModel;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class FreemarkerService {
    private final Template template;
    private static final ThreadLocal<DateTimeFormatter> DATE_TIME_FORMATTER = ThreadLocal.withInitial(
            () -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    );

    public FreemarkerService() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("main", IOUtils.toString(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("templates/main.ftlh")));
        cfg.setTemplateLoader(templateLoader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        this.template = cfg.getTemplate("main");
    }

    public String createReport(DbAnalyzerModel model) {
        Map<String, Object> root = new HashMap<>();
        root.put("tables", model.getTableQueries());
        root.put("from", model.getFrom().format(DATE_TIME_FORMATTER.get()));
        root.put("to", model.getTo().format(DATE_TIME_FORMATTER.get()));
        try (Writer out = new StringWriter()) {
            template.process(root, out);
            return out.toString();
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

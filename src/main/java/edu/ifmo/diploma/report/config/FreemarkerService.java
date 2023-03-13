package edu.ifmo.diploma.report.config;

import edu.ifmo.diploma.model.DbAnalyzerModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class FreemarkerService {
    private final Template template;

    public FreemarkerService() throws IOException, URISyntaxException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("templates").toURI()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        this.template = cfg.getTemplate("main.ftlh");
    }

    public void createReport(String packagePath, DbAnalyzerModel model) {
        Map<String, Object> root = new HashMap<>();
        root.put("user", "max");
        root.put("tables", model.getTableQueries());
        String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        try (Writer out = new FileWriter(String.format("%s/report-%s.html", packagePath, id))) {
            template.process(root, out);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

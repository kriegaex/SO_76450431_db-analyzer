package edu.ifmo.diploma.context;

import edu.ifmo.diploma.model.ExplainResult;
import io.vavr.control.Try;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Aspect
public class AnalyzerAspect {
    private final Logger log = LoggerFactory.getLogger(AnalyzerAspect.class);
    private final AnalyzerContext context = AnalyzerContext.getInstance(null, null);

    @Pointcut("target(java.sql.Statement)")
    public void statement() {
    }

    @AfterReturning("!within(AnalyzerAspect) && statement() && args(sql)")
    public void after(JoinPoint jp, String sql) throws Throwable {
        if (context.isQueryInAnalysis(sql)) {
            String explainQuery = context.getPlatform().withAnalyzePrefix(sql);
            Statement target = (Statement) jp.getTarget();
            Connection connection = target.getConnection();
            Statement statement = connection.createStatement();
            ExplainResult explainResult = Try.of(() -> statement.executeQuery(explainQuery))
                    .filter(ResultSet.class::isInstance)
                    .map(ResultSet.class::cast)
                    .map(this::toExplainResult)
                    .onFailure(e -> log.error("Error while execute query", e))
                    .getOrElseThrow(() -> new IllegalArgumentException("Bad sql grammar"));
            log.debug("Query {} spent {} milliseconds", sql, explainResult.getExecutionTime());
            context.addExplainResult(sql, explainResult);
        }
    }

    private ExplainResult toExplainResult(ResultSet resultSet) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1));
            }
            String result = stringBuilder.toString();
            log.debug(result);
            StackTraceElement[] baseStackTrace = Thread.currentThread().getStackTrace();
            ExplainResult explainResult = context.getPlatform()
                    .toExplainResult(result)
                    .setBaseStackTrace(baseStackTrace)
                    .setModuleStackTrace(Arrays.stream(baseStackTrace)
                            .filter(stackTraceElement -> stackTraceElement
                                    .getClassName()
                                    .startsWith(context.getBasePackage()))
                            .toArray(StackTraceElement[]::new));
            log.debug("stackTrace: {}", Arrays.toString(explainResult.getBaseStackTrace()));
            return explainResult;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not parse result set", e);
        }
    }
}
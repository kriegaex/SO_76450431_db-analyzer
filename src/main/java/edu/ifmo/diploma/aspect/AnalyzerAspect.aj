package edu.ifmo.diploma.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifmo.diploma.context.AnalyzerContext;
import edu.ifmo.diploma.model.ExplainResult;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.Arrays;

public aspect AnalyzerAspect {

    private final Logger log = LoggerFactory.getLogger(AnalyzerAspect.class);
    private final AnalyzerContext context = AnalyzerContext.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    pointcut statementExecuteQuery(String sql):
            call(* java.sql.Statement.execute*(java.lang.String)) && args(sql);

    ResultSet around(String sql):
            statementExecuteQuery(sql) {
        if (context.isQueryInAnalysis(sql)) {
            String explainQuery = context.getPlatform().analyzePrefix() + StringUtils.SPACE + sql;
            ExplainResult explainResult = Try.of(() -> proceed(explainQuery))
                    .map(this::toExplainResult)
                    .getOrElseThrow(() -> new IllegalArgumentException("Bad sql grammar"));
            context.addExplainResult(sql, explainResult);
            log.debug("Select query {} spent {} milliseconds", sql, explainResult.getExecutionTime());
        }
        return proceed(sql);
    }

    private ExplainResult toExplainResult(ResultSet resultSet) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1));
            }
            String result = stringBuilder.substring(1, stringBuilder.length() - 1);
            log.debug(result);
            ExplainResult explainResult = mapper.readValue(result, ExplainResult.class);
            explainResult.setStackTrace(Thread.currentThread().getStackTrace());
            log.debug("stackTrace: {}", Arrays.toString(explainResult.getStackTrace()));
            return explainResult;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not parse result set");
        }
    }
}
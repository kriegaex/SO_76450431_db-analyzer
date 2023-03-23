package edu.ifmo.diploma.proccessor;

import edu.ifmo.diploma.model.ExplainResult;
import org.apache.commons.lang3.StringUtils;

public class ManyJoinsProcessor extends QueryAnalyzerProcessor {
    public static final String PROCESSOR_TYPE = "MANY_JOINS";

    @Override
    public void analyze(String sql, ExplainResult explainResult) {
        if (StringUtils.countMatches(sql, "JOIN") > 3) {
            addAdvice(explainResult);
        }
    }

    @Override
    protected void addAdvice(ExplainResult explainResult) {
        explainResult.addAdvice(String.format("""
                        Advice type - %s <br/>
                        One possible reason could be the large number of connections.
                        We suggest you double-check the structure of your database, it may make sense to take this
                        selection in MATERIALIZED VIEW.
                        """,
                PROCESSOR_TYPE));
    }

    ManyJoinsProcessor() {
    }
}


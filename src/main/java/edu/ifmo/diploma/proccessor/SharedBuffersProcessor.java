package edu.ifmo.diploma.proccessor;

import edu.ifmo.diploma.model.ExplainPlan;
import edu.ifmo.diploma.model.ExplainResult;

public class SharedBuffersProcessor extends QueryAnalyzerProcessor {
    public static final String PROCESSOR_TYPE = "SHARED_BUFFERS";

    @Override
    public void analyze(String sql, ExplainResult explainResult) {
        ExplainPlan plan = explainResult.getPlan();
        if (plan.getSharedReadBlocks() > plan.getSharedHitBlocks()) {
            addAdvice(explainResult);
        }
    }

    @Override
    protected void addAdvice(ExplainResult explainResult) {
        ExplainPlan plan = explainResult.getPlan();
        explainResult.addAdvice(String.format("""
                        Advice type - %s <br/>
                        During query execution, it was found that the number of blocks read from the cache = %s, and
                        number of non-cache blocks read = %s.
                        One possible solution could be to increase the shared_buffers parameter
                        """,
                PROCESSOR_TYPE,
                plan.getSharedHitBlocks(),
                plan.getSharedReadBlocks()));
    }

    SharedBuffersProcessor() {
    }
}

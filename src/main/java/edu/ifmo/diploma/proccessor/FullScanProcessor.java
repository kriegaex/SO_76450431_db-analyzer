package edu.ifmo.diploma.proccessor;

import edu.ifmo.diploma.model.ExplainResult;
import edu.ifmo.diploma.model.enumeration.NodeType;

import java.util.Objects;

public class FullScanProcessor extends QueryAnalyzerProcessor {
    public static final String PROCESSOR_TYPE = "FULL_SCAN";

    @Override
    public void analyze(String sql, ExplainResult explainResult) {
        if (NodeType.SEQ_SCAN == explainResult.getPlan().getNodeType()) {
            addAdvice(explainResult);
        }
    }

    @Override
    protected void addAdvice(ExplainResult explainResult) {
        explainResult.addAdvice(String.format("""
                        Advice type - %s <br/>
                        When executing the query, it was found that the entire table was being fetched.
                        We suggest you optimize the query by setting an index on the fields you mention
                        in WHERE or HAVING clauses, or by reducing the selection with the LIMIT clause.
                        """,
                PROCESSOR_TYPE));
    }

    FullScanProcessor() {
    }
}

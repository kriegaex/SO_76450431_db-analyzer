package edu.ifmo.diploma.proccessor;

import edu.ifmo.diploma.model.ExplainPlan;
import edu.ifmo.diploma.model.ExplainResult;
import edu.ifmo.diploma.model.enumeration.DMLOperation;
import edu.ifmo.diploma.model.enumeration.NodeType;

import java.util.List;

public class DMLFullScanProcessor extends QueryAnalyzerProcessor {
    public static final String PROCESSOR_TYPE = "DML_FULL_SCAN";

    @Override
    public void analyze(String sql, ExplainResult explainResult) {
        if (NodeType.MODIFYTABLE == explainResult.getPlan().getNodeType() &&
                List.of(DMLOperation.UPDATE, DMLOperation.DELETE).contains(explainResult.getPlan().getDmlOperation())
        ) {
            if (explainResult.getPlan().flattened()
                    .map(ExplainPlan::getNodeType)
                    .anyMatch(NodeType.SEQ_SCAN::equals)
            ) {
                addAdvice(explainResult);
            }
        }
    }

    @Override
    protected void addAdvice(ExplainResult explainResult) {
        explainResult.addAdvice(String.format("""
                        Advice type - %s <br/>
                        While modifying table, it was found that the entire table was being fetched.
                        We suggest you optimize the query by setting an index on the fields you mention
                        in WHERE or HAVING clauses.
                        """,
                PROCESSOR_TYPE));
    }
}
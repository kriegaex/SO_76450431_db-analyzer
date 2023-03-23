package edu.ifmo.diploma.proccessor;

import edu.ifmo.diploma.model.ExplainResult;

import java.util.Collection;
import java.util.List;

public abstract class QueryAnalyzerProcessor {

    public abstract void analyze(String sql, ExplainResult explainResult);

    protected abstract void addAdvice(ExplainResult explainResult);

    public static QueryAnalyzerProcessor fullScanProcessor() {
        return new FullScanProcessor();
    }

    public static QueryAnalyzerProcessor dmlFullScanProcessor() {
        return new DMLFullScanProcessor();
    }

    public static QueryAnalyzerProcessor manyJoinsProcessor() {
        return new ManyJoinsProcessor();
    }

    public static QueryAnalyzerProcessor sharedBuffersProcessor() {
        return new SharedBuffersProcessor();
    }

    public static Collection<QueryAnalyzerProcessor> defaultProcessorChain() {
        return List.of(fullScanProcessor(),
                dmlFullScanProcessor(),
                manyJoinsProcessor(),
                sharedBuffersProcessor());
    }
}

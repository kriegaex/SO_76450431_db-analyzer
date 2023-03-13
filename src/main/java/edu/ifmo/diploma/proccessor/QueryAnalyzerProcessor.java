package edu.ifmo.diploma.proccessor;

public abstract class QueryAnalyzerProcessor {

    protected QueryAnalyzerProcessor nextProcessor;
    public QueryAnalyzerProcessor(QueryAnalyzerProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract void analyze(String sql);
}

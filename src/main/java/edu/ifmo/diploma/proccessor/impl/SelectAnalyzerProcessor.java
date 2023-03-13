package edu.ifmo.diploma.proccessor.impl;

import edu.ifmo.diploma.proccessor.QueryAnalyzerProcessor;

public class SelectAnalyzerProcessor extends QueryAnalyzerProcessor {
    public SelectAnalyzerProcessor(QueryAnalyzerProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public void analyze(String sql) {

        nextProcessor.analyze(sql);
    }
}

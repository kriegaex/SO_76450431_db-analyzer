package edu.ifmo.diploma.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbAnalyzerModel {
    private final Map<String, QueryExplainResult> tableQueries = new ConcurrentHashMap<>();

    public void addResult(String sql, ExplainResult result) {
        tableQueries.merge(result.getPlan().getRelationName(),
                new QueryExplainResult().add(sql, result),
                QueryExplainResult::merge);
    }

    public void clear() {
        tableQueries.clear();
    }

    public Map<String, QueryExplainResult> getTableQueries() {
        return Map.copyOf(tableQueries);
    }
    public static class QueryExplainResult {
        private final Map<String, ExplainResult> queryExplainResult = new ConcurrentHashMap<>();

        public QueryExplainResult add(String sql, ExplainResult result) {
            queryExplainResult.put(sql, result);
            return this;
        }

        public static QueryExplainResult merge(QueryExplainResult first, QueryExplainResult second) {
            first.queryExplainResult.putAll(second.queryExplainResult);
            return first;
        }

        public Map<String, ExplainResult> getQueryExplainResult() {
            return Map.copyOf(queryExplainResult);
        }
    }
}

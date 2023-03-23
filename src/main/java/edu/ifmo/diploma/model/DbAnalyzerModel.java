package edu.ifmo.diploma.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbAnalyzerModel {
    private LocalDateTime from;

    private LocalDateTime to;
    private final Map<String, QueryExplainResult> tableQueries = new ConcurrentHashMap<>();
    public DbAnalyzerModel() {}

    public void addResult(String sql, ExplainResult result) {
        tableQueries.merge(result.getPlan().getRelationName(),
                new QueryExplainResult().add(sql, result),
                QueryExplainResult::merge);
    }

    public void clear() {
        tableQueries.clear();
    }

    public boolean isEmpty() {
        return tableQueries.isEmpty();
    }

    public Map<String, QueryExplainResult> getTableQueries() {
        return Map.copyOf(tableQueries);
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
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

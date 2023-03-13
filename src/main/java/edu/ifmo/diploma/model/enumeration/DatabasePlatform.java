package edu.ifmo.diploma.model.enumeration;

public enum DatabasePlatform {
    ORACLE {
        @Override
        public String analyzePrefix() {
            return "EXPLAIN ANALYZE";
        }
    },
    POSTGRESQL {
        @Override
        public String analyzePrefix() {
            return "EXPLAIN (FORMAT JSON, ANALYZE, WAL TRUE)";
        }
    },
    MYSQL {
        @Override
        public String analyzePrefix() {
            return "EXPLAIN ANALYZE";
        }
    },
    ;

    public abstract String analyzePrefix();
}

package edu.ifmo.diploma.model.enumeration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifmo.diploma.model.ExplainPlan;
import edu.ifmo.diploma.model.ExplainResult;
import edu.ifmo.diploma.model.mysql.MySQLMapper;
import edu.ifmo.diploma.model.mysql.MySQLPlan;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public enum DatabasePlatform {
    POSTGRESQL {
        @Override
        String analyzeModifyPrefix() {
            return "EXPLAIN (FORMAT JSON)";
        }

        @Override
        String analyzeSelectPrefix() {
            return "EXPLAIN (ANALYZE, BUFFERS, FORMAT JSON)";
        }

        @Override
        public ExplainResult toExplainResult(String result) throws JsonProcessingException {
            log.debug("POSTGRESQL_PLAN {}", result);
            return OBJECT_MAPPER.readValue(result.substring(1, result.length() - 1), ExplainResult.class);
        }
    },
    MYSQL {
        private final MySQLMapper mySQLMapper = new MySQLMapper();

        @Override
        String analyzeModifyPrefix() {
            return analyzeSelectPrefix();
        }

        @Override
        String analyzeSelectPrefix() {
            return "EXPLAIN FORMAT=JSON";
        }

        @Override
        public ExplainResult toExplainResult(String result) throws JsonProcessingException {
            MySQLPlan mySQLPlan = OBJECT_MAPPER.readValue(result, MySQLPlan.class);
            log.debug("MY_SQL_PLAN {}", result);
            return mySQLMapper.convert(mySQLPlan);
        }
    },
    ORACLE {
        @Override
        String analyzeModifyPrefix() {
            return analyzeSelectPrefix();
        }

        @Override
        String analyzeSelectPrefix() {
            return StringUtils.EMPTY;
        }

        @Override
        public ExplainResult toExplainResult(String result) throws IOException {
            ExplainPlan explainPlan = new ExplainPlan();
            explainPlan.setRelationName("NAN");
            explainPlan.setNodeType(NodeType.NAN);
            ExplainResult explainResult = new ExplainResult();
            explainResult.setPlan(explainPlan);
            return explainResult;
        }
    };

    abstract String analyzeModifyPrefix();

    abstract String analyzeSelectPrefix();

    public String withAnalyzePrefix(String sql) {
        return sql.trim().toUpperCase().startsWith("SELECT")
                ? analyzeSelectPrefix() + StringUtils.SPACE + sql
                : analyzeModifyPrefix() + StringUtils.SPACE + sql;
    }

    public abstract ExplainResult toExplainResult(String result) throws IOException;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(DatabasePlatform.class);
}

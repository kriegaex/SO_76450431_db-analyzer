package edu.ifmo.diploma.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MySQLPlan {
    @JsonProperty("query_block")
    private QueryBlock queryBlock;

    public QueryBlock getQueryBlock() {
        return queryBlock;
    }

    public void setQueryBlock(QueryBlock queryBlock) {
        this.queryBlock = queryBlock;
    }

    @Override
    public String toString() {
        return "MySQLPlan{" +
                "queryBlock=" + queryBlock +
                '}';
    }
}

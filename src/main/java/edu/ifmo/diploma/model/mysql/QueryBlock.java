package edu.ifmo.diploma.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryBlock {
    @JsonProperty("table")
    private Table table;
    @JsonProperty("cost_info")
    private CostInfo costInfo;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public CostInfo getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(CostInfo costInfo) {
        this.costInfo = costInfo;
    }

    @Override
    public String toString() {
        return "QueryBlock{" +
                "table=" + table +
                '}';
    }
}

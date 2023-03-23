package edu.ifmo.diploma.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CostInfo {
    @JsonProperty("query_cost")
    private double queryCost;

    public double getQueryCost() {
        return queryCost;
    }

    public void setQueryCost(double queryCost) {
        this.queryCost = queryCost;
    }
}

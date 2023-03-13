package edu.ifmo.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExplainResult {
    @JsonProperty("Plan")
    private ExplainPlan plan;

    @JsonProperty("Planning Time")
    private double planningTime;
    @JsonProperty("Execution Time")
    private Double executionTime;

    private StackTraceElement[] stackTrace;

    public ExplainResult() {
    }

    public double getPlanningTime() {
        return planningTime;
    }
    public void setPlanningTime(double planningTime) {
        this.planningTime = planningTime;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }

    public ExplainPlan getPlan() {
        return plan;
    }

    public void setPlan(ExplainPlan plan) {
        this.plan = plan;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }
}

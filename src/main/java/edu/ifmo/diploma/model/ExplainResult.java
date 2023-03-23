package edu.ifmo.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExplainResult {
    @JsonProperty("Plan")
    private ExplainPlan plan;

    @JsonProperty("Execution Time")
    private double executionTime;
    private Long criticalTime;

    private final Collection<String> advice = new ArrayList<>();

    private StackTraceElement[] baseStackTrace;

    private StackTraceElement[] moduleStackTrace;

    public ExplainResult() {
    }

    public Collection<String> getAdvice() {
        return advice;
    }

    public void addAdvice(String advice) {
        if (advice == null) return;
        this.advice.add(advice);
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public Long getCriticalTime() {
        return criticalTime;
    }

    public void setCriticalTime(Long criticalTime) {
        this.criticalTime = criticalTime;
    }

    public ExplainPlan getPlan() {
        return plan;
    }

    public void setPlan(ExplainPlan plan) {
        this.plan = plan;
    }

    public ExplainResult setBaseStackTrace(StackTraceElement[] stackTrace) {
        this.baseStackTrace = Arrays.copyOf(stackTrace, stackTrace.length);
        return this;
    }

    public ExplainResult setModuleStackTrace(StackTraceElement[] stackTrace) {
        this.moduleStackTrace = Arrays.copyOf(stackTrace, stackTrace.length);
        return this;
    }

    public StackTraceElement[] getBaseStackTrace() {
        return Arrays.copyOf(baseStackTrace, baseStackTrace.length);
    }

    public StackTraceElement[] getModuleStackTrace() {
        return Arrays.copyOf(moduleStackTrace, moduleStackTrace.length);
    }
}

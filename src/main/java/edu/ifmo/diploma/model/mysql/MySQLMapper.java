package edu.ifmo.diploma.model.mysql;

import edu.ifmo.diploma.model.ExplainPlan;
import edu.ifmo.diploma.model.ExplainResult;

import java.math.BigDecimal;
import java.util.Optional;

public class MySQLMapper {
    public ExplainResult convert(MySQLPlan mySQLPlan) {
        ExplainPlan explainPlan = new ExplainPlan();
        explainPlan.setRelationName(valueOrNull(mySQLPlan.getQueryBlock().getTable().getTableName()));
        explainPlan.setNodeType(valueOrNull(mySQLPlan.getQueryBlock().getTable().getAccessType().toNodeType()));
        explainPlan.setTotalCost(BigDecimal.valueOf(valueOrNull(mySQLPlan.getQueryBlock().getCostInfo().getQueryCost())));
        ExplainResult explainResult = new ExplainResult();
        explainResult.setPlan(explainPlan);
        explainResult.setExecutionTime(mySQLPlan.getQueryBlock().getCostInfo().getQueryCost() * 100);
        return explainResult;
    }

    private <T> T valueOrNull(T t) {
        return Optional.of(t)
                .orElse(null);
    }
}

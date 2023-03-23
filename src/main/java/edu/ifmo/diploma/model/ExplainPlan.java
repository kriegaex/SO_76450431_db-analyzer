package edu.ifmo.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import edu.ifmo.diploma.model.enumeration.DMLOperation;
import edu.ifmo.diploma.model.enumeration.NodeType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExplainPlan {
    private NodeType nodeType;

    private DMLOperation dmlOperation;

    @JsonProperty("Relation Name")
    private String relationName;
    @JsonProperty("Startup Cost")
    private BigDecimal startupCost;
    @JsonProperty("Total Cost")
    private BigDecimal totalCost;
    @JsonProperty("Plan Rows")
    private Long planRows;
    @JsonProperty("Plan Width")
    private Long planWidth;

    @JsonProperty("Shared Hit Blocks")
    private long sharedHitBlocks;

    @JsonProperty("Shared Read Blocks")
    private long sharedReadBlocks;
    @JsonProperty("Plans")
    private Collection<ExplainPlan> plans;

    public ExplainPlan() {
    }

    public Stream<ExplainPlan> flattened() {
        return Stream.concat(
                Stream.of(this),
                plans.stream()
        );
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    @JsonSetter("Node Type")
    public void setNodeType(String nodeType) {
        this.nodeType = NodeType.valueOf(nodeType
                .toUpperCase(Locale.ROOT)
                .replace(StringUtils.SPACE, "_")
        );
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @JsonSetter("Operation")
    public void setDmlOperation(String operation) {
        this.dmlOperation = DMLOperation.valueOf(operation.toUpperCase(Locale.ROOT));
    }

    public DMLOperation getDmlOperation() {
        return dmlOperation;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public void replaceIfNodeTypeOuter() {
        if (List.of(NodeType.LIMIT,
                NodeType.GATHER,
                NodeType.GATHER_MERGE,
                NodeType.UNIQUIE,
                NodeType.SORT,
                NodeType.AGGREGATE,
                NodeType.NESTED_LOOP).contains(nodeType)) {
            this.relationName = flattened()
                    .filter(explainPlan -> explainPlan.relationName != null)
                    .findFirst()
                    .map(explainPlan -> explainPlan.relationName)
                    .orElseThrow();
        }
    }

    public BigDecimal getStartupCost() {
        return startupCost;
    }

    public void setStartupCost(BigDecimal startupCost) {
        this.startupCost = startupCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getPlanRows() {
        return planRows;
    }

    public void setPlanRows(Long planRows) {
        this.planRows = planRows;
    }

    public Long getPlanWidth() {
        return planWidth;
    }

    public void setPlanWidth(Long planWidth) {
        this.planWidth = planWidth;
    }

    public long getSharedHitBlocks() {
        return sharedHitBlocks;
    }

    public void setSharedHitBlocks(long sharedHitBlocks) {
        this.sharedHitBlocks = sharedHitBlocks;
    }

    public long getSharedReadBlocks() {
        return sharedReadBlocks;
    }

    public void setSharedReadBlocks(long sharedReadBlocks) {
        this.sharedReadBlocks = sharedReadBlocks;
    }
}

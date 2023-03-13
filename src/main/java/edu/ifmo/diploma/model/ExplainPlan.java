package edu.ifmo.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import edu.ifmo.diploma.model.enumeration.NodeType;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExplainPlan {
    private NodeType nodeType;

    @JsonProperty("Relation Name")
    private String relationName;

    public ExplainPlan() {
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    @JsonSetter("Node Type")
    public void setNodeType(String nodeType) {
        this.nodeType = NodeType.valueOf(nodeType.toUpperCase(Locale.ROOT).replace(StringUtils.SPACE, "_"));
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }
}

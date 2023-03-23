package edu.ifmo.diploma.model.mysql.enumeration;

import edu.ifmo.diploma.model.enumeration.NodeType;

public enum AccessType {
    ALL {
        @Override
        public NodeType toNodeType() {
            return NodeType.SEQ_SCAN;
        }
    },
    ;

    public abstract NodeType toNodeType();
}

package edu.ifmo.diploma.model.mysql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ifmo.diploma.model.mysql.enumeration.AccessType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {
    @JsonProperty("table_name")
    private String tableName;

    @JsonProperty("access_type")
    private AccessType accessType;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", accessType=" + accessType +
                '}';
    }
}

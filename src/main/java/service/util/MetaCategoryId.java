package service.util;

import service.model.SubEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Levon on 20-Dec-17.
 */
public class MetaCategoryId {

    private String tableName;
    private Map<String, MetaCategoryType> columns;
    private Map<String, MetaCategoryId> subEntities;

    public MetaCategoryId(String tableName, Map<String, MetaCategoryType> columns) {
        this.tableName = tableName;
        this.columns  = columns;
        this.subEntities = new HashMap<>();
    }

    public MetaCategoryId(String tableName, Map<String, MetaCategoryType> columns, Map<String, MetaCategoryId> subEntities) {
        this.tableName = tableName;
        this.columns = columns;
        this.subEntities = subEntities;
    }

    public MetaCategoryId(String tableName) {
        this(tableName, new HashMap<>());
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, MetaCategoryType> getColumns() {
        return columns;
    }

    public Map<String, MetaCategoryId> getSubEntities() {
        return subEntities;
    }
}

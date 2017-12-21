package service.util;

import service.model.SubEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Erik on 20-Dec-17.
 */
public class MetaCategoryId {

    private String tableName;
    private Map<String, MetaCategoryType> columns;

    public MetaCategoryId(String tableName, Map<String, MetaCategoryType> columns) {
        this.tableName = tableName;
        this.columns  = columns;
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
}

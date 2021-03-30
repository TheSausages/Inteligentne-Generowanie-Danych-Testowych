package TableMapping;

import DatabaseConnection.DatabaseDrivers;

import java.util.List;

public class TableMappingClass {
    private String tableName;
    private DatabaseDrivers tableType;
    private List<ColumnMappingClass> columns;

    public TableMappingClass() {}

    public TableMappingClass(String tableName, DatabaseDrivers tableType) {
        this.tableName = tableName;
        this.tableType = tableType;
    }

    public void setColumns(List<ColumnMappingClass> columns) {
        this.columns = columns;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableType(DatabaseDrivers tableType) {
        this.tableType = tableType;
    }

    public DatabaseDrivers getTableType() {
        return tableType;
    }

    public List<ColumnMappingClass> getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }
}

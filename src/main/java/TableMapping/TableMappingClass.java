package TableMapping;

import DatabaseConnection.SupportedDatabases;

import java.util.ArrayList;
import java.util.List;

public class TableMappingClass {
    private String tableName;
    private SupportedDatabases tableType;
    private List<ColumnMappingClass> columns;

    public TableMappingClass() {
        columns = new ArrayList<>();
    }

    public TableMappingClass(String tableName, SupportedDatabases tableType) {
        this.tableName = tableName;
        this.tableType = tableType;
        columns = new ArrayList<>();
    }

    public void writeTableInfo() {
        System.out.println("-------------------------------------------");

        System.out.println("Table Name:" + tableName);
        System.out.println("Table Type:" + tableType);

        columns.forEach(ColumnMappingClass::writeColumnInfo);
    }

    public void addColumn(ColumnMappingClass columnMappingClass) {
        columns.add(columnMappingClass);
    }

    public void setColumns(List<ColumnMappingClass> columns) {
        this.columns = columns;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableType(SupportedDatabases tableType) {
        this.tableType = tableType;
    }

    public SupportedDatabases getTableType() {
        return tableType;
    }

    public List<ColumnMappingClass> getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }
}

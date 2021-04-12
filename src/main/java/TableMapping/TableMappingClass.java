package TableMapping;

import DatabaseConnection.SupportedDatabases;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Setter
@Getter
public class TableMappingClass {
    private String tableName;
    private SupportedDatabases tableType;
    private List<ColumnMappingClass> columns;

    public static TableBuilder builder() {
        return new TableBuilder();
    }

    public static final class TableBuilder {
        private String tableName;
        private SupportedDatabases tableType;
        private List<ColumnMappingClass> columns = new ArrayList<>();

        public TableBuilder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public TableBuilder tableType(SupportedDatabases supportedDatabases) {
            this.tableType = supportedDatabases;
            return this;
        }

        public TableBuilder addColumn(ColumnMappingClass columnMappingClass) {
            columns.add(columnMappingClass);
            return this;
        }

        public Stream<ColumnMappingClass> streamColumns() {
            return this.columns.stream();
        }

        public TableMappingClass build() {
            if (tableName.isEmpty()) {
                throw new IllegalStateException("Table name cannot be empty!");
            }

            if (tableType == null) {
                throw new IllegalStateException("A table must have a table type!");
            }

            TableMappingClass tableMappingClass = new TableMappingClass();
            tableMappingClass.tableName = this.tableName;
            tableMappingClass.tableType = this.tableType;
            tableMappingClass.columns = this.columns;

            return tableMappingClass;
        }
    }

    public void writeTableInfo() {
        System.out.println("-------------------------------------------");

        System.out.println("Table Name:" + tableName);
        System.out.println("Table Type:" + tableType);

        columns.forEach(ColumnMappingClass::writeColumnInfo);
    }
}

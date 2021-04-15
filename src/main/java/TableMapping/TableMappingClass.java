package TableMapping;

import DatabaseConnection.SupportedDatabases;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Setter
@Getter
public class TableMappingClass {
    private String tableName;
    private SupportedDatabases tableType;
    private List<ColumnMappingClass> columns;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TableMappingClass that = (TableMappingClass) o;

        return new EqualsBuilder().append(tableName, that.tableName).append(tableType, that.tableType).append(columns, that.columns).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tableName).append(tableType).append(columns).toHashCode();
    }

    public static TableBuilder builder() {
        return new TableBuilder();
    }

    public static final class TableBuilder {
        private String tableName;
        private SupportedDatabases tableType;
        private final List<ColumnMappingClass> columns = new ArrayList<>();

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
            if (StringUtils.isEmpty(tableName)) {
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

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

/**
 * Class that represents a table withing the database. Contains information about it and a list of Columns in form of {@link TableMappingClass}
 */
@Setter
@Getter
public class TableMappingClass {
    /**
     * Name of the table
     */
    private String tableName;

    /**
     * Type of table - the type of database it is from. See {@link SupportedDatabases}
     */
    private SupportedDatabases tableType;

    /**
     * Number of generation for a given table. Can be changed withing the .json file containing mapping information
     */
    private int numberOfGenerations = 5;

    /**
     * Number of foreign keys inside the database
     */
    private long numberOfForeignKeys = 0;

    /**
     * List of {@link ColumnMappingClass} that are inside a given table.
     */
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

    /**
     * Returns the builder instance for the Class
     * @return Builder for the Class
     */
    public static TableBuilder builder() {
        return new TableBuilder();
    }

    /**
     * The builder pattern class for this Class. Information needed to create the Class are: the name of the table ({@link TableMappingClass#tableName}) and the type of the table ({@link TableMappingClass#tableType})
     */
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
            tableMappingClass.numberOfForeignKeys = this.columns.stream().filter(column -> column.getForeignKey().isForeignKey()).count();
            tableMappingClass.columns = this.columns;

            return tableMappingClass;
        }
    }

    /**
     * Debugging method that writes information about the table to the console
     */
    public void writeTableInfo() {
        System.out.println("-------------------------------------------");

        System.out.println("Table Name:" + tableName);
        System.out.println("Table Type:" + tableType);


        columns.forEach(ColumnMappingClass::writeColumnInfo);
    }
}

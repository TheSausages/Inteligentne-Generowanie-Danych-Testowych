package TableMapping;

import DataCreation.ColumnNameMapping;
import DataCreation.GenerateInterface;
import TableMapping.Fields.Field;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a column from a table within the database
 */
@Setter
@Getter
public class ColumnMappingClass {
    /**
     * Name of the column
     */
    private String name;

    /**
     * What field type the column is. See {@link Field}
     */
    private Field field;

    /**
     * If the Column can take a null value
     */
    private boolean nullable;

    /**
     * The Default value of the column
     */
    private String defaultValue;

    /**
     * Does the column AutoIncrement
     */
    private boolean isAutoIncrement;

    /**
     * Does the column have a unique constraint
     */
    private boolean isUnique;

    /**
     * Is the column a primary key
     */
    private boolean isPrimaryKey;

    /**
     * Is the column a foreign key. If it is, what is the column referencing. See {@link ForeignKeyMapping}
     */
    private ForeignKeyMapping foreignKey;

    /**
     * The chosen generation class for the column. See {@link GenerateInterface}
     */
    private GenerateInterface chosenGenerationClass = null;

    /**
     * Returns the builder instance for the Class
     * @return Builder for the Class
     */
    public static ColumnBuilder builder() {
        return new ColumnBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ColumnMappingClass that = (ColumnMappingClass) o;

        return new EqualsBuilder().append(nullable, that.nullable).append(isAutoIncrement, that.isAutoIncrement).append(isUnique, that.isUnique).append(isPrimaryKey, that.isPrimaryKey).append(name, that.name).append(field, that.field).append(defaultValue, that.defaultValue).append(foreignKey, that.foreignKey).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(field).append(nullable).append(defaultValue).append(isAutoIncrement).append(isUnique).append(isPrimaryKey).append(foreignKey).toHashCode();
    }

    /**
     * The builder pattern class for this Class. Information needed to create the Class are: the name of the column ({@link ColumnMappingClass#name}) and the type of field of the class ({@link ColumnMappingClass#field})
     */
    public static final class ColumnBuilder {
        private String name;
        private Field field;
        private boolean nullable = true;
        private String defaultValue = null;
        private boolean isAutoIncrement = false;
        private boolean isUnique = false;
        private boolean isPrimaryKey = false;
        private ForeignKeyMapping foreignKey = null;

        public ColumnBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ColumnBuilder field(Field field) {
            this.field = field;
            return this;
        }

        public ColumnBuilder notNullable() {
            this.nullable = false;
            return this;
        }

        public ColumnBuilder defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ColumnBuilder isAutoIncrement() {
            this.isAutoIncrement = true;
            return this;
        }

        public ColumnBuilder isUnique() {
            this.isUnique = true;
            return this;
        }

        public ColumnBuilder isPrimaryKey() {
            this.isPrimaryKey = true;
            return this;
        }

        public ColumnBuilder foreignKey(ForeignKeyMapping foreignKey) {
            this.foreignKey = foreignKey;
            return this;
        }

        public ColumnMappingClass build() {
            if (StringUtils.isEmpty(name)) {
                throw new IllegalStateException("Column name cannot be empty!");
            }

            if (field == null || field.isEmpty()) {
                throw new IllegalStateException("Column field type cannot be empty!");
            }


            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            columnMappingClass.name = this.name;
            columnMappingClass.field = this.field;
            columnMappingClass.defaultValue = this.defaultValue;
            columnMappingClass.isUnique = this.isUnique;
            columnMappingClass.nullable = this.nullable;
            columnMappingClass.isAutoIncrement = this.isAutoIncrement;
            columnMappingClass.isPrimaryKey = this.isPrimaryKey;

            if (this.foreignKey == null) {
                columnMappingClass.foreignKey = new ForeignKeyMapping(false);
            }

            columnMappingClass.chosenGenerationClass = (ColumnNameMapping.getGenerator(columnMappingClass));

            return columnMappingClass;
        }
    }

    /**
     * Debugging method that writes information about the table to the console
     */
    public void writeColumnInfo() {
        System.out.println();
        System.out.println("Column Name:" + name);
        System.out.println(this.field.writeFieldInfo());
        System.out.println("Is Nullable:" + nullable);
        System.out.println("Default value:" + (defaultValue == null ? "Not Selected" : defaultValue));
        System.out.println("Does the column Auto Increment:" + isAutoIncrement);
        System.out.println("Does the column have to be unique:" + isUnique);
        System.out.println("Is the Column a primary key:" + isPrimaryKey);
        System.out.println("Is the Column a foreign key:" + (foreignKey.isForeignKey() ? "true, for the table '" + foreignKey.getForeignKeyTable()+ "' and column: " + foreignKey.getForeignKeyColumn() : "false"));
        System.out.println(isAutoIncrement ? "Is AutoIncrement, no generation class selected" : "Chosen generation Class:" + chosenGenerationClass.getClass().getSimpleName());
        System.out.println();
    }
}

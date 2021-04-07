package TableMapping;

import lombok.Getter;
import lombok.Setter;

/**
 * This class maps a given column from any database. The exact way the mapping depends on the database
 */

@Setter
@Getter
public class ColumnMappingClass {
    private String name;
    private Field field;
    private boolean nullable;
    private String defaultValue;
    private boolean isAutoIncrement;
    private boolean isUnique;
    private boolean isPrimaryKey;
    private ForeignKeyMapping foreignKey;

    public ColumnMappingClass() {
    }

    public static ColumnBuilder builder() {
        return new ColumnBuilder();
    }

    public static final class ColumnBuilder {
        private String name;
        private Field field;
        private boolean nullable = true;
        private String defaultValue = null;
        private boolean isAutoIncrement = false;
        private boolean isUnique = false;

        public ColumnBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ColumnBuilder field(Field field) {
            this.field = field;
            return this;
        }

        public ColumnBuilder nullable() {
            this.nullable = true;
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

        public ColumnMappingClass build() {
            if (name.isEmpty()) {
                throw new IllegalStateException("Column name cannot be empty!");
            }

            if (field.isEmpty()) {
                throw new IllegalStateException("Column type cannot be empty!");
            }


            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            columnMappingClass.name = this.name;
            columnMappingClass.field = this.field;
            columnMappingClass.defaultValue = this.defaultValue;
            columnMappingClass.isUnique = this.isUnique;
            columnMappingClass.nullable = this.nullable;
            columnMappingClass.isAutoIncrement = this.isAutoIncrement;
            columnMappingClass.isPrimaryKey = false;
            columnMappingClass.foreignKey = new ForeignKeyMapping(false);

            return columnMappingClass;
        }
    }

    public void writeColumnInfo() {
        System.out.println();
        System.out.println("Column Name:" + name);
        System.out.println("Column Type:" + field.getSqlType() + "(" + field.getMaxSize() + "," + field.getPrecision() + "), is it unsinged:" + field.isUnsigned());
        System.out.println("Is Nullable:" + nullable);
        System.out.println("Default value:" + (defaultValue == null ? "Not Selected" : defaultValue));
        System.out.println("Does the column Auto Increment:" + isAutoIncrement);
        System.out.println("Does the column have to be unique:" + isUnique);
        System.out.println("Is the Column a primary key:" + isPrimaryKey);
        System.out.println("Is the Column a foreign key:" + (foreignKey.isForeignKey() ? "true, for the table '" + foreignKey.getForeignKeyTable()+ "' and column: " + foreignKey.getForeignKeyColumn() : "false"));
        System.out.println();
    }
}

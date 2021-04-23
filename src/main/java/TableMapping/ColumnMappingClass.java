package TableMapping;

import DataCreation.ColumnNameMapping;
import DataCreation.generateInterface;
import TableMapping.Fields.Field;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

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

            return columnMappingClass;
        }
    }

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
        System.out.println();
    }

    public List<String> getColumnStructureIntoList() {
        List<String> info = new ArrayList<>();

        info.add("Column Name:" + name);
        info.add(this.field.writeFieldInfo());
        info.add("Is Nullable:" + nullable);
        info.add("Default value:" + (defaultValue == null ? "Not Selected" : defaultValue));
        info.add("Auto Increment:" + isAutoIncrement);
        info.add("Is Unique:" + isUnique);
        info.add("Is a Primary Key:" + isPrimaryKey);
        info.add("Is a Foreign Key:" + (foreignKey.isForeignKey() ? "true, for table '" + foreignKey.getForeignKeyTable()+ "' and column: " + foreignKey.getForeignKeyColumn() : "false"));
        info.add("Selected generation class:" + ColumnNameMapping.getGeneratorName(this));

        return info;
    }
}

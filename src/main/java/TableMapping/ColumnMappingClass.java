package TableMapping;

public class ColumnMappingClass {
    private String name;

    private String type;

    private String nullable;

    private String defaultValue;

    private boolean isAutoIncrement;

    private boolean isUnique;

    private boolean isPrimaryKey;

    private ForeignKeyMapping isForeignKey;

    public ColumnMappingClass() {}

    public ColumnMappingClass(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setIsForeignKey(ForeignKeyMapping isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getName() {
        return name;
    }

    public ForeignKeyMapping getIsForeignKey() {
        return isForeignKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getNullable() {
        return nullable;
    }

    public String getType() {
        return type;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isUnique() {
        return isUnique;
    }
}

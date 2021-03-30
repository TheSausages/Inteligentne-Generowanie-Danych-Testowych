package TableMapping;

import java.util.regex.Pattern;

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
        nullable = true;
        defaultValue = null;
        isAutoIncrement = false;
        isUnique = false;
        isPrimaryKey = false;
        foreignKey = new ForeignKeyMapping(false);
    }

    public ColumnMappingClass(String name) {
        this.name = name;
    }

    public void mapColumnsMySQL(String[] words) {
        this.name = words[2].replaceAll("`","");
        this.findField(words[3]);

        for (int i = 4; i < words.length; i++) {
            if (words[i].contains("NOT-NULL")) {
                nullable = false;
                continue;
            }

            if (words[i].matches("DEFAULT-.+")) {
                defaultValue = words[i].substring(words[i].indexOf("-") + 1);
                continue;
            }

            if (words[i].contains("AUTO_INCREMENT")) {
                isAutoIncrement = true;
                continue;
            }

            if (words[i].contains("UNIQUE")) {
                isUnique = true;
            }
        }

        /*System.out.println(name);
        System.out.println(field.getSqlType() + "(" + field.getMaxSize() + "," + field.getPrecision() + ")");
        System.out.println(nullable);
        System.out.println(defaultValue == null ? "null" : defaultValue);
        System.out.println(isAutoIncrement);
        System.out.println(isUnique);
        System.out.println(isPrimaryKey);
        System.out.println(isForeignKey);
        System.out.println();*/
    }

    public void writeColumnInfo() {
        System.out.println();
        System.out.println("Column Name:" + name);
        System.out.println("Column Type:" + field.getSqlType() + "(" + field.getMaxSize() + "," + field.getPrecision() + "), is it unsinged:" + field.isUnsigned());
        System.out.println("Is Nullable:" + nullable);
        System.out.println("Default value of the column is:" + (defaultValue == null ? "null" : defaultValue));
        System.out.println("Does the column Auto Increment:" + isAutoIncrement);
        System.out.println("Does the column have to be unique:" + isUnique);
        System.out.println("Is the Column a primary key:" + isPrimaryKey);
        System.out.println("Is the Column a foreign key:" + (foreignKey.isForeignKey() ? foreignKey.isForeignKey() + ", for the table " + foreignKey.getForeignKeyTable() + " column:" + foreignKey.getForeignKeyColumn() : foreignKey.isForeignKey()));
        System.out.println();
    }

    private void findField(String word) {
        field = new Field();

        if (word.contains("unsigned")) {
            field.setUnsigned(true);
            word = word.replace("-unsigned", "");
        }

        if (Pattern.compile(".+\\([,\\d]+\\)").matcher(word).matches()) {
            field.setSqlType(word.substring(0, word.indexOf("(")));
            field.setMaxSize(Integer.parseInt(word.substring(word.indexOf("(") + 1, word.indexOf(")"))));
        } else {
            field.setSqlType(word);
            field.setMaxSize(0);
        }
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

    public void setForeignKey(ForeignKeyMapping foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public String getName() {
        return name;
    }

    public ForeignKeyMapping getForeignKey() {
        return foreignKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Field getField() {
        return field;
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

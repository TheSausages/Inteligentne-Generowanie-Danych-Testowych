package TableMapping;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
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
        this.name = words[2].substring(1, words[2].length() - 1);
        this.findField(words[3]);

        for (int i = 4; i < words.length; i++) {

            if (words[i].charAt(words[i].length() - 1) == ',') {
                words[i] = StringUtils.chop(words[i]);
            }

            switch (words[i]) {
                case "NOT-NULL" -> nullable = false;
                case "UNIQUE" -> isUnique = true;
                case "AUTO_INCREMENT" -> isAutoIncrement = true;
                default -> {
                    if (words[i].matches("DEFAULT-.+")) {
                        defaultValue = words[i].substring(words[i].indexOf("-") + 1);
                    }
                }
            }
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
        System.out.println("Is the Column a foreign key:" + (foreignKey.isForeignKey() ? foreignKey.isForeignKey() + ", for the table " + foreignKey.getForeignKeyTable() + " column:" + foreignKey.getForeignKeyColumn() : foreignKey.isForeignKey()));
        System.out.println();
    }

    private void findField(String word) {
        field = new Field();

        if (word.contains("unsigned")) {
            field.setUnsigned(true);
            word = word.replace("-unsigned", "");
        }

        String[] elements = word.split("[,()]");
        switch (elements.length) {
            case 2 -> {
                field.setSqlType(elements[0]);
                field.setMaxSize(Integer.parseInt(elements[1]));
            }

            case 1 -> {
                field.setSqlType(elements[0]);
            }

            case 3 -> {
                field.setSqlType(elements[0]);
                field.setMaxSize(Integer.parseInt(elements[1]));
                field.setPrecision(Integer.parseInt(elements[2]));
            }
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

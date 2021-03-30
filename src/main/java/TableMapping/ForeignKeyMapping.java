package TableMapping;

public class ForeignKeyMapping {
    private boolean isForeignKey;
    private String foreignKeyTable;
    private String foreignKeyColumn;

    public ForeignKeyMapping() {}

    public ForeignKeyMapping(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public void foreignKeyInfo(String foreignKeyColumn, String foreignKeyTable) {
        this.isForeignKey = true;
        this.foreignKeyColumn = foreignKeyColumn;
        this.foreignKeyTable = foreignKeyTable;
    }

    public void setForeignKey(boolean foreignKey) {
        isForeignKey = foreignKey;
    }

    public void setForeignKeyColumn(String foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
    }

    public void setForeignKeyTable(String foreignKeyTable) {
        this.foreignKeyTable = foreignKeyTable;
    }

    public String getForeignKeyColumn() {
        return foreignKeyColumn;
    }

    public String getForeignKeyTable() {
        return foreignKeyTable;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }
}

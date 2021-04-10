package TableMapping;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
}

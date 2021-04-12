package TableMapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ForeignKeyMapping {
    private boolean isForeignKey;
    private String foreignKeyTable;
    private String foreignKeyColumn;

    public ForeignKeyMapping(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public void foreignKeyInfo(String foreignKeyColumn, String foreignKeyTable) {
        this.isForeignKey = true;
        this.foreignKeyColumn = foreignKeyColumn;
        this.foreignKeyTable = foreignKeyTable;
    }
}

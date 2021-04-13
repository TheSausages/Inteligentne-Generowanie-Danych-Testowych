package TableMapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ForeignKeyMapping that = (ForeignKeyMapping) o;

        return new EqualsBuilder().append(isForeignKey, that.isForeignKey).append(foreignKeyTable, that.foreignKeyTable).append(foreignKeyColumn, that.foreignKeyColumn).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(isForeignKey).append(foreignKeyTable).append(foreignKeyColumn).toHashCode();
    }
}

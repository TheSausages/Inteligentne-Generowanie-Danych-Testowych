package TableMapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A class that represents a foreign key within a database
 */
@Setter
@Getter
@NoArgsConstructor
public class ForeignKeyMapping {
    /**
     * A {@link Boolean} value representing if a given column is a foreign key
     */
    private boolean isForeignKey;

    /**
     * Name of the table that the foreign key references
     */
    private String foreignKeyTable;

    /**
     * Name of the column that the foreign key references
     */
    private String foreignKeyColumn;

    /**
     * Class Constructor
     * @param isForeignKey Parameter that says if a given column is a foreign key
     */
    public ForeignKeyMapping(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    /**
     * Method used if a class is a foreign key. Automatically changes the {@link ForeignKeyMapping#isForeignKey} to true
     * @param foreignKeyColumn Name of the column that the foreign key references
     * @param foreignKeyTable Name of the table that the foreign key references
     */
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

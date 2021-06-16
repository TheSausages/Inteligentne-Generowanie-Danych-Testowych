package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class that represents a SQL field that holds a large binary object
 */
@NoArgsConstructor
@Setter
@Getter
public class BlobField extends Field{
    private long maxSize; //byte

    /**
     *  {@inheritDoc}
     */
    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        this.setSqlType(info[0]);

        switch (info[0]) {
            case "Tinyblob" -> this.maxSize = 255L;
            case "Blob", "Text" -> this.maxSize = 65535L;
            case "Mediumblob", "Mediumtext" -> this.maxSize  = 16777215L;
            case "Longblob", "Longtext" -> this.maxSize  = 4294967295L;
            case "Tinytext" -> this.maxSize  = 256L;
            case "BinaryServer" -> this.maxSize = (info.length < 2 || StringUtils.isEmpty(info[1]) ? 8000L : Long.parseLong(info[1]));
            case "VarbinaryServer" -> this.maxSize = (info.length < 2 || StringUtils.isEmpty(info[1]) ? 8000L : (info[1].equals("MAX") ? 1073741824L : Long.parseLong(info[1])));
            case "Ncblob", "BlobOracle", "Cblob" -> this.maxSize = 12800000000000000L;
            case "Bfile" -> this.maxSize = 2L; //read only
            default -> this.maxSize  = 1L;
        }
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public String writeFieldInfo() {
        return "Column type:" + this.getSqlType() + "(" + this.maxSize + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BlobField blobField = (BlobField) o;

        return new EqualsBuilder().append(maxSize, blobField.maxSize).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(maxSize).toHashCode();
    }
}

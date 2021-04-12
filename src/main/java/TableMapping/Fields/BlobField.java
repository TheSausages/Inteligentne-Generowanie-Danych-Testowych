package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Setter
@Getter
public class BlobField extends Field{
    private long maxSize; //byte

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);

        switch (info[0]) {
            case "Tinyblob" -> this.maxSize = 255L;
            case "Blob", "Text" -> this.maxSize = 65535L;
            case "Mediumblob", "Mediumtext" -> this.maxSize  = 16777215L;
            case "Longblob", "Longtext" -> this.maxSize  = 4294967295L;
            case "Tinytext" -> this.maxSize  = 256L;
            case "BinaryServer" -> this.maxSize = (StringUtils.isEmpty(info[1]) ? 8000L : Integer.parseInt(info[1]));
            case "VarbinaryServer" -> this.maxSize = (StringUtils.isEmpty(info[1]) ? 8000L : (info[1].equals("max") ? 1073741824L : Integer.parseInt(info[1])));
            case "Ncblob", "BlobOracle", "Cblob" -> this.maxSize = 12800000000000000L;
            case "Bfile" -> this.maxSize = 2L; //read only
            default -> this.maxSize  = 1L;
        }
    }

    @Override
    public String writeFieldInfo() {
        return "Column type:" + this.getSqlType() + "(" + this.maxSize + ")";
    }
}

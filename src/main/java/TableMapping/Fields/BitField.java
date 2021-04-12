package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class BitField extends Field{
    private int numberOfBits;

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getNumberOfBits() + ")";
    }

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setNumberOfBits(info.length < 2 || StringUtils.isEmpty(info[1]) ? -1 : Integer.parseInt(info[1]));
    }
}

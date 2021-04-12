package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class BitField extends Field{
    private int numberOfBits = -1;

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getNumberOfBits() + ")";
    }

    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        this.setSqlType(info[0]);

        if (info.length < 2 || StringUtils.isEmpty(info[1])) {
            return;
        }
        this.numberOfBits = Integer.parseInt(info[1]);
    }
}

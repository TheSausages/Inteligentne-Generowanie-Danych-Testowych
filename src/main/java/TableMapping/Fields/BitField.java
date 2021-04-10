package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BitField extends Field{
    private int numberOfBits;

    public BitField() {
        super();
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getNumberOfBits() + ")";
    }

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setNumberOfBits(info.length < 2 || info[1] == null ? -1 :Integer.parseInt(info[1]));
    }
}

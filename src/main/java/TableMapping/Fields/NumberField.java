package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

@Setter
@Getter
public class NumberField extends Field{
    private int maxSize;
    private int precision;
    private boolean isUnsigned = false;

    public NumberField() {
        super();
    }

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setMaxSize(info.length < 2 || info[1] == null ? -1 :Integer.parseInt(info[1]));
        this.setPrecision(info.length < 3 || info[2] == null ? 0 :Integer.parseInt(info[2]));
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getMaxSize() + "," + this.getPrecision() + "), is it unsinged:" + this.isUnsigned();
    }
}

package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class NumberField extends Field{
    private int maxSize;
    private int precision;
    private boolean isUnsigned = false;

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setMaxSize(info.length < 2 || StringUtils.isEmpty(info[1])? -1 :Integer.parseInt(info[1]));
        this.setPrecision(info.length < 3 || StringUtils.isEmpty(info[2]) ? 0 :Integer.parseInt(info[2]));
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getMaxSize() + "," + this.getPrecision() + "), is it unsinged:" + this.isUnsigned();
    }

    public void removeUnsignedFromName() {
        String name = this.getSqlType();

        this.setSqlType(name.substring(0, name.indexOf('_')));
    }
}

package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Setter
@Getter
@NoArgsConstructor
public class TextField extends Field{
    private int maxSize = -1;

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setMaxSize(info.length < 2 || StringUtils.isEmpty(info[1]) || info[1].equals("0") ? -1 :Integer.parseInt(info[1]));
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getMaxSize() + ")";
    }
}

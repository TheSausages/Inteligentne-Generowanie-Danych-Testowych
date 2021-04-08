package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BooleanField extends Field{
    public BooleanField() {
        super();
        setCategory("Boolean");
    }

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType();
    }
}

package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TextField extends Field{
    private int maxSize = -1;

    public TextField() {
        super();
        setCategory("Text");
    }

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);
        this.setMaxSize(info.length < 2 || info[1] == null ? -1 :Integer.parseInt(info[1]));
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getMaxSize() + ")";
    }
}

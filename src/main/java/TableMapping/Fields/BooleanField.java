package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BooleanField extends Field{
    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        this.setSqlType(info[0]);
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType();
    }
}

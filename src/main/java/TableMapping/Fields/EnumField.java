package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
//Only 1 of the elements
public class EnumField extends Field{
    private List<String> elements;

    @Override
    public String writeFieldInfo() {
        StringBuilder enumField = new StringBuilder();

        enumField.append("Column Type:").append(this.getSqlType()).append("(");

        elements.forEach(element -> enumField.append("'").append(element).append("',"));

        enumField.deleteCharAt(enumField.length() - 1);

        enumField.append(")");

        return enumField.toString();
    }

    @Override
    public void setFieldInfo(String[] info) {
        elements = new ArrayList<>();

        this.setSqlType(info[0]);

        elements.addAll(Arrays.asList(info).subList(1, info.length));
    }
}

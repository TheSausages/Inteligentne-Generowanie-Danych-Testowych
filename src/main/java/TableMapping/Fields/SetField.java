package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
//can be more that 1 of elements
public class SetField extends Field {
    private List<String> elements;

    @Override
    public String writeFieldInfo() {
        StringBuilder setField = new StringBuilder();

        setField.append("Column Type:").append(this.getSqlType()).append("(");

        elements.forEach(element -> setField.append("'").append(element).append("',"));

        setField.deleteCharAt(setField.length() - 1);

        setField.append(")");

        return setField.toString();
    }

    @Override
    public void setFieldInfo(String[] info) {
        elements = new ArrayList<>();

        this.setSqlType(info[0]);

        elements.addAll(Arrays.asList(info).subList(1, info.length));
    }
}

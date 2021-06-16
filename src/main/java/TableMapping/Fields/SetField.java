package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents a SQL field that holds a Set - it can hold many values from a given list of values.
 */
@Setter
@Getter
@NoArgsConstructor
//can be more that 1 of elements
public class SetField extends Field {
    /**
     * List of values the Field can hold
     */
    private List<String> elements;

    /**
     *  {@inheritDoc}
     */
    @Override
    public String writeFieldInfo() {
        StringBuilder setField = new StringBuilder();

        setField.append("Column Type:").append(this.getSqlType()).append("(");

        elements.forEach(element -> setField.append("'").append(element).append("',"));

        setField.deleteCharAt(setField.length() - 1);

        setField.append(")");

        return setField.toString();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        elements = new ArrayList<>();

        this.setSqlType(info[0]);

        Arrays.stream(info).skip(1).forEach(element -> {
            elements.add(element == null ? "NotGiven" : element.substring(1, element.length() - 1));
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SetField setField = (SetField) o;

        return new EqualsBuilder().append(elements, setField.elements).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(elements).toHashCode();
    }
}

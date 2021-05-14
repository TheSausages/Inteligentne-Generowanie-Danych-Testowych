package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents a SQL field that holds an Enum - it can hold 1 value from a given list of values
 */
@Setter
@Getter
@NoArgsConstructor
//Only 1 of the elements
public class EnumField extends Field{
    /**
     * List of values the Field can hold
     */
    private List<String> elements;

    /**
     *  {@inheritDoc}
     */
    @Override
    public String writeFieldInfo() {
        StringBuilder enumField = new StringBuilder();

        enumField.append("Column Type:").append(this.getSqlType()).append("(");

        elements.forEach(element -> enumField.append("'").append(element).append("',"));

        enumField.deleteCharAt(enumField.length() - 1);

        enumField.append(")");

        return enumField.toString();
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

        EnumField enumField = (EnumField) o;

        return new EqualsBuilder().append(elements, enumField.elements).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(elements).toHashCode();
    }
}

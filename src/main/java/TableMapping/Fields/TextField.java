package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;


/**
 * Class that represents a SQL field that holds some Text
 */
@Setter
@Getter
@NoArgsConstructor
public class TextField extends Field{
    /**
     * The maximum number of symbols the field can hold
     */
    private int maxSize = -1;

    /**
     *  {@inheritDoc}
     */
    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        this.setSqlType(info[0]);
        this.setMaxSize(info.length < 2 || StringUtils.isEmpty(info[1]) || info[1].equals("0") ? -1 :Integer.parseInt(info[1]));
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + "(" + this.getMaxSize() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextField textField = (TextField) o;

        return new EqualsBuilder().append(maxSize, textField.maxSize).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(maxSize).toHashCode();
    }
}

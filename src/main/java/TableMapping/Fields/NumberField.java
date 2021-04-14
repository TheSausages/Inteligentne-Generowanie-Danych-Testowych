package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Setter
@Getter
@NoArgsConstructor
public class NumberField extends Field{
    private int maxSize;
    private int precision;
    private boolean isUnsigned = false;

    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NumberField that = (NumberField) o;

        return new EqualsBuilder().append(maxSize, that.maxSize).append(precision, that.precision).append(isUnsigned, that.isUnsigned).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(maxSize).append(precision).append(isUnsigned).toHashCode();
    }
}

package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append("Boolean").toHashCode();
    }
}

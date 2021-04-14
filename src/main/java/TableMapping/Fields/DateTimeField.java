package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

@Setter
@Getter
@NoArgsConstructor
public class DateTimeField extends Field{
    private String format;

    @Override
    public void setFieldInfo(String[] info) {
        if (this.isInfoNullOrEmpty(info)) {
            this.setSqlType("Not Given");
            return;
        }

        this.setSqlType(info[0]);

        switch (info[0]) {
            case "Date" -> this.format = "YYYY-MM-DD";
            case "Time" -> this.format = "hh:mm:ss";
            case "Datetime", "Timestamp", "Smalldatetime" -> this.format = "YYYY-MM-DD hh:mm:ss";
            case "Year" -> this.format = "YYYY";
            case "DatetimeServer" -> this.format = "YYYY-MM-DD hh:mm:ss.nnn";
            case "Datetime2" -> this.format = "YYYY-MM-DD hh:mm:ss.nnnnnnn";
            case "Datetimeoffset" -> this.format = "YYYY-MM-DD hh:mm:ss.nnnnnnn[+|-]hh:mm";
            case "DateOracle" -> this.format = "DD-MON-YY";
            default -> this.format = "1";
        }
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + " with format:" + this.getFormat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DateTimeField that = (DateTimeField) o;

        return new EqualsBuilder().append(format, that.format).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(format).toHashCode();
    }
}

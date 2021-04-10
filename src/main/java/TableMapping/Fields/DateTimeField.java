package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DateTimeField extends Field{
    private String format;

    @Override
    public void setFieldInfo(String[] info) {
        this.setSqlType(info[0]);

        switch (info[0]) {
            case "Date" -> this.format = "YYYY-MM-DD";
            case "Time" -> this.format = "hh:mm:ss";
            case "Datetime", "Timestamp", "Smalldatetime" -> this.format = "YYYY-MM-DD hh:mm:ss";
            case "Year" -> this.format = "YYYY";
            case "DatetimeServer" -> this.format = "YYYY-MM-DD hh:mm:ss.nnn";
            case "Datetime2" -> this.format = "YYYY-MM-DD hh:mm:ss.nnnnnnn";
            case "Datetimeoffset" -> this.format = "YYYY-MM-DD hh:mm:ss.nnnnnnn[+|-]hh:mm";
            case "DateOracle" -> this.format = (info.length < 2 || info[1] == null ? "DD-MON-YY" : info[1]);
            default -> this.format = "1";
        }
    }

    @Override
    public String writeFieldInfo() {
        return "Column Type:" + this.getSqlType() + " with format:" + this.getFormat();
    }
}

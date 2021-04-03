package TableMapping;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Field {
    private String sqlType;
    private int maxSize = -1;
    private int precision = 0;
    private boolean isUnsigned = false;

    public Field() {}

    public Field(String sqlType, String maxSize, String precision, boolean isUnsigned) {
        this.sqlType = sqlType;
        this.maxSize = Integer.parseInt(maxSize);
        this.precision = Integer.parseInt(precision);
        this.isUnsigned = isUnsigned;
    }

    public boolean isEmpty() {
        return sqlType.isEmpty();
    }
}

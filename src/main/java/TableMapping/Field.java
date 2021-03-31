package TableMapping;

public class Field {
    private String sqlType;
    private int maxSize = -1;
    private int precision = 0;
    private boolean isUnsigned = false;

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public void setUnsigned(boolean unsigned) {
        isUnsigned = unsigned;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getSqlType() {
        return sqlType;
    }

    public boolean isUnsigned() {
        return isUnsigned;
    }

    public int getPrecision() {
        return precision;
    }
}

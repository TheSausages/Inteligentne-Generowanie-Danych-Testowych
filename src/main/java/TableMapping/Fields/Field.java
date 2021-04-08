package TableMapping.Fields;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

@Setter
@Getter
public abstract class Field {
    String category;
    private String sqlType;

    public Field() {
        setCategory("No");
    }

    public abstract void setFieldInfo(String[] info);

    public abstract String writeFieldInfo();

    public static Field findFieldType(String sqlType) {
        try {
            return FieldMapping.valueOf(StringUtils.capitalize(sqlType)).getFieldClass();
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return new TextField();
        }
    }

    public boolean isEmpty() {
        return sqlType.isEmpty();
    }
}

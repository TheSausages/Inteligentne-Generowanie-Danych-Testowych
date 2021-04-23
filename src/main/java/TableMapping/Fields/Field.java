package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

@Setter
@Getter
@NoArgsConstructor
public abstract class Field {
    private String sqlType;
    private transient String fieldType = getClass().getSimpleName();

    public abstract void setFieldInfo(String[] info);

    public abstract String writeFieldInfo();

    public static Field findFieldType(String sqlType) {
        try {
            return FieldMapping.valueOf(StringUtils.capitalize(sqlType)).getFieldClass();
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage() + ", Used default field - Textfield");
            return new TextField();
        }
    }

    protected boolean isInfoNullOrEmpty(String[] info) {
        if (info == null || info[0] == null) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(sqlType);
    }
}

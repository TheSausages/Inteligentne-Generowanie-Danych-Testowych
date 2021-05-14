package TableMapping.Fields;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Abstract class that represents a general field for a column
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class Field {
    /**
     * The SQL type of the field
     */
    private String sqlType;

    /**
     * Simple name of the field class. Used inside the .json mapping information file. See {@link GenerateInformation.GsonGenerationClassAdapter}
     */
    private transient String fieldType = getClass().getSimpleName();

    /**
     * Abstract method that is used to set field information for a given field
     * @param info A {@link String} array containing information about a given field
     */
    public abstract void setFieldInfo(String[] info);

    /**
     * Abstract method used in debugging. Writes all information about a field to the console
     * @return Information about the field in a {@link String}
     */
    public abstract String writeFieldInfo();

    /**
     * Method that checks what Field type the SQL type is mapped to and returns it
     * @param sqlType the SQL type of the column
     * @return A {@link Field} type that the SQL type is mapped to
     */
    public static Field findFieldType(String sqlType) {
        try {
            return FieldMapping.valueOf(StringUtils.capitalize(sqlType)).getFieldClass();
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage() + ", Used default field - Textfield");
            return new TextField();
        }
    }

    /**
     * Method that checks if the Information about the column field are empty oir null
     * @param info
     * @return
     */
    protected boolean isInfoNullOrEmpty(String[] info) {
        if (info == null || info[0] == null) {
            return true;
        }
        return false;
    }

    /**
     * Method that return true if no SQL type if assigned to the Field, false otherwise
     * @return If no SQL type assigned - true, otherwise false
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(sqlType);
    }
}

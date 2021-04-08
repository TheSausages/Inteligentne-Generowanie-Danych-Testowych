package TableMapping.Fields;

import java.lang.reflect.InvocationTargetException;

public enum FieldMapping {
    Varchar(TextField.class),

    Number(NumberField.class),
    Int(NumberField.class),
    Int_unsigned(NumberField.class),
    Decimal(NumberField.class),

    Tinyint(BooleanField.class);

    Class<? extends Field> fieldClass;

    FieldMapping(Class<? extends Field> fieldClass) {
        this.fieldClass = fieldClass;
    }

    Field getFieldClass() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return fieldClass.getDeclaredConstructor().newInstance();
    }
}

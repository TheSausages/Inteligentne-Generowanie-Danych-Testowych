package TableMapping.Fields;

import java.lang.reflect.InvocationTargetException;

/**
 * Enum that holds the rules for mapping SQL types into their class represenations. Example: a Varchar SQL type will be mapped into {@link TextField}
 */
public enum FieldMapping {
    //text
    Char(TextField.class),
    Binary(TextField.class),
    Varbinary(TextField.class),
    Varchar(TextField.class),
    Varchar2(TextField.class),
    TextServer(TextField.class),
    Nchar(TextField.class),
    Nvarchar(TextField.class),
    Nvarchar2(TextField.class),
    Ntext(TextField.class),
    Image(TextField.class),
    Clob(TextField.class),
    Nclob(TextField.class),
    Long(TextField.class),
    Raw(TextField.class),

    //blob / Binary
    Tinyblob(BlobField.class),
    Blob(BlobField.class),
    Mediumblob(BlobField.class),
    Longblob(BlobField.class),
    Tinytext(BlobField.class),
    Text(BlobField.class),
    Mediumtext(BlobField.class),
    Longtext(BlobField.class),
    BinaryServer(BlobField.class),
    BlobOracle(BlobField.class),
    Cblob(BlobField.class),
    Ncblob(BlobField.class),
    Bfile(BlobField.class),

    //Numbers
    Number(NumberField.class),
    Int(NumberField.class),
    Int_unsigned(NumberField.class),
    Decimal(NumberField.class),
    Smallint(NumberField.class),
    Mediumint(NumberField.class),
    Integer(NumberField.class),
    Bigint(NumberField.class),
    Serial(NumberField.class),
    Dec(NumberField.class),
    Float(NumberField.class),
    Double(NumberField.class),
    Tinyint(NumberField.class),
    Money(NumberField.class),
    Smallmoney(NumberField.class),
    Real(NumberField.class),
    Numeric(NumberField.class),
    Binary_float(NumberField.class),
    Binary_double(NumberField.class),

    //boolean
    Tinyint1(BooleanField.class),
    BitServer(BooleanField.class),

    //date
    Date(DateTimeField.class),
    Time(DateTimeField.class),
    Datetime(DateTimeField.class),
    DatetimeServer(DateTimeField.class),
    Datetime2(DateTimeField.class),
    Datetimeoffset(DateTimeField.class),
    Timestamp(DateTimeField.class),
    Year(DateTimeField.class),
    DateOracle(DateTimeField.class),

    //bit
    Bit(BitField.class),

    //enum
    Enum(EnumField.class),

    //set
    Set(SetField.class)
    ;

    private final Class<? extends Field> fieldClass;

    /**
     * Constructor for the Enum
     * @param fieldClass
     */
    FieldMapping(Class<? extends Field> fieldClass) {
        this.fieldClass = fieldClass;
    }

    /**
     * A method that return an instance of the selected {@link Field} class
     * @return An instance of a selected class that extends {@link Field}
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    Field getFieldClass() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return fieldClass.getDeclaredConstructor().newInstance();
    }
}

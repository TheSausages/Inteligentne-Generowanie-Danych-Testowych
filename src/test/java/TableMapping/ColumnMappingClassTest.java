package TableMapping;

import DatabaseConnection.DatabaseInfo;
import TableMapping.Fields.Field;
import TableMapping.Fields.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnMappingClassTest {

    @Test
    void builder_NoInformationGiven_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder();

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Column name cannot be empty!");
    }

    @Test
    void builder_NoName_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .field(getTypicalField());

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Column name cannot be empty!");
    }

    @Test
    void builder_EmptyName_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("")
                .field(getTypicalField());

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Column name cannot be empty!");
    }

    @Test
    void builder_NoField_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName");

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Column field type cannot be empty!");
    }

    @Test
    void builder_NameAndField_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName")
                .field(getTypicalField());

        //when
        ColumnMappingClass mappingClass = builder.build();

        //then
        assertEquals(mappingClass.getName(), "ColumnName");
        assertEquals(mappingClass.getField().getSqlType(), "Varchar2");
    }

    @Test
    void builder_NameAndFieldAndDefaultValue_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName")
                .field(getTypicalField())
                .defaultValue("Default");

        //when
        ColumnMappingClass mappingClass = builder.build();

        //then
        assertEquals(mappingClass.getName(), "ColumnName");
        assertEquals(mappingClass.getField().getSqlType(), "Varchar2");
        assertEquals(mappingClass.getDefaultValue(), "Default");
    }

    @Test
    void builder_NameAndFieldAndUnique_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName")
                .field(getTypicalField())
                .isUnique();

        //when
        ColumnMappingClass mappingClass = builder.build();

        //then
        assertEquals(mappingClass.getName(), "ColumnName");
        assertEquals(mappingClass.getField().getSqlType(), "Varchar2");
        assertTrue(mappingClass.isUnique());
    }

    @Test
    void builder_NameAndFieldAndNotNullable_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName")
                .field(getTypicalField())
                .notNullable();

        //when
        ColumnMappingClass mappingClass = builder.build();

        //then
        assertEquals(mappingClass.getName(), "ColumnName");
        assertEquals(mappingClass.getField().getSqlType(), "Varchar2");
        assertFalse(mappingClass.isNullable());
    }

    @Test
    void builder_NameAndFieldAndAutoIncrement_ThrowException() {
        //given
        ColumnMappingClass.ColumnBuilder builder = ColumnMappingClass.builder()
                .name("ColumnName")
                .field(getTypicalField())
                .isAutoIncrement();

        //when
        ColumnMappingClass mappingClass = builder.build();

        //then
        assertEquals(mappingClass.getName(), "ColumnName");
        assertEquals(mappingClass.getField().getSqlType(), "Varchar2");
        assertTrue(mappingClass.isAutoIncrement());
    }

    private Field getTypicalField() {
        Field field = new TextField();
        field.setFieldInfo(new String[]{"Varchar2", "25"});
        return field;
    }
}
package TableMapping;

import DatabaseConnection.SupportedDatabases;
import TableMapping.Fields.Field;
import TableMapping.Fields.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableMappingClassTest {

    @Test
    void builder_NoInformationGiven_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder();

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Table name cannot be empty!");
    }

    @Test
    void builder_NullName_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName(null);

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Table name cannot be empty!");
    }

    @Test
    void builder_OnlyName_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName("TableName");

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "A table must have a table type!");
    }

    @Test
    void builder_OnlyNullType_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableType(null);

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Table name cannot be empty!");
    }

    @Test
    void builder_NullTypeWithName_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName("TableName")
                .tableType(null);

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "A table must have a table type!");
    }

    @Test
    void builder_OnlyType_ThrowException() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableType(SupportedDatabases.MYSQL);

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "Table name cannot be empty!");
    }

    @Test
    void builder_NameAndType_NoError() {
        //given
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName("TableName")
                .tableType(SupportedDatabases.MYSQL);

        //when
        TableMappingClass tableMapping = builder.build();

        //then
        assertEquals(tableMapping.getTableName(), "TableName");
        assertEquals(tableMapping.getTableType(), SupportedDatabases.MYSQL);
    }

    @Test
    void builder_NameAndTypeAndColumn_NoError() {
        //given
        ColumnMappingClass columnMappingClass = getTypicalColumn(1);
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName("TableName")
                .tableType(SupportedDatabases.MYSQL)
                .addColumn(getTypicalColumn(1));

        //when
        TableMappingClass tableMapping = builder.build();

        //then
        assertEquals(tableMapping.getTableName(), "TableName");
        assertEquals(tableMapping.getTableType(), SupportedDatabases.MYSQL);
        assertEquals(tableMapping.getColumns().get(0), columnMappingClass);
    }

    @Test
    void builder_NameAndTypeAndMultipleColumn_NoError() {
        //given
        ColumnMappingClass columnMappingClass1 = getTypicalColumn(1);
        ColumnMappingClass columnMappingClass2 = getTypicalColumn(2);
        ColumnMappingClass columnMappingClass3 = getTypicalColumn(3);
        TableMappingClass.TableBuilder builder = TableMappingClass.builder()
                .tableName("TableName")
                .tableType(SupportedDatabases.MYSQL)
                .addColumn(getTypicalColumn(1))
                .addColumn(getTypicalColumn(2))
                .addColumn(getTypicalColumn(3));

        //when
        TableMappingClass tableMapping = builder.build();

        //then
        assertEquals(tableMapping.getTableName(), "TableName");
        assertEquals(tableMapping.getTableType(), SupportedDatabases.MYSQL);
        assertEquals(tableMapping.getColumns().get(0), columnMappingClass1);
        assertEquals(tableMapping.getColumns().get(1), columnMappingClass2);
        assertEquals(tableMapping.getColumns().get(2), columnMappingClass3);
    }

    private ColumnMappingClass getTypicalColumn(int i) {
        return ColumnMappingClass.builder()
                .name("FirstName")
                .field(getTypicalField())
                .build();
    }

    private Field getTypicalField() {
        Field field = new TextField();
        field.setFieldInfo(new String[]{"Varchar2", "25"});
        return field;
    }
}
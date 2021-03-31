package TableMapping;

import Exceptions.DataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnMappingClassTest {

    @Nested
    @DisplayName("SQL Columns")
    class SQL {
        @Test
        void mapColumnsMySQL_OnlyName_ThrowException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String columnName = "`Column`";

            //when
            RuntimeException exception = assertThrows(DataException.class, () -> {
                columnMappingClass.mapColumnsMySQL(columnName);
            });

            //then
            assertEquals(exception.getMessage(), "Not enought information on column Column");
        }

        @Test
        void mapColumnsMySQL_OnlyNameAndType_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String columnName = "`Column`";
            String columnType = "int";
            String line = columnName + " " + columnType;

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), columnName.substring(1, columnName.length() - 1));
            assertEquals(columnMappingClass.getField().getSqlType(), columnType);
            assertEquals(columnMappingClass.getField().getMaxSize(), -1);
            assertEquals(columnMappingClass.getField().getPrecision(), 0);
            assertNull(columnMappingClass.getDefaultValue());
            assertTrue(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }

        @Test
        void mapColumnsMySQL_OnlyNameAndTypeWithSizeWithoutPrecision_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String columnName = "`Column`";
            String columnType = "Decimal";
            String size = "6";
            String line = columnName + " " + columnType + "(" + size + ")";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), columnName.substring(1, columnName.length() - 1));
            assertEquals(columnMappingClass.getField().getSqlType(), columnType);
            assertEquals(columnMappingClass.getField().getMaxSize(), Integer.parseInt(size));
            assertEquals(columnMappingClass.getField().getPrecision(), 0);
            assertNull(columnMappingClass.getDefaultValue());
            assertTrue(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }

        @Test
        void mapColumnsMySQL_OnlyNameAndTypeWithSizeAndPrecision_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String columnName = "`Column`";
            String columnType = "Decimal";
            String size = "6";
            String precision = "3";
            String line = columnName + " " + columnType + "(" + size + "," + precision + ")";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), columnName.substring(1, columnName.length() - 1));
            assertEquals(columnMappingClass.getField().getSqlType(), columnType);
            assertEquals(columnMappingClass.getField().getMaxSize(), Integer.parseInt(size));
            assertEquals(columnMappingClass.getField().getPrecision(), Integer.parseInt(precision));
            assertNull(columnMappingClass.getDefaultValue());
            assertTrue(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }
    }
}
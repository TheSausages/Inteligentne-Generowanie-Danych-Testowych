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
        void mapColumnsMySQL_NoData_ThrowException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();

            //when
            RuntimeException exception = assertThrows(DataException.class, () -> {
                columnMappingClass.mapColumnsMySQL("");
            });

            //then
            assertEquals(exception.getMessage(), "Not information on Column received");
        }

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
            assertEquals(exception.getMessage(), "Not enough information on column Column");
        }

        @Test
        void mapColumnsMySQL_OnlyType_ThrowException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String columnName = "decimal(6,3)";

            //when
            RuntimeException exception = assertThrows(DataException.class, () -> {
                columnMappingClass.mapColumnsMySQL(columnName);
            });

            //then
            assertEquals(exception.getMessage(), "Not information besides Type on Column received");
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

        @Test
        void mapColumnsMySQL_WithNameTypeNotNullableDefaultValue_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String line = "`Column` Decimal(6,3) NOT-NULL DEFAULT-63";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), "Column");
            assertEquals(columnMappingClass.getField().getSqlType(), "Decimal");
            assertEquals(columnMappingClass.getField().getMaxSize(), 6);
            assertEquals(columnMappingClass.getField().getPrecision(), 3);
            assertEquals(columnMappingClass.getDefaultValue(), "63");
            assertFalse(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }

        @Test
        void mapColumnsMySQL_WithNameTypeAutoIncrementDefaultValue_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            String line = "`Column` Decimal(6,3) AUTO_INCREMENT DEFAULT-63";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), "Column");
            assertEquals(columnMappingClass.getField().getSqlType(), "Decimal");
            assertEquals(columnMappingClass.getField().getMaxSize(), 6);
            assertEquals(columnMappingClass.getField().getPrecision(), 3);
            assertEquals(columnMappingClass.getDefaultValue(), "63");
            assertTrue(columnMappingClass.isNullable());
            assertTrue(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }

        @Test
        void mapColumnsMySQL_WithNameTypeNotNullUniqueIsPrimaryKey_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            columnMappingClass.setPrimaryKey(true);
            String line = "`Column` Decimal(6,3) NOT-NULL UNIQUE";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), "Column");
            assertEquals(columnMappingClass.getField().getSqlType(), "Decimal");
            assertEquals(columnMappingClass.getField().getMaxSize(), 6);
            assertEquals(columnMappingClass.getField().getPrecision(), 3);
            assertNull(columnMappingClass.getDefaultValue());
            assertFalse(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertTrue(columnMappingClass.isUnique());
            assertTrue(columnMappingClass.isPrimaryKey());
            assertFalse(columnMappingClass.getForeignKey().isForeignKey());
        }

        @Test
        void mapColumnsMySQL_WithNameTypeNotNullDefaultValueIsForeignKey_NoException() {
            //given
            ColumnMappingClass columnMappingClass = new ColumnMappingClass();
            columnMappingClass.setForeignKey(createForeignKey());
            String line = "`Column` VARCHAR(30) NOT-NULL DEFAULT-'DefaultValue'";

            //when
            columnMappingClass.mapColumnsMySQL(line);

            //then
            assertEquals(columnMappingClass.getName(), "Column");
            assertEquals(columnMappingClass.getField().getSqlType(), "VARCHAR");
            assertEquals(columnMappingClass.getField().getMaxSize(), 30);
            assertEquals(columnMappingClass.getField().getPrecision(), 0);
            assertEquals(columnMappingClass.getDefaultValue(), "DefaultValue");
            assertFalse(columnMappingClass.isNullable());
            assertFalse(columnMappingClass.isAutoIncrement());
            assertFalse(columnMappingClass.isUnique());
            assertFalse(columnMappingClass.isPrimaryKey());
            assertTrue(columnMappingClass.getForeignKey().isForeignKey());
            assertEquals(columnMappingClass.getForeignKey().getForeignKeyColumn(), "column");
            assertEquals(columnMappingClass.getForeignKey().getForeignKeyTable(), "table");
        }
    }

    private ForeignKeyMapping createForeignKey() {
        ForeignKeyMapping foreignKeyMapping = new ForeignKeyMapping();
        foreignKeyMapping.setForeignKey(true);
        foreignKeyMapping.setForeignKeyTable("table");
        foreignKeyMapping.setForeignKeyColumn("column");

        return foreignKeyMapping;
    }
}
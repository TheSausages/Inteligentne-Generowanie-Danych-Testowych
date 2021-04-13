package TableMapping;

import DatabaseConnection.DatabaseInfo;
import TableMapping.Fields.BitField;
import TableMapping.Fields.BlobField;
import TableMapping.Fields.Field;
import TableMapping.Fields.NumberField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldsTest {
    @Nested
    @DisplayName("Testing Bit Field")
    class BitField {
        @Test
        void BitField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BitField_nullElementInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BitField_nullElementsInInfoArray_NoError() {
            //given
            String[] info = new String[]{null, null};
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
            assertEquals(((TableMapping.Fields.BitField) field).getNumberOfBits(), -1);
        }

        @Test
        void BitField_nullSecondElementInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Bit", null};
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Bit");
            assertEquals(((TableMapping.Fields.BitField) field).getNumberOfBits(), -1);
        }

        @Test
        void BitField_CorrectInfoInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Bit", "5"};
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Bit");
            assertEquals(((TableMapping.Fields.BitField) field).getNumberOfBits(), 5);
        }
    }

    @Nested
    @DisplayName("Testing Blob Field")
    class BlobField {
        @Test
        void BlobField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BlobField_nullElementInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.BitField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BlobField_TinyblobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Tinyblob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Tinyblob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 255L);
        }

        @Test
        void BlobField_BlobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Blob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Blob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 65535L);
        }

        @Test
        void BlobField_TextInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Text"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Text");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 65535L);
        }

        @Test
        void BlobField_MediumblobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Mediumblob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Mediumblob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 16777215L);
        }

        @Test
        void BlobField_MediumtextInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Mediumtext"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Mediumtext");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 16777215L);
        }

        @Test
        void BlobField_LongblobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Longblob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Longblob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 4294967295L);
        }

        @Test
        void BlobField_LongtextInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Longtext"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Longtext");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 4294967295L);
        }

        @Test
        void BlobField_TinytextInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Tinytext"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Tinytext");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 256L);
        }

        @Test
        void BlobField_BinaryServerNoSizeInInfoArray_NoError() {
            //given
            String[] info = new String[]{"BinaryServer"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "BinaryServer");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 8000L);
        }

        @Test
        void BlobField_BinaryServerWithSizeInInfoArray_NoError() {
            //given
            String[] info = new String[]{"BinaryServer", "8520"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "BinaryServer");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 8520L);
        }

        @Test
        void BlobField_VarbinaryServerNoSizeInInfoArray_NoError() {
            //given
            String[] info = new String[]{"VarbinaryServer"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "VarbinaryServer");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 8000L);
        }

        @Test
        void BlobField_VarbinaryServerMaxSizeInInfoArray_NoError() {
            //given
            String[] info = new String[]{"VarbinaryServer", "MAX"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "VarbinaryServer");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 1073741824L);
        }

        @Test
        void BlobField_VarbinaryServerWithSizeInInfoArray_NoError() {
            //given
            String[] info = new String[]{"VarbinaryServer", "4826"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "VarbinaryServer");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 4826L);
        }

        @Test
        void BlobField_NcblobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Ncblob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Ncblob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 12800000000000000L);
        }

        @Test
        void BlobField_BlobOracleInInfoArray_NoError() {
            //given
            String[] info = new String[]{"BlobOracle"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "BlobOracle");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 12800000000000000L);
        }

        @Test
        void BlobField_CblobInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Cblob"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Cblob");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 12800000000000000L);
        }

        @Test
        void BlobField_BfileInInfoArray_NoError() {
            //given
            String[] info = new String[]{"Bfile"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Bfile");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 2L);
        }

        @Test
        void BlobField_UnknownInInfoArray_NoError() {
            //given
            String[] info = new String[]{"u2I8qa"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "u2I8qa");
            assertEquals(((TableMapping.Fields.BlobField) field).getMaxSize(), 1L);
        }
    }

    @Nested
    @DisplayName("Testing Boolean Field")
    class BooleanField {
        @Test
        void BooleanField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.BooleanField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BooleanField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.BooleanField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BooleanField_CorrectInfoInfoArray_NoError() {
            //given
            String[] info = new String[]{"Boolean"};
            Field field = new TableMapping.Fields.BooleanField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Boolean");
        }
    }

    @Nested
    @DisplayName("Testing DateTime Field")
    class DateTimeField {
        @Test
        void DateTimeField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void DateTimeField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void DateTimeField_DateInfoArray_NoError() {
            //given
            String[] info = new String[]{"Date"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Date");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD");
        }

        @Test
        void DateTimeField_TimeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Time"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Time");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "hh:mm:ss");
        }

        @Test
        void DateTimeField_DatetimeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Datetime"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Datetime");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss");
        }

        @Test
        void DateTimeField_TimestampInfoArray_NoError() {
            //given
            String[] info = new String[]{"Timestamp"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Timestamp");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss");
        }

        @Test
        void DateTimeField_SmalldatetimeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Smalldatetime"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Smalldatetime");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss");
        }

        @Test
        void DateTimeField_YearInfoArray_NoError() {
            //given
            String[] info = new String[]{"Year"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Year");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY");
        }

        @Test
        void DateTimeField_DatetimeServerInfoArray_NoError() {
            //given
            String[] info = new String[]{"DatetimeServer"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "DatetimeServer");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss.nnn");
        }

        @Test
        void DateTimeField_Datetime2InfoArray_NoError() {
            //given
            String[] info = new String[]{"Datetime2"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Datetime2");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss.nnnnnnn");
        }

        @Test
        void DateTimeField_DatetimeoffsetInfoArray_NoError() {
            //given
            String[] info = new String[]{"Datetimeoffset"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Datetimeoffset");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "YYYY-MM-DD hh:mm:ss.nnnnnnn[+|-]hh:mm");
        }

        @Test
        void DateTimeField_DateOracleInfoArray_NoError() {
            //given
            String[] info = new String[]{"DateOracle"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "DateOracle");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "DD-MON-YY");
        }

        @Test
        void DateTimeField_RandomInfoArray_NoError() {
            //given
            String[] info = new String[]{"Random"};
            Field field = new TableMapping.Fields.DateTimeField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Random");
            assertEquals(((TableMapping.Fields.DateTimeField) field).getFormat(), "1");
        }
    }

    @Nested
    @DisplayName("Testing Enum Field")
    class EnumField {
        @Test
        void EnumField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void EnumField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void EnumField_multipleNullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null, null, null};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void EnumField_OnlyNameInfoArray_NoError() {
            //given
            String[] info = new String[]{"enum"};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "enum");
        }

        @Test
        void EnumField_NameAndOneElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"enum", "'Low'"};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "enum");
            assertEquals(((TableMapping.Fields.EnumField) field).getElements().toString(), "[Low]");
        }

        @Test
        void EnumField_NameAndMultipleElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"enum", "'Low'", "'Medium'", "'High'"};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "enum");
            assertEquals(((TableMapping.Fields.EnumField) field).getElements().toString(), "[Low, Medium, High]");
        }

        @Test
        void EnumField_NameAndMultipleAAElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"enum", "'Low'", "'Medium'", null};
            Field field = new TableMapping.Fields.EnumField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "enum");
            assertEquals(((TableMapping.Fields.EnumField) field).getElements().toString(), "[Low, Medium, NotGiven]");
        }
    }

    @Nested
    @DisplayName("Testing Number Field")
    class NumberField {
        @Test
        void NumberField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void NumberField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void NumberField_multipleNullInfoArray_NoError() {
            //given
            String[] info = new String[]{null, null, null};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
            assertFalse(((TableMapping.Fields.NumberField) field).isUnsigned());
        }

        @Test
        void NumberField_OnlyNameInfoArray_NoError() {
            //given
            String[] info = new String[]{"Number"};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Number");
            assertEquals(((TableMapping.Fields.NumberField) field).getMaxSize(), -1);
            assertEquals(((TableMapping.Fields.NumberField) field).getPrecision(), 0);
            assertFalse(((TableMapping.Fields.NumberField) field).isUnsigned());
        }

        @Test
        void NumberField_NameAndMaxSizeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Number", "12"};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Number");
            assertEquals(((TableMapping.Fields.NumberField) field).getMaxSize(), 12);
            assertEquals(((TableMapping.Fields.NumberField) field).getPrecision(), 0);
            assertFalse(((TableMapping.Fields.NumberField) field).isUnsigned());
        }

        @Test
        void NumberField_NameAndPrecisionInfoArray_NoError() {
            //given
            String[] info = new String[]{"Number", null, "4"};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Number");
            assertEquals(((TableMapping.Fields.NumberField) field).getMaxSize(), -1);
            assertEquals(((TableMapping.Fields.NumberField) field).getPrecision(), 4);
            assertFalse(((TableMapping.Fields.NumberField) field).isUnsigned());
        }

        @Test
        void NumberField_NameMaxSizePrecisionInfoArray_NoError() {
            //given
            String[] info = new String[]{"Number", "12", "4"};
            Field field = new TableMapping.Fields.NumberField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Number");
            assertEquals(((TableMapping.Fields.NumberField) field).getMaxSize(), 12);
            assertEquals(((TableMapping.Fields.NumberField) field).getPrecision(), 4);
            assertFalse(((TableMapping.Fields.NumberField) field).isUnsigned());
        }

        @Test
        void NumberField_NameMaxSizePrecisionUnsignedInfoArray_NoError() {
            //given
            String[] info = new String[]{"Number", "12", "4"};
            Field field = new TableMapping.Fields.NumberField();

            //when
            ((TableMapping.Fields.NumberField) field).setUnsigned(true);
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Number");
            assertEquals(((TableMapping.Fields.NumberField) field).getMaxSize(), 12);
            assertEquals(((TableMapping.Fields.NumberField) field).getPrecision(), 4);
            assertTrue(((TableMapping.Fields.NumberField) field).isUnsigned());
        }
    }

    @Nested
    @DisplayName("Testing Set Field")
    class SetField {
        @Test
        void SetField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void SetField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void SetField_multipleNullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null, null, null};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void SetField_OnlyNameInfoArray_NoError() {
            //given
            String[] info = new String[]{"Set"};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Set");
        }

        @Test
        void SetField_NameAndOneElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"Set", "'a'"};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Set");
            assertEquals(((TableMapping.Fields.SetField) field).getElements().toString(), "[a]");
        }

        @Test
        void SetField_NameAndMultipleElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"Set", "'a'", "'b'", "'c'"};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Set");
            assertEquals(((TableMapping.Fields.SetField) field).getElements().toString(), "[a, b, c]");
        }

        @Test
        void SetField_NameAndMultipleAAElementInfoArray_NoError() {
            //given
            String[] info = new String[]{"Set", "'a'", "'b'", null};
            Field field = new TableMapping.Fields.SetField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Set");
            assertEquals(((TableMapping.Fields.SetField) field).getElements().toString(), "[a, b, NotGiven]");
        }
    }

    @Nested
    @DisplayName("Testing Text Field")
    class TextField {
        @Test
        void TextField_nullInfoArray_NoError() {
            //given
            String[] info = null;
            Field field = new TableMapping.Fields.TextField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void TextField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.TextField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void TextField_OnlyNameInfoArray_NoError() {
            //given
            String[] info = new String[]{"Text"};
            Field field = new TableMapping.Fields.TextField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Text");
            assertEquals(((TableMapping.Fields.TextField) field).getMaxSize(), -1);
        }

        @Test
        void TextField_NameNullSizeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Text", null};
            Field field = new TableMapping.Fields.TextField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Text");
            assertEquals(((TableMapping.Fields.TextField) field).getMaxSize(), -1);
        }

        @Test
        void TextField_NameAndSizeInfoArray_NoError() {
            //given
            String[] info = new String[]{"Text", "20"};
            Field field = new TableMapping.Fields.TextField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Text");
            assertEquals(((TableMapping.Fields.TextField) field).getMaxSize(), 20);
        }
    }
}

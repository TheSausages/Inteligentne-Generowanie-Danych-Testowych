package TableMapping;

import DatabaseConnection.DatabaseInfo;
import TableMapping.Fields.BitField;
import TableMapping.Fields.BlobField;
import TableMapping.Fields.Field;
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
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BooleanField_nullInInfoArray_NoError() {
            //given
            String[] info = new String[]{null};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Not Given");
        }

        @Test
        void BooleanField_CorrectInfoInfoArray_NoError() {
            //given
            String[] info = new String[]{"Boolean"};
            Field field = new TableMapping.Fields.BlobField();

            //when
            field.setFieldInfo(info);

            //then
            assertEquals(field.getSqlType(), "Boolean");
        }
    }
}

package TableMapping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForeignKeyMappingTest {

    @Test
    void foreignKeyInfo() {
        //given
        ForeignKeyMapping foreignKey = new ForeignKeyMapping(false);

        //when
        foreignKey.foreignKeyInfo("Column", "Table");

        //then
        assertEquals(foreignKey.getForeignKeyColumn(), "Column");
        assertEquals(foreignKey.getForeignKeyTable(), "Table");
    }
}
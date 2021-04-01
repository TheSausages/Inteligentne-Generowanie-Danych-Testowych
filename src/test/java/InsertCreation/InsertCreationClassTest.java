package InsertCreation;

import Exceptions.ConnectionException;
import TableMapping.TableMappingClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InsertCreationClassTest {
    @Nested
    @DisplayName("List excitement test")
    class ListInfoTest {

         @Test
        void insertCreationClass() {
             //given
             List<TableMappingClass> mapping = new ArrayList<>();

             //when
             new InsertCreationClass().InsertCreationClass(mapping);

             //then
            // assertEquals(thetextfie.txt,"");
        }
}
}

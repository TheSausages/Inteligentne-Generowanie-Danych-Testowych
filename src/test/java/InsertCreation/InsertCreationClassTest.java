package InsertCreation;

import Exceptions.ConnectionException;
import TableMapping.TableMappingClass;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InsertCreationClassTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Nested
    @DisplayName("List excitement DataCreationTest")
    class ListInfoTest {

         @Test
        void insertCreationClass() {
             //given
            // List<TableMappingClass> mapping = new ArrayList<>();

             //when
            // new InsertCreationClass().InsertCreationClass(mapping);

             //then
             //assertEquals(,"");
        }
}
}

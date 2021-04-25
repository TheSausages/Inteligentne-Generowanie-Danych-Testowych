package InsertCreation;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.internal.matchers.Null;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({TemporaryFileParameterResolver.class})
class InsertSavingClassTest {
    @Test
    void insertSavingClass_saveString_noErrors(@TemporaryFileParameterResolver.Temporary File file) throws IOException {
        //given
        InsertSavingClass savingClass =  new InsertSavingClass();
        savingClass.setFile(file);
        String insert = "plik";

        //when
        savingClass.saveToFile(insert);
        String s = FileUtils.readFileToString(savingClass.getFile(), StandardCharsets.UTF_8);

        //then
        assertEquals(s,insert);
    }
    @Test
    void insertSavingClass_saveNull_throwException(@TemporaryFileParameterResolver.Temporary File file) throws IOException {
        //given
        InsertSavingClass savingClass =  new InsertSavingClass();
        savingClass.setFile(file);
        String insert = null;

        //when
        Exception exception  = assertThrows(NullPointerException.class,() -> savingClass.saveToFile(insert));

        //then
        assertEquals(exception.getMessage(),"Cannot invoke \"String.length()\" because \"str\" is null");
    }
}
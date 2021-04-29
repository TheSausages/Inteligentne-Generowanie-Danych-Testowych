package InsertCreation;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InsertSavingClassTest {
    @TempDir
    File dirPath;
    File tempFile;

    @BeforeEach
    void setUp() {
        tempFile = new File(dirPath, "testFile.txt");
    }


    @Test
    void insertSavingClass_saveString_noErrors() throws IOException {
        //given
        InsertSavingClass savingClass =  new InsertSavingClass();
        savingClass.setFile(tempFile);
        String insert = "plik";

        //when
        savingClass.saveToFile(insert);
        String s = FileUtils.readFileToString(savingClass.getFile(), StandardCharsets.UTF_8);

        //then
        assertEquals(s,insert);
    }
}
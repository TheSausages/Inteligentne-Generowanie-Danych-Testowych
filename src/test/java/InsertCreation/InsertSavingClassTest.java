package InsertCreation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class InsertSavingClassTest {

    File file1;
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        try {
            file1 = testFolder.newFile("testfile.txt");
        } catch (IOException ioe) {
            System.err.println("error creating creationg test file");
        }
    }
    @Test
    void insertSavingClass() {
        String str = "randomstring";
        StringWriter stringWriter = new StringWriter();
        InsertSavingClass.InsertSavingClass(str/*stringWriter.toString()*/);
        assertEquals("randomstringg", stringWriter.toString());
    }
}
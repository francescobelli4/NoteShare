package utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class TestUtils {

    @BeforeAll
    static void setup() {
        Utils.createDir("testPath");

        try (FileWriter fileWriter = new FileWriter("testPath/f.txt")) {
            fileWriter.write("Test");
        } catch (IOException _) {
        }
    }

    @AfterAll
    static void tearDown() {
        File f = new File("testPath");
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    void createDir() {
        File f = new File("testPath");
        assertTrue(f.exists());
    }

    @Test
    void findFile() {
        assertNull(Utils.findFile("TMOOO"));

        assertNotNull(Utils.findFile("testPath/f.txt"));
    }

    @Test
    void readFile() throws IOException {
        assertEquals("Test", Utils.readFile(Utils.findFile("testPath/f.txt")));
    }

    @Test
    void findFolder() {
        assertNotNull(Utils.findFolder("testPath"));
    }

    @Test
    void saveAccessToken() {

        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class, Mockito.CALLS_REAL_METHODS)) {
            mockedStatic.when(Utils::getOSLocalPath).thenReturn("testPath/");

            Utils.saveAccessToken(":D");
            File accessToken = Utils.findFile(Utils.getOSLocalPath()+"access_token.txt");
            assertNotNull(accessToken);
            assertEquals(":D", Utils.readFile(accessToken));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
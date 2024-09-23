package test.java;

import Commands.ChangeDirectory;
import Commands.*;
import FileSystem.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.zip.ZipEntry;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeDirectoryTest {
    private FileSystem fs;
    private ChangeDirectory changeDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        fs = new FileSystem("/Users/zaharyan/Projects/Java/MIREA/ConfManagement/ash1/ash.zip"); // Specify the path to your test ZIP file
        changeDirectory = new ChangeDirectory(fs);
    }

    @Test
    public void testExecuteWithEmptyParam() {
        changeDirectory.execute("");
        assertEquals("ash/", fs.getCurrentDirectory().getName()); // Assuming "HOME" is the home directory
    }

    @Test
    public void testExecuteWithValidPath() {
        changeDirectory.execute("src");
        assertEquals("ash/src/", fs.getCurrentDirectory().getName());
    }

    @Test
    public void testExecuteWithInvalidPath() {
        changeDirectory.execute("invalid");
        assertNotEquals("invalid", fs.getCurrentDirectory().getName());
    }
}
package test.java;

import Commands.List;
import FileSystem.FileSystem; // Adjust import based on your package structure
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.zip.ZipEntry;

import static org.junit.jupiter.api.Assertions.*;

public class ListTest {
    private FileSystem fs;
    private List list;

    @BeforeEach
    public void setUp() throws Exception {
        fs = new FileSystem("/Users/zaharyan/Projects/Java/MIREA/ConfManagement/ash1/ash.zip"); // Specify the path to your test ZIP file
        list = new List(fs);
    }

    @Test
    public void testExecuteWithEmptyParam() {
        list.execute("kekw");
    }

    @Test
    public void testExecuteWithValidPath() {
        list.execute("/");
    }
}
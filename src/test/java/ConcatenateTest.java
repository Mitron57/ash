package test.java;

import Commands.Concatenate;
import FileSystem.FileSystem; // Adjust import based on your package structure
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConcatenateTest {
    private FileSystem fs;
    private Concatenate concatenate;

    @BeforeEach
    public void setUp() throws Exception {
        fs = new FileSystem("/Users/zaharyan/Projects/Java/MIREA/ConfManagement/ash1/ash.zip");
        concatenate = new Concatenate(fs);
    }

    @Test
    public void testExecuteWithInvalidPath() {
        concatenate.execute("invalid/path");
    }

    @Test
    public void testExecuteWithValidFile() {
        concatenate.execute("src/Interfaces/Command.java");
    }
}
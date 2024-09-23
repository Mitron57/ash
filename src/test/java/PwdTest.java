package test.java;

import Commands.Pwd;
import Interfaces.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.zip.ZipEntry;

import static org.mockito.Mockito.*;

public class PwdTest {
    private FileSystem fs;
    private Pwd pwd;

    @BeforeEach
    public void setUp() {
        fs = mock(FileSystem.class);
        pwd = new Pwd(fs);
    }

    @Test
    public void testExecute() {
        ZipEntry currentDir = mock(ZipEntry.class);
        when(fs.getCurrentDirectory()).thenReturn(currentDir);
        pwd.execute("");
    }
}
package Interfaces;

import java.io.InputStream;
import java.util.zip.ZipEntry;

public interface FileSystem {
    ZipEntry getCurrentDirectory();
    ZipEntry getFile(String path);
    boolean isDirectory(String path);
    byte changeDirectory(String path);
    ZipEntry[] getSubfiles(String path);
    InputStream openFile(ZipEntry path);
}

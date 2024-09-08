package FileSystem;

import EnvVariables.Variables;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileSystem implements Interfaces.FileSystem {
    private final ZipFile rootFile;
    private ZipEntry currentFile;
    private final FileTree tree;
    public FileSystem(String path) throws IOException {
        rootFile = new ZipFile(path);
        currentFile = rootFile.entries().nextElement();
        tree = new FileTree(currentFile);
        rootFile.stream().forEach(this::addFile);
        Variables.variables.put("HOME", currentFile.getComment().replace("/", ""));
    }

    private void addFile(ZipEntry entry) {
        tree.insert(entry);
    }

    @Override
    public ZipEntry getCurrentDirectory() {
        return currentFile;
    }

    @Override
    public ZipEntry getFile(String path) {
        return tree.getFile(path);
    }

    @Override
    public boolean isDirectory(String path) {
        return tree.getDirectory(path) != null;
    }

    @Override
    public byte changeDirectory(String path) {
        ZipEntry found = tree.getDirectory(path);
        if (found == null) {
            return 1;
        }
        currentFile = found;
        return 0;
    }

    @Override
    public ZipEntry[] getSubfiles(String path) {
        return tree.getSubfiles(path);
    }

    @Override
    public InputStream openFile(ZipEntry file) {
        if (file == null || file.isDirectory()) {
            return null;
        }
        try {
            return rootFile.getInputStream(file);
        } catch (IOException e) {
            return null;
        }
    }
}

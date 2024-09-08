package Commands;

import Utils.Parser;

import java.util.Arrays;
import java.util.zip.ZipEntry;

public class List implements Interfaces.Command {
    Interfaces.FileSystem fs;

    public List(Interfaces.FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void execute(String param) {
        if (param.isEmpty()) {
            printFiles(fs.getCurrentDirectory().getName().replaceFirst("/$", ""));
            return;
        }
        String newPath = Parser.parseToAbsolutePath(param, fs.getCurrentDirectory().toString());
        if (newPath == null) {
            System.out.println("ls: Invalid path");
            return;
        }
        printFiles(newPath);
    }

    private void printFiles(String path) {
        ZipEntry[] files = fs.getSubfiles(path);
        if (files == null) {
            System.out.println("ls: " + path.replace("//", "/") + ": No such file or directory");
            return;
        }
        Arrays.stream(files).forEach(elem -> System.out.print(elem.getComment() + " "));
        System.out.println();
    }
}

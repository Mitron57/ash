package Commands;

import Interfaces.Command;
import Utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;

public class Concatenate implements Command {
    Interfaces.FileSystem fs;

    public Concatenate(Interfaces.FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void execute(String param) {
        String newPath = Parser.parseToAbsolutePath(param, fs.getCurrentDirectory().toString());
        if (newPath == null) {
            System.out.println("cat: Invalid path");
            return;
        }
        ZipEntry file = fs.getFile(newPath);
        if (file == null) {
            System.out.println("cat: No such file or directory");
            return;
        }
        if (file.isDirectory()) {
            System.out.println("cat: " + param + ": Is a directory");
            return;
        }
        InputStream in = fs.openFile(file);
        if (in == null) {
            System.out.println("cat: Cannot open file: " + file.getName().replace("/", ""));
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line).append("\n");
        }
        System.out.println(sb);
    }
}

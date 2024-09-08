package Commands;

import EnvVariables.Variables;
import Interfaces.Command;
import Utils.Parser;

public class ChangeDirectory implements Command {
    Interfaces.FileSystem fs;
    String directory;

    public ChangeDirectory(Interfaces.FileSystem fs) {
        this.fs = fs;
        this.directory = fs.getCurrentDirectory().getName();
    }

    @Override
    public void execute(String param) {
        if (param.isEmpty()) {
            fs.changeDirectory(Variables.variables.get("HOME"));
            return;
        }
        String newPath = Parser.parseToAbsolutePath(param, fs.getCurrentDirectory().toString());
        if (newPath == null) {
            System.out.println("cd: Invalid path");
            return;
        }
        if (fs.changeDirectory(newPath) != 0) {
            System.out.println("cd: no such file or directory: " + param);
        }
    }
}

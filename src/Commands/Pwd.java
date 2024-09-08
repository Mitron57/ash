package Commands;

import Interfaces.Command;

public class Pwd implements Command {
    Interfaces.FileSystem fs;

    public Pwd(Interfaces.FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void execute(String param) {
        System.out.println(fs.getCurrentDirectory());
    }
}

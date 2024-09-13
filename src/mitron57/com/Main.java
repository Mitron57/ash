package mitron57.com;

import Commands.ChangeDirectory;
import Commands.Concatenate;
import Commands.Neofetch;
import Commands.Pwd;
import EnvVariables.Variables;
import FileSystem.FileSystem;
import Interfaces.Command;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static FileSystem fs = null;
    static HashMap<String, Command> commands = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No filesystem has been specified");
            return;
        }
        int result = parseStartArguments(args);
        if (result != 0) {
            return;
        }
        Scanner scan = new Scanner(System.in);
        String name = null, hostname = null;
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter your name: ");
            name = scan.nextLine();
            if (name.contains(" ")) {
                System.out.println("Invalid name");
                name = null;
            } else {
                break;
            }
        }
        if (name == null) {
            System.out.println("Invalid registration, goodbye!");
            return;
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter your hostname: ");
            hostname = scan.nextLine();
            if (hostname.contains(" ")) {
                System.out.println("Invalid hostname");
                hostname = null;
            } else {
                break;
            }
        }
        if (hostname == null) {
            System.out.println("Invalid registration, goodbye!");
            return;
        }
        registerCommands();
        while (true) {
            String currentDir = fs.getCurrentDirectory().getName();
            currentDir = currentDir.replace(Variables.variables.get("HOME"), "~" ).replaceFirst("/$", "");
            System.out.printf(name + "@" + hostname + ": " + currentDir + " $ ");
            String line = scan.nextLine().trim();
            if (line.equals("exit")) {
                break;
            }
            List<String> splited = Arrays.stream(line.split(" ")).collect(Collectors.toCollection(ArrayList::new));
            String cmd = splited.removeFirst();
            String params = String.join("", splited);
            if (!commands.containsKey(cmd)) {
                System.out.println("ash: command not found: " + cmd);
                continue;
            }
            commands.get(cmd).execute(params);
        }
    }

    private static int parseStartArguments(String[] args) throws IOException {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].startsWith("-")) {
                return switch (args[i]) {
                    case "-fs", "--filesystem" -> {
                        fs = new FileSystem(args[i + 1]);
                        yield 0;
                    }
                    case "--help", "-h" -> {
                        printHelp();
                        yield 1;
                    }
                    default -> {
                        System.out.println("Unknown option: " + args[i] + "\nRun with option --help to see supprted usage");
                        yield 1;
                    }
                };
            }
        }
        System.out.println("No filesystem has been specified");
        return 1;
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar mitron57.jar [options]");
        System.out.println("Options:");
        System.out.println("  -fs, --filesystem <path>   Specify the filesystem path");
        System.out.println("  -h, --help                 Show this help message");
        System.out.println();
        System.out.println("Supported commands:");
        System.out.println("  exit                       Exit the application");
        System.out.println("  pwd                        Print the current working directory");
        System.out.println("  ls <path>                  List files in <path>");
        System.out.println("  cd <path>                  Change the current directory to <path>");
        System.out.println("  cat <file>                 Display the contents of <file>");
        System.out.println("  neofetch                 Try it to see what happens XD. Don't worry it won't blow up your PC. Maybe...");
        // Add more commands as needed
    }

    private static void registerCommands() {
        commands.put("cd", new ChangeDirectory(fs));
        commands.put("pwd", new Pwd(fs));
        commands.put("ls", new Commands.List(fs));
        commands.put("neofetch", new Neofetch());
        commands.put("cat", new Concatenate(fs));
    }
}
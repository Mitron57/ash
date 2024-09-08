package Commands;

import Interfaces.Command;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Neofetch implements Command {
    @Override
    public void execute(String params) {
        List<String> javaCupLines = getJavaCupLines();
        List<String> systemInfoLines = getSystemInfoLines();

        int maxLines = Math.max(javaCupLines.size(), systemInfoLines.size());
        for (int i = 0; i < maxLines; i++) {
            String javaCupLine = i < javaCupLines.size() ? javaCupLines.get(i) : "";
            String systemInfoLine = i < systemInfoLines.size() ? systemInfoLines.get(i) : "";
            System.out.printf("%-25s %s%n", javaCupLine, systemInfoLine);
        }
    }

    private List<String> getJavaCupLines() {
        String javaCup = """
                 (((
                  )))
                 (((
              +-----+
              |     |]
              `-----'
        """;
        List<String> lines = new ArrayList<>();
        Collections.addAll(lines, javaCup.split("\n"));
        return lines;
    }

    private List<String> getSystemInfoLines() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        List<String> lines = new ArrayList<>();
        lines.add("\033[0;32mOS: " + osBean.getName() + " " + osBean.getVersion() + "\033[0m");
        lines.add("\033[0;32mArchitecture: " + osBean.getArch() + "\033[0m");
        lines.add("\033[0;32mAvailable processors (cores): " + osBean.getAvailableProcessors() + "\033[0m");
        lines.add("\033[0;32mSystem load average: " + osBean.getSystemLoadAverage() + "\033[0m");
        lines.add("\033[0;32mShell: ash 0.0.1\033[0m");
        return lines;
    }
}
package Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class Parser {
    public static Queue<String> parse(String input) {
        Queue<String> result = new LinkedList<>();
        return Arrays.stream(input.split("/")).filter(elem -> !elem.isEmpty()).collect(Collectors.toCollection(() -> result));
    }

    public static String parseToAbsolutePath(String input, String currentDir) {
        Queue<String> paths = Parser.parse(input);
        Stack<String> stack = new Stack<>();
        stack.addAll(Arrays.asList(currentDir.split("/")));
        while (!paths.isEmpty()) {
            String part = paths.poll();
            if (part.equals(".")) {
                continue;
            }
            if (part.equals("..") && !stack.isEmpty()) {
                stack.pop();
            } else {
                stack.push(part);
            }
        }
        String newPath = String.join("/", stack);
        return newPath.isEmpty() ? null : newPath;
    }
}

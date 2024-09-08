package FileSystem;

import Utils.Parser;

import java.util.*;
import java.util.zip.ZipEntry;

public class FileTree {
    static class Node {
        Node parent;
        ZipEntry data;
        List<Node> children;
        public Node(ZipEntry data, Node parent) {
            this.parent = parent;
            this.data = data;
            children = new ArrayList<>();
        }
    }
    private final Node root;

    public FileTree(ZipEntry root) {
        this.root = new Node(root, null);
        this.root.data.setComment(this.root.data.getName().replace("/", ""));
    }

    void insert(ZipEntry data) {
        if (root.data.toString().equals(data.toString())) {
            return;
        }
        Queue<String> paths = Parser.parse(data.toString());
        if (!(paths.peek() + "/").equals(root.data.toString())) {
            return;
        }
        paths.poll();
        var node = this.root;
        while (paths.size() > 1) {
            String filename = paths.poll();
            for (int i = 0; i < node.children.size(); i++) {
                var child = node.children.get(i);
                if (child.data.getComment().equals(filename)) {
                    node = child;
                    break;
                }
            }
        }
        data.setComment(paths.poll());
        node.children.add(new Node(data, node));
    }

    private Node getNode(String path) {
        Queue<String> paths = Parser.parse(path);
        paths.poll();
        var node = this.root;
        String last = node.data.getComment();
        while (!paths.isEmpty()) {
            String filename = paths.poll();
            last = filename;
            for (int i = 0; i < node.children.size(); i++) {
                var child = node.children.get(i);
                if (child.data.getComment().equals(filename)) {
                    node = child;
                }
            }
        }
        return node.data.getComment().equals(last) ? node : null;
    }

    ZipEntry getFile(String path) {
        Node node = getNode(path);
        return node == null ? null : node.data;
    }

    ZipEntry getDirectory(String path) {
        Node node = getNode(path);
        if (node == null) {
            return null;
        }
        if (node.data.isDirectory()) {
            return node.data;
        }
        return null;
    }

    ZipEntry[] getSubfiles(String path) {
        Node node = getNode(path);
        if (node == null || !node.data.isDirectory()) {
            return null;
        }
        return node.children.stream().map(child -> child.data).toArray(ZipEntry[]::new);
    }
}
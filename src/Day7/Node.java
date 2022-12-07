package Day7;

import java.util.ArrayList;
import java.util.List;

public class Node {
    // Type is a little bit redundant, but it makes the code a little bit easier to read. We could also use isLeaf and isRoot.
    private final String type;
    private int size;
    private String name;
    private List<Node> children = new ArrayList<Node>();
    private Node parent;

    public Node(int size, String name, String type) {
        this.size = size;
        this.name = name;
        this.type = type;
    }

    public Node(int size, String name, Node parent, String type) {
        this.size = size;
        this.name = name;
        this.parent = parent;
        this.type = type;
    }

    public Node(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public Node getChildByName(String name) {
        for (Node child : this.children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

}

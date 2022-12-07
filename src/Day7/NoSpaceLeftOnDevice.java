package Day7;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NoSpaceLeftOnDevice {

    public static void main(String[] args) {
        String filename = "src/Day7/Day7.txt";
        Node root = parseInputFile(filename);
        Node processedRoot = processTreeSizes(root);
        int findMaxTotalSize = 100000;
        int rootSize = processedRoot.getSize();
        // Part 1
        System.out.println("Total size of 100000: " + sumDirectoriesWithMaxX(processedRoot, findMaxTotalSize)); // 1206825
        // Part 2
        System.out.println("Smallest Directory's size that would let us install the update: " + findSmallestDirectoryToDelete(processedRoot, 70000000, 30000000, rootSize).getSize()); // 9608311
    }

    /**
     * Method findSmallestDirectoryToDelete finds the smallest directory (type = "dir"), that if deleted would free up enough space on the filesystem to run the update.
     * Part 2 of Day 7.
     *
     * @param root                The root node of the tree
     * @param fileSystemSize      The size of the file system
     * @param minUnusedSpace      The minimum amount of unused space we want to have
     * @param fileSystemUsedSpace The amount of space that is already used
     * @return The smallest directory that we can delete to free up enough space
     */
    public static Node findSmallestDirectoryToDelete(Node root, int fileSystemSize, int minUnusedSpace, int fileSystemUsedSpace) {
        int unusedSpace = fileSystemSize - fileSystemUsedSpace;
        if (unusedSpace >= minUnusedSpace) {
            return root;
        }
        double smallestSize = Double.MAX_VALUE;
        Node smallestNode = root;
        for (Node child : root.getChildren()) {
            if (!child.isLeaf()) {
                Node smallestChild = findSmallestDirectoryToDelete(child, fileSystemSize, minUnusedSpace, fileSystemUsedSpace);
                if (smallestChild != null && smallestChild.getSize() < smallestSize && smallestChild.getSize() >= minUnusedSpace - unusedSpace) {
                    smallestSize = smallestChild.getSize();
                    smallestNode = smallestChild;
                }
            }
        }
        return smallestNode;
    }

    /**
     * Sum all of the nodes of type "dir" with a total size of at most x
     * Part 1 of Day 7
     *
     * @param root The root node of the tree
     * @param x    The maximum total size of a directory that we want to find (and sum)
     * @return The total size of the tree
     */
    public static int sumDirectoriesWithMaxX(Node root, int x) {
        int sum = 0;
        if (!root.isLeaf()) {
            if (root.getSize() <= x) {
                sum += root.getSize();
            }
            for (Node child : root.getChildren()) {
                sum += sumDirectoriesWithMaxX(child, x);
            }
        }
        return sum;
    }

    /**
     * Creates a tree of nodes from the input file based on the cmd line arguments
     *
     * @param filename The name of the file to parse
     * @return The root node of the tree
     */
    public static Node parseInputFile(String filename) {
        // Create a new Node root
        Node root = new Node("/", "dir");
        // Read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Check whether the line is a command. Commands start with $
                String[] lineArray = line.split(" ");
                if (line.charAt(0) == '$') {
                    // Split the line into an array of strings
                    // Check whether the command is cd
                    if (lineArray[1].equals("cd")) {
                        // Check whether the argument is ..
                        switch (lineArray[2]) {
                            case ".." ->
                                // Move up one level
                                    root = root.getParent();
                            case "/" -> {
                                // Move to the outermost directory
                                while (root.getParent() != null) {
                                    root = root.getParent();
                                }
                            }
                            case "ls" -> {
                                // If ls is the command, print the contents of the current directory
                                // Print the contents of the current directory using a for loop
                                for (int i = 0; i < root.getChildren().size(); i++) {
                                    System.out.println(root.getChildren().get(i).getName());
                                }
                            }
                            default ->
                                // Move down to the child with the name that equals the argument
                                    root = root.getChildByName(lineArray[2]);
                        }
                    }
                } else {
                    // Split the line into an array of strings
                    // Check whether the line is a directory
                    Node newNode;
                    if (lineArray[0].equals("dir")) {
                        // Create a new Node for the directory
                        newNode = new Node(0, lineArray[1], root, "dir");
                        // Add the new Node to the current Node
                    } else {
                        // Create a new Node for the file
                        newNode = new Node(Integer.parseInt(lineArray[0]), lineArray[1], root, "file");

                    }
                    // Add the new Node to the current Node
                    root.addChild(newNode);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return back to the top of the tree
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    /**
     * Process tree sizes adds the size of the directory to each node of type "dir" in the tree. The size is the size of its files and all of its directories and so on using recursion
     *
     * @param root The root node of the tree
     * @return The root node of the tree with the size of each node calculated
     */
    public static Node processTreeSizes(Node root) {
        if (!root.isLeaf()) {
            for (Node child : root.getChildren()) {
                root.setSize(root.getSize() + processTreeSizes(child).getSize());
            }
        } else {
            root.setSize(root.getSize());
        }
        return root;
    }
}

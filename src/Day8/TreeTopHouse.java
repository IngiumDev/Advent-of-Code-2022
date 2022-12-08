package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TreeTopHouse {
    public static void main(String[] args) {
        String filename = "src/Day8/Day8.txt";
        ArrayList<ArrayList<Integer>> map = loadTreeFile(filename);
        int treesVisible = countVisibleTrees(map);
        System.out.println("Trees visible: " + treesVisible);
        // Part Two
        System.out.println("Highest Scenic Score: " + findHighestScenicScorePossible(map));
    }

    /**
     * Find the highest scenic score possible of the forest
     * A tree's scenic score is found by multiplying together its viewing distance in each of the four directions
     *
     * @param map The map of the forest
     * @return The highest scenic score possible
     */
    public static int findHighestScenicScorePossible(ArrayList<ArrayList<Integer>> map) {
        int highestScenicScore = 0;
        // For each tree, find the highest scenic score possible, excluding the edges
        for (int row = 1; row < map.size() - 1; row++) {
            for (int col = 1; col < map.get(row).size() - 1; col++) {
                // Calculate the scenic score possible for this tree
                int scenicScore = calculateScenicScore(map, row, col);
                // If it's the highest so far, update the highestScenicScore
                if (scenicScore > highestScenicScore) {
                    highestScenicScore = scenicScore;
                }
            }
        }
        return highestScenicScore;
    }

    /**
     * Calculate the scenic score for a tree at a given position
     * To measure the viewing distance from a given tree, look up, down, left, and right from that tree; stop if you reach an edge or at the first tree that is the same height or taller than the tree under consideration.
     * (If a tree is right on the edge, at least one of its viewing distances will be zero.)
     * The scenic score for a tree is the product of its viewing distances in each of the four directions.
     *
     * @param map The map of the forest
     * @param x   The x coordinate of the tree
     * @param y   The y coordinate of the tree
     * @return The scenic score of the tree at (x, y)
     */
    public static int calculateScenicScore(ArrayList<ArrayList<Integer>> map, int x, int y) {
        int treeHeight = map.get(x).get(y);
        int upScore = 0;
        int downScore = 0;
        int leftScore = 0;
        int rightScore = 0;
        // Look up
        for (int i = x - 1; i >= 0; i--) {
            upScore++;
            if (map.get(i).get(y) >= treeHeight) {
                break;
            }

        }
        // Look left
        for (int i = y - 1; i >= 0; i--) {
            leftScore++;
            if (map.get(x).get(i) >= treeHeight) {
                break;
            }

        }
        // Look down
        for (int i = x + 1; i < map.size(); i++) {
            downScore++;
            if (map.get(i).get(y) >= treeHeight) {
                break;
            }

        }
        // Look right
        for (int i = y + 1; i < map.get(x).size(); i++) {
            rightScore++;
            if (map.get(x).get(i) >= treeHeight) {
                break;
            }

        }
        // A tree's scenic score is found by multiplying together its viewing distance in each of the four directions.
        return upScore * downScore * leftScore * rightScore;
    }

    /**
     * Count the number of trees visible from outside the forest
     * A tree is visible when all the trees in the same row or column are shorter than it
     * That can be in any direction i.e. if a tree is visible in one direction, we consider it visible
     *
     * @param map The map of the forest
     * @return The number of trees visible from outside the forest
     */
    public static int countVisibleTrees(ArrayList<ArrayList<Integer>> map) {
        int count = 0;
        for (int i = 1; i < map.size() - 1; i++) {
            for (int j = 1; j < map.get(i).size() - 1; j++) {
                if (isTreeVisible(map, i, j)) {
                    count++;
                }
            }
        }
        // Add all the trees on the edges of the grid
        count += map.size() * 2 + map.get(0).size() * 2 - 4;
        return count;
    }

    /**
     * Check if a tree is visible from outside the forest
     * A tree is visible when all the trees in the same row or column are shorter than it
     * That can be in any direction i.e. if a tree is visible in one direction, we consider it visible
     *
     * @param map The map of the forest
     * @param i   The row of the tree
     * @param j   The column of the tree
     * @return True if the tree at (i, j) is visible from outside the forest
     */
    public static boolean isTreeVisible(ArrayList<ArrayList<Integer>> map, int i, int j) {
        int treeHeight = map.get(i).get(j);
        // A tree is visible if it is taller than all the trees either above or below it or to the left or right of it
        // We need to check above and below, and left and right separately because it is visible if it either of these conditions are true
        // Check above
        boolean visible = true;
        for (int k = i - 1; k >= 0; k--) {
            if (map.get(k).get(j) >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // Check down
        visible = true;
        for (int k = i + 1; k < map.size(); k++) {
            if (map.get(k).get(j) >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // Check left
        visible = true;
        for (int k = j - 1; k >= 0; k--) {
            if (map.get(i).get(k) >= treeHeight) {
                visible = false;
                break;
            }
        }
        if (visible) {
            return true;
        }
        // Check right
        visible = true;
        for (int k = j + 1; k < map.get(i).size(); k++) {
            if (map.get(i).get(k) >= treeHeight) {
                visible = false;
                break;
            }
        }
        return visible;
    }

    /**
     * @param filename The name of the file to read
     * @return The map of the forest as a 2D ArrayList of Integers
     */
    public static ArrayList<ArrayList<Integer>> loadTreeFile(String filename) {
        ArrayList<ArrayList<Integer>> treeMap = new ArrayList<>();
        // load the file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                ArrayList<Integer> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    // Add the height of the tree to the row
                    row.add(Integer.parseInt(line.substring(i, i + 1)));
                }
                treeMap.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treeMap;
    }

}

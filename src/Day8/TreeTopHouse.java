package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* --- Day 8: Treetop Tree House ---

The expedition comes across a peculiar patch of tall trees all planted carefully in a grid. The Elves explain that a previous expedition planted these trees as a reforestation effort. Now, they're curious if this would be a good location for a tree house.

First, determine whether there is enough tree cover here to keep a tree house hidden. To do this, you need to count the number of trees that are visible from outside the grid when looking directly along a row or column.

The Elves have already launched a quadcopter to generate a map with the height of each tree (your puzzle input). For example:

30373
25512
65332
33549
35390

Each tree is represented as a single digit whose value is its height, where 0 is the shortest and 9 is the tallest.

A tree is visible if all of the other trees between it and an edge of the grid are shorter than it. Only consider trees in the same row or column; that is, only look up, down, left, or right from any given tree.

All of the trees around the edge of the grid are visible - since they are already on the edge, there are no trees to block the view. In this example, that only leaves the interior nine trees to consider:

    The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since other trees of height 5 are in the way.)
    The top-middle 5 is visible from the top and right.
    The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of height 0 between it and an edge.
    The left-middle 5 is visible, but only from the right.
    The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at most height 2 between it and an edge.
    The right-middle 3 is visible from the right.
    In the bottom row, the middle 5 is visible, but the 3 and 4 are not.

With 16 trees visible on the edge and another 5 visible in the interior, a total of 21 trees are visible in this arrangement.

Consider your map; how many trees are visible from outside the grid?
*/
public class TreeTopHouse {
    public static void main(String[] args) {
        String filename = "src/Day8/Day8.txt";
        ArrayList<ArrayList<Integer>> map = loadTreeFile(filename);
        int treesVisible = countVisibleTrees(map);
        System.out.println("Trees visible: " + treesVisible);
    }
    // Count the trees that are visible from the outside of the grid
    // A tree is visible when all the trees in the same row or column are shorter than it
    public static int countVisibleTrees(ArrayList<ArrayList<Integer>> map) {
        int count = 0;
        for (int i = 1; i < map.size() - 1; i++) {
            for (int j = 1; j < map.get(i).size() - 1; j++) {
                if(isTreeVisible(map, i, j)) {
                    count++;
                }
            }
        }
        // Add all the trees on the edges of the grid
        count += map.size() * 2 + map.get(0).size() * 2 - 4;
        return count;
    }
    // Method isTreeVisible
    // A tree is visible when all the trees in the same row or column are shorter than it
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
    // Load in the input file into a 2D ArrayList of Integers
    public static ArrayList<ArrayList<Integer>> loadTreeFile(String filename) {
        ArrayList<ArrayList<Integer>> treeMap = new ArrayList<>();
        // load the file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
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

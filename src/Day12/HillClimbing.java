package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HillClimbing {
    // Parse the input file and create a Map<Point, Integer> of the points
    public static void main(String[] args) {
        String filename = "src/Day12/Day12.txt";
        HashMap<ArrayList<Integer>, Integer> grid = parseInputFile(filename);
        ArrayList<ArrayList<Integer>> stardAndEnd = findStartAndEnd(filename);
        int[] WIDTH_AND_HEIGHT = findGridSize("src/Day12/Day12.txt");
        System.out.println(" Running Hill Climbing Algorithm");
        System.out.println(findShortestPath(grid, stardAndEnd.get(0), stardAndEnd.get(1), true, WIDTH_AND_HEIGHT[0], WIDTH_AND_HEIGHT[1]));
        System.out.println(" Running Hill Climbing Algorithm with a twist");
        System.out.println(findShortestPath(grid, stardAndEnd.get(0), stardAndEnd.get(1), false, WIDTH_AND_HEIGHT[0], WIDTH_AND_HEIGHT[1]));
    }

    // Return an int[] with the width and height of the grid
    public static int[] findGridSize(String filename) {
        int[] size = new int[2];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int height = 0;
            while ((line = br.readLine()) != null) {
                height++;
                size[0] = line.length();
            }
            size[1] = height;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    // Find the start and end points and return them in a Point array
    public static ArrayList<ArrayList<Integer>> findStartAndEnd(String fileName) {
        ArrayList<ArrayList<Integer>> startAndEnd = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == 'S') {
                        ArrayList<Integer> start = new ArrayList<>();
                        start.add(lineCount);
                        start.add(i);
                        startAndEnd.add(start);
                    } else if (c == 'E') {
                        ArrayList<Integer> end = new ArrayList<>();
                        end.add(lineCount);
                        end.add(i);
                        startAndEnd.add(end);
                    }

                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startAndEnd;
    }

    // Find the shortest path from the start to the end using Dijkstra's algorithm
    public static int findShortestPath(HashMap<ArrayList<Integer>, Integer> grid, ArrayList<Integer> start, ArrayList<Integer> end, boolean part1, int WIDTH, int HEIGHT) {

        HashMap<ArrayList<Integer>, Integer> shortestPath = new HashMap<>();
        shortestPath.put(start, 0);
        // Create a priority queue of the points to visit
        ArrayList<ArrayList<Integer>> queue = new ArrayList<>();
        queue.add(start);
        while (queue.size() > 0) {
            ArrayList<Integer> p = queue.get(0);
            queue.remove(0);
            int pX = p.get(0);
            int pY = p.get(1);
            // Check the four adjacent points
            ArrayList<Integer> up = new ArrayList<>();
            up.add(pX - 1);
            up.add(pY);
            ArrayList<Integer> down = new ArrayList<>();
            down.add(pX + 1);
            down.add(pY);
            ArrayList<Integer> left = new ArrayList<>();
            left.add(pX);
            left.add(pY - 1);
            ArrayList<Integer> right = new ArrayList<>();
            right.add(pX);
            right.add(pY + 1);
            if (pX != 0) {
                processPointMovement(p, up, shortestPath, queue, grid, part1);
            }
            if (pX != HEIGHT - 1) {
                processPointMovement(p, down, shortestPath, queue, grid, part1);
            }
            if (pY != 0) {
                processPointMovement(p, left, shortestPath, queue, grid, part1);
            }
            if (pY != WIDTH - 1) {
                processPointMovement(p, right, shortestPath, queue, grid, part1);
            }
        }
        return shortestPath.get(end);
    }

    public static void processPointMovement(ArrayList<Integer> p, ArrayList<Integer> dir, HashMap<ArrayList<Integer>, Integer> shortestPath, ArrayList<ArrayList<Integer>> queue, HashMap<ArrayList<Integer>, Integer> grid, boolean part1) {
        int heightAtP = grid.get(p);
        // Printing out the height of the point
        int heightAtDir = grid.get(dir);
        if (heightAtDir - heightAtP <= 1) // We can go down as many as we want, but only up 1
        {
            int pathLength = shortestPath.get(p) + 1;
            if (shortestPath.getOrDefault(dir, Integer.MAX_VALUE) > pathLength) {
                queue.add(dir);
                if (!part1 && heightAtDir == 0) shortestPath.put(dir, 0);
                else shortestPath.put(dir, pathLength);
            }
        }
    }

    public static HashMap<ArrayList<Integer>, Integer> parseInputFile(String filename) {
        char[] lowerCase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        HashMap<ArrayList<Integer>, Integer> grid = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Go through each line of the file and add each character to the input arraylist by row
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == 'S') {
                        c = 'a';
                    } else if (c == 'E') {
                        c = 'z';
                    }
                    // Find the index of the character in the alphabet
                    int index = 0;
                    for (int j = 0; j < lowerCase.length; j++) {
                        if (c == lowerCase[j]) {
                            index = j;
                            break;
                        }
                    }
                    ArrayList<Integer> point = new ArrayList<>();
                    point.add(lineCount);
                    point.add(i);
                    grid.put(point, index);
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }


}

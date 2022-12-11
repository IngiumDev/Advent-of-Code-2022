package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RopeBridge {
    public static void main(String[] args) {
        // Start with a 2D ArrayList of 1x1 with B as the first element because head and tail are touching
        ArrayList<ArrayList<String>> grid = new ArrayList<>();
        // Create a second grid to keep track of where the tail has been
        ArrayList<ArrayList<String>> tailGrid = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        row1.add("B");
        tailGrid.add(row1);
        ArrayList<String> row2 = new ArrayList<>();
        String filename = "src/Day9/Day9.txt";
        ArrayList<String[]> instructions = parseInputFile(filename);
        row2.add("B");
        grid.add(row2);
        processInstructions(grid, instructions, tailGrid);
        System.out.println("Number of positions the tail visited at least once: " + countTailVisited(tailGrid)); // 6284

    }
    // Method counts where the tail of the rope visits at least once (cells with #)

    public static void countTailVisitedPartTwo(ArrayList<String[]> input) {
        HashMap<String, int[]> directions = new HashMap<>();
        directions.put("D", new int[]{0, -1});
        directions.put("U", new int[]{0, 1});
        directions.put("L", new int[]{-1, 0});
        directions.put("R", new int[]{1, 0});
        // pos = [(0, 0) for i in range(10)] but in Java
        int[][] pos = new int[10][2];
        


    }
    public static int countTailVisited(ArrayList<ArrayList<String>> grid) {
        int count = 0;
        for (ArrayList<String> row : grid) {
            for (String cell : row) {
                if (Objects.equals(cell, "#") || Objects.equals(cell, "B") || Objects.equals(cell, "T")) {
                    count++;
                }
            }
        }
        return count + 1;
    }

    // Value in grid - H when head, T when tail, # when visited, . when empty, and B when both are there
    // Method to process the instructions and move the head and tail of the rope around and arraylist
    // Method moves the head right

    public static void processInstructions(ArrayList<ArrayList<String>> grid, ArrayList<String[]> instructions, ArrayList<ArrayList<String>> tailGrid) {
        // Iterate through the instructions
        for (String[] instruction : instructions) {
            // Get the direction and the number of steps
            String direction = instruction[0];
            int steps = Integer.parseInt(instruction[1]);
            // print out the direction and number of steps
            // Move the head in the direction
            switch (direction) {
                case "R" -> {
                    // Move the head right steps number of times
                    for (int i = 0; i < steps; i++) {
                        // Move the head right
                        moveHeadRight(grid, tailGrid);
                    }
                }
                case "L" -> {
                    // Move the head left steps number of times
                    for (int i = 0; i < steps; i++) {
                        // Move the head left
                        moveHeadLeft(grid, tailGrid);
                    }
                }
                case "U" -> {
                    // Move the head up steps number of times
                    for (int i = 0; i < steps; i++) {
                        // Move the head up
                        moveHeadUp(grid, tailGrid);
                    }
                }
                case "D" -> {
                    // Move the head down steps number of times
                    for (int i = 0; i < steps; i++) {
                        // Move the head down
                        moveHeadDown(grid, tailGrid);
                    }
                }
            }
            // print out the grid

        }
    }

    public static void moveHeadRight(ArrayList<ArrayList<String>> grid, ArrayList<ArrayList<String>> tailGrid) {

        // Get the current position of the head
        int[] headPosition = getHeadPosition(grid);
        // Get the current position of the tail
        int[] tailPosition = getTailPosition(grid);
        assert tailPosition != null;
        tailGrid.get(tailPosition[0]).set(tailPosition[1], "#");
        // Check if the head is touching the tail
        assert headPosition != null;
        boolean touching = isTouching(headPosition, tailPosition);
        boolean isAdjacent = isAdjacent(headPosition, tailPosition);
        boolean isDiagonal = isDiagonal(headPosition, tailPosition);
        // Iterate through the number of steps

        // If there is no column to the right, add a new column
        if (headPosition[1] + 1 >= grid.get(0).size()) {
            addColumnRight(grid);
            addColumnRight(tailGrid);
            // Get the new head position
            headPosition = getHeadPosition(grid);
            // Get the new tail position
            tailPosition = getTailPosition(grid);
            assert tailPosition != null;
            tailGrid.get(tailPosition[0]).set(tailPosition[1], "#");
        }
        // Move the head to the right
        assert headPosition != null;
        headPosition[1]++;
        // Set old head position to empty
        grid.get(headPosition[0]).set(headPosition[1] - 1, ".");
        // Checks if they would be adjacent or diagonal after the move
        boolean adjOrDiag = isAdjacentOrDiagonal(headPosition, tailPosition);

        // If they would not be adjacent or diagonal
        // If they were adjacent before the move, move the tail to the right
        // If they were diagonal before the move, move the tail next to the head on the left
        grid.get(headPosition[0]).set(headPosition[1], "H");
        if (!adjOrDiag) {
            // If they were adjacent before the move
            if (isAdjacent) {
                // Move the tail to the right
                tailPosition[1]++;
                // Set old tail position to empty
                grid.get(tailPosition[0]).set(tailPosition[1] - 1, "#");
                // Set new tail position to tail
                grid.get(tailPosition[0]).set(tailPosition[1], "T");

            } // If they were diagonal before the move
            else if (isDiagonal) {
                // Move the tail next to the head on the left
                tailPosition[1]--;
                // Set old tail position to empty
                grid.get(tailPosition[0]).set(tailPosition[1] + 1, "#");
                // Set new tail position to tail
                tailPosition = new int[]{headPosition[0], headPosition[1] - 1};
                grid.get(tailPosition[0]).set(tailPosition[1], "T");
            }
        }

        // If the tail is would not be adjacent or diagonal to the head, move the tail to the right

        if (isTouching(headPosition, tailPosition)) {
            grid.get(headPosition[0]).set(headPosition[1], "B");
        }

        // If the head and tail were touching, set the tail to T because it was B before
        if (touching) {
            grid.get(tailPosition[0]).set(tailPosition[1], "T");
        }
        // Set the head to H


    }

    // Method moves the head left
    public static void moveHeadLeft(ArrayList<ArrayList<String>> grid, ArrayList<ArrayList<String>> tailGrid) {
        // Get the current position of the head
        int[] headPosition = getHeadPosition(grid);
        // Get the current position of the tail
        int[] tailPosition = getTailPosition(grid);
        assert tailPosition != null;
        tailGrid.get(tailPosition[0]).set(tailPosition[1], "#");
        assert headPosition != null;
        boolean isAdjacent = isAdjacent(headPosition, tailPosition);
        boolean isDiagonal = isDiagonal(headPosition, tailPosition);
        // Check if the head is touching the tail
        boolean touching = isTouching(headPosition, tailPosition);
        // Iterate through the number of steps

        // If there is no column to the left, add a new column
        if (headPosition[1] - 1 < 0) {
            addColumnLeft(grid);
            addColumnLeft(tailGrid);
            // Get the new head position
            headPosition = getHeadPosition(grid);
            // Get the new tail position
            tailPosition = getTailPosition(grid);
        }
        // Move the head to the left
        assert headPosition != null;
        headPosition[1]--;
        // Set old head position to empty
        grid.get(headPosition[0]).set(headPosition[1] + 1, ".");
        // Checks if they would be adjacent or diagonal after the move
        assert tailPosition != null;
        boolean adjOrDiag = isAdjacentOrDiagonal(headPosition, tailPosition);

        // If the tail is would not be adjacent or diagonal to the head, move the tail to the left
        grid.get(headPosition[0]).set(headPosition[1], "H");
        // If they would not be adjacent or diagonal
        // If they were adjacent before the move, move the tail to the left
        // If they were diagonal before the move, move the tail next to the head on the right
        if (!adjOrDiag) {
            // If they were adjacent before the move
            if (isAdjacent) {
                // Move the tail to the left
                tailPosition[1]--;
                // Set old tail position to empty
                grid.get(tailPosition[0]).set(tailPosition[1] + 1, "#");
                // Set new tail position to tail
                grid.get(tailPosition[0]).set(tailPosition[1], "T");

            } // If they were diagonal before the move
            else if (isDiagonal) {
                // Move the tail next to the head on the right
                tailPosition[1]++;
                // Set old tail position to empty
                grid.get(tailPosition[0]).set(tailPosition[1] - 1, "#");
                // Set new tail position to tail
                tailPosition = new int[]{headPosition[0], headPosition[1] + 1};
                grid.get(tailPosition[0]).set(tailPosition[1], "T");
            }
        }
        if (isTouching(headPosition, tailPosition)) {
            grid.get(headPosition[0]).set(headPosition[1], "B");
        }

        // If the head and tail were touching, set the tail to T because it was B before
        if (touching) {
            grid.get(tailPosition[0]).set(tailPosition[1], "T");
        }
        // Set the head to H


    }

    // Method moves the head up
    public static void moveHeadUp(ArrayList<ArrayList<String>> grid, ArrayList<ArrayList<String>> tailGrid) {
        // Get the current position of the head
        int[] headPosition = getHeadPosition(grid);
        // Get the current position of the tail
        int[] tailPosition = getTailPosition(grid);
        assert tailPosition != null;
        tailGrid.get(tailPosition[0]).set(tailPosition[1], "#");
        // Check if the head is touching the tail
        assert headPosition != null;
        boolean isAdjacent = isAdjacent(headPosition, tailPosition);
        boolean isDiagonal = isDiagonal(headPosition, tailPosition);
        boolean touching = isTouching(headPosition, tailPosition);

        // If there is no row above, add a new row
        if (headPosition[0] - 1 < 0) {
            addRowTop(grid);
            addRowTop(tailGrid);
            // Get the new head position
            headPosition = getHeadPosition(grid);
            // Get the new tail position
            tailPosition = getTailPosition(grid);
        }
        // Move the head up
        assert headPosition != null;
        headPosition[0]--;
        // Set old head position to empty
        grid.get(headPosition[0] + 1).set(headPosition[1], ".");
        // Checks if they would be adjacent or diagonal after the move
        assert tailPosition != null;
        boolean adjOrDiag = isAdjacentOrDiagonal(headPosition, tailPosition);

        // If the tail is would not be adjacent or diagonal to the head, move the tail up
        grid.get(headPosition[0]).set(headPosition[1], "H");
        // If they would not be adjacent or diagonal
        // If they were adjacent before the move, move the tail to the up
        // If they were diagonal before the move, move the tail next to the head on the bottom
        if (!adjOrDiag) {
            // If they were adjacent before the move
            if (isAdjacent) {
                // Move the tail up
                tailPosition[0]--;
                // Set old tail position to empty
                grid.get(tailPosition[0] + 1).set(tailPosition[1], "#");
                // Set new tail position to tail
                grid.get(tailPosition[0]).set(tailPosition[1], "T");

            } // If they were diagonal before the move
            else if (isDiagonal) {
                // Set old tail position to empty
                grid.get(tailPosition[0]).set(tailPosition[1], "#");
                // Set new tail position to tail
                tailPosition = new int[]{headPosition[0] + 1, headPosition[1]};
                // If row below the head is empty, add a new row

                grid.get(tailPosition[0]).set(tailPosition[1], "T");
            }
        }
        if (isTouching(headPosition, tailPosition)) {
            grid.get(headPosition[0]).set(headPosition[1], "B");
        }

        // If the head and tail were touching, set the tail to T because it was B before
        if (touching) {
            grid.get(tailPosition[0]).set(tailPosition[1], "T");
        }
        // Set the head to H


    }

    // Method moves the head down
    public static void moveHeadDown(ArrayList<ArrayList<String>> grid, ArrayList<ArrayList<String>> tailGrid) {
        // Get the current position of the head
        int[] headPosition = getHeadPosition(grid);
        // Get the current position of the tail
        int[] tailPosition = getTailPosition(grid);
        assert tailPosition != null;
        tailGrid.get(tailPosition[0]).set(tailPosition[1], "#");
        // Check if the head is touching the tail
        assert headPosition != null;
        boolean isAdjacent = isAdjacent(headPosition, tailPosition);
        boolean isDiagonal = isDiagonal(headPosition, tailPosition);
        boolean touching = isTouching(headPosition, tailPosition);
        // Iterate through the number of steps

        // If there is no row below, add a new row
        if (headPosition[0] + 1 >= grid.size()) {
            addRowBottom(grid);
            addRowBottom(tailGrid);
            // Get the new head position
            headPosition = getHeadPosition(grid);
            // Get the new tail position
            tailPosition = getTailPosition(grid);
        }
        // Move the head down
        assert headPosition != null;
        headPosition[0]++;
        // Set old head position to empty
        grid.get(headPosition[0] - 1).set(headPosition[1], ".");
        // Checks if they would be adjacent or diagonal after the move
        assert tailPosition != null;
        boolean adjOrDiag = isAdjacentOrDiagonal(headPosition, tailPosition);

        // Set the head to H
        grid.get(headPosition[0]).set(headPosition[1], "H");
        if (isTouching(headPosition, tailPosition)) {
            grid.get(headPosition[0]).set(headPosition[1], "B");
        }
        // If they would not be adjacent or diagonal
        // If they were adjacent before the move, move the tail down
        // If they were diagonal before the move, move the tail next to the head on the top
        if (!adjOrDiag) {
            // If they were adjacent before the move
            if (isAdjacent) {
                // Move the tail down
                tailPosition[0]++;
                // Set old tail position to empty
                grid.get(tailPosition[0] - 1).set(tailPosition[1], "#");
                // Set new tail position to tail
                grid.get(tailPosition[0]).set(tailPosition[1], "T");

            } // If they were diagonal before the move
            else if (isDiagonal) {
                // Move the tail next to the head on the top
                tailPosition[0]--;
                // Set old tail position to empty
                grid.get(tailPosition[0] + 1).set(tailPosition[1], "#");
                // Set new tail position to tail
                tailPosition = new int[]{headPosition[0] - 1, headPosition[1]};
                grid.get(tailPosition[0]).set(tailPosition[1], "T");
            }
        }
        if (touching) {
            grid.get(tailPosition[0]).set(tailPosition[1], "T");
        }


    }

    // Method gets the position of the head
    public static int[] getHeadPosition(ArrayList<ArrayList<String>> grid) {
        // Iterate through the grid
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                // If the current element is H, return the position or B, return the position (B because head and tail are touching)
                if (grid.get(i).get(j).equals("H") || grid.get(i).get(j).equals("B")) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Method gets the position of the tail
    public static int[] getTailPosition(ArrayList<ArrayList<String>> grid) {
        // Iterate through the grid using for each loop
        for (ArrayList<String> row : grid) {
            for (String element : row) {
                // If the current element is T, return the position or B, return the position (B because head and tail are touching)
                if (element.equals("T") || element.equals("B")) {
                    // Prints the position of the tail
                    return new int[]{grid.indexOf(row), row.indexOf(element)};
                }
            }
        }
        return null;
    }

    // Method checks if the head is touching the tail
    public static boolean isTouching(int[] headPosition, int[] tailPosition) {
        // If the head and tail are touching, return true
        return headPosition[0] == tailPosition[0] && headPosition[1] == tailPosition[1];
        // If the head and tail are not touching, return false
    }

    // Checks if the tail is adjacent or diagonal to the head
    public static boolean isAdjacentOrDiagonal(int[] headPosition, int[] tailPosition) {
        // If the head and tail are adjacent or diagonal, return true
        return Math.abs(headPosition[0] - tailPosition[0]) <= 1 && Math.abs(headPosition[1] - tailPosition[1]) <= 1;
    }

    // Checks if the head is adjacent to the tail. If they are diagonal, return false
    public static boolean isAdjacent(int[] headPosition, int[] tailPosition) {
        // If the head and tail are adjacent, return true
        return Math.abs(headPosition[0] - tailPosition[0]) <= 1 && Math.abs(headPosition[1] - tailPosition[1]) <= 1 && !(Math.abs(headPosition[0] - tailPosition[0]) == 1 && Math.abs(headPosition[1] - tailPosition[1]) == 1);
    }

    // Checks if the head is diagonal to the tail. If they are adjacent, return false
    public static boolean isDiagonal(int[] headPosition, int[] tailPosition) {
        // If the head and tail are diagonal, return true
        return Math.abs(headPosition[0] - tailPosition[0]) == 1 && Math.abs(headPosition[1] - tailPosition[1]) == 1;
    }

    // Add column to the left of an inputted 2D ArrayList<ArrayList<String>>
    public static void addColumnLeft(ArrayList<ArrayList<String>> grid) {
        // Iterate through the grid
        for (ArrayList<String> strings : grid) {
            // Add a new element to the beginning of the row
            strings.add(0, ".");
        }
    }

    // Add column to the right of an inputted 2D ArrayList<ArrayList<String>>
    public static void addColumnRight(ArrayList<ArrayList<String>> grid) {
        // Iterate through the grid
        for (ArrayList<String> strings : grid) {
            // Add a new element to the end of the row
            strings.add(".");
        }
    }

    // Add row to the top of an inputted 2D ArrayList<ArrayList<String>>
    public static void addRowTop(ArrayList<ArrayList<String>> grid) {
        // Create a new row
        ArrayList<String> newRow = new ArrayList<>();
        // Add the correct number of elements to the row
        for (int i = 0; i < grid.get(0).size(); i++) {
            newRow.add(".");
        }
        // Add the new row to the top of the grid
        grid.add(0, newRow);
    }

    // Add row to the bottom of an inputted 2D ArrayList<ArrayList<String>>
    public static void addRowBottom(ArrayList<ArrayList<String>> grid) {
        // Create a new row
        ArrayList<String> newRow = new ArrayList<>();
        // Add the correct number of elements to the row
        for (int i = 0; i < grid.get(0).size(); i++) {
            newRow.add(".");
        }
        // Add the new row to the bottom of the grid
        grid.add(newRow);
    }

    // Method prints the grid
    public static void printGrid(ArrayList<ArrayList<String>> grid) {
        // Iterate through the grid
        for (ArrayList<String> strings : grid) {
            for (String string : strings) {
                // Print the current element
                System.out.print(string);
            }
            // Print a new line
            System.out.println();
        }
    }

    /**
     * @param filename The name of the file to read
     * @return An ArrayList of Strings which is the list of instructions
     */
    public static ArrayList<String[]> parseInputFile(String filename) {
        ArrayList<String[]> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                input.add(split);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}

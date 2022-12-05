package Day5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class SupplyStacks {
    public static void main(String[] args) {
        String fileName = "src\\Day5\\Day5.txt";
        // Part one
        System.out.println(partOne(fileName)); // Output: JRVNHHCSJ
        System.out.println(partTwo(fileName)); // Output: GNFBSBJLH
    }

    // Part One overarching method that combines the previous methods to process all the data and then return the top of each stack
    public static String partOne(String filename) {
        // Gets the stack data from the file
        String[] stackData = getStackData(loadFile(filename));
        // Creates the initial stack
        ArrayList<Stack<String>> initialStack = createInitialStack(stackData);
        // Gets the movement data from the file
        ArrayList<ArrayList<Integer>> movementData = getMovementData(loadFile(filename));
        // Processes the movement data
        ArrayList<Stack<String>> finalStack = processMovementDataCrateMover9000(movementData, initialStack);
        return getTopOfStacks(finalStack);
    }

    // Part two overarching method that combines the previous methods to process all the data and then return the top of each stack
    public static String partTwo(String filename) {
        // Gets the stack data from the file
        String[] stackData = getStackData(loadFile(filename));
        // Creates the initial stack
        ArrayList<Stack<String>> initialStack = createInitialStack(stackData);
        // Gets the movement data from the file
        ArrayList<ArrayList<Integer>> movementData = getMovementData(loadFile(filename));
        // Processes the movement data
        ArrayList<Stack<String>> finalStack = processMovementDataCrateMover9001(movementData, initialStack);
        return getTopOfStacks(finalStack);
    }

    // Processes the movement data for the CrateMover 9001. Returns the final stacks. Same thing as the processMovementData method, but with the extra functionality of the CrateMover 9001 which is that it can move multiple crates at once
    public static ArrayList<Stack<String>> processMovementDataCrateMover9001(ArrayList<ArrayList<Integer>> movementData, ArrayList<Stack<String>> initialStack) {
        // Loops through the movement data
        for (ArrayList<Integer> movementDatum : movementData) {
            // Gets the number of crates to move
            int numberOfCrates = movementDatum.get(0);
            // Gets the stack to move from
            // We subtract 1 from the stack values because the stacks are 0 indexed while the movement data is 1 indexed.
            int stackToMoveFrom = movementDatum.get(1) - 1;
            // Gets the stack to move to
            int stackToMoveTo = movementDatum.get(2) - 1;
            // Creates a new stack to hold the crates to move
            Stack<String> cratesToMove = new Stack<>();
            // Loops through the number of crates to move
            for (int j = 0; j < numberOfCrates; j++) {
                // Adds the crate to the temporary stack
                cratesToMove.add(initialStack.get(stackToMoveFrom).pop());
            }
            // Loops through the number of crates to move
            for (int j = 0; j < numberOfCrates; j++) {
                // Adds the crate to the final stack
                initialStack.get(stackToMoveTo).add(cratesToMove.pop());
            }
        }
        // Returns the stack
        return initialStack;
    }

    // Gets the value at the top of each stack and combines them into a string without spaces or brackets
    public static String getTopOfStacks(ArrayList<Stack<String>> stacks) {
        //Creates a StringBuilder to hold the values of the top of each stack without spaces or brackets
        StringBuilder topOfStacks = new StringBuilder();
        //Loops through each stack
        for (Stack<String> stack : stacks) {
            //Adds the value at the top of the stack to the StringBuilder and removes the brackets and spaces
            topOfStacks.append(stack.peek().replaceAll("[\\[\\] ]", ""));
        }
        return topOfStacks.toString();
    }


    // Processes the movement data and moves the crates around based on the movement data and the initial stack. Returns the final stacks.
    public static ArrayList<Stack<String>> processMovementDataCrateMover9000(ArrayList<ArrayList<Integer>> movementData, ArrayList<Stack<String>> initialStacks) {
        // Loads the movement data and then pops and pushes the crates onto the stacks.
        for (ArrayList<Integer> movement : movementData) {
            // Gets the three values from the movement data. 1 is how many crates to move, 2 is the stack to move from, and 3 is the stack to move to.
            // We subtract 1 from the stack values because the stacks are 0 indexed while the movement data is 1 indexed.
            int numOfCratesToMove = movement.get(0);
            int stackToMoveFrom = movement.get(1) - 1;
            int stackToMoveTo = movement.get(2) - 1;
            // Pops and pushes the crates onto the stacks.
            for (int i = 0; i < numOfCratesToMove; i++) {
                initialStacks.get(stackToMoveTo).push(initialStacks.get(stackToMoveFrom).pop());
            }
        }
        return initialStacks;
    }

    // Gets the movement data from the input String[] and returns it as an ArrayList<ArrayList<Integer>>. Each inner Arraylist will be 3 integers long. The first integer is the amount to move, the second is the stack to move from, and the third is the stack to move to.
    public static ArrayList<ArrayList<Integer>> getMovementData(String[] input) {
        ArrayList<ArrayList<Integer>> stackData = new ArrayList<>();
        // Find where the movement data starts, which is after the first blank line.
        int movementDataStart = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i].equals("")) {
                movementDataStart = i + 1;
                break;
            }
        }
        // Get the movement data.
        for (int i = movementDataStart; i < input.length; i++) {
            ArrayList<Integer> movement = new ArrayList<>();
            String[] movementData = input[i].split(" ");
            movement.add(Integer.parseInt(movementData[1]));
            movement.add(Integer.parseInt(movementData[3]));
            movement.add(Integer.parseInt(movementData[5]));
            stackData.add(movement);
        }
        return stackData;
    }

    public static ArrayList<Stack<String>> createInitialStack(String[] input) {
        // Last line is the number of stacks
        // Find the largest number in the last line of input
        int numberOfStacks = 0;
        for (int i = 0; i < input[input.length - 1].length(); i++) {
            if (Character.isDigit(input[input.length - 1].charAt(i))) {
                numberOfStacks = Character.getNumericValue(input[input.length - 1].charAt(i));
            }
        }
        ArrayList<Stack<String>> stacks = new ArrayList<>();
        // Create the stacks
        for (int i = 0; i < numberOfStacks; i++) {
            stacks.add(new Stack<>());
        }
        // Split input into columns of 3 each skipping one index each time
        ArrayList<ArrayList<String>> grid = new ArrayList<>();
        for (int i = 0; i < input.length - 1; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < input[i].length(); j += 4) {
                grid.get(i).add(input[i].substring(j, j + 3));
            }
        }
        // Start at the top of grid - 1 and work down to 0 (bottom of stack) and add to the respective stack. If the input is empty, skip it.
        for (int i = grid.size() - 1; i >= 0; i--) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (!grid.get(i).get(j).equals("   ")) {
                    stacks.get(j).push(grid.get(i).get(j));
                }
            }
        }
        return stacks;
    }

    // Get the stack data structure from the input
    public static String[] getStackData(String[] input) {
        // Count the number of lines before \n
        int count = 0;
        for (String s : input) {
            if (s.equals("")) {
                break;
            }
            count++;
        }
        // Create an array with length count
        String[] stackData = new String[count];
        // Copy the data into the array
        System.arraycopy(input, 0, stackData, 0, count);
        return stackData;
    }

    // Load the file into an array of Strings
    public static String[] loadFile(String filename) {
        // Count the number of lines in the file and then add each line into a new array of Strings
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] input = new String[count];
        try {
            Scanner scanner = new Scanner(new File(filename));
            for (int i = 0; i < count; i++) {
                input[i] = scanner.nextLine();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}



package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CathodeRayTube {
    public static void main(String[] args) {
        String fileName = "src/Day10/Day10.txt";
        ArrayList<String[]> instructions = processInstructionsFile(fileName);
        ArrayList<ArrayList<Integer>> cycleValuePair = createCycleValuePair(instructions);
        int[] cycleValues = {20, 60, 100, 140, 180, 220};
        System.out.println(calculateSummedSignalStrengths(cycleValuePair, cycleValues)); // 16480
        ArrayList<ArrayList<String>> crt = processCRTInstructions(cycleValuePair);
        printCRT(crt); // PLEFULPB
    }

    /**
     * Processes the CRT instructions from the cycleValuePair
     *
     * @param cycleValuePair The list of cycle value pairs
     * @return CRT grid
     */
    public static ArrayList<ArrayList<String>> processCRTInstructions(ArrayList<ArrayList<Integer>> cycleValuePair) {
        // Initialize the CRT (40x6) with periods
        ArrayList<ArrayList<String>> crt = createCRT();
        // Initialize position of the (middle of the sprite) The sprite is 3 pixels wide
        int[] position = {0, 0};
        // Initialize the painting pixel
        int[] pixel = {0, 0};
        for (ArrayList<Integer> cycleValue : cycleValuePair) {
            int value = cycleValue.get(1);
            //
            position[0] = value;
            // If the painting pixel is in any position of the sprite, paint it with a hash
            if (pixel[0] == position[0] || pixel[0] == position[0] + 1 || pixel[0] == position[0] - 1) {
                crt.get(pixel[1]).set(pixel[0], "#");
            }
            pixel[0]++;

            // If the pixel has reached the end of the row, reset it to the beginning of the row
            if (pixel[0] == 40) {
                pixel[0] = 0;
                pixel[1]++;
                position[1]++;
            }

        }
        return crt;
    }

    /**
     * Create the CRT grid
     *
     * @return A 40x6 CRT grid
     */
    public static ArrayList<ArrayList<String>> createCRT() {
        ArrayList<ArrayList<String>> crt = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < 40; j++) {
                row.add(".");
            }
            crt.add(row);
        }
        return crt;
    }

    /**
     * Print the CRT grid
     *
     * @param crt The CRT grid
     */
    public static void printCRT(ArrayList<ArrayList<String>> crt) {
        for (ArrayList<String> row : crt) {
            for (String pixel : row) {
                System.out.print(pixel);
            }
            System.out.println();
        }
    }

    /**
     * @param cycleValuePair The list of cycle value pairs
     * @param cyclesToSum    The cycles to sum
     * @return The sum of the signal strengths
     */
    public static int calculateSummedSignalStrengths(ArrayList<ArrayList<Integer>> cycleValuePair, int[] cyclesToSum) {
        int sum = 0;
        for (ArrayList<Integer> row : cycleValuePair) {
            for (int cycle : cyclesToSum) {
                if (row.get(0) == cycle) {
                    sum += row.get(0) * row.get(1);
                }
            }
        }
        return sum;
    }

    /**
     * @param instructions The list of instructions
     * @return The cycle value pairs
     */
    public static ArrayList<ArrayList<Integer>> createCycleValuePair(ArrayList<String[]> instructions) {
        ArrayList<ArrayList<Integer>> cycleValuePair = new ArrayList<>();
        int cycle = 0;
        int value = 1;
        for (String[] instruction : instructions) {
            if (instruction[0].equals("noop")) {
                cycle++;
                // Add the cycle and value to the cycleValuePair
                ArrayList<Integer> cycleValue = new ArrayList<>();
                cycleValue.add(cycle);
                cycleValue.add(value);
                cycleValuePair.add(cycleValue);
            } else if (instruction[0].equals("addx")) {
                int addValue = Integer.parseInt(instruction[1]);
                cycle += 1;
                ArrayList<Integer> cycleValue = new ArrayList<>();
                cycleValue.add(cycle);
                cycleValue.add(value);
                cycleValuePair.add(cycleValue);
                cycle += 1;
                cycleValue = new ArrayList<>();
                cycleValue.add(cycle);
                cycleValue.add(value);
                cycleValuePair.add(cycleValue);
                value += addValue;
            }

        }
        cycle++;
        ArrayList<Integer> cycleValue = new ArrayList<>();
        cycleValue.add(cycle);
        cycleValue.add(value);
        cycleValuePair.add(cycleValue);

        return cycleValuePair;
    }

    /**
     * @param filename The filename of the instructions
     * @return The list of instructions
     */
    public static ArrayList<String[]> processInstructionsFile(String filename) {
        ArrayList<String[]> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] instruction = line.split(" ");
                instructions.add(instruction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}

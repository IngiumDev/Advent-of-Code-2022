package Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CalorieCounter {
    public static void main(String[] args) {
        String filename = "src\\Day1\\Day1.txt";
        // Print out the sum of the elf with the most calories
        System.out.println(findFattestElf(filename, 1)); // 71300
        // Print out the sums of the top 3 elves with the most calories
        System.out.println(findFattestElf(filename, 3)); //209691

    }

    public static int findFattestElf(String filename, int topElvesAmount) {
        // Create array for highest calorie count
        ArrayList<Integer> highestCalorieCount = new ArrayList<Integer>();

        // Read the input file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int calorieCount = 0;
            // process the line.
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    // Add the calorie count to the array because we've finished counting one elf's calories
                    highestCalorieCount.add(calorieCount);
                    // Reset the calorie count each time we hit a blank line
                    calorieCount = 0;
                } else {
                    // Add the calorie count to the total for that elf
                    calorieCount += Integer.parseInt(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If only the top elf is needed, return the highest calorie count
        // Find the highest calorie count
        // Creates counter for the max calorie value so far
        int highestCalorieCountinList = 0;
        // Loop through the list of calorie counts
        if (topElvesAmount == 1) {
            for (Integer integer : highestCalorieCount) {
                // If the current calorie count is higher than the highest calorie count so far, set the highest calorie count to the max calorie count
                if (integer > highestCalorieCountinList) {
                    highestCalorieCountinList = integer;
                }
            }
            return highestCalorieCountinList;
        }
        // If the top x elves are needed, return the sum of the top x elves
        // Sort the list of calorie counts
        highestCalorieCount.sort(null);
        // Create a counter for the sum of the top x elves
        int sumOfTopxElves = 0;
        // Loop through the list of calorie counts
        for (int i = highestCalorieCount.size() - 1; i > highestCalorieCount.size() - topElvesAmount - 1; i--) {
            // Add the calorie count to the sum of the top x elves
            sumOfTopxElves += highestCalorieCount.get(i);
        }
        return sumOfTopxElves;
    }
}
package Day6;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TuningTrouble {
    public static void main(String[] args) {
        String fileName = "src\\Day6\\Day6.txt";
        String input = processFile(fileName);
        // Part 1
        System.out.println("Part 1: " + countFirstFourUnique(input)); // 1538
        // Part 2
        System.out.println("Part 2: " + countFirstFourteenUnique(input)); // 2315
    }

    /**
     * Mehod for Part 1
     * Counts the number of characters before the first four unique characters are found
     * Starts at index 3 and checks if the previous 4 characters are unique to each other. If they are, return the index of the first character after the marker
     * If not, continue checking until the end of the string
     * returns -1 if no marker is found
     *
     * @param input String to be processed
     * @return int number of characters before the first start-of-packet marker is detected
     */
    public static int countFirstFourUnique(String input) {
        int index = 3;
        while (index < input.length()) {
            if (input.charAt(index) != input.charAt(index - 1) && input.charAt(index) != input.charAt(index - 2) && input.charAt(index) != input.charAt(index - 3) && input.charAt(index - 1) != input.charAt(index - 2) && input.charAt(index - 1) != input.charAt(index - 3) && input.charAt(index - 2) != input.charAt(index - 3)) {
                return index + 1;
            }
            index++;
        }
        return -1;
    }

    /**
     * Method for Part 2
     * Method countFirstFourteenUnique counts how many characters need to be processed before the first start-of-message marker is detected.
     * Starts at index 13 and checks if the previous 14 characters are unique which each other. If they are, it returns the index of the first character after the marker.
     * If they are not, it increments the index and checks again.
     * Returns -1 if no marker is found.
     * Same as countFirstFourUnique, but with 14 characters instead of 4.
     *
     * @param input String to be processed
     * @return int number of characters before the first start-of-message marker is detected
     */
    public static int countFirstFourteenUnique(String input) {
        for (int i = 13; i < input.length(); i++) {
            // Loops through the current section of the string to check if the characters are unique.
            for (int j = i - 13; j < i; j++) {
                // Creates a char variable to store the current character.
                char currentChar = input.charAt(j);
                // Loops through the rest of the current section of the string to check if the current character is unique.
                for (int k = j + 1; k <= i; k++) {
                    if (currentChar == input.charAt(k)) {
                        // Breaks out of the loop if the current character is not unique.
                        k = i + 1;
                        j = i;
                    } else if (k == i && j == i - 1) {
                        // Returns the index of the first character after the marker if the current character is unique and the end of the current section of the string is reached.
                        return i + 1;
                    }
                }
            }
        }
        return -1;

    }

    /**
     * Processes the file based on filename and returns a String with the line input
     *
     * @param filename the name of the file to read
     * @return a string containing the line of the file
     */
    public static String processFile(String filename) {
        StringBuilder line = new StringBuilder();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return line.toString();
    }

}

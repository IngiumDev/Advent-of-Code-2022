package Day6;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TuningTrouble {

    public static void main(String[] args) {
        String fileName = "src\\Day6\\Day6.txt";
        // Part 1
        System.out.println("Part 1: " + countFirstFourUnique(processFile(fileName)));
    }
    // Method countFirstFourUnique counts how many characters need to be processed before the first start-of-packet marker is detected.
    // Starts at index 3 and checks if the previous 4 characters are unique which each other. If they are, it returns the index of the first character after the marker.
    // If they are not, it increments the index and checks again.
    // Returns -1 if no marker is found.
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
     * @param filename the name of the file to read
     * @return a string containing the line of the file
     */
    // Processes the file based on filename and returns a String with the line input
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

package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RopeBridge {

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

package Day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class RegolithReservoir {
    public static void main(String[] args) {
        ArrayList<String> input = parseInputFile("src/Day14/Day14.txt");
        ArrayList<ArrayList<Character>> reservoir = createReservoir();
        processRockPositions(input, reservoir);

        //Count the number of sand units that have come to rest
        int numberOfSandUnitsThatHaveComeToRest = simulateFallingSandUntilBottomIsReached(reservoir);
        System.out.println("Number of sand units that have come to rest: " + numberOfSandUnitsThatHaveComeToRest);

        // Part 2
        // Reset the reservoir
        reservoir = createReservoir();
        processRockPositions(input, reservoir);
        // Create a floor
        createFloor(reservoir);
        int numberOfSandUnitsThatHaveComeToRestPart2 = simulateFallingSandUntilSandGeneratorIsReached(reservoir);
        System.out.println("Number of sand units that have come to rest: " + numberOfSandUnitsThatHaveComeToRestPart2);
    }


    // Process the instruction String ArrayList
    public static void processRockPositions(ArrayList<String> instructions, ArrayList<ArrayList<Character>> reservoir) {
        // Loop through each line of the instructions
        for (String instruction : instructions) {
            // Split by ->
            String[] splitInstruction = instruction.split(" -> ");
            int previousX = 0;
            int previousY = 0;
            // Loop through each coordinate
            for (String coordinate : splitInstruction) {
// Split by ,
                String[] splitCoordinate = coordinate.split(",");
                int x = Integer.parseInt(splitCoordinate[0]);
                int y = Integer.parseInt(splitCoordinate[1]);
                // If this is the first coordinate, set the previous coordinates to this coordinate
                if (previousX != 0 || previousY != 0) {
                    // If the previous X is less than the current X, draw a horizontal line from the previous X to the current X
                    if (previousX < x) {
                        for (int i = previousX; i <= x; i++) {
                            reservoir.get(y).set(i, '#');
                        }
                    }
                    // If the previous X is greater than the current X, draw a horizontal line from the current X to the previous X
                    if (previousX > x) {
                        for (int i = x; i <= previousX; i++) {
                            reservoir.get(y).set(i, '#');
                        }
                    }
                    // If the previous Y is less than the current Y, draw a vertical line from the previous Y to the current Y
                    if (previousY < y) {
                        for (int i = previousY; i <= y; i++) {
                            reservoir.get(i).set(x, '#');
                        }
                    }
                    // If the previous Y is greater than the current Y, draw a vertical line from the current Y to the previous Y
                    if (previousY > y) {
                        for (int i = y; i <= previousY; i++) {
                            reservoir.get(i).set(x, '#');
                        }
                    }
                    // Set the previous X and Y to the current X and Y
                }
                previousX = x;
                previousY = y;
            }


        }
    }

    // Create 1000x1000 ArrayList of Chars and fill it with '.'
    public static ArrayList<ArrayList<Character>> createReservoir() {
        ArrayList<ArrayList<Character>> reservoir = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ArrayList<Character> row = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                row.add('.');
            }
            reservoir.add(row);
        }
        // Set the source to + (500, 0)
        reservoir.get(0).set(500, '+');
        return reservoir;
    }

    public static ArrayList<String> parseInputFile(String filename) {
        ArrayList<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    // Simulate the falling sand until it reaches the bottom of the reservoir and then count the number of sand units that have come to rest
    public static int simulateFallingSandUntilBottomIsReached(ArrayList<ArrayList<Character>> reservoir) {
        int numberOfSandUnitsThatHaveComeToRest = 0;
        // Start at the source
        int x = 500;
        int y = 0;
        // Loop until the bottom of the reservoir is reached
        while (y < 999) {
            // If the position is empty, move down one
            if (reservoir.get(y + 1).get(x) == '.') {
                reservoir.get(y + 1).set(x, 'o');
                reservoir.get(y).set(x, '.');
                y++;

                continue;
            }
            // If the position diagonally left down is empty, move diagonally left down one
            if (reservoir.get(y + 1).get(x - 1) == '.') {
                reservoir.get(y + 1).set(x - 1, 'o');
                reservoir.get(y).set(x, '.');
                y++;
                x--;

                continue;
            }
            // If the position diagonally right down is empty, move diagonally right down one
            if (reservoir.get(y + 1).get(x + 1) == '.') {
                reservoir.get(y + 1).set(x + 1, 'o');
                reservoir.get(y).set(x, '.');
                y++;
                x++;
                continue;
            }

            // Restart the loop from the top
            numberOfSandUnitsThatHaveComeToRest++;
            x = 500;
            y = 0;
        }
        return numberOfSandUnitsThatHaveComeToRest;
    }

    // Save the reservoir to a txt file
    public static void saveReservoirToFile(ArrayList<ArrayList<Character>> reservoir) {
        try {
            FileWriter writer = new FileWriter("src/Day14/reservoir.txt");
            for (ArrayList<Character> row : reservoir) {
                for (Character character : row) {
                    writer.write(character);
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create the floor of the reservoir i.e. the bottom row which is all # and 2 rows below the last row with #. Returns the y of the bottom row
    public static void createFloor(ArrayList<ArrayList<Character>> reservoir) {
        // Loop through each row in the reservoir and find the last row with a #
        int y = 0;
        for (int i = 0; i < reservoir.size(); i++) {
            ArrayList<Character> row = reservoir.get(i);
            for (Character character : row) {
                if (character == '#') {
                    y = i;
                    break;
                }
            }
        }
        // Set the bottom row to all # (bottoom row is y + 2)
        for (int i = 0; i < reservoir.get(y + 2).size(); i++) {
            reservoir.get(y + 2).set(i, '#');
        }
    }
    // Simulate the falling sand until it reaches the sand generator (+) and then count the number of sand units that have come to rest
    public static int simulateFallingSandUntilSandGeneratorIsReached(ArrayList<ArrayList<Character>> reservoir) {
        int numberOfSandUnitsThatHaveComeToRest = 0;
        // Start at the source
        int x = 500;
        int y = 0;
        // Loop until the sand generator is reached
        while (y  < 999) {
            // If the position is empty, move down one
            // If the position is empty, move down one
            if (reservoir.get(y + 1).get(x) == '.') {
                reservoir.get(y + 1).set(x, 'o');
                reservoir.get(y).set(x, '.');
                y++;

                continue;
            }
            // If the position diagonally left down is empty, move diagonally left down one
            if (reservoir.get(y + 1).get(x - 1) == '.') {
                reservoir.get(y + 1).set(x - 1, 'o');
                reservoir.get(y).set(x, '.');
                y++;
                x--;

                continue;
            }
            // If the position diagonally right down is empty, move diagonally right down one
            if (reservoir.get(y + 1).get(x + 1) == '.') {
                reservoir.get(y + 1).set(x + 1, 'o');
                reservoir.get(y).set(x, '.');
                y++;
                x++;
                continue;
            }
            // If the sand has no where to go and is at the sand generator, stop the loop
            if (x == 500 && y == 0) {
                break;
            }

            // Restart the loop from the top
            numberOfSandUnitsThatHaveComeToRest++;
            x = 500;
            y = 0;
        }
        return numberOfSandUnitsThatHaveComeToRest+1;
    }

}

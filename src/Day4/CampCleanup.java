package Day4;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CampCleanup {
    public static void main(String[] args) {
        String filename = "src\\Day4\\Day4.txt";
        // Part 1
        System.out.println(countFullyContainedRangesInPairs(filename)); // 444
        // Part 2
        System.out.println(countFullyContainedRangesInTotal(filename)); // 801
    }

    // Part 1
    // Iterates through a file and counts the number of fully contained ranges
    public static int countFullyContainedRangesInPairs(String filename) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split the line into the two separate assignments (ranges)
                String[] sampleRange = line.split(",");
                // Check if  either of the ranges are fully contained in the other
                if (isFullyContainedInRanges(sampleRange[0], sampleRange[1])) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Part 2
    // Iterates through a file and counts the number of (even slightly) contained ranges
    public static int countFullyContainedRangesInTotal(String filename) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split the line into the two separate assignments (ranges)
                String[] sampleRange = line.split(",");
                // Check if  either of the ranges overlap the other
                if (isOverlappingRanges(sampleRange[0], sampleRange[1])) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Checks if either of the two input ranges are fully contained in the other
    public static boolean isFullyContainedInRanges(String range1, String range2) {
        String[] range1Split = range1.split("-");
        String[] range2Split = range2.split("-");
        // Create an int for each start & end of the ranges
        int range1Start = Integer.parseInt(range1Split[0]);
        int range1End = Integer.parseInt(range1Split[1]);
        int range2Start = Integer.parseInt(range2Split[0]);
        int range2End = Integer.parseInt(range2Split[1]);
        // Check whether range1 is fully contained in range2
        if (range1Start >= range2Start && range1End <= range2End) {
            return true;
        }
        // Check whether range2 is fully contained in range1
        return range2Start >= range1Start && range2End <= range1End;
    }

    //Check if two range overlap even slightly
    public static boolean isOverlappingRanges(String range1, String range2) {
        String[] range1Split = range1.split("-");
        String[] range2Split = range2.split("-");
        // Create an int for each start & end of the ranges
        int range1Start = Integer.parseInt(range1Split[0]);
        int range1End = Integer.parseInt(range1Split[1]);
        int range2Start = Integer.parseInt(range2Split[0]);
        int range2End = Integer.parseInt(range2Split[1]);
        // Check if the ranges overlap at all
        if (range1Start <= range2Start && range1End >= range2Start) {
            return true;
        }
        return range2Start <= range1Start && range2End >= range1Start;
    }
}

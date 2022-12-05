package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RucksackReorganization {
    public static void main(String[] args) {
        String filename = "src\\Day3\\Day3.txt";
        System.out.println("Part 1: " + sumRucksackDuplicates(filename)); // 8401
        System.out.println("Part 2: " + sumRucksackBadges(filename)); //2641
    }

    // sumRucksackDuplicates() method iterates through the input of different rucks, splits each line in two by the middle and then checks which letters appear twice and sums their priority
    public static int sumRucksackDuplicates(String filename) {
        // create a HashMap with priorities of letters
        HashMap<Character, Integer> priority = getAlphabetPriorities();
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String rucksack1 = line.substring(0, line.length() / 2);
                String rucksack2 = line.substring(line.length() / 2);
                // We need to prevent double counting so we create a String that will hold the letters that appear twice in the rucksack that we have already summed
                StringBuilder preventDoubleCounting = new StringBuilder();
                // We iterate through the first rucksack
                for (int i = 0; i < rucksack1.length(); i++) {
                    // If the letter appears twice in the rucksack and we haven't already summed it, we add its priority to the sum
                    if (rucksack2.contains(String.valueOf(rucksack1.charAt(i))) && !preventDoubleCounting.toString().contains(String.valueOf(rucksack1.charAt(i)))) {
                        // We add the priority of the letter to the sum
                        sum += priority.get(rucksack1.charAt(i));
                        // We add the letter to the String that will prevent double counting
                        preventDoubleCounting.append(rucksack1.charAt(i));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    // sumRucksackBadges() method iterates through each group of Elves (every 3 lines = 1 group) and checks which letters appear twice (their badge) and sums their priority
    public static int sumRucksackBadges(String filename) {
        HashMap<Character, Integer> priority = getAlphabetPriorities();
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // read line by line
            String line;
            // Creates a String[] that will hold the rucksacks of each group of Elves. Every group of elves has 3 elves and each elf has a rucksack
            String[] rucksacks = new String[3];
            while ((line = br.readLine()) != null) {
                // We add the rucksack of the first elf to the rucksacks[]
                rucksacks[0] = line;
                // We add the rucksack of the second elf to the rucksacks[]
                if ((line = br.readLine()) != null) {
                    // If the line is not null, we continue to the next line
                    rucksacks[1] = line;
                } else {
                    // If the line is null, we break the loop
                    break;
                }
                // We add the rucksack of the third elf to the rucksacks[]
                if ((line = br.readLine()) != null) {
                    // If the line is not null, we continue to the next line
                    rucksacks[2] = line;
                } else {
                    // If the line is null, we break the loop
                    break;
                }
                // To prevent double counting of the same badge, we create boolean foundBadge that will be true if we have already found the badge
                boolean foundBadge = false;
                // Find the common letter

                for (int i = 0; i < rucksacks[0].length(); i++) {
                    // We iterate through the first rucksack and check if the letter appears in the other two rucksacks
                    if (rucksacks[1].contains(String.valueOf(rucksacks[0].charAt(i))) && rucksacks[2].contains(String.valueOf(rucksacks[0].charAt(i))) && !foundBadge) {
                        // If the letter appears in the other two rucksacks, we add its priority to the sum
                        sum += priority.get(rucksacks[0].charAt(i));
                        // We set found to true so we don't add the priority of the same letter twice
                        foundBadge = true;
                    }
                }
                // We reset foundBadge to false so we can find the next badge
                foundBadge = false;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    // Creates a HashMap with the alphabet and its priorities
    public static HashMap<Character, Integer> getAlphabetPriorities() {
        HashMap<Character, Integer> priority = new HashMap<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < alphabet.length; i++) {
            // for each letter we add its priority to the HashMap
            priority.put(alphabet[i], i + 1);
        }
        return priority;
    }
}

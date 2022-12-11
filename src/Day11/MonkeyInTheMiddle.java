package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MonkeyInTheMiddle {
    public static void main(String[] args) {
        String filename = "src/Day11/Day11.txt";
        ArrayList<Monkey> monkeys = parseInputMonkey(filename);
        processMonkeyGameOne(monkeys, 20, true);
        System.out.println(calculateMonkeyBusiness(monkeys)); // 110264
        ArrayList<Monkey> monkeys2 = parseInputMonkey(filename);
        processMonkeyGameOne(monkeys2, 10000, false);
        System.out.println(calculateMonkeyBusiness(monkeys2)); // 23612457316

    }

    /**
     * Calculates the level of monkey business (product of the number of items inspected by the two most active monkeys)
     * Probably does not need to be a BigInteger, but I wanted to be safe (long should be fine)
     *
     * @param monkeys the monkeys
     * @return the level of monkey business
     */
    public static BigInteger calculateMonkeyBusiness(ArrayList<Monkey> monkeys) {
        int monkey1 = 0;
        int monkey2 = 0;
        for (Monkey monkey : monkeys) {
            if (monkey.getInspected() > monkey1) {
                monkey2 = monkey1;
                monkey1 = monkey.getInspected();
            } else if (monkey.getInspected() > monkey2) {
                monkey2 = monkey.getInspected();
            }
        }
        System.out.println("Monkey 1 inspected items " + monkey1 + " times.");
        System.out.println("Monkey 2 inspected items " + monkey2 + " times.");
        return BigInteger.valueOf(monkey1).multiply(BigInteger.valueOf(monkey2));
    }

    /**
     * Process the monkey game
     *
     * @param monkeys  ArrayList of monkeys
     * @param rounds   number of rounds to play
     * @param divThree if true, worry level is divided by 3 if monkey gets bored with item
     */
    public static void processMonkeyGameOne(ArrayList<Monkey> monkeys, int rounds, boolean divThree) {
        List<Integer> divByList = new ArrayList<>();
        for (Monkey x : monkeys) {
            Integer divBy = x.getDivBy();
            divByList.add(divBy);
        }
        int lcm = calculateLCMOfList(divByList, 0);
        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys) {
                // Inspect item;
                inspectItems(monkeys, monkey, divThree, lcm);
            }
        }
    }

    /**
     * Inspect items of a monkey
     *
     * @param monkeys  List of monkeys
     * @param monkey   Monkey to inspect items
     * @param divThree If true, divide worry level by 3
     * @param lcm      Least common multiple of all monkeys' divBy values
     */
    public static void inspectItems(ArrayList<Monkey> monkeys, Monkey monkey, boolean divThree, int lcm) {
        // Loop through items

        ArrayList<Long> items = monkey.getStartingItems();
        for (Long item : items) {
            // Find out what to do with item
            if (monkey.isMultiplyOld()) {
                item *= item;
            } else if (monkey.getMultiplyBy() != 0) {
                item *= monkey.getMultiplyBy();
            } else {
                item += monkey.getAddBy();
            }
            // Monkey gets bored so divide by 3 and round down to the nearest integer
            if (divThree) {
                item /= 3;
            }
            // Make item smaller
            // Basically, we don't really care how big the item is, so we can mod it by the LCM of the divBy values, which will make it smaller and means it will still pass the divBy test for each
            item %= lcm;
            // Now check if the item is divisible by the monkeys number

            int throwTo;
            if (item % monkey.getDivBy() == 0) {
                throwTo = monkey.getThrowTrue();
            } else {
                throwTo = monkey.getThrowFalse();

            }
            monkeys.get(throwTo).addStartingItem(item);
            monkey.incrementInspected();
        }
        // Clear items
        monkey.clearStartingItems();
    }

    /**
     * Create a list of monkeys from the input file
     *
     * @param filename The filename of the input file
     * @return An ArrayList of monkeys with the correct properties read from the schema
     */
    // Parse the input file into a list of monkeys
    public static ArrayList<Monkey> parseInputMonkey(String filename) {
        ArrayList<Monkey> monkeys = new ArrayList<>();
        ArrayList<ArrayList<String>> monkeyStrings = new ArrayList<>();
        // Read the input file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the input into an ArrayList of String for each monkey section
                // A monkey section is a line of text that starts with "Monkey" and each line after that until the next "Monkey" line
                if (line.startsWith("Monkey")) {
                    monkeyStrings.add(new ArrayList<>());
                }
                monkeyStrings.get(monkeyStrings.size() - 1).add(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Process each monkey section
        for (ArrayList<String> monkeyString : monkeyStrings) {
            // Get the starting items
            // Starting items are in the second line and look like this: "Starting items: 65, 78,"
            // In that case we want to get the numbers 65 and 78 in the ArrayList
            ArrayList<Long> startingItems = new ArrayList<>();
            String[] startingItemsString = monkeyString.get(1).split("Starting items: ")[1].split(",");
            for (String startingItemString : startingItemsString) {
                if (!startingItemString.equals("")) {
                    startingItems.add(Long.parseLong(startingItemString.trim()));
                }
            }
            // Find out the operations that the monkey does
            // Operations are in the third line
            // If the third line ends with * old, set multiplyOld to true and multiplyBy and addBy to 0
            // If the third line ends with + 33, set multiplyOld to false and multiplyBy to 0 and addBy to 33
            // If the third line ends with * 22 number, set multiplyOld to false and multiplyBy to the 22 and addBy to 0
            // 33 and 22 are just examples, they can be any number
            boolean multiplyOld = false;
            int multiplyBy = 0;
            int addBy = 0;
            if (monkeyString.get(2).endsWith(" old")) {
                multiplyOld = true;
            } else if (monkeyString.get(2).contains("*")) {
                multiplyBy = Integer.parseInt(monkeyString.get(2).split("\\*")[1].trim());
            } else if (monkeyString.get(2).contains("+")) {
                addBy = Integer.parseInt(monkeyString.get(2).split("\\+")[1].trim());
            }
            // Find the divBy test
            // The divBy test is in the fourth line and looks like this: "Test: divisible by 3"
            // In that case we want to get the number 3
            int divBy = Integer.parseInt(monkeyString.get(3).split("divisible by ")[1].trim());
            // Find the throwTrue monkey
            // The throwTrue monkey is in the fifth line and looks like this: "If true: throw to monkey 2"
            // In that case we want to get the number 2
            int throwTrue = Integer.parseInt(monkeyString.get(4).split("If true: throw to monkey ")[1]);
            // Find the throwFalse monkey
            // The throwFalse monkey is in the sixth line and looks like this: "If false: throw to monkey 3"
            // In that case we want to get the number 3
            int throwFalse = Integer.parseInt(monkeyString.get(5).split("If false: throw to monkey ")[1]);
            // Create the monkey
            Monkey monkey = new Monkey(startingItems, divBy, throwTrue, throwFalse, multiplyOld, multiplyBy, addBy);
            // Add the monkey to the list of monkeys
            monkeys.add(monkey);
        }
        return monkeys;

    }

    /**
     * Calculate the LCM of a list of numbers recursively
     *
     * @param divByList The list of divBy values
     * @param index     The index of the divByList to start at (0)
     * @return The LCM of the divByList
     */
    public static int calculateLCMOfList(List<Integer> divByList, int index) {
        if (index == divByList.size() - 1) {
            return divByList.get(index);
        }
        int a = divByList.get(index);
        int b = calculateLCMOfList(divByList, index + 1);
        return (a * b) / calculateGCF(a, b);
    }

    /**
     * Calculate the GCF of two numbers recursively
     *
     * @param a The first number
     * @param b The second number
     * @return The GCF of a and b
     */
    public static int calculateGCF(int a, int b) {
        if (b == 0) {
            return a;
        }
        return calculateGCF(b, a % b);
    }
}


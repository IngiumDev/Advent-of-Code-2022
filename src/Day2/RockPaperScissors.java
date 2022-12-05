package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RockPaperScissors {
    public static void main(String[] args) {
        String filename = "src\\Day2\\Day2.txt";
        // Calculate score for part one
        System.out.println(calculateScore(filename, true)); //11475
        // Calculate score for part two
        System.out.println(calculateScore(filename, false)); //16862
    }

    // calculateScore() calculates the total score of the game given an input with the opponents choice and the strategy guide we received
    // true = part one, false = part two
    public static int calculateScore(String filename, boolean tellsYouWhatToChose) {
        // Create variable for your score
        int score = 0;
        // Load in the file
        if (tellsYouWhatToChose) {
            // Part one
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                // Process each line
                while ((line = br.readLine()) != null) {
                    // Split the line into the opponent's choice and our Strategy guide
                    String[] choices = line.split(" ");

                    // Calculate the score
                    // X = rock
                    // Y = paper
                    // Z = scissors
                    // Rock = 1 point
                    // Paper = 2 points
                    // Scissors = 3 points
                    // Win = 6 points
                    // Draw = 3 points
                    // Lose = 0 points
                    if (choices[0].equals("A")) {
                        // If opponent choses rock
                        if (choices[1].equals("X")) {
                            // If we chose rock
                            // Draw (3+1)
                            score += 4;
                        } else if (choices[1].equals("Y")) {
                            // If we chose paper
                            // Win (6+2)
                            score += 8;
                        } else if (choices[1].equals("Z")) {
                            // If we chose scissors
                            // Loss  (3)
                            score += 3;
                        }
                    } else if (choices[0].equals("B")) {
                        // If opponent choses paper
                        if (choices[1].equals("X")) {
                            // If we chose rock
                            // Loss (1)
                            score += 1;
                        } else if (choices[1].equals("Y")) {
                            // If we chose paper
                            // Draw (3+2)
                            score += 5;
                        } else if (choices[1].equals("Z")) {
                            // If we chose scissors
                            // Win (6+3)
                            score += 9;
                        }
                    } else if (choices[0].equals("C")) {
                        // If opponent choses scissors
                        if (choices[1].equals("X")) {
                            // If we chose rock
                            // Win (6+1)
                            score += 7;
                        } else if (choices[1].equals("Y")) {
                            // If we chose paper
                            // Loss  (2)
                            score += 2;
                        } else if (choices[1].equals("Z")) {
                            // If we chose scissors
                            // Draw (3+3)
                            score += 6;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return score;
        } else {
            // Part Two
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                // Process each line
                while ((line = br.readLine()) != null) {
                    // Split the line into the opponent's choice and our Strategy guide
                    String[] choices = line.split(" ");
                    // Calculate the score
                    // X = you need to lose
                    // Y = you need to draw
                    // Z = you need to win
                    // Rock = 1 point
                    // Paper = 2 points
                    // Scissors = 3 points
                    // Win = 6 points
                    // Draw = 3 points
                    // Lose = 0 points
                    if (choices[0].equals("A")) {
                        // If opponent choses rock
                        if (choices[1].equals("X")) {
                            // Need to lose
                            // Lose using scissors (3)
                            score += 3;
                        } else if (choices[1].equals("Y")) {
                            // Need to draw
                            // Draw using rock (3+1)
                            score += 4;
                        } else if (choices[1].equals("Z")) {
                            // Need to win
                            // Win using paper (6+2)
                            score += 8;
                        }

                    } else if (choices[0].equals("B")) {
                        // If opponent choses paper
                        if (choices[1].equals("X")) {
                            // Need to lose
                            // Lose using rock (1)
                            score += 1;
                        } else if (choices[1].equals("Y")) {
                            // Need to draw
                            // Draw using paper (3+2)
                            score += 5;
                        } else if (choices[1].equals("Z")) {
                            // Need to win
                            // Win using scissors (6+3)
                            score += 9;
                        }
                    } else if (choices[0].equals("C")) {
                        // If opponent choses scissors
                        if (choices[1].equals("X")) {
                            // Need to lose
                            // Lose using paper (2)
                            score += 2;
                        } else if (choices[1].equals("Y")) {
                            // Need to draw
                            // Draw using scissors (3+3)
                            score += 6;
                        } else if (choices[1].equals("Z")) {
                            // Need to win
                            // Win using rock (6+1)
                            score += 7;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return score;
    }
}

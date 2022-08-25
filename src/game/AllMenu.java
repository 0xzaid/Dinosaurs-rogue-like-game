package game;

import edu.monash.fit2099.engine.Item;

import java.util.Scanner;

/**
 * A class that represents the menu for the sophisticated game driver.
 */
public class AllMenu {
    /**
     * store the specified moves and eco points by the player
     */
    private int[] movesAndPoints = new int[2];

    /**
     * Menu for choosing the game mode
     * @return specified moves and eco points (if challenge mode is chosen), else null
     */
    public int[] gameModeMenu(){
        boolean stillChoosing = true; // flag for keeping the loop of the menu
        Scanner scanner = new Scanner(System.in);
        int option;

        // game mode menu
        while (stillChoosing){
            System.out.println();
            System.out.println("Please select the game mode : ");
            System.out.println("1. Challenge mode");
            System.out.println("2. Sandbox mode");
            System.out.println("3. Quit (Close the program)");
            System.out.print("Please enter a number between 1 to 3 :");

            // to avoid taking incorrect input
            try {
                option = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Input should only be an integer number between 1 to 3");
                continue;
            }catch (Exception e){
                System.out.println("Another exception occur. Please re enter your option.");
                continue;
            }

            switch (option){
                case 1:
                    System.out.println();
                    System.out.println("You choose challenge mode!");
                    return challengeMenu();
                case 2:
                    // regular sandbox
                    System.out.println();
                    System.out.println("You choose sandbox mode!");
                    return null;
                case 3:
                    System.out.println();
                    System.out.println("You choose to quit (close the program)!");
                    System.exit(0);
                    break;
                default:
            }
            System.out.println();
        }
        return null;
    }

    /**
     * Menu for asking specified moves and eco points for challenge mode.
     * @return specified moves and eco points provided by the player
     */
    public int[] challengeMenu(){
        boolean stillChoosing = true; // flag for keeping the loop of the menu
        Scanner scanner = new Scanner(System.in);
        int moves = 0;
        int ecoPoints = 0;
        int[] specifiedBoundary = new int[2];

        while (stillChoosing) {
            try {
                System.out.print("Please enter the number of moves : ");
                moves = Integer.parseInt(scanner.nextLine());
                System.out.print("Please enter the number of eco points : ");
                ecoPoints = Integer.parseInt(scanner.nextLine());
                if (moves < 0 || ecoPoints < 0){
                    throw new NumberFormatException();
                }
                stillChoosing = false;
            } catch (NumberFormatException e) {
                System.out.println("Input should only be positive integers");
                continue;
            } catch (Exception e) {
                System.out.println("Another exception occur. Please re enter your option.");
                continue;
            }
        }
        System.out.println("Number of moves specified : " + moves + " , Number of eco points specified : " + ecoPoints);
        specifiedBoundary[0] = moves; specifiedBoundary[1] = ecoPoints;
        return specifiedBoundary;
    }
}

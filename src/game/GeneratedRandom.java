package game;

import java.util.Random;

/**
 * This class provides methods for random integer generation
 */
public class GeneratedRandom {
    /**
     * Static method for generating random integer for possibilities matter
     * @param upperBound Upper bound of the random generation
     * @return An integer from the random generation
     */
    public static int randomPossibilities(int upperBound){
        // range from 0 to upperBound - 1
        Random random = new Random();
        int randPos = random.nextInt(upperBound);
        return randPos;
    }

    /**
     * Static method for generating random integers sequence for naming
     * @return String of random integers sequence
     */
    public static String uniqueNameId(){
        Random random = new Random();
        int upperBound = 9999999;
        int randInt = random.nextInt(upperBound);
        return String.valueOf(randInt);
    }
}

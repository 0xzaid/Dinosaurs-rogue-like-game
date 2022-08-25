package game;

/**
 * A class that responsible for any weather-related implementation
 */
public class Weather{
    /**
     * To mark whether it is currently rain or not
     */
    public static boolean isRain;
    /**
     * Constant variable containing targeted success probabilities of raining
     */
    private static final int SUCCESS_RAIN = 0;
    /**
     * Constant variable containing upper bound probabilities of raining
     */
    private static final int UPPER_BOUND_RAIN = 5;

//    private static final int SUCCESS_RAIN = 0; // testing purpose
//    private static final int UPPER_BOUND_RAIN = 1; // testing purpose

    /**
     * Constant variable containing rainfall sips
     */
    private static final int RAINFALL_SIPS = 20;
    /**
     * Constant variable containing rainfall multiplier
     */
    private static final double[] RAINFALL = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
    /**
     * Constant variable containing maximum index of the rainfall multiplier
     */
    private static final int RAINFALL_MAX_INDEX = 6;
    /**
     * Attribute for the counting turns
     */
    private int turns = 0;
    /**
     * Counter for triggering the rain in every 10 turn
     */
    private int counter = 1;
    /**
     * Amount of water added to the lakes when raining
     */
    public static int waterAdded;

    /**
     * To execute the raining behaviour
     * @return amount of water added to the lakes
     */
    public static int rain(){
        waterAdded = 0; // to reset the water added in every rain occurrence

        int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_RAIN); // 20% chance
        if (chance == SUCCESS_RAIN){
            // if success
            int rainfall_amount = GeneratedRandom.randomPossibilities(RAINFALL_MAX_INDEX); // 0.1 to 0,6 inclusive
            // water added is the rainfall_sips multiplied by the rainfall multiplier (randomly chosen)
            waterAdded = (int) (RAINFALL[rainfall_amount] * RAINFALL_SIPS);

            // message to indicate it is raining
            System.out.println("It's raining with " + String.valueOf(waterAdded) + " sips added to every lake.");
            // it is raining, set to true
            isRain = true;
        }
        else {
            // message to indicate it is not raining
            System.out.println("It's NOT raining.");
            // it is raining, set to false
            isRain = false;
        }
        return waterAdded; // if no rain occurs, should always return 0, otherwise return the non zero values
    }

    /**
     * Return indication whether it is raining or not
     * @return true if its raining, false otherwise
     */
    public static boolean isRain() {
        return isRain;
    }
}
package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents the Dinosaur's eggs
 */
public abstract class Egg extends PortableItem implements Edible {

    /**
     * An instance variable to keep track of the age of egg
     */
    private int hatchCounter = 0;
    /**
     * An instance variable that stores the time for hatching
     */
    private int hatchMax = 50;
    /**
     * private instance variable that indicates the amount of FoodLevel to be added when an egg is eaten
     */
    private int addFood = 10;

    /**
     * Constructor.
     *
     * @param name        name of the current egg
     * @param displayChar Character that represents the egg in the map
     */
    public Egg(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Method that keeps track of the egg's age and hatches when it reaches the hatching time
     *
     * @param location Location of the current egg
     */
    @Override
    public void tick(Location location) {
        incrementHatchCounter();

        if (hatchCounter == hatchMax && !location.containsAnActor()) {
            hatch(location);
        }
    }

    /**
     * A private method that removes the egg and replaces it by a baby of the same type of the gg
     *
     * @param location the location of the current egg in the map
     */
    protected void hatch(Location location) {
        location.removeItem(this);
        if (this instanceof StegosaurEgg) {
            location.addActor(new BabyStegosaur());
        } else if (this instanceof BrachiosaurEgg) {
            location.addActor(new BabyBrachiosaur());
        } else if (this instanceof AllosaurEgg){
            location.addActor(new BabyAllosaur());
        } else if (this instanceof PterodactylEgg){
            location.addActor(new BabyPterodactyl());
        }
    }

    /**
     * Getter.
     *
     * @return addFood attribute of egg
     */
    @Override
    public int getAddFood() {
        return addFood;
    }

    /**
     * A method that increases the hatch counter by 1
     */
    public void incrementHatchCounter() {
        hatchCounter++;
    }
}

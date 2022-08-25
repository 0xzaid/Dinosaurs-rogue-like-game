package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents the species of Herbivore Brachiosaurs. Is a child of the Dinosaur class
 */
public class Brachiosaur extends Dinosaur {

    /**
     * Constructor.
     * All adult brachiosaurs are represents by a 'B' and have 160 hitpoints
     *
     * @param name a String that represents the name of the Brachiosaur instance
     */
    public Brachiosaur(String name) {
        super(name, 'B', 160);
        super.setFoodLevel(100);
        super.maxHitPoints = 160;
        super.maxWaterLevel = 200;
        this.addCapability(DinosaurCapability.HERBIVORE);
        this.addCapability(DinosaurCapability.BRACHIOSAUR);
    }


//    public Brachiosaur(String name, boolean male) {
//        super(name, 'B', 160);
//        super.setMale(male);
//        this.addCapability(DinosaurCapability.HERBIVORE);
//    }


}
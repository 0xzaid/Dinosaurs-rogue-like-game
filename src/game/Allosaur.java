package game;

/**
 * A class that represent the species of Carnivore Allosaurs. Is a child of the Dinosaur class
 */
public class Allosaur extends Dinosaur {

    /**
     * Constructor.
     * All adult allosaurs are represented by a 'A' and have 100 hitpoints
     *
     * @param name  a String that represents the name of the Allosaur instance
     */
    public Allosaur(String name) {
        super(name, 'A', 100);
        this.addCapability(DinosaurCapability.CARNIVORE);
        this.addCapability(DinosaurCapability.ALLOSAUR);

    }

//    public Allosaur(String name, boolean male) {
//        super(name, 'A', 100);
//        this.addCapability(DinosaurCapability.CARNIVORE);
//        super.setMale(male);
//        super.setFoodLevel(100);
//
//    }

}

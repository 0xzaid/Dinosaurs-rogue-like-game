package game;


/**
 * A class that represents the species of Carnivore Pterodactyl. Is a child of the Dinosaur class
 */
public class Pterodactyl extends Dinosaur {

    /**
     * Constructor.
     * All adult Pterodactyls are represents by a 'P' and have 100 hit points
     *
     * @param name a String that represents the name of the Pterodactyl instance
     */
    public Pterodactyl(String name) {
        super(name, 'P', 100);
        this.addCapability(DinosaurCapability.CARNIVORE);
        this.addCapability(DinosaurCapability.PTERODACTYL);
        this.addCapability(DinosaurCapability.CATCH_FISH);
        this.addCapability(DinosaurCapability.FLY);
        //this.setFuel(0);
    }

    /**
     * Constructor.
     * All adult Pterodactyls are represents by a 'P' and have 100 hit points
     *
     * @param name a String that represents the name of the Pterodactyl instance
     * @param male a boolean that represents gender of Pterodactyl instance
     */
    public Pterodactyl(String name, boolean male) {
        super(name, 'P', 100);
        super.setMale(male);
        this.addCapability(DinosaurCapability.CARNIVORE);
        this.addCapability(DinosaurCapability.PTERODACTYL);
        this.addCapability(DinosaurCapability.CATCH_FISH);
        this.addCapability(DinosaurCapability.FLY);
    }
}
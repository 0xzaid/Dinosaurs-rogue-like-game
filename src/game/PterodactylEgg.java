package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a Pterodactyl's Egg, is a subclass of Egg
 */
public class PterodactylEgg extends Egg {

    /**
     * The current Egg's age
     */
    private int age = 0;

    /**
     * Constructor.
     * All PterodactylEgg's are represented by a '('
     *
     * @param name string that represents the current PterodactylEgg's name
     */
    public PterodactylEgg(String name) {
        super(name, 'q');
        this.addCapability(DinosaurCapability.CARNIVORE_EATING);
        this.addCapability(DinosaurCapability.CARNIVORE_FEEDING);
    }

    /**
     * Method that keeps count of PterodactylEgg's and hatches if its hits the max hatch time (20)
     *
     * @param eggLocation location of the current egg
     */
    @Override
    public void tick(Location eggLocation) {
        super.tick(eggLocation);
        age++;
        System.out.println("PterodactylEgg's age is " + this.age);

        if (age >= 15) {
            hatch(eggLocation);
            Player player;
            player = Application.mapping.trackPlayer();
            player.getEcoPoints().pterodactylHatchesPoints();
        }
    }
}



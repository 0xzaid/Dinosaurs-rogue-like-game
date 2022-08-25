package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a Stegosaur's Egg, is a subclass of Egg
 */
public class StegosaurEgg extends Egg {
    /**
     * The current Egg's age
     */
    private int age = 0;

    /**
     * Constructor.
     * All StegosaurEgg's are represented by a '('
     *
     * @param name string that represents the current StegosaurEgg's name
     */
    public StegosaurEgg(String name) {
        super(name, '(');
        this.addCapability(DinosaurCapability.CARNIVORE_EATING);
        this.addCapability(DinosaurCapability.CARNIVORE_FEEDING);
    }

    /**
     * Method that keeps count of StegosaurEgg's and hatches if its hits the max hatch time (20)
     *
     * @param eggLocation location of the current egg
     */
    @Override
    public void tick(Location eggLocation) {
        super.tick(eggLocation);
        age++;

        if (age == 15) {
            hatch(eggLocation);
            Player player;
            player = Application.mapping.trackPlayer();
            player.getEcoPoints().stegosaurHatchesPoints();
        }
    }


}

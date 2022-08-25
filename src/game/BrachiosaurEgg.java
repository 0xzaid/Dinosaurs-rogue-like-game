package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a Brachiosaur's Egg, is a subclass of Egg
 */
public class BrachiosaurEgg extends Egg {

    /**
     * The current Egg's age
     */
    private int age = 0;

    /**
     * Constructor.
     * All Brachiosaur's eggs are represented by a ')'
     *
     * @param name string that represents the current BrachiosaurEgg's name
     */
    public BrachiosaurEgg(String name) {
        super(name, ')');
        this.addCapability(DinosaurCapability.CARNIVORE_EATING);
        this.addCapability(DinosaurCapability.CARNIVORE_FEEDING);
    }

    /**
     * Method that keeps count of BrachiosaurEgg's age and hatches if its hit the max hatch time (20)
     *
     * @param eggLocation location of the current egg
     */
    @Override
    public void tick(Location eggLocation) {
        super.tick(eggLocation);
        age++;

        if (age == 20) {
            hatch(eggLocation);
            Player player;
            player = Application.mapping.trackPlayer();
            player.getEcoPoints().brachiosaurHatchesPoints();
        }
    }


}

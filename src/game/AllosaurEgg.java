package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents an Allosaur's egg, is a subclass of egg
 */
public class AllosaurEgg extends Egg {
    /**
     * The current Egg's age
     */
    private int age = 0;

    /**
     * Constructor.
     * All allosaurs eggs are represemted by a 'a'
     *
     * @param name string that represents the current AllosaurEgg's name
     */
    public AllosaurEgg(String name) {
        super(name, '*');
        this.addCapability(DinosaurCapability.CARNIVORE_EATING);
        this.addCapability(DinosaurCapability.CARNIVORE_FEEDING);
    }

    /**
     * Method that keeps count of AllosaurEgg's age and hatches if its hit the max hatch time (10)
     *
     * @param eggLocation location of the current egg
     */
    @Override
    public void tick(Location eggLocation) {
        super.tick(eggLocation);
        age++;

        if (age == 10) {
            hatch(eggLocation);
            Player player;
            player = Application.mapping.trackPlayer();
            player.getEcoPoints().allosaurHatchesPoints();
        }
    }

}

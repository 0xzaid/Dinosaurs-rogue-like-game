package game;

/**
 * A class that presents BabyPterodactyl. Is a subclass of BabyDinosaurs
 */
public class BabyPterodactyl extends BabyDinosaur {

    /**
     * Constructor.
     * All BabyPterodactyl are represented by 'p'
     */
    public BabyPterodactyl() {
        super("BabyPterodactyl", 'p');
        this.addCapability(DinosaurCapability.CARNIVORE);
        this.addCapability(DinosaurCapability.BABY_PTERODACTYL);
        this.addCapability(DinosaurCapability.CATCH_FISH);
        this.addCapability(DinosaurCapability.FLY);
        this.setFuel(20);

    }

}


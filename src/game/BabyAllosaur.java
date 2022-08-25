package game;

/**
 * A class that represents BabyAllosaurs. Is a subclass of BabyDinosaur
 */
public class BabyAllosaur extends BabyDinosaur {
    /**
     * Constructor.
     * All BabyAllosaurs are represented by 'a'
     *
     */
    public BabyAllosaur() {
        super("BabyAllosaur", 'a');
        this.addCapability(DinosaurCapability.CARNIVORE);
    }
}

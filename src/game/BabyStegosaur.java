package game;

/**
 * A class that presents BabyStegosaurs. Is a subclass of BabyDinosaurs
 */
public class BabyStegosaur extends BabyDinosaur {
    /**
     * Constructor.
     * All BabyStegosaurs are represented by 's'
     */
    public BabyStegosaur() {
        super("BabyStegosaur", 's');
        this.addCapability(DinosaurCapability.HERBIVORE);

    }
}

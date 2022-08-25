package game;

/**
 * A class that represents BabyBrachiosaurs. Is a subclass of BabyDinosaurs
 */
public class BabyBrachiosaur extends BabyDinosaur{
    /**
     * Constructor.
     * All BabyBrachiosaurs are represented by 'b'
     *
     */
    public BabyBrachiosaur() {
        super("BabyBrachioasur",'b');
        this.addCapability(DinosaurCapability.HERBIVORE);
    }
}

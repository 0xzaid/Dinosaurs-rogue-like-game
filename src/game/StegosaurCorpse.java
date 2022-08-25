package game;

/**
 * A class that represents a dead Stegosaur instance, is a subclass of Corpse
 */
public class StegosaurCorpse extends Corpse{

    /**
     * Constructor.
     * All StegosaurCorpses are represented by a '{'
     *
     * @param name string that represents the current StegosaurCorpse's name
     */
    public StegosaurCorpse(String name) {
        super(name, '{');
        addFood = 50;
        maxAddFood = 50;
    }

    /**
     * Getter.
     *
     * @return Amount of Eco to add to player if fed
     */
    @Override
    public int getAddEco() {
        return addEco;
    }

    /**
     * Getter.
     *
     * @return addFood attribute of StegosaurCorpse
     */
    public int getAddFood() {
        return addFood;
    }

}

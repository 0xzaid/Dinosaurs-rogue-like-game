package game;

/**
 * A class that represents a dead Allosaur instance, is a subclass of Corpse
 */
public class AllosaurCorpse extends Corpse {

    /**
     * Constructor.
     * All AllosaurCorpses are represented by a '/'
     *
     * @param name string that represents the current AllosaurCorpse's name
     */
    public AllosaurCorpse(String name) {
        super(name, '/');
        addFood = 50; maxAddFood = 50;
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
     * @return addFood attribute of AllosaurCorpse
     */
    public int getAddFood() {
        return addFood;
    }



}

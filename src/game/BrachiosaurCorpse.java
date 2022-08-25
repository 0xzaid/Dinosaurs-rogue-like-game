package game;

import edu.monash.fit2099.engine.Location;

/**
 * A class that represents a dead Brachiosaur instance, is a subclass of Corpse
 */
public class BrachiosaurCorpse extends Corpse {

    /**
     * Constructor.
     * All BrachiosaurCorpses are represented by a '}'
     *
     * @param name string that represents the current BrachiosaurCorpse's name
     */
    public BrachiosaurCorpse(String name) {
        super(name, '}');
        addFood = 100; maxAddFood = 100;
    }

    /**
     * Method that removes Corpse after the a period of time
     *
     * @param location Location of the current BrachiosaurCorpse
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        age++;
        if (this.age == 40)
            location.removeItem(this);
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
     * @return addFood attribute of BrachiosaurCorpse
     */

    public int getAddFood() {
        return addFood;
    }

}

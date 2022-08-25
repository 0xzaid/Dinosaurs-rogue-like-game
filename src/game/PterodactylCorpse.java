package game;

/**
 * A class that represents a dead Pterodactyl instance, is a subclass of Corpse
 */
public class PterodactylCorpse extends Corpse {

    /**
     * Constructor.
     * All PterodactylCorpse are represented by a 'x'
     *
     * @param name string that represents the current PterodactylCorpse's name
     */
    public PterodactylCorpse(String name) {
        super(name, 'x');
        addFood = 30;
        maxAddFood = 30;
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
     * @return addFood attribute of PterodactylCorpse
     */
    public int getAddFood() {
        return addFood;
    }
}

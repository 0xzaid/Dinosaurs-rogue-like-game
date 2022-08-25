package game;

/**
 * Fish that live in the lake
 */
public class Fish extends PortableItem implements Feedable {
    /**
     * Amount of addFood actor gains when fish is eaten
     */
    private int addFood = 5;

    /**
     * Constructor to initialise the name of the item, and display characters.
     *
     * @param name Name of the item
     */
    public Fish(String name) {
        super(name, 'f');
        this.addCapability(DinosaurCapability.FISH);
    }

    @Override
    public int getAddEco() {
        return 0;
    }

    /**
     * Method for getting addFood attribute
     *
     * @return addFood attribute
     */
    @Override
    public int addingFood() {
        return addFood;
    }

    /**
     * Setter.
     *
     * @param addFood addFood of the fish
     */
    public void setAddFood(int addFood) {
        this.addFood = addFood;
    }
}

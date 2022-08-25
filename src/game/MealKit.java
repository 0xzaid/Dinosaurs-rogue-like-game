package game;

import edu.monash.fit2099.engine.Actor;

/**
 * Blueprint of MealKit base class
 */
public abstract class MealKit extends PortableItem implements Feedable {
    /**
     * Attribute to denote the food levels granted by the item to dinosaur
     */
    protected int addFood;
    /**
     * Attribute to denote eco points added to player
     */
    protected int addEco = 0;

    /**
     * This constructor initialise the name of the meal kit, display character and the capabilities of the meal kit
     * @param name Name of the meal kit
     * @param displayChar Display character of the meal kit
     */
    public MealKit(String name, char displayChar) {
        super(name, displayChar);
        this.addCapability(Abilities.MEAL_KIT);
    }

    /**
     * Abstract method for getting maximum food levels of a dinosaur
     * @param dinosaur Targeted dinosaur
     * @return Maximum food levels granted by specific dinosaur
     */
    public abstract int getAddFood(Actor dinosaur);

    /**
     * Getters for addEco attribute
     * @return Eco points gained
     */
    public int getAddEco() {
        return addEco;
    }

    /**
     * Abstract method for getting addFood attribute
     * @return addFood attribute
     */
    @Override
    public abstract int addingFood();

}

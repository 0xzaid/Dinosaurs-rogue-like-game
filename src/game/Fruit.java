package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

/**
 * A class responsible for fruit functionalities
 */
public class Fruit extends PortableItem implements Feedable {
    /**
     * Attribute to denote the food levels granted by the item to dinosaur
     */
    private int addFood = 20;
    /**
     * Age of the fruit
     */
    private int age = 0;
    /**
     * Attribute to denote eco points added to player
     */
    private int addEco = 10;
    /**
     * Maximum age
     */
    private int maxAge = 15;

    /**
     * This constructor initialise the name of the fruit and the capabilities of fruit
     * @param name Name of the fruit
     */
    public Fruit(String name) {
        super(name, 'o');
        this.addCapability(DinosaurCapability.HERBIVORE_EATING);
        this.addCapability(DinosaurCapability.HERBIVORE_FEEDING);
    }

    /**
     * This method perform time transition of a fruit on its location
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
    }

    /**
     * This method perform time transition of a fruit on its location
     * @param location The location of the actor carrying this Item.
     */
    @Override
    public void tick(Location location) {
        incRot();
        //rotting
        if (age >= maxAge)
            location.removeItem(this);
    }

    /**
     * Increment the age of the fruit
     */
    public void incRot() {
        this.age++;
    }

    /**
     * Getters for addEco attribute
     * @return Eco points gained
     */
    @Override
    public int getAddEco() {
        return addEco;
    }

    /**
     * Abstract method for getting addFood attribute
     * @return addFood attribute
     */
    @Override
    public int addingFood() {
        return addFood;
    }

    // this does not seems right
//    @Override
//    public List<Action> getAllowableActions() {
//        List<Action> list = super.getAllowableActions();
//
//        list.add(new EatAction(this));
//        return list;
//    }
}

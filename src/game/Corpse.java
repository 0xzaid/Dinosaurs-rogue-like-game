package game;


import edu.monash.fit2099.engine.Location;

/**
 * An abstract class Corpse that holds common methods and instance variables for the subclasses of Corpse.
 */
public abstract class Corpse extends PortableItem implements Feedable {

    /**
     * protected instance variable that indicates the amount of FoodLevel to be added when a corpse is eaten
     */
    protected int addFood = 50;

    protected int maxAddFood;
    /**
     * protected instance variable that indicates the amount of EcoPoints to be added to Player when a corpse is eaten
     */
    protected int addEco = 10;

    /**
     * Integer that represents age of the corpse
     */
    protected int age = 0;

    private String realName;

    /**
     * Constructor.
     *
     * @param name      name of the current Corpse
     * @param character the character that represents the Corpse
     */
    protected Corpse(String name, char character) {
        super(name, character);
        this.addCapability(DinosaurCapability.CARNIVORE_EATING);
        this.addCapability(DinosaurCapability.CARNIVORE_FEEDING);
        this.addCapability(DinosaurCapability.CORPSE);
        realName = name;
    }

    /**
     * Method that removes Corpse after the a period of time
     *
     * @param location Location of the current Corpse
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        age++;
        if (age == 20)
            location.removeItem(this);
    }

    /**
     * Getter.
     *
     * @return the current age of the Corpse
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter.
     *
     * @param age the new age of this Corpse
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Decrement the food level if it is not 0
     * @param decrease the number of food level to decrease
     */
    public void decrementFoodLevel(int decrease){
        if (addFood > 0){
            addFood = addFood - decrease;
        } else if (addFood == 0){
            // remove the item if reaches 0, but this should happen inside FeedAction
            addFood = 0;
        }
    }

    /**
     * Getter.
     *
     * @return the current name of the Corpse
     */
    public String getName(){
        return this.realName;
    }

    /**
     * Setter.
     *
     * @param name the new name of this Corpse
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return the current food level of the Corpse
     */
    public int getAddFood() {
        return addFood;
    }

    /**
     * Setter.
     *
     * @param addFood the new food level of this Corpse
     */
    public void setAddFood(int addFood) {
        this.addFood = addFood;
    }

    /**
     * Getter.
     *
     * @return the current maximum food level of the Corpse
     */
    public int getMaxAddFood() {
        return maxAddFood;
    }

}

package game;

import edu.monash.fit2099.engine.Actor;

/**
 * A class that responsible for all Vegetarian Meal Kit functionalities
 */
public class VegetarianMealKit extends MealKit {

    /**
     * This constructor initialise the name of the meal kit, the display character, and the capabilities of the
     * meal kit
     * @param name Name of the meal kit
     */
    public VegetarianMealKit(String name) {
        super(name, 'v');
        this.addCapability(DinosaurCapability.HERBIVORE_EATING);
        this.addCapability(DinosaurCapability.HERBIVORE_FEEDING);
    }

    /**
     * For getting maximum food levels of a dinosaur
     * @param dinosaur Targeted dinosaur
     * @return Maximum food levels granted by specific dinosaur
     */
    public int getAddFood(Actor dinosaur) {
        if (dinosaur.hasCapability(DinosaurCapability.STEGOSAUR)){
            addFood = ((Stegosaur) dinosaur).getMaxHitPoints();
        } else if (dinosaur.hasCapability(DinosaurCapability.BRACHIOSAUR)){
            addFood = ((Brachiosaur) dinosaur).getMaxHitPoints();
        }
        return addFood;
    }

    /**
     * Method for getting addFood attribute
     * @return addFood attribute
     */
    @Override
    public int addingFood(){
        return this.addFood;
    }

    /**
     * Getters for addEco attribute
     * @return Eco points gained
     */
    @Override
    public int getAddEco() {
        return 0;
    }
}

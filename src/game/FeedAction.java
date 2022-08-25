package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents the action of feeding dinosaurs
 */
public class FeedAction extends Action {

    /**
     * Target to be fed
     */
    private Actor target;
    /**
     * Item to feed the actor
     */
    private PortableItem item;

    /**
     * Constructor.
     *
     * @param actor The current actor being fed
     * @param item The item to be fed
     */
    public FeedAction(Actor actor, PortableItem item) {
        this.target = actor;
        this.item = item;
    }

    /**
     * Executes the FeedAction
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string containing what actor got fed what
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        boolean pteroFood = false;
        int addedFoodLevel = 0;
        int addedEcoPoints = 0;

        //if (actor.isConscious()) {
            //((Dinosaur) target).addFood(item.AddingFood());
        //} else {

        // if (item instanceof PortableItem)
            ((Dinosaur) target).addFood(item.addingFood());
            //target.heal(item.AddingFood());
            addedFoodLevel = item.addingFood();

        //}

        if (item.hasCapability(Abilities.MEAL_KIT)) {
            if (target.hasCapability(DinosaurCapability.STEGOSAUR)) {
                ((Dinosaur) target).addFood(((MealKit) item).getAddFood(target));
                addedFoodLevel = ((MealKit) item).getAddFood(target);
            } else if (target.hasCapability(DinosaurCapability.BRACHIOSAUR)) {
                ((Dinosaur) target).addFood(((MealKit) item).getAddFood(target));
                addedFoodLevel = ((MealKit) item).getAddFood(target);
            } else if (target.hasCapability(DinosaurCapability.ALLOSAUR)) {
                ((Dinosaur) target).addFood(((MealKit) item).getAddFood(target));
                addedFoodLevel = ((MealKit) item).getAddFood(target);
            } else if (target.hasCapability(DinosaurCapability.PTERODACTYL)) {
                ((Dinosaur) target).addFood(((MealKit) item).getAddFood(target));
                addedFoodLevel = ((MealKit) item).getAddFood(target);
            }
        }

        if (target.hasCapability(DinosaurCapability.PTERODACTYL) && item.hasCapability(DinosaurCapability.CORPSE)){
            pteroFood = true;
            Corpse corpse;

            ((Dinosaur) target).addFood(10);
            addedFoodLevel = 10;
            // should only decrease the corpse food level and corpse remain in inventory since its not fully consumed
            corpse = (Corpse) item;
            corpse.decrementFoodLevel(10);

            corpse.setName(corpse.getName() + " " + ((Corpse) item).getAddFood() + "/" + ((Corpse) item).getMaxAddFood());

            ((Player) actor).getEcoPoints().fedDinosaurPoints();
            addedEcoPoints = ((Feedable) item).getAddEco();

            if (corpse.addFood == 0){
                actor.removeItemFromInventory(corpse);
                // if this does not remove the corpse, see Corpse in decrementFoodLevel()
            }
        }



        if (pteroFood == false){
            actor.removeItemFromInventory(item);
        }

        if (item instanceof Fruit){
            ((Player) actor).getEcoPoints().fedDinosaurPoints();
            addedEcoPoints = ((Feedable) item).getAddEco();
        }


        return (actor + " Fed " + target + " " + item + " for " + addedFoodLevel + " Food levels and Player gained "
                + addedEcoPoints + " Eco Points at " + map.locationOf(target).x() + "," +map.locationOf(target).y());
    }


    /**
     * A method that returns a string in menu
     * @param actor The actor performing the action.
     * @return which actor got fed which item
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Feed " + target + " " + item;
    }
}

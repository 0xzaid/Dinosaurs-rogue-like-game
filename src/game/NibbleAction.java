package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents the action of nibbling for dinosaurs
 */
public class NibbleAction extends Action {

    /**
     * The item to be eaten
     */
    private Item item;

    /**
     * Constructor.
     *
     * @param item to be eaten
     */
    public NibbleAction(Item item) {
        this.item = item;
    }

    /**
     * Execute NibbleAction
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string containing what actor nibble what item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int remaining;
        int addFL = ((PortableItem) item).addingFood();
        remaining = addFL - 10;
        addFL = 10;
        ((Dinosaur) actor).addFood(addFL);
        ((PortableItem) item).addFood = remaining;
        if (remaining == 0) {
            map.locationOf(actor).removeItem(item);
            System.out.println(item.getClass().getName()+ " has been fully eaten");
        }

        return menuDescription(actor);
    }

    /**
     * String description of the NibbleAction
     * @param actor The actor performing the action.
     * @return String description of the NibbleAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " nibbles on " + item.getClass().getName() + " and gained " + 10;
    }
}


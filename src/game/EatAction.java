package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents the action of eating for dinosaurs
 */
public class EatAction extends Action {

    /**
     * The item to be eaten
     */
    private Item item;

    /**
     * Constructor.
     *
     * @param item to be eaten
     */
    public EatAction(Item item) {
        this.item = item;
    }

    /**
     * Execute EatAction
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string containing what actor ate what item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        //add item's foodlevel into actor's foodlevel
        int addFL = ((PortableItem) item).addingFood();
        ((Dinosaur)actor).addFood(addFL);
        map.locationOf(actor).removeItem(item);

        return actor + " at [" + map.locationOf(actor).x() + " , "+map.locationOf(actor).y()  +  "] ate " + item +
                " and gained " + ((PortableItem)item).addingFood();
    }

    /**
     * String description of the EatAction
     * @param actor The actor performing the action.
     * @return String description of the EatAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " ate " + item + " and gained " + ((PortableItem)item).addingFood();
    }
}

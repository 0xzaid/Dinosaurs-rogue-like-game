package game;

import edu.monash.fit2099.engine.*;

/**
 * Action that refuels flying actor to full fuel
 */
public class RefuelAction extends Action {
    /**
     * Where the refueling is taking place
     */
    protected Location location;

    /**
     * Constructor.
     *
     * @param location Where the refueling is taking place
     */
    public RefuelAction(Location location) {
        this.location = location;
    }

    /**
     * Adds fuel to the actor, if pregnant less fuel is added
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return String describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (((Pterodactyl) actor).isPregnant()) {
            ((Pterodactyl) actor).setFuel(5);
        } else {
            ((Dinosaur) actor).setFuel(30);
        }
        actor.addCapability(DinosaurCapability.FLY);
        return actor + " at [" + map.locationOf(actor).x() + " , " + map.locationOf(actor).y() + "] refueled ";


    }

    /**
     * Method that describes the method in the menu
     *
     * @param actor The actor performing the action.
     * @return string stating the actor refueled
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " refueled ";
    }
}

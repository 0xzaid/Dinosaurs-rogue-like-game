package game;

import edu.monash.fit2099.engine.*;

/**
 * Adds waterLevel to dinosaurs that drank water
 */
public class DrinkAction extends Action {

    Location location;

    /**
     * Constructor.
     *
     * @param location where drinking is happening
     */
    public DrinkAction(Location location) {
        this.location = location;
    }

    /**
     * Adds waterLevel to actor and removes a sip from lake
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        Lake thirstToAdd = (Lake) location.getGround();
        int waterLevelGained = 0;

        // probably need to check the availability of water in Behaviour ?
        if (thirstToAdd.waterAvailable() != 0) {
            if (actor.hasCapability(DinosaurCapability.BRACHIOSAUR)) {
                waterLevelGained = 80;
                thirstToAdd.removeSips(1);
                ((Dinosaur) actor).addWaterLevel(waterLevelGained);

            } else {
                waterLevelGained = 30;
                thirstToAdd.removeSips(1);
                ((Dinosaur) actor).addWaterLevel(waterLevelGained);
            }
        }

        // for testing
//        System.out.println("Testing Info. [" + thirstToAdd.getClass().getName() + "] Lake's Water Sips now : "
//                + thirstToAdd.waterAvailable() + ", Lake's Fish now : " + thirstToAdd.fishCount());

        return actor + " at [" + map.locationOf(actor).x() + " , " + map.locationOf(actor).y() + "] drank " +
                thirstToAdd.getClass().getName() + " to gain " +
                (waterLevelGained + " water level, with a total of " + ((Dinosaur) actor).getWaterLevel());

    }

    /**
     * String that describes action in menu
     *
     * @param actor The actor performing the action.
     * @return A string that describes the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drank " + location.getGround().getClass().getName();
    }
}

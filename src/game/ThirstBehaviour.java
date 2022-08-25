package game;

import edu.monash.fit2099.engine.*;

/**
 * When dinosaurs are thirsty they seek water and return DrinkAction
 */
public class ThirstBehaviour implements Behaviour {

    /**
     * Calls getWater, moves towards the water and returns DrinkAction
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return DrinkAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        Lake currentLake = null;

        Location current = map.locationOf(actor);
        Location water = getWater(actor, map);


        if (water != null) {
            if (water.getGround().hasCapability(Abilities.LAKE)) {
                currentLake = (Lake) water.getGround();
            }
        }

        if (water == null || ((actor instanceof Dinosaur) && ((Dinosaur) actor).getWaterLevel() > 40)) {
            return null;
        }

        if (distance(current, water) > 1) {

            Location nearest = null;
            Exit exitUsed = null;

            for (Exit exit : current.getExits()) {
                if (nearest == null || distance(exit.getDestination(), water) < distance(nearest, water)) {
                    nearest = exit.getDestination();
                    exitUsed = exit;
                }
            }
            if (!exitUsed.getDestination().containsAnActor() && exitUsed.getDestination().canActorEnter(actor))
                return new MoveActorAction(nearest, exitUsed.getName());
            else {
                return new WanderBehaviour().getAction(actor, map);
            }

        } else if (distance(current, water) == 1 && currentLake.hasCapability(Abilities.HAS_WATER)) {
            return new DrinkAction(water);
        }
        return null;

    }

    /**
     * Gets nearest water location
     *
     * @param actor actor seeking water
     * @param map   current map
     * @return Location of nearest water
     */
    private Location getWater(Actor actor, GameMap map) {
        Location current = null;
        Lake currentLake = null;
        Location nearest = null;
        Location actorLocation = map.locationOf(actor);
        int iter = 0;
        for (int i = 0; i < map.getXRange().max(); i++) {
            for (int j = 0; j < map.getYRange().max(); j++) {
                if ((map.at(i, j).getGround() instanceof Lake)) {
                    current = map.at(i, j);
                    currentLake = (Lake) current.getGround();
                    if (iter == 0) {
                        nearest = current;
                    }

                    if (distance(actorLocation, current) < distance(actorLocation, nearest) && currentLake.hasCapability(Abilities.HAS_WATER)) {
                        // (currentLake.waterAvailable()) > 0
                        nearest = current;
                    }


                    iter++;
                }
            }
        }

        if (currentLake != null) {
            if (currentLake.hasCapability(Abilities.HAS_NO_WATER)) {
                return null;
            }
        }

        return nearest;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

}

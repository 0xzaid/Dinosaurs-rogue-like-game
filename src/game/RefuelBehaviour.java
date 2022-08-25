package game;


import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Method that looks for trees and moves the actors to them to refuel
 */
public class RefuelBehaviour implements Behaviour {

    /**
     * If tree is found, move towards tree, else wander
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return MoveAction or WanderAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location current = map.locationOf(actor);
        Location tree = getTree(actor, map);

//        if (current.getGround().hasCapability(Abilities.TREE)) {
//            return new RefuelAction(tree);
//        }
        if (tree == null) {
            return null;
        }

        if (distance(current, tree) > 1) {

            Location nearest = null;
            Exit exitUsed = null;

            for (Exit exit : current.getExits()) {
                if (nearest == null || distance(exit.getDestination(), tree) < distance(nearest, tree)) {
                    nearest = exit.getDestination();
                    exitUsed = exit;
                }
            }
            if (!exitUsed.getDestination().containsAnActor() && exitUsed.getDestination().canActorEnter(actor))
                return new MoveActorAction(nearest, exitUsed.getName());
            else {
                return new WanderBehaviour().getAction(actor, map);
            }

        } else if (distance(current, tree) <= 0 && ((Dinosaur) actor).getFuel() <= 0) {
            return new RefuelAction(tree);
        }

        return null;

    }

    /**
     * Gets the closest tree to the actor
     *
     * @param actor that wants to refuel
     * @param map   current map
     * @return Location of nearest tree
     */
    private Location getTree(Actor actor, GameMap map) {
        Location currentTree = null;
        Location nearestTree = null;
        Location actorLocation = map.locationOf(actor);

        int iter = 0;
        for (int i = 0; i < map.getXRange().max(); i++) {
            for (int j = 0; j < map.getYRange().max(); j++) {
                if ((map.at(i, j).getGround() instanceof Tree)) {
                    currentTree = map.at(i, j);
                    if (iter == 0) {
                        nearestTree = currentTree;
                    }

                    if (distance(actorLocation, currentTree) < distance(actorLocation, nearestTree)) {
                        nearestTree = currentTree;
                    }
                    iter++;

                }
            }
        }
        if (nearestTree != null) {
            return nearestTree;
        } else {
            return null;
        }

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

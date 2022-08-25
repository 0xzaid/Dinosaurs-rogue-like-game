package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * BreedBehaviour class will responsible for Breeding Behaviour of the dinosaurs.
 */
public class BreedBehaviour implements Behaviour {
    /**
     * Targeted dinosaur for breeding action
     */
    private Actor target;

    /**
     * This method is for deciding whether a dinosaur (actor) should breed or just moving around.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return An action of whether the dinosaur should breed to just moving around.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // to find the targeted dinosaur
        this.target = getTarget(actor, map);
        if (target == null || ((Dinosaur)actor).getFuel()  <= 0 ||  ((Dinosaur)target).getFuel() <= 0){
//            System.out.println("breed 27");
            return null;
        }

        // locating the actor and target on the map
        Location a = map.locationOf(actor);
        Location b = map.locationOf(target);
        int currentDistance = distance(a, b);

        // to consider all directions to find another dinosaur (target)
        for (Exit exit : a.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, b);
                if (currentDistance != 0 && newDistance < currentDistance) {
                    // move around if no dinosaur nearby
                    if(actor.hasCapability(DinosaurCapability.PTERODACTYL)) {
                        System.out.println("Looking for female");
                        ((Pterodactyl) actor).decrementFuelLevel();
                    }
                    return new MoveActorAction(destination, exit.getName());
                } else if (currentDistance == 0) {
                    //if both are pterodactyl && both under trees
                    if(actor.hasCapability(DinosaurCapability.PTERODACTYL) && target.hasCapability(DinosaurCapability.PTERODACTYL)) {
                        if(a.getGround().hasCapability(Abilities.TREE) && b.getGround().hasCapability(Abilities.TREE)) {
                            return new BreedAction(target);
                        }
                    } else {
                        // breed if there is dinosaur nearby
                        return new BreedAction(target);
                    }

                    }
                }
            }
//        System.out.println("breed 62");
        return null;
    }

    /**
     * To calculate the distance between point a and point b.
     * @param a Point a
     * @param b Point b
     * @return Result of the distance between point a and b.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * This method is to find the targeted dinosaur
     * @param actor Actor performing the action
     * @param map GameMap containing the actor
     * @return The reference of actor1 (target) dinosaur
     */
    private Actor getTarget(Actor actor, GameMap map) {
        // To iterate the whole map to find the target
        for (int i = 0; i < map.getXRange().max(); i++) {
            for (int j = 0; j < map.getYRange().max(); j++) {
                // Male dinosaur (The actor) will responsible for mating female dinosaur (actor1)
                Actor actor1 = map.at(i, j).getActor();
                // if actor1 is of dinosaur type and opposite genders
                if (actor1 instanceof Dinosaur && ((Dinosaur) actor).isMale() && !((Dinosaur) actor1).isMale())
                    // same type of dino
                    if (actor1.getClass().equals(actor.getClass()))
                        // if actor is not actor1, and actor1 is not pregnant yet
                        if (actor != actor1 && !((Dinosaur) actor1).isPregnant()) {
                            return actor1;
                        }
            }
        }
//        System.out.println("breed 98");
        return null;
    }


}
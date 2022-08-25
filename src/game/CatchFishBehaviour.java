package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * Behaviour for flying dinosaurs to catch fish from lakes
 */
public class CatchFishBehaviour implements Behaviour {
    /**
     * Calls getWater to get location of water, moves actor towards closest water and catches 0, 1 or 2 fish
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return EatAction to eat the fish
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

        if ((current.getGround() instanceof Lake && current.getGround().hasCapability(Abilities.HAS_WATER)
                && ((Dinosaur) actor).isThirsty())) {
            return new DrinkAction(water);
        } else if (current.getGround() instanceof Lake && current.getGround().hasCapability(Abilities.HAS_FISH)
                && ((Dinosaur) actor).getFoodLevel() < 40) {
            Random random = new Random();
            int no;
            if (currentLake.fishCount() >= 2) {
                no = random.nextInt(3);
                System.out.println(no);
                switch (no) {
                    case 0 -> System.out.println("Didnt catch any fish");
                    case 1 -> {
                        System.out.println("Fish before eating " + currentLake.fishCount());
                        currentLake.removeFish(1);
                        System.out.println("Fish after eating " + currentLake.fishCount());
                        return new EatAction(currentLake.getFish().get(0));
                    }
                    case 2 -> {
                        System.out.println("Fish before eating " + currentLake.fishCount());
                        Fish fishToEat = currentLake.getFish().get(0);
                        fishToEat.setAddFood(10);
                        currentLake.removeFish(2);
                        System.out.println("Fish after eating " + currentLake.fishCount());
                        return new EatAction(fishToEat);
                    }
                }
            }

        }

//        if (current.getGround().hasCapability(Abilities.HAS_WATER) && ((Dinosaur) actor).isThirsty()) {
//            return new DrinkAction(water);
//        }
//        if (current.getGround().hasCapability(Abilities.HAS_FISH) && ((Dinosaur) actor).isHungry()) {
//            return new EatAction(currentLake.getFish().get(0));
//        }

        if (water == null) {
            System.out.println("catch fish ln 65");
            return null;
        }


        if (distance(current, water) > 0) {

            Location nearest = null;
            Exit exitUsed = null;

            for (Exit exit : current.getExits()) {
                if (nearest == null || distance(exit.getDestination(), water) < distance(nearest, water)) {
                    nearest = exit.getDestination();
                    exitUsed = exit;
                }
            }
            if (!exitUsed.getDestination().containsAnActor() && exitUsed.getDestination().canActorEnter(actor)) {
                return new MoveActorAction(nearest, exitUsed.getName());

//            else if (distance(current, water) == 0 && currentLake.hasCapability(Abilities.HAS_WATER) && ((Dinosaur) actor).isThirsty()) {
//                return new DrinkAction(water);

            }


        } else {
            return new WanderBehaviour().getAction(actor, map);
        }
        System.out.println("catch fish ln 93");
        //return null;
        return new WanderBehaviour().getAction(actor, map);

    }

    /**
     * Gets location of nearest water block
     *
     * @param actor that is seeking water
     * @param map   the GameMap containing the Actor
     * @return Location containing the nearest water
     */
    private Location getWater(Actor actor, GameMap map) {
        Location current;
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

                    if (distance(actorLocation, current) < distance(actorLocation, nearest) &&
                            currentLake.hasCapability(Abilities.HAS_WATER)) {
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


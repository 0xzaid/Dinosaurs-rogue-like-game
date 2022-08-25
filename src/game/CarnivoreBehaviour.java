package game;

import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents Carnivore Dinosaur's behaviours
 */
public class CarnivoreBehaviour implements Behaviour {


    /**
     * A method that implements Carnivore Dinosaurs behaviour
     * If there is nearby Carnivorous food, moves towards and eat it
     *
     * @param actor The dinosaur
     * @param map   current map
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        List<Exit> exits = map.locationOf(actor).getExits();
        Location location;

        for (Exit exit : exits) {
            //items nearby
            List<Item> items = exit.getDestination().getItems();
            for (Item item : items) {
                // if bugged, check if this is the problem : && exit.getDestination().canActorEnter(actor), try to delete this
                if (item.hasCapability(DinosaurCapability.CARNIVORE_EATING) && exit.getDestination().canActorEnter(actor)) {
                    map.moveActor(actor, exit.getDestination());
                    if(actor.hasCapability(DinosaurCapability.PTERODACTYL)) {
                        return new NibbleAction(item);
                    }
                    return new EatAction(item);

                }
            }
        }
        return new WanderBehaviour().getAction(actor, map);
    }
}
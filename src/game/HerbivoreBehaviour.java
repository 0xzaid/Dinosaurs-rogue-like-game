package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents Herbivore Dinosaur's behaviours
 */
public class HerbivoreBehaviour implements Behaviour {


    /**
     * A method that implements Herbivore Dinosaurs behaviour
     * If there is nearby herbivorous food, move towards it and eat it
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {


        List<Exit> exits = map.locationOf(actor).getExits();


        for (Exit exit : exits) {
            //items nearby
            List<Item> items = exit.getDestination().getItems();

            //ground nearby
            Ground ground = exit.getDestination().getGround();

            for (Item item : items) {
                //if item is herbivorous and no actor in the way
                if (item.hasCapability(DinosaurCapability.HERBIVORE_EATING) && !exit.getDestination().containsAnActor() && exit.getDestination().canActorEnter(actor)) {
                    //move dinosaur and eat item
                    map.moveActor(actor, exit.getDestination());

                    return new EatAction(item);
                }
            }
            //if item from ground herbivores can eat and no actors in the way
            if (ground.hasCapability(DinosaurCapability.HERBIVORE_EATING) && !exit.getDestination().containsAnActor() && exit.getDestination().canActorEnter(actor)) {
                map.moveActor(actor, exit.getDestination());
                return new EatAction(exit.getDestination().getItems().get(0));
            }


        }
        return null;

    }


}

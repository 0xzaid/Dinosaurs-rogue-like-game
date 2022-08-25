package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Behaviour that looks for actors to attack
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Looks for actors to attack and returns attackAction
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return AttackAction on target
     */
    public Action getAction(Actor actor, GameMap map) {

        List<Exit> exits = map.locationOf(actor).getExits();

        for (Exit exit : exits) {

            if (exit.getDestination().containsAnActor()) {
                Actor target = exit.getDestination().getActor();

                if ((actor instanceof Allosaur && (target instanceof Pterodactyl || target instanceof BabyPterodactyl))) {
                    return new AttackAction(target);
                }

            }
        }
        return null;
    }
}

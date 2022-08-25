package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * QuitInGameAction class will responsible for quit in game action of the player
 */
public class QuitInGameAction extends Action {

    /**
     * This method execute the logic behind quit in game action of player.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string as an exit code
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return "-1";
    }

    /**
     * This method will return a string to describe player has quit the game
     * @param actor Player
     * @return A string to describe player has quit the game
     */
    @Override
    public String menuDescription(Actor actor) {
        // restart the game
        return "Quit from current game";
    }
}

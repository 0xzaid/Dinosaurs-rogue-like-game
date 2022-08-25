package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
	/**
	 * Attribute Menu
	 */
	private Menu menu = new Menu();
	/**
	 * Attribute Eco Points
	 */
	private EcoPoints ecoPoints = new EcoPoints(0);

	// private EcoPoints ecoPoints = new EcoPoints(3000); // testing set to 3000

	/**
	 * Constructor.
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	/**
	 * Method for executing actions when it comes to the player's turn
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return Action taken by the Player
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Method to determine allowable actions that can be performed to the player
	 * @param otherActor the Actor that might be performing the action
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return List of allowable actions
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return super.getAllowableActions(otherActor, direction, map);
	}

	/**
	 * Getters for the eco points.
	 * @return Eco point attribute
	 */
	public EcoPoints getEcoPoints() {
		return ecoPoints;
	}
}

package game;

import edu.monash.fit2099.engine.*;

/**
 * A class to represent the wall.
 */
public class Wall extends Ground {

	/**
	 * Constructor that initialise the display characters of a wall
	 */
	public Wall() {
		super('#');
	}

	/**
	 * Method to check if the actor can enter the ground type (Wall)
	 *
	 * @param actor the Actor to check
	 * @return False because the actor cannot enter the wall
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * Method to check if an object can be thrown on this ground type (Wall)
	 *
	 * @return True because the objects cannot be thrown to the wall
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}

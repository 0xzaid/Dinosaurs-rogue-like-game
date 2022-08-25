package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {
	/**
	 * Attribute for the counting turns
	 */
	private int turns = 0;
	/**
	 * Counter for triggering the rain in every 10 turn
	 */
	private int counter = 1;
	/**
	 * Reference to the dinosaur at the current tree location
	 */
	private Dinosaur dinosaur = null;

	/**
	 * This constructor initialise the display characters of floor
	 */
	public Floor() {
		super('_');
	}

	/**
	 * This method perform time transition of a floor on its location
	 * @param floorLocation Location of the floor
	 */
	@Override
	public void tick(Location floorLocation) {
		turns++; // increment turns

		// turn off rain 1 turn after every 10th turns
		turnOffRain();
		// trigger rain to fall in every 10 turns (with chances)
		triggerRain(floorLocation);

		// to get the reference of the dinosaur in the current floor
		checkDinosaur(floorLocation);
		// fill dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
		fulfillThirstOnRain();
	}

	/**
	 * To get the reference of the dinosaur on current floor location (if any)
	 * @param floorLocation location of the floor
	 */
	private void checkDinosaur(Location floorLocation) {
		if (floorLocation.getActor() != null) {
			if (floorLocation.getActor().hasCapability(DinosaurCapability.DINOSAUR)) {
				dinosaur = (Dinosaur) floorLocation.getActor();
			}
		}
	}

	/**
	 * To fill the dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
	 */
	private void fulfillThirstOnRain() {
		if (dinosaur != null) {
			if (dinosaur.hasCapability(DinosaurCapability.DINOSAUR) && (!dinosaur.isConscious()) && dinosaur.isThirsty() && Weather.isRain()) {
				dinosaur.addWaterLevelFromRain();
				System.out.println(dinosaur.getClass().getName() + " is CONSCIOUS NOW");
			}
		}
	}

	/**
	 * To trigger rains to fall in every 10 turns
	 * @param floorLocation the location of the floor
	 */
	private void triggerRain(Location floorLocation) {
		// we use location [0, 0] to trigger the rain at the beginning of the game
		// rain should only happen once globally in every 10 turns
		if ((floorLocation.x() == 0 && floorLocation.y() == 0) && (turns == counter * 10)) {
			counter++;
			Weather.rain();
		}
	}

	/**
	 * Turn off the rain 1 turn after every 10th turns
	 */
	private void turnOffRain() {
		if (turns % 10 == 1) {
			Weather.isRain = false;
		}
	}

	/**
	 * This method listed all the allowable actions of a floor
	 * @param actor the Actor acting
	 * @param location the current Location where the floor is at
	 * @param direction the direction of the Ground from the Actor
	 * @return List of actions
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		Actions list = super.allowableActions(actor, location, direction);

		// can only move to second map if the actor is the player
		if (actor instanceof Player) {
			if (location.y() == 0) {
				// to second map
				list.add(new MoveActorAction(Application.secondGameMap.at(location.x(), 24), "to Second Map! [" + location.x() + ", 24]"));
			} else if (location.y() == 24) {
				// to first map
				list.add(new MoveActorAction(Application.firstGameMap.at(location.x(), 0), "to First Map! [" + location.x() + ", 0]"));
			}
		}
		return list;
	}
}

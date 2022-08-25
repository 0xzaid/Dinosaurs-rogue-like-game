package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	/**
	 * Constant variable containing targeted success probabilities of growing bush at the beginning of the turn
	 */
	private static final int SUCCESS_BEGIN = 0;
	/**
	 * Constant variable containing upper bound probabilities of growing bush at the beginning of the turn
	 */
	private static final int UPPER_BOUND_BEGIN = 999;
	/**
	 * Constant variable containing targeted success probabilities of growing bush at any turn
	 */
	private static final int SUCCESS_ANY_TURN = 7;
	/**
	 * Constant variable containing upper bound probabilities of growing bush at any turn
	 */
	private static final int UPPER_BOUND_ANY_TURN = 999;
	/**
	 * Attribute for the counting turns
	 */
	private int turns = 0;
	/**
	 * DirtAuxiliary objects as a helper class for Dirt
	 */
	private DirtAuxiliary dirtAuxiliary = new DirtAuxiliary();
	/**
	 * Counter for triggering the rain in every 10 turn
	 */
	private int counter = 1;
	/**
	 * Reference to the dinosaur at the current dirt location
	 */
	private Dinosaur dinosaur = null;

	/**
	 * This constructor initialise the display characters and capabilities of dirt
	 */
	public Dirt() {
		super('.');
		this.addCapability(Abilities.DIRT);
	}

	/**
	 * This method perform time transition of a dirt on its location
	 * @param dirtLocation Location of the dirt
	 */
	@Override
	public void tick(Location dirtLocation) {
		super.tick(dirtLocation);
		turns++; // increment turns

		// grow bush
		growingBush(dirtLocation);

		// turn off rain 1 turn after every 10th turns
		turnOffRain();
		// trigger rain to fall in every 10 turns (with chances)
		triggerRain(dirtLocation);

		// to get the reference of the dinosaur in the current dirt
		checkDinosaur(dirtLocation);
		// fill dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
		fulfillThirstOnRain();
	}

	/**
	 * For growing bush
	 * @param dirtLocation location of the dirt
	 */
	private void growingBush(Location dirtLocation) {
		// if there is a tree next to the dirt, no chance to grow bush
		if (!dirtAuxiliary.nextToTree(dirtLocation)) {
			if (turns >= 1) { // at the beginning
				growBushBeginning(dirtLocation);
			}
			if (turns >= 2) { // at any turn
				growBushAnyTurn(dirtLocation);
			}
		}
	}

	/**
	 * To trigger rains to fall in every 10 turns
	 * @param dirtLocation the location of the dirt
	 */
	private void triggerRain(Location dirtLocation) {
		// we use location [0, 0] to trigger the rain at the beginning of the game
		// rain should only happen once globally in every 10 turns
		if ((dirtLocation.x() == 0 && dirtLocation.y() == 0) && (turns == counter * 10) && Application.mapping.firstGameMap(dirtLocation)) {
			counter++;
			Weather.rain();
		}
	}

	/**
	 * Turn off the rain 1 turn after every 10th turns
	 */
	private void turnOffRain() {
		// rain should only happen in one turn in every 10th turn, turn off at 11th turn, 21th turn, 31th turn etc
		if (turns % 10 == 1) {
			Weather.isRain = false;
		}
	}

	/**
	 * To get the reference of the dinosaur on current dirt location (if any)
	 * @param dirtLocation location of the dirt
	 */
	private void checkDinosaur(Location dirtLocation) {
		if (dirtLocation.getActor() != null) {
			if (dirtLocation.getActor().hasCapability(DinosaurCapability.DINOSAUR)) {
				dinosaur = (Dinosaur) dirtLocation.getActor();
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
	 * Set the ground to defined ground type.
	 * @param success Success probabilities
	 * @param chanceUpperBound Upper bound of the probabilities
	 * @param dirtLocation Location of the dirt
	 * @param type Defined ground type
	 */
	private void setGroundByChance(int success, int chanceUpperBound, Location dirtLocation, Ground type){
		int chance = GeneratedRandom.randomPossibilities(chanceUpperBound);
		if (chance == success && type.hasCapability(Abilities.BUSH)) {
			dirtLocation.setGround(new Bush());
		}
	}

	/**
	 * This method is for growing bush at the beginning of the turn (with probabilities)
	 * @param dirtLocation Location of the dirt
	 */
	private void growBushBeginning(Location dirtLocation){
		setGroundByChance(SUCCESS_BEGIN, UPPER_BOUND_BEGIN, dirtLocation, new Bush());
		}

	/**
	 * This method is for growing bush at any turn (with probabilities) by considering if there is another bush nearby.
	 * @param dirtLocation Location of the dirt
	 */
	private void growBushAnyTurn(Location dirtLocation) {
		int calculatedX;
		int calculatedY;
		int[] size = dirtAuxiliary.maximumMapSize(); // the maximum size of the map
		int mapBoundaryX = size[0]; // maximum x boundary
		int mapBoundaryY = size[1]; // maximum y boundary

		// bounded until 2 square only
		int X_BOUND = 2;
		int Y_BOUND = 2;
		// flag to terminate the search earlier
		boolean nextToBush = false;

		// for iterating the location in which they are nearby to the current dirt location
		for (int iterateX = -2; iterateX < X_BOUND + 1; iterateX++) {
			if (nextToBush) {
				break;
			}
			for (int iterateY = -2; iterateY < Y_BOUND + 1; iterateY++) {
				calculatedX = dirtLocation.x();
				calculatedY = dirtLocation.y();
				// add current dirt location with the iterated location
				calculatedX = calculatedX + iterateX;
				calculatedY = calculatedY + iterateY;
				if (calculatedX > mapBoundaryX || calculatedY > mapBoundaryY){
					continue;
				}
				// to check whether the location has a bush or nit
				nextToBush = dirtAuxiliary.isBush(dirtLocation, dirtLocation.map(), calculatedX, calculatedY);
				if (iterateX == -2 && iterateY == 0) {
					if (nextToBush) {
						break;
					}
				} else if (iterateX == -1 && (iterateY == -1 || iterateY == 0 || iterateY == 1)) {
					if (nextToBush) {
						break;
					}
				} else if(iterateX == 0) {
					if (nextToBush) {
						break;
					}
				} else if (iterateX == 1 && (iterateY == -1 || iterateY == 0 || iterateY == 1)) {
					if (nextToBush) {
						break;
					}
				} else if (iterateX == 2 && iterateY == 0) {
					if (nextToBush) {
						break;
					}
				}
			}
		}
		if (nextToBush){
			// calculate chance of growing bush at any turn
			int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_ANY_TURN);
			if (chance < SUCCESS_ANY_TURN && this.hasCapability(Abilities.BUSH)) {
				dirtLocation.setGround(new Bush());
			}
		}
	}

	/**
	 * Allowable actions for the dirt object
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return list of allowable actions
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		Actions list = super.allowableActions(actor, location, direction);

		// can only move to second map if the actor is the player
		if (actor instanceof Player){
			if (location.y() == 0){
				// to second map
				list.add(new MoveActorAction(Application.secondGameMap.at(location.x(), 24), "to Second Map! [" + location.x() + ", 24]"));
			}
			else if (location.y() == 24 ){
				// to first map
				list.add(new MoveActorAction(Application.firstGameMap.at(location.x(), 0), "to First Map! [" + location.x() + ", 0]"));
			}
		}
		return list;
	}
}

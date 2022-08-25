package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * Tree class will responsible for all tree functionalities
 */
public class Tree extends Ground {
	/**
	 * Constant variable containing index for harvesting fruit from the array list of fruits
	 */
	private static final int HARVESTED_FRUIT_INDEX = 0;
	/**
	 * Constant variable containing targeted success probabilities for producing fruits
	 */
	private static final int SUCCESS_PRODUCE = 0;
	/**
	 * Constant variable containing upper bound probabilities for producing fruits
	 */
	private static final int UPPER_BOUND_PRODUCE = 10;
	/**
	 * Constant variable containing targeted success probabilities for fruit falling
	 */
	private static final int SUCCESS_FALL = 0;
	/**
	 * Constant variable containing upper bound probabilities for fruit falling
	 */
	private static final int UPPER_BOUND_FALL = 25;
	/**
	 * Age of the tree
	 */
	private int turns = 0;
	/**
	 * Array list of Fruit to contain all produced fruits on the tree
	 */
	private ArrayList<Fruit> fruits = new ArrayList<>();
	/**
	 * Counter for triggering the rain in every 10 turn
	 */
	private int counter = 1;
	/**
	 * Reference to the dinosaur at the current tree location
	 */
	private Dinosaur dinosaur = null;

	/**
	 * This constructor initialise the display characters and capabilities of dirt
	 */
	public Tree() {
		super('+');
		this.addCapability(Abilities.TREE);
		this.addCapability(Abilities.HAS_NO_FRUIT);
	}

	/**
	 * This method perform time transition of a tree on its location
	 * @param treeLocation Location of the tree
	 */
	@Override
	public void tick(Location treeLocation) {
		super.tick(treeLocation);

		turns++; // increment age
		if (turns == 10)
			displayChar = 't';
		if (turns == 20)
			displayChar = 'T';

		// produce the fruit on the bush
		produceRipeFruit(treeLocation);
		// chance for fruit to fall from the tree
		fruitFall(treeLocation);

		// turn off rain 1 turn after every 10th turns
		turnOffRain();
		// trigger rain to fall in every 10 turns (with chances)
		triggerRain(treeLocation);

		// update capabilities
		updateCapabilities();

		// to get the reference of the dinosaur in the current tree
		checkDinosaur(treeLocation);
		// fill dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
		fulfillThirstOnRain();

	}

	/**
	 * To update the capabilities of the bush
	 */
	private void updateCapabilities() {
		// if tree has no fruit, set capabilities to HAS_NO_FRUIT
		if (fruits.size() == 0 && this.hasCapability(Abilities.HAS_FRUIT)){
			this.removeCapability(Abilities.HAS_FRUIT);
			this.addCapability(Abilities.HAS_NO_FRUIT);
		} // if tree has a fruit, set capabilities to HAS_FRUIT
		else if (fruits.size() != 0 && this.hasCapability(Abilities.HAS_NO_FRUIT)){
			this.removeCapability(Abilities.HAS_NO_FRUIT);
			this.addCapability(Abilities.HAS_FRUIT);
		}
	}

	/**
	 * To get the reference of the dinosaur on current tree location (if any)
	 * @param treeLocation location of the bush
	 */
	private void checkDinosaur(Location treeLocation) {
		if (treeLocation.getActor() != null){
			if (treeLocation.getActor().hasCapability(DinosaurCapability.DINOSAUR)){
			dinosaur = (Dinosaur) treeLocation.getActor();
			}
		}
	}

	/**
	 * To fill the dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
	 */
	private void fulfillThirstOnRain() {
		if (dinosaur != null){
			if (dinosaur.hasCapability(DinosaurCapability.DINOSAUR) && (!dinosaur.isConscious()) && dinosaur.isThirsty() && Weather.isRain()){
				dinosaur.addWaterLevelFromRain();
				System.out.println(dinosaur.getClass().getName() + " is CONSCIOUS NOW");
			}
		}
	}

	/**
	 * To trigger rains to fall in every 10 turns
	 * @param treeLocation the location of the tree
	 */
	private void triggerRain(Location treeLocation) {
		// we use location [0, 0] to trigger the rain at the beginning of the game
		// rain should only happen once globally in every 10 turns
		if ((treeLocation.x() == 0 && treeLocation.y() == 0) && (turns == counter * 10) && Application.mapping.firstGameMap(treeLocation)){
			counter++;
			Weather.rain();
		}
	}

	/**
	 * Turn off the rain 1 turn after every 10th turns
	 */
	private void turnOffRain() {
		// rain should only happen in one turn in every 10th turn, turn off at 11th turn, 21th turn, 31th turn etc
		if (turns % 10 == 1){
			Weather.isRain = false;
		}
	}

	/**
	 * This method is for the tree to produce fruit (with probabilities)
	 * @param treeLocation Location of the tree
	 */
	private void produceRipeFruit(Location treeLocation){
		Player player;
		int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_PRODUCE); // 10% chance
		if (chance == SUCCESS_PRODUCE){
			fruits.add(new Fruit("Fruit"));

			player = Application.mapping.trackPlayer();
			// add eco points to player when producing fruit in a tree occur
			player.getEcoPoints().ripeFruitTreePoints();
		}
	}

	/**
	 * This method is for deciding whether a fruit will fall from a tree or not
	 * @param treeLocation Location of the tree
	 */
	public void fruitFall(Location treeLocation){
		int chance  = GeneratedRandom.randomPossibilities(UPPER_BOUND_FALL); // 4% chance, 1/25
		if (chance == SUCCESS_FALL && fruits.size() != 0){
			Fruit fruitFall = fruits.get(0);
			fruits.remove(0);
			treeLocation.addItem(fruitFall);
		}
	}

	/**
	 * This method is for player to harvest the fruit
	 */
	public Fruit toBeHarvested(){
		Fruit fruit = fruits.get(HARVESTED_FRUIT_INDEX);
		fruitHarvested();
		return fruit;
	}

	/**
	 * This method is for removing the fruit from the tree
	 */
	private void fruitHarvested(){
		fruits.remove(HARVESTED_FRUIT_INDEX);
	}

	/**
	 * This method listed all the allowable actions of a tree
	 * @param actor the Actor acting
	 * @param location the current Location where the bush is at
	 * @param direction the direction of the Ground from the Actor
	 * @return List of actions
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction) {
		Actions list = super.allowableActions(actor, location, direction);

		// if there is 1 fruit or more, HarvestAction can be performed
		if (fruits.size() != 0){
			list.add(new HarvestAction(this));
		}

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

	/**
	 * Getters for age attribute
	 * @return Age of the tree
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * Getters for the array list of fruits
	 * @return Array list of fruits
	 */
	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
}

package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * Bush class will responsible for all bush functionalities
 */
public class Bush extends Ground {
    /**
     * Constant variable containing targeted success probabilities of producing fruit
     */
    private static final int SUCCESS_PRODUCE = 0;
    /**
     * Constant variable containing upper bound probabilities of producing fruit
     */
    private static final int UPPER_BOUND_PRODUCE = 20;
    /**
     * Constant variable containing targeted success probabilities of bush gets killed when stepped by Brachiosaur
     */
    private static final int SUCCESS_STEPPED = 0;
    /**
     * Constant variable containing upper bound probabilities of bush gets killed when stepped by Brachiosaur
     */
    private static final int UPPER_BOUND_STEPPED = 2;
    /**
     * Constant variable containing index for harvesting fruit from the array list of fruits
     */
    private static final int HARVESTED_FRUIT_INDEX = 0;
    /**
     * Array list of Fruit to contain all produced fruits on the bush
     */
    private ArrayList<Fruit> fruits = new ArrayList<>();
    /**
     * Attribute for the counting turns
     */
    private int turns = 0;
    /**
     * Counter for triggering the rain in every 10 turn
     */
    private int counter = 1;
    /**
     * Reference to the dinosaur at the current bush location
     */
    private Dinosaur dinosaur = null;

    /**
     * This constructor initialise the display characters and capabilities of a bush
     */
    public Bush() {
        super('"');
        this.addCapability(Abilities.BUSH);
        this.addCapability(Abilities.HAS_NO_FRUIT);
    }

    /**
     * This method perform time transition of a bush on its location
     * @param bushLocation Location of the bush
     */
    @Override
    public void tick(Location bushLocation) {
        super.tick(bushLocation);
        turns++; // increment turns

        // produce the fruit on the bush
        produceRipeFruit();

        // turn off rain 1 turn after every 10th turns
        turnOffRain();
        // trigger rain to fall in every 10 turns (with chances)
        triggerRain(bushLocation);

        // update capabilities
        updateCapabilities();

        // to get the reference of the dinosaur in the current bush
        checkDinosaur(bushLocation);
        // if the Brachiosaur stepped on the bush, there is 50% chance the bush gets killed
        brachiosaurKillsBush(bushLocation);
        // fill dinosaur's water level when rain falls (when dinosaur become unconscious due to thirst)
        fulfillThirstOnRain();
    }

    /**
     * To update the capabilities of the bush
     */
    private void updateCapabilities() {
        // if bush has no fruit, set capabilities to HAS_NO_FRUIT
        if (fruits.size() == 0 && this.hasCapability(Abilities.HAS_FRUIT)){
            this.removeCapability(Abilities.HAS_FRUIT);
            this.addCapability(Abilities.HAS_NO_FRUIT);
        } // if bush has a fruit, set capabilities to HAS_FRUIT
        else if (fruits.size() != 0 && this.hasCapability(Abilities.HAS_NO_FRUIT)){
            this.removeCapability(Abilities.HAS_NO_FRUIT);
            this.addCapability(Abilities.HAS_FRUIT);
        }
    }

    /**
     * To decide whether the brachiosaur success on killing the bush or not.
     * @param bushLocation the location of the bush
     */
    private void brachiosaurKillsBush(Location bushLocation) {
        if (dinosaur != null) {
            if (dinosaur.hasCapability(DinosaurCapability.BRACHIOSAUR)) {
                bushKilled(bushLocation);
            }
        }
    }

    /**
     * To get the reference of the dinosaur on current bush location (if any)
     * @param bushLocation location of the bush
     */
    private void checkDinosaur(Location bushLocation) {
        if (bushLocation.getActor() != null){
            if (bushLocation.getActor().hasCapability(DinosaurCapability.DINOSAUR)){
            dinosaur = (Dinosaur) bushLocation.getActor();
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
     * @param bushLocation the location of the bush
     */
    private void triggerRain(Location bushLocation) {
        // we use location [0, 0] to trigger the rain at the beginning of the game
        // rain should only happen once globally in every 10 turns
        if ((bushLocation.x() == 0 && bushLocation.y() == 0) && (turns == counter * 10) && Application.mapping.firstGameMap(bushLocation)){
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
     * This method is for the bush to produce fruit (with probabilities)
     */
    private void produceRipeFruit(){
        int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_PRODUCE); // 5% chance to produce fruit
        // we always use the lower bound or values less than lower bound as our success probabilities
        // if values fall on lower bound or under lower bound in some cases, then the object has success probabilities
        if (chance == SUCCESS_PRODUCE){
            fruits.add(new Fruit("Fruit"));
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
     * This method is for removing the fruit from the bush
     */
    private void fruitHarvested(){
        fruits.remove(HARVESTED_FRUIT_INDEX);
    }

    /**
     * This method listed all the allowable actions of a bush
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
     * This method is for deciding whether the bush will gets killed when stepped by Brachiosaur or not
     * @param bushLocation The location of the bush
     */
    private void bushKilled(Location bushLocation){
        int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_STEPPED); // 50% chance of bush gets killed
        if (chance == SUCCESS_STEPPED){
            bushLocation.setGround(new Dirt());
        }
    }

    /**
     * This method is to get the array list of fruits on the bush
      * @return Array list of fruits on the bush
     */
    public ArrayList<Fruit> getFruits() {
        return fruits;
    }
}

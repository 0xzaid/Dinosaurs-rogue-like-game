package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * HarvestAction class will responsible for Harvest Action of the player.
 */
public class HarvestAction extends Action {
    /**
     * Constant variable containing targeted success probabilities of harvesting a fruit
     */
    private static final int HARVEST_SUCCESS = 2;
    /**
     * Constant variable containing upper bound probabilities of harvesting a fruit
     */
    private static final int HARVEST_UPPER_BOUND = 5;
    /**
     * Fruit object attribute
     */
    private Fruit fruit;
    /**
     * Bush object attribute
     */
    private Bush bush;
    /**
     * Tree object attribute
     */
    private Tree tree;

    /**
     * This constructor initialise the bush attribute
     */
    public HarvestAction(Bush bush) {this.bush = bush;}

    /**
     * This constructor initialise the tree attribute
     */
    public HarvestAction(Tree tree) {this.tree = tree;}

    /**
     * This method execute the logic behind harvest action of player.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string to describe the player has harvest the fruit
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int chance = GeneratedRandom.randomPossibilities(HARVEST_UPPER_BOUND); // 40% success rate
        fruit = null;

        if (chance < HARVEST_SUCCESS) {
            // if it is a bush object and it has a fruit
            if ((bush != null) && bush.hasCapability(Abilities.HAS_FRUIT)) {
                this.fruit = bush.toBeHarvested();
            } // if it is a tree object and it has a fruit
            else if ((tree != null) && tree.hasCapability(Abilities.HAS_FRUIT)) {
                this.fruit = tree.toBeHarvested();
            }
        }

        // if the fruit is harvested
        if (this.fruit != null){
            // store the fruit inside the player's inventory
            actor.addItemToInventory(this.fruit);
            // add the eco points of the player when they harvest a fruit
            ((Player) actor).getEcoPoints().fruitHarvestedPoints();
            return menuDescription(actor);
            }
        else {
            // If the player fails to harvest the fruit
            return "You search the tree or bush for fruit, but you can’t find any ripe ones.";
        }
    }

    /**
     * This method will return a string to describe player has harvest the fruit
     * @param actor Player
     * @return A string to describe player has harvest the fruit
     */
    @Override
    public String menuDescription(Actor actor) {
        // if it is a bush object and it has a fruit
        if ((bush != null) && bush.hasCapability(Abilities.HAS_FRUIT)) {
            fruit = bush.getFruits().get(0);
        } // if it is a tree object and it has a fruit
        else if ((tree != null) && tree.hasCapability(Abilities.HAS_FRUIT)) {
            fruit = tree.getFruits().get(0);
        }
        return actor + " harvest " + fruit;
    }
}

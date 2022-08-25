package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

/**
 * Lake class will responsible for all lake functionalities
 */
public class Lake extends Ground{
    /**
     * Constant variable containing maximum fish that a single lake can carry
     */
    private final int MAXIMUM_FISH = 25;
    /**
     * Constant variable containing targeted success probabilities for new fish to born
     */
    private final int SUCCESS_BORN = 3;
    /**
     * Constant variable containing upper bound probabilities for new fish to born
     */
    private final int UPPER_BOUND_BORN = 5;
    /**
     * Initial value of water sips of a single lake
     */
    private int waterSips = 25;
    /**
     * Array list of Fish to contain all fish in the lake
     */
    private ArrayList<Fish> fish = new ArrayList<>();
    /**
     * Attribute for the counting turns
     */
    private int turns = 0;
    /**
     * Counter for triggering the rain in every 10 turn
     */
    private int counter = 1;
    /**
     * Reference to the pterodactyl at the current lake location
     */
    private Pterodactyl ptero = null;

    /**
     * This constructor initialise the display characters and capabilities of lake
     */
    public Lake() {
        super('~');
        this.addCapability(Abilities.LAKE);
        this.addCapability(DinosaurCapability.DRINK);
        this.addCapability(Abilities.HAS_WATER);
        this.addCapability(Abilities.HAS_FISH);

        // adding 5 fish as initialization of the lake
        for (int j = 0; j < 5; j++){
            fish.add(new Fish("Fish"));
        }
    }

    /**
     * This method perform time transition of a lake on its location
     * @param lakeLocation Location of the lake
     */
    @Override
    public void tick(Location lakeLocation) {
        super.tick(lakeLocation);
        turns++; // increment turns// increment turns

        // adding water sips to the lake during rain
        addingWaterSipsFromRain(lakeLocation);
        // limit out the number of fish that can born on the lake
        maximumFishToBorn();

        // turn off rain 1 turn after every 10th turns
        turnOffRain();
        // trigger rain to fall in every 10 turns (with chances)
        triggerRain(lakeLocation);

        // update capabilities
        updateCapabilities();

        // only pterodactyl can fly over the lake
        // to get the reference of the pterodactyl in the current lake
        checkPterodactyl(lakeLocation);
        // fill pterodactyl's water level when rain falls (when dinosaur become unconscious due to thirst)
        fulfillThirstOnRainPtero();
    }

    /**
     * To update the capabilities of the bush
     */
    private void updateCapabilities() {
        // if lake has no water sips, set capabilities to HAS_NO_WATER
        if (waterSips == 0 && this.hasCapability(Abilities.HAS_WATER)){
            this.removeCapability(Abilities.HAS_WATER);
            this.addCapability(Abilities.HAS_NO_WATER);
        } // if lake has a water sips, set capabilities to HAS_WATER
        else if (waterSips > 0 && this.hasCapability(Abilities.HAS_NO_WATER)) {
            this.removeCapability(Abilities.HAS_NO_WATER);
            this.addCapability(Abilities.HAS_WATER);
        }

        // if lake has no fish, set capabilities to HAS_NO_FISH
        if (fish.size() == 0 && this.hasCapability(Abilities.HAS_FISH)){
            this.removeCapability(Abilities.HAS_FISH);
            this.addCapability(Abilities.HAS_NO_FISH);
        } // if lake has a fish, set capabilities to HAS_FISH
        else if (fish.size() > 0 && this.hasCapability(Abilities.HAS_NO_FISH)) {
            this.removeCapability(Abilities.HAS_NO_FISH);
            this.addCapability(Abilities.HAS_FISH);
        }
    }

    /**
     * Adding water sips into the lake during rain
     * @param lakeLocation the location of the lake
     */
    private void addingWaterSipsFromRain(Location lakeLocation) {
        if (lakeLocation.x() >= 0 && lakeLocation.y() >= 0){
            if (Weather.isRain()){
                waterSips = waterSips + Weather.waterAdded;
            }
        }
    }

    /**
     * Limit the number of fish that can born inside the lake
     */
    private void maximumFishToBorn() {
        if (fish.size() < MAXIMUM_FISH){
            fishBorn();
        }
    }

    /**
     * To get the reference of the pterodactyl on current lake location (if any)
     * @param lakeLocation location of the lake
     */
    private void checkPterodactyl(Location lakeLocation) {
        if (lakeLocation.getActor() != null) {
            if (lakeLocation.getActor().hasCapability(DinosaurCapability.PTERODACTYL)) {
                ptero = (Pterodactyl) lakeLocation.getActor();
            }
        }
    }

    /**
     * To fill the pterodactyl's water level when rain falls (when dinosaur become unconscious due to thirst)
     */
    private void fulfillThirstOnRainPtero() {
        if (ptero != null){
            if (ptero.hasCapability(DinosaurCapability.PTERODACTYL) && (!ptero.isConscious()) && ptero.isThirsty() && Weather.isRain()){
                ptero.addWaterLevelFromRain();
                System.out.println(ptero.getClass().getName() + " is CONSCIOUS NOW");
            }
        }
    }

    /**
     * To trigger rains to fall in every 10 turns
     * @param lakeLocation the location of the lake
     */
    private void triggerRain(Location lakeLocation) {
        if ((lakeLocation.x() == 0 && lakeLocation.y() == 0) && (turns == counter * 10) && Application.mapping.firstGameMap(lakeLocation)){
            counter++;
            Weather.rain();
            //System.out.println("Rain on lake");
        }
    }

    /**
     * Turn off the rain 1 turn after every 10th turns
     */
    private void turnOffRain() {
        if (turns % 10 == 1){
            Weather.isRain = false;
        }
    }

    /**
     * To make fish to born (with chances)
     */
    private void fishBorn(){
        int chance = GeneratedRandom.randomPossibilities(UPPER_BOUND_BORN); // 60% chance
        if (chance < SUCCESS_BORN){
            fish.add(new Fish("Fish"));
        }
    }

    /**
     * Return the number of water sips available inside the lake
     * @return the number of water sips available inside the lake
     */
    public int waterAvailable() {
        return waterSips;
    }

    /**
     * To remove the number of water sips inside the lake based from the quantity
     * @param quantity number of water sips to be removed
     */
    public void removeSips(int quantity){
        waterSips =  waterSips - quantity;
    }

    /**
     * Return the array list of fish
     * @return the array list of fish
     */
    public ArrayList<Fish> getFish() {
        return fish;
    }

    /**
     * To remove the fish inside the lake based from the quantity
     * @param quantity number of fish to be removed
     */
    public void removeFish(int quantity){
        // checked amount of fish before removing (in respective behaviour/action that use this method)
        for (int q = 0; q < quantity; q++){
            fish.remove(0);
        }
    }

    /**
     * Return the number of fish inside the lake
     * @return the number of fish inside the lake
     */
    public int fishCount(){
        return fish.size();
    }

    /**
     * This method listed all the allowable actions of a lake
     * @param actor the Actor acting
     * @param location the current Location where the lake is at
     * @param direction the direction of the Ground from the Actor
     * @return List of actions
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        return super.allowableActions(actor, location, direction);
    }

    /**
     * To decide whether the actor can enter the lake
     * @param actor the Actor to check
     * @return true if the actor can enter, false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(DinosaurCapability.FLY);
    }

    /**
     * To decide whether the actor can throw object inside the lake
     * @return true if the actor cannot throw object inside the lake, false otherwise
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }
}

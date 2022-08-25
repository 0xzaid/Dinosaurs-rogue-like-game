package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * A class that represents Dinosaurs and holds common methods of all types of dinosaurs
 */
public abstract class Dinosaur extends Actor {

    /**
     * integer that sets the starting food level value of the Dinosaur
     */
    private int foodLevel = 50;

    private int fuel = 30;

    /**
     * integer that sets the starting thirst level of the Dinosaur
     */
    private int waterLevel = 60;

    /**
     * Maximum water level of a dinosaur
     */
    protected int maxWaterLevel = 100;
    /**
     * integer to keep track for how many turns the dinosaur has been unconscious for
     */
    private int unconsciousCounter = 0;
    /**
     * boolean that sets default pregnancy status as false. This is to keep count of when the
     * Dinosaur should lay it's egg
     */
    private boolean pregnant = false;
    /**
     * Integer that keeps track for how many turns the dinosaur has been pregnant for
     */
    private int pregnantCounter = 0;
    /**
     * protected Arraylist to store the behaviours to able to loop through them and do actions based on it's attributes
     */
    protected ArrayList<Behaviour> behaviourArrayList = new ArrayList<>();

    /**
     * private attribute that has 50% chance of making a dinosaur a male, else it is a female
     */
    private boolean male = (Math.random() < 0.5);

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    protected Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        super.maxHitPoints = 100;
        this.addCapability(DinosaurCapability.DINOSAUR);

        if (this instanceof Stegosaur) {
            behaviourArrayList.add(new BreedBehaviour());
            behaviourArrayList.add(new ThirstBehaviour());
            behaviourArrayList.add(new WanderBehaviour());
            behaviourArrayList.add(new HerbivoreBehaviour());

        } else if (this instanceof Allosaur) {
            behaviourArrayList.add(new BreedBehaviour());
            behaviourArrayList.add(new ThirstBehaviour());
            behaviourArrayList.add(new CarnivoreBehaviour());
            behaviourArrayList.add(new WanderBehaviour());
            behaviourArrayList.add(new AttackBehaviour());

        } else if (this instanceof Brachiosaur) {
            behaviourArrayList.add(new BreedBehaviour());
            behaviourArrayList.add(new ThirstBehaviour());
            behaviourArrayList.add(new WanderBehaviour());
            behaviourArrayList.add(new HerbivoreBehaviour());

        } else if (this instanceof Pterodactyl) {
            behaviourArrayList.add(new BreedBehaviour());
            behaviourArrayList.add(new RefuelBehaviour());
            behaviourArrayList.add(new ThirstBehaviour());
            behaviourArrayList.add(new CatchFishBehaviour());
            behaviourArrayList.add(new WanderBehaviour());
            behaviourArrayList.add(new CarnivoreBehaviour());


        } else if (this instanceof BabyDinosaur) {
            behaviourArrayList.add(new ThirstBehaviour());
            behaviourArrayList.add(new WanderBehaviour());
            if (this instanceof BabyAllosaur || this instanceof BabyPterodactyl) {
                behaviourArrayList.add(new CarnivoreBehaviour());
                if (this instanceof BabyPterodactyl) {
                    behaviourArrayList.add(new RefuelBehaviour());
                    behaviourArrayList.add(new CatchFishBehaviour());
                }
            } else if (this instanceof BabyBrachiosaur || this instanceof BabyStegosaur) {
                behaviourArrayList.add(new HerbivoreBehaviour());
            }
        }

    }

    @Override
    public boolean isConscious() {
        return foodLevel > 0 && waterLevel > 0;
    }

    @Override
    public void heal(int points) {
        this.foodLevel += points;
        this.foodLevel = Math.min(this.foodLevel, maxHitPoints);
    }

    @Override
    public void hurt(int points) {
        if (this.foodLevel > 0) {
            this.foodLevel -= points;
        } else if (this.foodLevel == 0) {
            this.foodLevel = 0;
        }
    }

    /**
     * Returns the actions that another actor can do to the current actor
     *
     * @param actor     the other actor
     * @param direction String representing the direction of the other Actor
     * @param map       current GameMap
     * @return Actions
     */
    @Override
    public Actions getAllowableActions(Actor actor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));

        for (Item item : actor.getInventory()) {
            if (item instanceof Feedable) {
                if (this.hasCapability(DinosaurCapability.CARNIVORE)) {
                    if (item.hasCapability(DinosaurCapability.CARNIVORE_FEEDING)) {
                        actions.add(new FeedAction(this, (PortableItem) item));
                    }
                }
                if (this.hasCapability(DinosaurCapability.HERBIVORE)) {
                    if (item.hasCapability(DinosaurCapability.HERBIVORE_FEEDING)) {
                        actions.add(new FeedAction(this, (PortableItem) item));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * play Dinosaur's turn and execute its behaviours
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        //if can lose FoodLevel, then do, else increase unconsciousCounter
        // for all dinosaur types (except Brachiosaur)
        if (getFoodLevel() >= 0) {
            decrementFoodLevel();
            System.out.println(this.getClass().getName() + " Food level is " + this.foodLevel);
        }
        if (getWaterLevel() >= 0) {
            decrementWaterLevel();
            System.out.println(this.getClass().getName() + " Water level is " + this.waterLevel);
        }
        // for all dinosaur types (including Brachiosaur)
        if (getWaterLevel() <= 0 || getFoodLevel() <= 0) {
            this.incrementUnconsciousCounter();
            System.out.println(this.getClass().getName() + " Been unconscious for " + this.unconsciousCounter);
        }

        boolean refuel = false;
        if (this instanceof Pterodactyl) {
            if (getFuel() <= 0) {
                this.setFuel(0);
                refuel = true;
                this.removeCapability(DinosaurCapability.FLY);
            } else {
                display.println(this.getClass().getName() + " fuel's level is " + this.getFuel());
            }
        }
        if (refuel) {
            display.println(this.getClass().getName() + " at " + "(" + map.locationOf(this).x() + "," +
                    map.locationOf(this).y() + ")" + " needs to refuel is now walking ");

        }

        // for Brachiosaur (food level and water level)
        boolean hungry = false;
        if (this instanceof Brachiosaur) {
            if (getFoodLevel() < 140 && getFoodLevel() > 0)
                hungry = true;
        } else {

            if (getFoodLevel() < 90 && getFoodLevel() > 0) {
                hungry = true;
            }
        }
        if (hungry) {
            display.println(this.getClass().getName() + " at " + "(" + map.locationOf(this).x() + "," +
                    map.locationOf(this).y() + ")" + " is getting hungry!!");
        }

        // for deciding whether dinosaur is thirsty or not
        boolean thirsty = false;
        if ((getWaterLevel() > 0) && (getWaterLevel() < 40)) {
            thirsty = true;
        }

        // if dinosaur is thirsty, shows the message
        if (thirsty) {
            display.println(this.getClass().getName() + " at " + "(" + map.locationOf(this).x() + "," +
                    map.locationOf(this).y() + ")" + " is getting thirsty!!");
        }

        //if been unconscious for more than 20 turns, replace by a corpse
        Location location = map.locationOf(this);

        // try this if it bugs
        // if (this.getUnconsciousCounter() > 15 && waterLevel < 0 && !(Weather.isRain())){}
        if (this.getUnconsciousCounter() > 15 && waterLevel == 0) {
            if (this.hasCapability(DinosaurCapability.STEGOSAUR)) {
                map.removeActor(this);
                location.addItem(new StegosaurCorpse("Stegosaur Corpse"));
            } else if (this.hasCapability(DinosaurCapability.ALLOSAUR)) {
                map.removeActor(this);
                location.addItem(new AllosaurCorpse("Allosaur Corpse"));
            } else if (this instanceof Pterodactyl) {
                map.removeActor(this);
                location.addItem(new PterodactylCorpse("Pterodactyl Corpse"));
            } else if (this.hasCapability(DinosaurCapability.BRACHIOSAUR)) {
                map.removeActor(this);
                location.addItem(new BrachiosaurCorpse("Brachiosaur Corpse")); // see if its work
            }
        }
        if (this.getUnconsciousCounter() >= 20) {
            if (this.hasCapability(DinosaurCapability.STEGOSAUR)) {
                map.removeActor(this);
                location.addItem(new StegosaurCorpse("Stegosaur Corpse"));
            } else if (this.hasCapability(DinosaurCapability.ALLOSAUR)) {
                map.removeActor(this);
                location.addItem(new AllosaurCorpse("Allosaur Corpse"));
            } else if (this instanceof Pterodactyl) {
                map.removeActor(this);
                location.addItem(new PterodactylCorpse("Pterodactyl Corpse"));
            }

        } else if (this.getUnconsciousCounter() >= 15) {
            // Brachiosaur dies
            if (this.hasCapability(DinosaurCapability.BRACHIOSAUR)) {
                map.removeActor(this);
                location.addItem(new BrachiosaurCorpse("Brachiosaur Corpse")); // see if its work
            }

        }
        // for laying eggs
        if (this.pregnant) {
            incrementPregnantCounter();

            if (this.pregnantCounter >= 10) {
                if (this instanceof Stegosaur) {
                    map.locationOf(this).addItem(new StegosaurEgg("StegosaurEgg"));
                    setPregnantCounter(0);
                    setPregnant(false);
                    System.out.println(this.toString() + " lays an egg at " + map.locationOf(this).x() + " ,"
                            + map.locationOf(this).x());
                } else if (this instanceof Pterodactyl) {
                    if (map.locationOf(this).getGround().hasCapability(Abilities.TREE)) {
                        setPregnant(false);
                        setPregnantCounter(0);
                        map.locationOf(this).addItem(new PterodactylEgg("PterodactylEgg"));
                        System.out.println(this.toString() + " lays an egg at " + map.locationOf(this).x() + " ,"
                                + map.locationOf(this).x());
                    }
                }
            } else if (this.pregnantCounter >= 20) {
                if (this instanceof Allosaur) {
                    map.locationOf(this).addItem(new AllosaurEgg("AllosaurEgg"));
                    setPregnantCounter(0);
                    setPregnant(false);
                    System.out.println(this.toString() + " lays an egg at " + map.locationOf(this).x() + " ,"
                            + map.locationOf(this).x());
                }
            } else if (this.pregnantCounter >= 30) {
                if (this instanceof Brachiosaur) {
                    map.locationOf(this).addItem(new BrachiosaurEgg("BrachiosaurEgg"));
                    setPregnantCounter(0);
                    setPregnant(false);
                    System.out.println(this.toString() + " lays an egg at " + map.locationOf(this).x() + " ,"
                            + map.locationOf(this).x());
                }
            }
        }
        Action doBehaviour = null;


        for (Behaviour behaviour : behaviourArrayList) {
            if (this.isConscious()) {
                if (!this.isThirsty() && !this.isHungry() && behaviour.getAction(this, map) != null) {
                    doBehaviour = behaviour.getAction(this, map);

                } else if (behaviour instanceof BreedBehaviour) { // BreedBehaviour
                    if (this instanceof Stegosaur || this instanceof Allosaur || (this instanceof Pterodactyl && !refuel))
                        if (this.getFoodLevel() > 50) {
                            doBehaviour = behaviour.getAction(this, map);

                        }
                } else if (this instanceof Brachiosaur && this.getFoodLevel() > 70) {
                    doBehaviour = behaviour.getAction(this, map);

                } else if (behaviour instanceof ThirstBehaviour) { // ThirstBehaviour
                    this.decrementFuelLevel();
                    doBehaviour = behaviour.getAction(this, map);

                } else if (refuel && behaviour instanceof RefuelBehaviour) {
                    doBehaviour = behaviour.getAction(this, map);

                } else if (behaviour instanceof CarnivoreBehaviour && this.isHungry()) { // CarnivoreBehaviour
                    doBehaviour = behaviour.getAction(this, map);


                } else if (!refuel && behaviour instanceof CatchFishBehaviour && this.isHungry()) {
                    this.decrementFuelLevel();
                    doBehaviour = behaviour.getAction(this, map);

                } else if (behaviour instanceof WanderBehaviour && this instanceof Pterodactyl) { // WanderBehaviour
                    doBehaviour = behaviour.getAction(this, map);
                    if (!refuel) {
                        this.decrementFuelLevel();
                    }
                } else if (behaviour instanceof AttackBehaviour) { // AttackBehaviour
                    doBehaviour = behaviour.getAction(this, map);
//                    return doBehaviour;

                } else if (!(this.hasCapability(DinosaurCapability.PTERODACTYL)) ||
                        !(this.hasCapability(DinosaurCapability.BABY_PTERODACTYL))) {
                    doBehaviour = behaviour.getAction(this, map);
                }
                if (doBehaviour != null) {
                    return doBehaviour;

                }


            }
        }

        return new DoNothingAction();
    }

    /**
     * Setter.
     *
     * @param male Set Dinosaur as a male
     */
    public void setMale(boolean male) {
        this.male = male;
    }

    /**
     * Decreases food level by 1 every turn
     */
    protected void decrementFoodLevel() {
        if (this.foodLevel > 0) {
            this.foodLevel--;
        } else if (this.foodLevel == 0) {
            this.foodLevel = 0;
        }
    }

    /**
     * Getter.
     *
     * @return current Dinosaur's food level
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * Method that adds foodLevel to dinosaurs
     *
     * @param addFL amount of foodLevel to add
     */
    public void addFood(int addFL) {
        int max;
        //Brachiosaur has different max foodLevel
        if (this instanceof Brachiosaur) {
            max = 160;
        } else {
            max = 100;
        }
        if (this.foodLevel + addFL >= max) {
            this.foodLevel = max;
        } else {
            this.foodLevel += addFL;
        }
    }

    /**
     * Setter.
     *
     * @param foodLevel new foodLevel to be set
     */
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    /**
     * Getter.
     *
     * @return integer that represents the max hit points of a dinosaur
     */
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    /**
     * Getter.
     *
     * @return waterLevel of current dinosaur
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    public void decrementWaterLevel() {
        if (this.waterLevel > 0) {
            this.waterLevel--;
        } else if (this.waterLevel == 0) {
            this.waterLevel = 0;
        }
    }

    public void addWaterLevel(int waterLevel) {
        if (this instanceof Brachiosaur) {
            if (this.waterLevel + waterLevel > 200) {
                this.waterLevel = 200;
            } else {
                this.waterLevel += waterLevel;
            }
        } else {
            if (this.waterLevel + waterLevel > 100) {
                this.waterLevel = 100;
            } else {
                this.waterLevel += waterLevel;
            }

        }
    }

    public void addWaterLevelFromRain() {
        this.waterLevel += 10;
    }

    public boolean isThirsty() {
        return this.getWaterLevel() < 40;
    }

    public boolean isHungry() {
        return this.getFoodLevel() < 90;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    /**
     * Setter.
     *
     * @param b pregnant or not
     */
    public void setPregnant(boolean b) {
        this.pregnant = b;
    }

    public void setPregnantCounter(int pregnantCounter) {
        this.pregnantCounter = pregnantCounter;
    }

    /**
     * Counter to keep track of many turns the dinosaur been pregnant for
     */
    private void incrementPregnantCounter() {
        this.pregnantCounter++;
        System.out.println(this.getClass().getName() + " pregnant for " + this.getPregnantCounter());
    }

    public int getPregnantCounter() {
        return pregnantCounter;
    }

    /**
     * Getter.
     *
     * @return current dinosaur's unconscious counter
     */
    protected int getUnconsciousCounter() {
        return this.unconsciousCounter;
    }

    /**
     * Increase unconscious counter by 1 every turn
     */
    private void incrementUnconsciousCounter() {
        this.unconsciousCounter++;
    }

    /**
     * Getter.
     *
     * @return if Dinosaur is pregnant or not
     */
    public boolean isPregnant() {
        return this.pregnant;
    }

    /**
     * Getter.
     *
     * @return if Dinosaur is male or not
     */
    public boolean isMale() {
        return male;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        if (this.hasCapability(DinosaurCapability.PTERODACTYL) || this.hasCapability(DinosaurCapability.BABY_PTERODACTYL)) {
            this.fuel = fuel;
        }
    }

    protected void decrementFuelLevel() {
        this.fuel--;
    }

}


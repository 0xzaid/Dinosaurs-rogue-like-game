package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that represents BabyDinosaurs and holds common methods of all types of BabyDinosaurs
 */
public abstract class BabyDinosaur extends Dinosaur {

    /**
     * Integer that represents age of the BabyDinosaur
     */
    private int age = 0;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     */
    protected BabyDinosaur(String name, char displayChar) {
        super(name, displayChar, 100);
        super.setFoodLevel(10);
    }

    /**
     * play BabyDinosaur's turn and Executes its behaviours
     * Increase's the age of the BabyDinosaur, replaces the baby with the adult version of the same
     *
     * @param actions    collection of possible Actions for this Actor
     * @param action
     * @param map        the map containing the Actor
     * @param display
     * @return Action
     */
    public Action playTurn(Actions actions, Action action, GameMap map, Display display) {
        this.incAge();
        Location location = map.locationOf(this);

        if (this.getAge() >= 30) {
            if (this instanceof BabyStegosaur) {
                map.addActor(new Stegosaur("Stegosaur"), location);
                map.removeActor(this);
            } else if(this instanceof BabyPterodactyl) {
                map.addActor(new Pterodactyl("Grown up Pterodactyl"), location);
                map.removeActor(this);
            }
        } else if (this.getAge() >= 50) {
            if (this instanceof BabyBrachiosaur) {
                map.removeActor(this);
                map.addActor(new Brachiosaur("Brachiosaur"), location);
            } else if (this instanceof BabyAllosaur) {
                map.removeActor(this);
                map.addActor(new Allosaur("Allosaur"), location);
            }
            return new DoNothingAction();
        }

        for (int i = 0; i < behaviourArrayList.size() && this.isConscious(); i++) {
            //wander behaviour
            Action action1 = behaviourArrayList.get(i).getAction(this, map);
            if (action1 != null) {
                return action1;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Setter.
     *
     * @param age new value of age to be set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter.
     *
     * @return current BabyDinosaure's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Method that increases the age attribute by 1
     */
    private void incAge() {
        age++;
        System.out.println(this.getClass().getName() + " age is " + this.getAge());
    }
}

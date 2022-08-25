package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * BreedAction class will responsible for Breeding Action of the dinosaurs.
 */
public class BreedAction extends Action {
    /**
     * Targeted dinosaur for breeding action
     */
    private Actor target;

    /**
     * This constructor will initialize the target dinosaur.
     * @param target A dinosaur where the breeding action will apply
     */
    public BreedAction(Actor target) {
        this.target = target;
    }

    /**
     * This method execute the logic behind breeding action of a dinosaur.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string to describe the target dinosaur is now pregnant.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Actor pregnantDino = null;
        // if the actor is male, only the mating will occur
        if (((Dinosaur) actor).isMale()) {
            ((Dinosaur) target).setPregnant(true);
            pregnantDino = target;
        }
        return menuDescription(pregnantDino);
    }

    /**
     * This method will return a string to describe the target dinosaur is now pregnant.
     * @param actor The targeted dinosaur
     * @return A string to describe the target dinosaur is now pregnant.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " is now pregnant ";
    }


}

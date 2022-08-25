package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that represents the species of Herbivore Stegosaurs. Is a child of Dinosaur class
 */
public class Stegosaur extends Dinosaur {

    /**
     * Constructor.
     * All adult Stegosaurs are represented by a 'S' and have 100 hit points.
     *
     * @param name the name of this Stegosaur
     */
    public Stegosaur(String name) {
        super(name, 'S', 100);
        this.addCapability(DinosaurCapability.HERBIVORE);
        this.addCapability(DinosaurCapability.STEGOSAUR);
    }

//	public Stegosaur(String name, boolean male) {
//		super(name, 'S', 100);
//		super.setMale(male);
//		this.addCapability(DinosaurCapability.HERBIVORE);
//        this.addCapability(DinosaurCapability.STEGOSAUR);
//	}
}

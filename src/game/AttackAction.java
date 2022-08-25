package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

import javax.sound.sampled.Port;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}


	/**
	 * Executes AttackAction
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return result of the attack
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

//		if (rand.nextBoolean()) {
//			return actor + " misses " + target + ".";
//		}

		// ----

		int damage = 0;
		String result = null; // "" ?

		if(actor instanceof Allosaur && target instanceof Pterodactyl || target instanceof BabyPterodactyl) {
			result = actor + " ate " + target + " for max food level";
			((Dinosaur) actor).addFood(100);
			map.removeActor(target);
			return result;
		}

		else if (actor instanceof Player){
			if (rand.nextBoolean()) {
				return actor + " misses " + target + ".";
			}

			else {
				damage = weapon.damage();
				result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
			}
		}



		// -------

		target.hurt(damage);
		if (!target.isConscious()) {

			//------
			Item corpse = null;
			if (target instanceof Stegosaur || target instanceof BabyStegosaur){
				corpse = new StegosaurCorpse("Stegosaur Corpse");
			} else if (target instanceof Brachiosaur || target instanceof BabyBrachiosaur){
				corpse = new BrachiosaurCorpse("Brachiosaur Corpse");
			} else if (target instanceof Allosaur || target instanceof BabyAllosaur){
				corpse = new AllosaurCorpse("Allosaur Corpse");
			} else if (actor instanceof Allosaur && (target instanceof Pterodactyl|| target instanceof BabyPterodactyl)) {
				corpse = new PterodactylCorpse("Pterodactyl Corpse");
				// player should also be able to kill Ptero ?
			} else if (actor instanceof Player && (target instanceof Pterodactyl|| target instanceof BabyPterodactyl)){
				corpse = new PterodactylCorpse("Pterodactyl Corpse");
			}


			// ------
			map.locationOf(target).addItem(corpse);

			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}

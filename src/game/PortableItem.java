package game;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	/**
	 * Attribute addFood
	 */
	protected int addFood = 50;

	/**
	 * Constructor to initialise the name of the item, and display characters.
	 * @param name Name of the item
	 * @param displayChar Display character that represents the item in the map
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}

	/**
	 * Getters for addFood attribute
	 * @return addFood attribute
	 */
	public int addingFood(){
		return this.addFood;
	}
}

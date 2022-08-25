package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class responsible for laser gun functionalities
 */
public class LaserGun extends WeaponItem {
    /**
     * Constructor for laser gun.
     * @param name        name of the item
     */
    public LaserGun(String name) {
        super(name, 'g', 100, "hits");
    }
}

package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents the vending machine
 */
public class VendingMachine extends Ground {
    /**
     * Constant variable that represents the price of a fruit
     */
    private final int FRUIT_PRICE = 30;
    /**
     * Constant variable that represents the price of a vegetarian meal kit
     */
    private final int VEGETARIAN_MK_PRICE = 100;
    /**
     * Constant variable that represents the price of a carnivore meal kit
     */
    private final int CARNIVORE_MK_PRICE = 500;
    /**
     * Constant variable that represents the price of a stegosaur egg
     */
    private final int STEGOSAUR_EGG = 200;
    /**
     * Constant variable that represents the price of a brachiosaur egg
     */
    private final int BRACHIOSAUR_EGG = 500;
    /**
     * Constant variable that represents the price of a allosaur egg
     */
    private final int ALLOSAUR_EGG = 1000;
    /**
     * Constant variable that represents the price of a laser gun
     */
    private final int LASER_GUN = 500;
    /**
     * Constant variable that represents the price of a pterodactyl egg
     */
    private final int PTERODACTYL_EGG = 200;

    /**
     * Constructor to initialise the display characters of a vending machine
     */
    public VendingMachine() {
        super('[');
    }

    /**
     * Method to provide mechanism of buying fruit
     * @param ecoPoints Eco point
     * @return Fruit object
     */
    public Fruit buyFruit(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= FRUIT_PRICE){
            ecoPoints.deductByFruit();
            return new Fruit("Fruit");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying vegetarian meal kit
     * @param ecoPoints Eco point
     * @return Vegetarian meal kit object
     */
    public VegetarianMealKit buyVegMK(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= VEGETARIAN_MK_PRICE){
            ecoPoints.deductByVegMealKit();
            return new VegetarianMealKit("Vegetarian Meal Kit");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying carnivore meal kit
     * @param ecoPoints Eco point
     * @return Carnivore meal kit object
     */
    public CarnivoreMealKit buyCarMK(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= CARNIVORE_MK_PRICE){
            ecoPoints.deductByCarMealKit();
            return new CarnivoreMealKit("Carnivore Meal Kit");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying stegosaur egg
     * @param ecoPoints Eco point
     * @return Stegosaur egg object
     */
    public StegosaurEgg buyStegoEgg(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= STEGOSAUR_EGG){
            ecoPoints.deductByStegoEgg();
            return new StegosaurEgg("Stegosaur Egg");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying brachiosaur egg
     * @param ecoPoints Eco point
     * @return Brachiosaur egg object
     */
    public BrachiosaurEgg buyBrachioEgg(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= BRACHIOSAUR_EGG){
            ecoPoints.deductByBrachioEgg();
            return new BrachiosaurEgg("Brachiosaur Egg");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying allosaur egg
     * @param ecoPoints Eco point
     * @return Allosaur egg object
     */
    public AllosaurEgg buyAlloEgg(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= ALLOSAUR_EGG){
            ecoPoints.deductByAlloEgg();
            return new AllosaurEgg("Allosaur Egg");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying laser gun
     * @param ecoPoints Eco point
     * @return Laser gun object
     */
    public LaserGun buyLaserGun(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= LASER_GUN){
            ecoPoints.deductByLaserGun();
            return new LaserGun("Laser Gun");
        }
        return null;
    }

    /**
     * Method to provide mechanism of buying pterodactyl egg
     * @param ecoPoints Eco point
     * @return Pterodactyl egg object
     */
    public PterodactylEgg buyPteroEgg(EcoPoints ecoPoints){
        int eBalance = ecoPoints.getBalance();
        if (eBalance >= PTERODACTYL_EGG){
            ecoPoints.deductByPteroEgg();
            return new PterodactylEgg("Pterodactyl Egg");
        }
        return null;
    }

    /**
     * Method that determine allowable actions that can be perform to the vending machine
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return List of allowable actions
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions list = super.allowableActions(actor, location, direction);
        list.add(new BuyingAction(this));
        return list;
    }
}

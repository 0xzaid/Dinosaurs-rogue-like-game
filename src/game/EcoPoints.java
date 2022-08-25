package game;

/**
 * A class responsible for all eco points functionalities
 */
public class EcoPoints {
    /**
     * Attribute for eco point balance
     */
    private int balance;

    /**
     * This constructor initialise the attribute balance
     */
    public EcoPoints(int balance) {
        this.balance = balance;
    }

    /**
     * Getters for balance
     * @return attribute balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Increment point when a tree produce a ripe fruit
     */
    public void ripeFruitTreePoints(){
        balance++;
    }

    /**
     * Increment point when a player harvest a fruit from bush/tree
     */
    public void fruitHarvestedPoints(){
        balance += 10;
    }

    /**
     * Increment point when a dinosaur is fed by player
     */
    public void fedDinosaurPoints() {
        // only for feeding fruit
        balance += 10;
    }

    /**
     * Increment point when stegosaur egg hatches
     */
    public void stegosaurHatchesPoints(){
        balance += 100;
    }

    /**
     * Increment point when brachiosaur egg hatches
     */
    public void brachiosaurHatchesPoints(){
        balance += 1000;
    }

    /**
     * Increment point when allosaur egg hatches
     */
    public void allosaurHatchesPoints(){
        balance += 1000;
    }

    /**
     * Deduct point when buying fruit
     */
    public void deductByFruit(){
        balance -= 30;
    }

    /**
     * Deduct point when buying vegetarian meal kit
     */
    public void deductByVegMealKit(){
        balance -= 100;
    }

    /**
     * Deduct point when buying carnivore meal kit
     */
    public void deductByCarMealKit(){
        balance -= 500;
    }

    /**
     * Deduct point when buying stegosaur egg
     */
    public void deductByStegoEgg(){
        balance -= 200;
    }

    /**
     * Deduct point when buying brachiosaur egg
     */
    public void deductByBrachioEgg(){
        balance -= 500;
    }

    /**
     * Deduct point when buying allosaur egg
     */
    public void deductByAlloEgg(){
        balance -= 1000;
    }

    /**
     * Deduct point when buying laser gun
     */
    public void deductByLaserGun(){
        balance -= 500;
    }

    /**
     * Deduct point when buying pterodactyl egg
     */
    public void deductByPteroEgg(){
        balance -= 200;
    }

    /**
     * Increment point when pterodactyl egg hatches
     */
    public void pterodactylHatchesPoints(){
        balance += 100;
    }
}

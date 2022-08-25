package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.Scanner;

/**
 * BuyingAction class will responsible for Buying Action of the dinosaurs.
 */
public class BuyingAction extends Action {
    /**
     * Attribute vending machine
     */
    private VendingMachine vendMachine;

    /**
     * This constructor initialise the vending machine attribute
     * @param vendMachine
     */
    public BuyingAction(VendingMachine vendMachine) {
        this.vendMachine = vendMachine;
    }

    /**
     * This method execute the logic behind buying action of a player.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string to describe the player buys an item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        boolean stillBuying = true; // flag for keeping the loop of buying action
        EcoPoints ePoints = ((Player) actor).getEcoPoints(); // get the eco point of the player
        Scanner scanner = new Scanner(System.in);
        int option;
        Item item;
        boolean bought;

        // vending machine menu
        while (stillBuying){
            System.out.println("Current Eco Points Balance : " + ePoints.getBalance());
            System.out.println("Vending Machine Menu : ");
            System.out.println("1. Fruit (30 eco points)");
            System.out.println("2. Vegetarian Meal Kit (100 eco points)");
            System.out.println("3. Carnivore Meal Kit (500 eco points)");
            System.out.println("4. Stegosaur Egg (200 eco points)");
            System.out.println("5. Brachiosaur Egg (500 eco points)");
            System.out.println("6. Allosaur Egg (1000 eco points)");
            System.out.println("7. Laser Gun (500 eco points)");
            System.out.println("8. Pterodactyl Egg (200 eco points)");
            System.out.println("9. Exit");
            System.out.print("Please enter a number between 1 to 9 :");

            // to avoid taking incorrect input
            try {
                option = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Input should only be an integer number between 1 to 9");
                continue;
            }catch (Exception e){
                System.out.println("Another exception occur. Please re enter your option.");
                continue;
            }

            switch (option){
                case 1:
                    item = vendMachine.buyFruit(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Fruit has been purchased for 30 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a fruit.");
                    }
                    break;
                case 2:
                    item = vendMachine.buyVegMK(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Vegetarian meal kit has been purchased for 100 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a vegetarian meal kit.");
                    }
                    break;
                case 3:
                    item = vendMachine.buyCarMK(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Carnivore meal kit has been purchased for 500 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a carnivore meal kit.");
                    }
                    break;
                case 4:
                    item = vendMachine.buyStegoEgg(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Stegosaur egg has been purchased for 200 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a stegosaur egg.");
                    }
                    break;
                case 5:
                    item = vendMachine.buyBrachioEgg(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Brachiosaur egg has been purchased for 500 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a brachiosaur egg.");
                    }
                    break;
                case 6:
                    item = vendMachine.buyAlloEgg(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Allosaur egg has been purchased for 1000 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy an allosaur egg.");
                    }
                    break;
                case 7:
                    item = vendMachine.buyLaserGun(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Laser gun has been purchased for 500 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a laser gun.");
                    }
                    break;
                case 8:
                    item = vendMachine.buyPteroEgg(ePoints);
                    bought = storeItem(actor, item);
                    if (bought){
                        System.out.println("Pterodactyl Egg has been purchased for 200 eco points.");
                    }else{
                        System.out.println(ePoints.getBalance() + " eco points is not sufficient to buy a pterodactyl egg.");
                    }
                    break;
                case 9:
                    stillBuying = false;
                    break;
                default:
            }
            System.out.println();
        }
        return null;
    }

    /**
     * Method to store the item to the player's inventory
     * @param actor Actor that has the inventory
     * @param item Item to be store inside the inventory
     * @return True if the item has been successfully stored inside the inventory
     */
    private boolean storeItem(Actor actor, Item item){
        if (item != null){
            actor.addItemToInventory(item);
            return true;
        } else{
            return false;
        }
    }

    /**
     * This method will return a string to describe the player buys an item
     * @param actor The actor performing the action.
     * @return A string to describe the player buys an item
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys item on vending machine";
    }
}

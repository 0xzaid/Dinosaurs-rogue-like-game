package game;


/**
 * A class that dictates what the dinosaurs are capable of
 */
public enum DinosaurCapability {
    /**
     * Herbivore dinosaurs like Stegosaur and Brachiosaur
     */
    HERBIVORE,
    /**
     * Items that Herbivore dinosaurs can eat without the help of the player
     */
    HERBIVORE_EATING,
    /**
     * Items that a player can feed to a Herbivore Dinosaur
     */
    HERBIVORE_FEEDING,
    /**
     * Carnivore dinosaurs like Allosaur
     */
    CARNIVORE,
    /**
     * Items that Carnivore Dinosaurs can eat without the help of the player
     */
    CARNIVORE_EATING,
    /**
     * Items that a player can feed to a Carnivore Dinosaur
     */
    CARNIVORE_FEEDING,
    /**
     * For identifying Stegosaur
     */
    STEGOSAUR,
    /**
     * For identifying Brachiosaur
     */
    BRACHIOSAUR,
    /**
     * For identifying Allosaur
     */
    ALLOSAUR,
    /**
     * Dinosaur drinks water
     */
    DRINK,
    /**
     * For identifying Pterodactyl
     */
    PTERODACTYL,
    /**
     * For identifying BabyPterodactyl
     */
    BABY_PTERODACTYL,

    /**
     * Identifying Dinosaur
     */
    DINOSAUR,
    /**
     * Identifying fish
     */
    FISH,

    /**
     * Identifying dinosaur can catch fish
     */
    CATCH_FISH,
    /**
     * Identifying corpses
     */
    CORPSE,
    /**
     * Identifying Dinosaur can fly
     */
    FLY

}

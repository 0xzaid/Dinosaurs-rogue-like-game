package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * A class that inherit from World. This class will provide additional implementations for World class.
 */
public class AdvancedWorld extends World {
    /**
     * to store the data for specified moves and eco points by the player from the game driver
     */
    protected int[] movesAndPoints = new int[2];
    /**
     * to store the specified moves
     */
    protected int specifiedMoves = 0;
    /**
     * to store the specified eco points
     */
    protected int specifiedEcoPoints = 0;
    /**
     * to store the current moves of the player
     */
    protected int currentMoves = 0;
    /**
     * to specified the current eco points of the player
     */
    protected int currentEcoPoints = 0;
    /**
     * reference to the game maps through mapping class
     */
    protected Mapping mapping;

    /**
     * Constructor for AdvancedWorld class.
     *
     * @param display the Display that will display this World.
     */
    public AdvancedWorld(Display display) {
        super(display);
    }

    /**
     * To track the eco points balance of the player.
     * @return eco points balance of the player
     */
    private int trackPlayerPoints(){
        player = getMapping().trackPlayer();
        Player playerReal = (Player) player;
        return playerReal.getEcoPoints().getBalance();
    }

    /**
     * To run the challenge game mode.
     * @param moves specified moves by the player in the game driver
     * @param ecoPoints specified eco points by the player in the game driver
     * @return the exit code if the player decides to exit the game (-1 for exit, 0 for finishing the game)
     */
    public int runChallenge(int moves, int ecoPoints) {
        specifiedMoves = moves;
        specifiedEcoPoints = ecoPoints;
        int exitCode = -10;

        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations) {
            lastActionMap.put(actor, new DoNothingAction());
        }

        // This loop is basically the whole game
        while (stillRunning()) {
            currentEcoPoints = trackPlayerPoints();

            System.out.println();
            System.out.println("Current Moves : " + currentMoves);
            System.out.println("Current Eco Points : " + currentEcoPoints);

            if (currentMoves >= specifiedMoves && currentEcoPoints < specifiedEcoPoints) {
                System.out.println();
                System.out.println("You lose the game!");
                break;
            } else if (currentMoves >= specifiedMoves && currentEcoPoints >= specifiedEcoPoints){
                System.out.println();
                System.out.println("You win the game!");
                break;
            }

            GameMap playersMap = actorLocations.locationOf(player).map();
            playersMap.draw(display);

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (stillRunning()){
                    exitCode = processActorTurnInGame(actor);
                    if (exitCode == -1){
                        System.out.println();
                        System.out.println("You choose to quit from the game!");
                        return exitCode;
                    }
                }
            }
            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();
            }
            currentMoves++;
        }
        display.println(endGameMessage());
        return 0;
    }

    /**
     * To run the sandbox game mode.
     * @return the exit code if the player decides to exit the game (-1 for exit, 0 for finishing the game)
     */
    public int runSandbox() {
        int exitCode = -10;

        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations) {
            lastActionMap.put(actor, new DoNothingAction());
        }

        // This loop is basically the whole game
        while (stillRunning()) {
            GameMap playersMap = actorLocations.locationOf(player).map();
            playersMap.draw(display);

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (stillRunning()){
                    exitCode = processActorTurnInGame(actor);
                    if (exitCode == -1){
                        System.out.println();
                        System.out.println("You choose to quit from the game!");
                        return exitCode;
                    }
                }
            }
            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();
            }
        }
        display.println(endGameMessage());
        return 0;
    }

    /**
     * To process the actor turns in the game
     * @param actor The actors of the game
     * @return the exit code if the player decides to exit the game (-1 for exit, 0 for finishing the game)
     */
    protected int processActorTurnInGame(Actor actor) {
        Location here = actorLocations.locationOf(actor);
        GameMap map = here.map();

        Actions actions = new Actions();

        for (Item item : actor.getInventory()) {
            actions.add(item.getAllowableActions());
            // Game rule. If you're carrying it, you can drop it.
            actions.add(item.getDropAction());
        }

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            // Game rule. You don't get to interact with the ground if someone is standing
            // on it.
            if (actorLocations.isAnActorAt(destination)) {
                actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));
            } else {
                actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));
            }

            actions.add(destination.getMoveAction(actor, exit.getName(), exit.getHotKey()));
        }

        for (Item item : here.getItems()) {
            actions.add(item.getAllowableActions());
            // Game rule. If it's on the ground you can pick it up.
            actions.add(item.getPickUpAction());
        }
        actions.add(new DoNothingAction());

        actions.add(new QuitInGameAction()); // for quitting in the middle of the game

        Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);
        lastActionMap.put(actor, action);

        String result = action.execute(actor, map);

        if (result != null){
            // if the player decides to quit
            if (result.equals("-1")){
            return -1;
            }
        }
        display.println(result);
        return 0;
    }

    /**
     * Getters for the game maps.
     * @return array list of game maps
     */
    public ArrayList<GameMap> getGameMaps(){
        return gameMaps;
    }

    /**
     * Getters for the mapping
     * @return mapping object
     */
    public Mapping getMapping() {
        return mapping;
    }

    /**
     * Setters for the mapping
     * @param mapping mapping object
     */
    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }
}

package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * The class GameMap in engine package does not provide
 * complete functionalities for the usage of another class, this is the reason why Mapping class is created.
 * This class will provide additional implementations of GameMap class.
 */
public class Mapping{
    /**
     * GameMap attribute
     */
    public static ArrayList<GameMap> gameMaps;
    /**
     * Map size
     */
    public static int[] mapSize = new int[2];

    /**
     * This constructor initialise the gameMap attribute
     * @param gameMap GameMap attribute
     */
    public Mapping(ArrayList<GameMap> gameMap) {
        this.gameMaps = gameMap;
    }


    /**
     * To add more game maps into Mapping object
     * @param newMap new game map reference
     */
    public void addGameMap(GameMap newMap){
        gameMaps.add(newMap);
    }

    /**
     * This method is to track the reference object of the Player.
      * @return The reference of Player object.
     */
    public Player trackPlayer() {
        // to track player at every map
        for (GameMap gameMap : gameMaps) {
            NumberRange widths = gameMap.getXRange();
            NumberRange heights = gameMap.getYRange();
            Actor player;

            // iterate all locations on the map to find the player
            for (int y : heights) {
                for (int x : widths) {
                    player = gameMap.getActorAt(new Location(gameMap, x, y));
                    if (player instanceof Player) {
                        return (Player) player;
                    }

                }
            }
        }
        return null;
    }

    /**
     * This method is to find the maximum size of the map
     * @return An array containing x and y axis of the maximum map size
     */
    public int[] maxMapSize(){
        int[] arrSize = new int[2];

        GameMap sample = gameMaps.get(0); // all maps hav the same size, so just pick map 1
        arrSize[0] = sample.getXRange().max();
        arrSize[1] = sample.getYRange().max();

        mapSize[0] = sample.getXRange().max();
        mapSize[1] = sample.getYRange().max();

        return arrSize;
        }

    /**
     * To decide whether location is at the first map or not
     * @param location location fo current object
     * @return true if the location is at first game map, false otherwise
     */
    public boolean firstGameMap(Location location){
            if (gameMaps.get(0).at(0, 0).equals(location)){
                return true; // rain triggered only at first map, will rain on the whole map (1 and 2) / globally
            }
            return false;
        }
    }


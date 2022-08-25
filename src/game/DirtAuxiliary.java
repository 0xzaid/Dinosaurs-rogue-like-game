package game;


import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * An auxiliary class for Dirt
 */
public class DirtAuxiliary {

    /**
     * Method to identify the type of the ground
     * @param dirtLocation Location of the dirt
     * @param map GameMap where the dirt is at
     * @param x Location x axis
     * @param y Location y axis
     * @param ability Identifier of the ground type
     * @return True if the location's ground is the same type as the identifier's ground type
     */
    public boolean groundIdentifier(Location dirtLocation, GameMap map, int x, int y, Enum ability){
        int[] size = maximumMapSize(); // get maximum size of the map
        int mapBoundaryX = size[0]; int mapBoundaryY = size[1];

        // return false if the location is out of the map
        if (x < 0 || y < 0 || x > mapBoundaryX || y > mapBoundaryY){
            return false;
        }

        // return true if the ground of the location is the same type of the defined ground type
        Location otherLocation = map.at(x, y);
        if (otherLocation.getGround().hasCapability(ability)){
            return true;
        }
        return false;
    }

    /**
     * To determine whether the location has a bush or not
     * @param dirtLocation Location of the dirt
     * @param map GameMap where the dirt is at
     * @param x Location x axis
     * @param y Location y axis
     * @return True if the location's ground is a bush type
     */
    public boolean isBush(Location dirtLocation, GameMap map, int x, int y){
        return groundIdentifier(dirtLocation, map, x, y, Abilities.BUSH);
    }

    /**
     * To determine whether the location has a tree or not
     * @param dirtLocation Location of the dirt
     * @param map GameMap where the dirt is at
     * @param x Location x axis
     * @param y Location y axis
     * @return True if the location's ground is a tree type
     */
    public boolean isTree(Location dirtLocation, GameMap map, int x, int y){
        return groundIdentifier(dirtLocation, map, x, y, Abilities.TREE);
    }

    /**
     * To determine whether a location is next to a tree or not
     * @param dirtLocation Location of the dirt
     * @return True if the location is next to a tree
     */
    public boolean nextToTree(Location dirtLocation){
        int calculatedX; int calculatedY;
        int[] size = maximumMapSize(); // maximum size of the map
        int mapBoundaryX = size[0]; int mapBoundaryY = size[1];
        boolean tree = false; // flag to terminate the search earlier

        // bounded until 2 square only
        int xBound = 2; int yBound = 2;

        // for iterating the location in which it is next to the location
        for (int iterateX = -1; iterateX < xBound; iterateX++) {
            if (tree){
                break;
            }
            for (int iterateY = -1; iterateY < yBound; iterateY++){
                calculatedX = dirtLocation.x();
                calculatedY = dirtLocation.y();
                calculatedX = calculatedX + iterateX;
                calculatedY = calculatedY + iterateY;

                if (calculatedX <= mapBoundaryX || calculatedY <= mapBoundaryY){
                    tree = isTree(dirtLocation, dirtLocation.map(), calculatedX, calculatedY);
                    if (tree) {
                        break;
                    }
                }
            }
        }
        return tree;
    }

    /**
     * To determine the maximum size of the map
     * @return An array containing the maximum x and y axis of the map
     */
    public int[] maximumMapSize(){
        return Application.mapping.maxMapSize();
    }

}

package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 */
public class Application {
	/**
	 * The first game map (accessible through all the classes)
	 */
	public static GameMap firstGameMap;
	/**
	 * The second game map (accessible through all the classes)
	 */
	public static GameMap secondGameMap;
	/**
	 * AdvancedWorld object
	 */
	public static AdvancedWorld world;
	/**
	 * FancyGroundFactory object
	 */
	private static FancyGroundFactory groundFactory;
	/**
	 * Mapping object
	 */
	public static Mapping mapping;
	/**
	 * All Menu object
	 */
	private static AllMenu allMenu = new AllMenu();
	/**
	 * Specified moves and eco points by the player in the challenge mode
	 */
	private static int[] movesAndPoints = new int [2];
	/**
	 * Specified moves by the player in the challenge mode
	 */
	private static int specMoves = 0;
	/**
	 * Specified eco points by the player in the challenge mode
	 */
	private static int specEcoPoints = 0;

	/**
	 * Main method for the whole game
	 * @param args array string argument
	 */
	public static void main(String[] args) {
		boolean stillRunning = true; // flag for the game's execution

		// this loop will run as long as the player does not choose to close the program
		while (stillRunning){
			restartWorld(); // restart the world
			restartGroundFactory(); // restart the ground factory
			restartMaps(); // restart the maps
			world.setMapping(mapping); // set the mapping object to the world's mapping attribute
			restartActorsAndItems(); // restart the actors and items in the game maps

			// take the specified moves and eco points from the player
			// if challenge mode, it return the specified moves and eco points
			// if sandbox mode, return null
			movesAndPoints = allMenu.gameModeMenu();

			if(movesAndPoints != null){
				// challenge mode
				specMoves = movesAndPoints[0]; specEcoPoints = movesAndPoints[1];
				// need to pass the specified moves and eco points boundary
				world.runChallenge(specMoves, specEcoPoints);
			} else if (movesAndPoints == null){
				// sandbox mode, normal game
				world.runSandbox();
			}
		}
	}

	/**
	 * Restart the world
	 */
	private static void restartWorld(){
		world = new AdvancedWorld(new Display());
	}

	/**
	 * Restart the ground factory
	 */
	private static void restartGroundFactory(){
		groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new VendingMachine(), new Bush(), new Lake());
	}

	/**
	 * Restart all the game maps
	 */
	private static void restartMaps(){
		List<String> firstMap = Arrays.asList(
				"................................................................................",
				"................................................................................",
				".....#######....................................................................",
				".....#_____#.........[..........................................................",
				".....#_____#..........................................~~~.......................",
				".....###.###...........................................~........................",
				"................................................................................",
				"...................~~~................+++.......................................",
				".......................................++++............................~~~~.....",
				"...........................~~~~~...+++++........................................",
				".....................................++++++.....................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++..........~........................~.............................",
				".............+++++.......~.......................~..............................",
				"...............++........~......................~........+++++..................",
				".............+++.........~.....................~....++++++++....................",
				"............+++...............................~.......+++.......................",
				"................................................................................",
				"................~......................................~~................++.....",
				"...............~~~....................................~~................++.++...",
				"..................................~..................~~..................++++...",
				"..........................................................................++....",
				"................................................................................");

		firstGameMap = new GameMap(groundFactory, firstMap);
		world.addGameMap(firstGameMap);

		List<String> secondMap = Arrays.asList(
				"................................................................................",
				"................................................................................",
				".........~~~~~~.................................................................",
				"................................................................................",
				"................................................................................",
				".......................................+++++++...............~..................",
				"...............~~~~...................++++++..................~.................",
				"....................................++++++.....................~................",
				"......................................+++.......................~...............",
				".......................................+.........................~..............",
				"................................................................................",
				"................................................................................",
				"...............................~................................................",
				"...................~............................................................",
				"...................~............................................................",
				"...................~............................................................",
				"...................~..................~~........................................",
				"...................~............................................................",
				".......................................................+++++....................",
				".................++.............................................................",
				"..............+++++........~~~~~.........................................+++....",
				".................++++..................................................++++++...",
				"................++++.....................................................++++...",
				"...............................................[..........................++++..",
				"................................................................................");

		secondGameMap = new GameMap(groundFactory, secondMap);
		world.addGameMap(secondGameMap);

		mapping = new Mapping(world.getGameMaps());
	}

	/**
	 * Restart all the actors and items
	 */
	private static void restartActorsAndItems(){
		// get the maps reference
		firstGameMap = world.getGameMaps().get(0);
		secondGameMap = world.getGameMaps().get(1);

		// adding player
		Actor player = new Player("Player", '@', 100);
		// world.addPlayer(player, firstGameMap.at(14, 2));
		world.addPlayer(player, firstGameMap.at(20, 4));

		// --------------------------------------------------------------------------------------------------
		// Adding dinosaurs can be done below
		// first game map

		// firstGameMap.at(30, 0).addActor(new Stegosaur("Stegosaur"));
		// firstGameMap.at(0, 15).addActor(new Stegosaur("Stegosaur"));
		// firstGameMap.at(30, 10).addActor(new Stegosaur("Stegosaur"));

		// Place a pair of stegosaurs in the middle of the map
		firstGameMap.at(30, 10).addActor(new Stegosaur("Stegosaur"));
		// firstGameMap.at(32, 12).addActor(new Stegosaur("Stegosaur"));
		firstGameMap.at(29, 10).addActor(new Stegosaur("Stegosaur"));
		// firstGameMap.at(32, 10).addActor(new Stegosaur("Stegosaur"));


		firstGameMap.at(20, 8).addActor(new Brachiosaur("Brachiosaur"));
		// firstGameMap.at(21, 8).addActor(new Brachiosaur("Brachiosaur"));

		firstGameMap.at(24, 17).addActor(new Allosaur("Allosaur"));

		firstGameMap.at(20, 6).addActor(new Pterodactyl("Pterodactyl"));
		firstGameMap.at(24, 6).addActor(new Pterodactyl("Pterodactyl"));
		firstGameMap.at(22, 6).addActor(new Pterodactyl("Pterodactyl"));

		// ----------------------------------------------------------
		// second game map
		secondGameMap.at(28, 19).addActor(new Stegosaur("Stegosaur Map 2"));
		secondGameMap.at(29, 19).addActor(new Brachiosaur("Brachiosaur Map 2"));
	}

}

package src.backend.game;

import src.backend.models.Board;
import src.backend.models.Player;
import src.backend.models.Ship;
import src.enums.ShipType;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Game {
	private Player[] players = new Player[2];
	private final GameSettings gameSettings;
	private int boardSize;
	private int shipQuantity;
	private Map<ShipType,Integer> availableShips;
	private List<List<Coordinate>> placedShips = new ArrayList<>();

	public Game(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		this.availableShips = new HashMap<>();
		setupGame();
	}

	public Map<ShipType, Integer> getAvailableShips() {
		return availableShips;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public List<List<Coordinate>> getPlacedShips() {
		return new ArrayList<>(placedShips);
	}

	public String getCurrentPlayerName(){
		//todo get who is current playing so i can pass it to placementScreen to display name or other info
		return "helo";
	}


	private void setupGame(){
		this.boardSize = gameSettings.getBoardSize();
		this.shipQuantity = gameSettings.getShipQuantity();
		initializeGame();
		initializeAvailableShips(shipQuantity);
	}

	private void initializeGame(){

		players[0] = new Player("PLAYER1", shipQuantity);
		players[1] = new Player("PLAYER2", shipQuantity);
	}

	private void initializeAvailableShips(int totalShips){
		switch (totalShips){
			case 10 -> setupTenShips();
			case 15 -> setupFifteenShips();
			case 20 -> setupTwentyShips();
		}
	}

	//*SETUP INITIAL AVAILABLE SHIPS METHODS

	private void setupTenShips() {
		availableShips.put(ShipType.CLOUDRULER, 1);
		availableShips.put(ShipType.CONVOYSHEPHER, 2);
		availableShips.put(ShipType.ABYSSALASSASIN, 2);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 2);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 3);
	}

	private void setupFifteenShips() {
		availableShips.put(ShipType.CLOUDRULER, 2);
		availableShips.put(ShipType.CONVOYSHEPHER, 3);
		availableShips.put(ShipType.ABYSSALASSASIN, 3);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 3);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 4);
	}

	private void setupTwentyShips() {
		availableShips.put(ShipType.CLOUDRULER, 3);
		availableShips.put(ShipType.CONVOYSHEPHER, 4);
		availableShips.put(ShipType.ABYSSALASSASIN, 4);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 4);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 5);
	}


	//*PLAYER SHIPS MANAGEMENT

	public void fillPlayerShips(Ship ship, Player player){
		player.getPlayerShips().add(ship);
	}

	public Stream<String> remainingShipsNames(Player player){

		Stream<String> shipStreamNames = player.getPlayerShips().stream()
				.filter(ship -> !ship.isSunk())
				.map(Ship::getName);

		return shipStreamNames;
	}

	public void decreasePlayerShipsCounter(Player player) {
		player.decreaseAliveShipsCounter();
	}

	public boolean checkPlayerHasLost(Player player) {
		return player.checkHasLost();
	}

	public ArrayList<Ship> getPlayerShips(Player player) {
		return player.getPlayerShips();
	}

	public void decrementShipCount(ShipType shipType) {
		int count = availableShips.get(shipType);
		if (count > 0) {
			availableShips.put(shipType, count - 1);
		}
	}

	//*BOARD COORDINATES VALIDATION
	public void validateShipPlacement(){
		//todo when screen placement starts to be implemented so for each player ship trying to be placed when passed
		// this means placement screen will receive ship placement and pass it down to game where game will say is or
		// not valid, to be it cant overlap any ship, cant be diagonal neither out of board boundaries this can be
		// calculated based in initial coordinate and calculating what directions can ship be placed, right left, up
		// or down based on size so if coordinate is x then size is x then following coordinates need to be all empty
		// to have a valid placement
	}

	public void placeShip(List<Coordinate> shipCoordinates){
		placedShips.add(shipCoordinates);
	}

}

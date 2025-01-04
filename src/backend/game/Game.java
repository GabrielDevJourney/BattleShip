package src.backend.game;

import src.backend.models.Player;
import src.backend.models.Ship;
import src.backend.ships.*;
import src.enums.ShipType;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Game {
	private Player[] players = new Player[2];
	private Player currentPlayer;
	private int currentPlayerIndex = 0;
	private final GameSettings gameSettings;
	private int boardSize;
	private int shipQuantity;
	private Map<ShipType,Integer> availableShips;
	private List<List<Coordinate>> placedShips = new ArrayList<>();


	public Game(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		this.availableShips = new HashMap<>();
		initializeGame();
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
		return currentPlayer.getName();
	}


	//*INITIALIZE GAME METHOD

	private void initializeGame(){
		this.boardSize = gameSettings.getBoardSize();
		this.shipQuantity = gameSettings.getShipQuantity();
		initializePlayers();
		initializeAvailableShips(shipQuantity);
	}

	private void initializePlayers(){

		players[0] = new Player("PLAYER1", shipQuantity);
		players[1] = new Player("PLAYER2", shipQuantity);
		currentPlayer = players[0];
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

	private Ship createShip(ShipType shipType){
		return switch (shipType){
			case CLOUDRULER -> new CloudRuler();
			case CONVOYSHEPHER -> new ConvoyShepherd();
			case ABYSSALASSASIN -> new AbyssalAssassin();
			case DAUNTLESSDEFENDER -> new DauntlessDefender();
			case SHELLFIREJUGGERNAUT -> new ShellfireJuggernaut();
		};
	}

	public void decrementShipCount(ShipType shipType) {
		int count = availableShips.get(shipType);
		if (count > 0) {
			availableShips.put(shipType, count - 1);
		}
	}


	public void placeShip(List<Coordinate> shipCoordinates){
		placedShips.add(shipCoordinates);
	}

	//*HANDLE PLAYERS TURN FOR PLACEMENT
	public boolean hasNextPlayer(){
		return currentPlayerIndex < 1;
	}

	public void nextPlayer(){
		currentPlayerIndex++;
		currentPlayer = players[currentPlayerIndex];
		//initialize ships for next players since is a new player placing its own ships, so also clear placed ones so
		// next player will have a whole clear board and not having a clear board states but under the hood having
		// placed ships full from previous player
		placedShips.clear();
		availableShips.clear();
		initializeAvailableShips(shipQuantity);
	}
}

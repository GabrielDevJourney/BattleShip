package src.backend.game;

import src.backend.models.Board;
import src.backend.models.Player;
import src.backend.models.Ship;
import src.utils.GameValidator;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Game {
	private Player[] players = new Player[2];
	private Board board;
	private final GameSettings gameSettings;
	private int boardSize;
	private int shipQuantity;
	private boolean isManualPlacement;

	/*
	when creating players i need to pass the current game boats quantity to set alivedShipCounter
	 */
	/*
		Game creates ship instance
		GameValidator checks placement
		If valid, Ship.fillCoordinates() stores positions
		Player adds validated ship to their list
	 */

	public Game(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		setupGame();
	}

	private void setupGame(){
		this.boardSize = gameSettings.getBoardSize();
		this.shipQuantity = gameSettings.getShipQuantity();
		this.isManualPlacement = gameSettings.isManualPlacement();
	}

	private void initializeGame(){
		board = new Board(boardSize);
		players[0] = new Player("PLAYER1", shipQuantity);
		players[1] = new Player("PLAYER2", shipQuantity);
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

	//*BOARD COORDINATES VALIDATION
	public void validateShipPlacement(){
		//todo when screen placement starts to be implemented so for each player ship trying to be placed when passed
		// this means placement screen will receive ship placement and pass it down to game where game will say is or
		// not valid, to be it cant overlap any ship, cant be diagonal neither out of board boundaries this can be
		// calculated based in initial coordinate and calculating what directions can ship be placed, right left, up
		// or down based on size so if coordinate is x then size is x then following coordinates need to be all empty
		// to have a valid placement
	}

}

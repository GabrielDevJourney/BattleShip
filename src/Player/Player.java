package src.Player;

import src.Ship.Ship;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Player {
	private String name;
	private ArrayList<Ship>  playerShips = new ArrayList<>();
	private boolean hasLost;
	private int aliveShipsCounter;


	public Player(String name, int aliveShipsCounter) {
		this.name = name;
		this.aliveShipsCounter = aliveShipsCounter;
	}

	public int getAliveShipsCounter() {
		return aliveShipsCounter;
	}

	public ArrayList<Ship> getPlayerShips() {
		return playerShips;
	}

	public boolean isHasLost() {
		return hasLost;
	}

	public String getName() {
		return name;
	}


										//* METHODS

	/**
	 * when both game calls gameValidator for checking placement of current player ship, if approved
	 * then that same player ship is added to it's own ship list
	 */
	public void fillPlayerShips(Ship ship){
		playerShips.add(ship);
	}

	/**
	 * will be used to update info in Ui so i can let player know what are his reaming ships
	 */
	public Stream<String> remainingShipsNames(){

		Stream<String> shipStreamNames = playerShips.stream()
				.filter(ship -> !ship.isSunk())
				.map(Ship::getName);

		return shipStreamNames;
	}

	public boolean checkHasLost(){
		return aliveShipsCounter == 0;
	}

	public void decreaseAliveShipsCounter(){
		aliveShipsCounter --;
	}
}

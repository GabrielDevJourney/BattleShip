package src.backend.models;

import java.util.ArrayList;

public class Player {
	//todo thin about moving all logic apart from data/get/set to Game class

	private final String name;
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

	public boolean checkHasLost(){
		return aliveShipsCounter == 0;
	}

	public void decreaseAliveShipsCounter(){
		aliveShipsCounter --;
	}
}

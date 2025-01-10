package src.backend.models;

import src.utils.Coordinate;
import src.enums.ShipType;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all ships in the game.
 * Handles ship properties, hit tracking, and coordinate storage.
 */

public abstract class Ship {
	private ShipType type;
	private int size;
	private String name;
	private boolean isSunk;
	private int hitCounter;
	private ArrayList<Coordinate> coordinates = new ArrayList<>();

	public Ship(ShipType type) {
		this.type = type;
		this.size = type.getShipSize();
		this.name = type.getName();
	}

	public int getSize() {
		return size;
	}

	public int getHitCounter() {
		return hitCounter;
	}

	public String getName() {
		return name;
	}

	public boolean isSunk() {
		return isSunk;
	}

	public void setSunk(boolean sunk) {
		isSunk = sunk;
	}

	public ArrayList<Coordinate> getPositions() {
		return coordinates;
	}

	/**
	 * fills ship coordinates will coordinates validated trough gameValidator
	 */
	public void fillCoordinates(List<Coordinate> newCoordinates) {
		coordinates = new ArrayList<>(newCoordinates); // Set coordinates when placing the ship
	}

	/**
	 * increase hit counter and set sunk state baed on counter => size of ship
	 */
	public void increaseHits() {
		hitCounter++;
		checkSunkState();
	}

	private void checkSunkState() {
		if (hitCounter == size) {
			setSunk(true);
		}
	}

}

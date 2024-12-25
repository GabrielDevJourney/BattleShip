package src.Ship;

import src.Helpers.Coordinate;
import src.Enums.ShipType;

import java.util.ArrayList;

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
	public void fillCoordinates(ArrayList<Coordinate> newCoordinates) {
		if (newCoordinates.size() != size) {
			return;
		}

		for (Coordinate newCoordinate : newCoordinates) {
			//since using list i can use stream to then see if there is any match
			//anyMatch will use followed rule to check newCordinate with existing list coordinates
			if (coordinates.stream().anyMatch(existentCoordinate ->
					existentCoordinate.equals(newCoordinate))) {
				return;//already exists placed ship in this coordinates
			}
		}
		coordinates = newCoordinates; //no ship in here
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

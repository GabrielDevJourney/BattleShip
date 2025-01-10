package src.logic.models;

import src.utils.Coordinate;
import src.enums.ShipType;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all ships in the game.
 * Handles ship properties, hit tracking, and coordinate storage.
 */

public abstract class Ship {
	private final ShipType type;
	private final int size;
	private int hits;
	private List<Coordinate> coordinates;

	protected Ship(ShipType type) {
		this.type = type;
		this.size = type.getShipSize();
		this.hits = 0;
		this.coordinates = new ArrayList<>();
	}

	// Getters/Setters
	public ShipType getType() { return type; }
	public void incrementHits() { hits++; }
	public List<Coordinate> getCoordinates() { return coordinates; }
	public void setCoordinates(List<Coordinate> coordinates) { this.coordinates = coordinates; }
	public boolean isSunk() { return hits >= size; }
}

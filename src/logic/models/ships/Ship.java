package src.logic.models.ships;

import src.enums.ShipType;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all ships in the game.
 * Handles ship properties, hit tracking, and coordinate storage.
 */

public abstract class Ship {
	private int hits;
	private List<Coordinate> coordinates;

	protected Ship() {
		this.hits = 0;
		this.coordinates = new ArrayList<>();
	}

	public abstract ShipType getType();
	public void incrementHits() { hits++; }
	public int getHits() { return hits; }
	public List<Coordinate> getCoordinates() { return coordinates; }
	public void setCoordinates(List<Coordinate> coordinates) { this.coordinates = coordinates; }
}

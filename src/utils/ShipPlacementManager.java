package src.utils;

import java.util.ArrayList;
import java.util.List;

//FIXME: add private constructor
public class ShipPlacementManager {
	// Main method that checks all possible ways to place a ship from a starting point
	public static List<List<Coordinate>> getPossiblePlacements(Coordinate start, int shipSize, int boardSize, List<List<Coordinate>> existingShips) {
		List<List<Coordinate>> possiblePlacements = new ArrayList<>();

		// Check if ship can fit in each direction without going off the board
		boolean rightValid = start.getCol() + shipSize <= boardSize;
		boolean leftValid = start.getCol() - shipSize + 1 >= 0;
		boolean downValid = start.getRow() + shipSize <= boardSize;
		boolean upValid = start.getRow() - shipSize + 1 >= 0;

		// Try to place ship in each valid direction
		if(rightValid) validateRightDirection(start, shipSize, possiblePlacements, existingShips);
		if(leftValid) validateLeftDirection(start, shipSize, possiblePlacements, existingShips);
		if(downValid) validateDownDirection(start, shipSize, possiblePlacements, existingShips);
		if(upValid) validateUpDirection(start, shipSize, possiblePlacements, existingShips);

		// If no valid placements found, return null, otherwise return the list
		return possiblePlacements.isEmpty() ? null : possiblePlacements;
	}

	// Create list of coordinates for ship going right
	private static void validateRightDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow(), start.getCol() + i));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	// Create list of coordinates for ship going left
	private static void validateLeftDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow(), start.getCol() - i));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	// Create list of coordinates for ship going down
	private static void validateDownDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow() + i, start.getCol()));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	// Create list of coordinates for ship going up
	private static void validateUpDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow() - i, start.getCol()));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	// Check if placement is valid horizontal/vertical + not overlapping + not too close
	private static void validatePlacement(List<Coordinate> placement, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		if ((validateHorizontalPlacement(placement) || validateVerticalPlacement(placement))
				&& !isShipOverlapping(placement, existingShips)
				&& !isShipTooClose(placement, existingShips)) {
			possiblePlacements.add(placement);
		}
	}

	// Check if ship coordinates form a valid horizontal line
	private static boolean validateHorizontalPlacement(List<Coordinate> coordinates) {
		// Check if all coordinates are in same row
		boolean equalRows = coordinates.stream()
				.map(Coordinate::getRow)
				.distinct()
				.count() == 1;

		// Check if columns are next to each other if yes is horizontal placement
		boolean consecutiveCols = coordinates.stream()
				.map(Coordinate::getCol)
				.reduce((col1, col2) -> col1 + 1 == col2 || col1 - 1 == col2 ? col2 : col1)
				.isPresent();

		return equalRows && consecutiveCols;
	}

	// Check if ship coordinates form a valid vertical line
	private static boolean validateVerticalPlacement(List<Coordinate> coordinates) {
		// Check if all coordinates are in same column
		boolean equalCols = coordinates.stream()
				.map(Coordinate::getCol)
				.distinct()
				.count() == 1;

		// Check if rows are next to each other if yes is vertical placement
		boolean consecutiveRows = coordinates.stream()
				.map(Coordinate::getRow)
				.reduce((row1, row2) -> row1 + 1 == row2 || row1 - 1 == row2 ? row2 : row1)
				.isPresent();

		return equalCols && consecutiveRows;
	}

	// Check if ship would overlap with any existing ships from player list of already existent ships, already placed
	public static boolean isShipOverlapping(List<Coordinate> newShipCoordinates, List<List<Coordinate>> existingShips) {
		return existingShips.stream()
				.anyMatch(existingShip -> existingShip.stream()
						.anyMatch(newShipCoordinates::contains));
	}

	// Check if ship would be too close to any existing ships must be 1 cell gap
	public static boolean isShipTooClose(List<Coordinate> newShipCoordinates, List<List<Coordinate>> existingShips) {
		return existingShips.stream()
				.flatMap(List::stream)
				.anyMatch(existingCoordinate -> newShipCoordinates.stream()
						.anyMatch(newCoordinate -> isAdjacent(newCoordinate, existingCoordinate)));
	}

	// Check if two coordinates are next to each other including diagonal
	private static boolean isAdjacent(Coordinate coordinate1, Coordinate coordinate2) {
		return Math.abs(coordinate1.getRow() - coordinate2.getRow()) <= 1 &&
				Math.abs(coordinate1.getCol() - coordinate2.getCol()) <= 1;
	}
}
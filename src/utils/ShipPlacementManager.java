package src.utils;

import java.util.ArrayList;
import java.util.List;

public class ShipPlacementManager {
	public static List<List<Coordinate>> getPossiblePlacements(Coordinate start, int shipSize, int boardSize, List<List<Coordinate>> existingShips) {
		List<List<Coordinate>> possiblePlacements = new ArrayList<>();

		boolean rightValid = start.getCol() + shipSize <= boardSize;
		boolean leftValid = start.getCol() - shipSize + 1 >= 0;
		boolean downValid = start.getRow() + shipSize <= boardSize;
		boolean upValid = start.getRow() - shipSize + 1 >= 0;

		if(rightValid) validateRightDirection(start, shipSize, possiblePlacements, existingShips);
		if(leftValid) validateLeftDirection(start, shipSize, possiblePlacements, existingShips);
		if(downValid) validateDownDirection(start, shipSize, possiblePlacements, existingShips);
		if(upValid) validateUpDirection(start, shipSize, possiblePlacements, existingShips);

		return possiblePlacements.isEmpty() ? null : possiblePlacements;
	}

	private static void validateRightDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow(), start.getCol() + i));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	private static void validateLeftDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow(), start.getCol() - i));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	private static void validateDownDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow() + i, start.getCol()));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	private static void validateUpDirection(Coordinate start, int shipSize, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		List<Coordinate> placement = new ArrayList<>();
		for (int i = 0; i < shipSize; i++) {
			placement.add(new Coordinate(start.getRow() - i, start.getCol()));
		}
		validatePlacement(placement, possiblePlacements, existingShips);
	}

	private static void validatePlacement(List<Coordinate> placement, List<List<Coordinate>> possiblePlacements, List<List<Coordinate>> existingShips) {
		if ((validateHorizontalPlacement(placement) || validateVerticalPlacement(placement))
				&& !isShipOverlapping(placement, existingShips)
				&& !isShipTooClose(placement, existingShips)) {
			possiblePlacements.add(placement);
		}
	}

	private static boolean validateHorizontalPlacement(List<Coordinate> coordinates) {
		boolean equalRows = coordinates.stream()
				.map(Coordinate::getRow)
				.distinct()
				.count() == 1;

		boolean consecutiveCols = coordinates.stream()
				.map(Coordinate::getCol)
				.reduce((col1, col2) -> col1 + 1 == col2 || col1 - 1 == col2 ? col2 : col1)
				.isPresent();

		return equalRows && consecutiveCols;
	}

	private static boolean validateVerticalPlacement(List<Coordinate> coordinates) {
		boolean equalCols = coordinates.stream()
				.map(Coordinate::getCol)
				.distinct()
				.count() == 1;

		boolean consecutiveRows = coordinates.stream()
				.map(Coordinate::getRow)
				.reduce((row1, row2) -> row1 + 1 == row2 || row1 - 1 == row2 ? row2 : row1)
				.isPresent();

		return equalCols && consecutiveRows;
	}

	public static boolean isShipOverlapping(List<Coordinate> newShipCoordinates, List<List<Coordinate>> existingShips) {
		return existingShips.stream()
				.anyMatch(existingShip -> existingShip.stream()
						.anyMatch(newShipCoordinates::contains));
	}

	public static boolean isShipTooClose(List<Coordinate> newShipCoordinates, List<List<Coordinate>> existingShips) {
		return existingShips.stream()
				.flatMap(List::stream)
				.anyMatch(existingCoordinate -> newShipCoordinates.stream()
						.anyMatch(newCoordinate -> isAdjacent(newCoordinate, existingCoordinate)));
	}

	private static boolean isAdjacent(Coordinate coordinate1, Coordinate coordinate2) {
		return Math.abs(coordinate1.getRow() - coordinate2.getRow()) <= 1 &&
				Math.abs(coordinate1.getCol() - coordinate2.getCol()) <= 1;
	}
}
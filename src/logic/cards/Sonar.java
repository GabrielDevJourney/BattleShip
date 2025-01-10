package src.backend.cards;

import src.backend.game.Turn;
import src.backend.models.Board;
import src.backend.models.Card;

import static src.enums.CardType.SONAR;

import src.backend.models.Ship;
import src.enums.BoardState;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Sonar extends Card {
	public Sonar() {
		super(SONAR);
	}

	private List<Coordinate> getSonarArea(Coordinate center, Board defenderBoard) {
		List<Coordinate> sonarArea = new ArrayList<>();
		int coordinateCenterRow = center.getRow();
		int coordinateCenterCol = center.getCol();

		// for center coordiante previous row until next row
		for (int row = coordinateCenterRow - 1; row <= coordinateCenterRow + 1; row++) {
			//and previous col until next col
			for (int col = coordinateCenterCol - 1; col <= coordinateCenterCol + 1; col++) {
				//create each coordiante that belong to 3x3 area
				Coordinate coordinate = new Coordinate(row, col);

				if (isValidCoordinate(defenderBoard, coordinate)) {
					sonarArea.add(coordinate);
				}
			}
		}
		return sonarArea;
	}

	@Override
	public void executeCard(Turn currentTurn, Coordinate coordinateCenter) {
		Board defenderBoard = currentTurn.getDefenderBoard();

		if (isValidCoordinate(defenderBoard, coordinateCenter)) {
			// Get valid coordinates in 3x3 area
			List<Coordinate> sonarArea = getSonarArea(coordinateCenter, defenderBoard);

			// must see if there is a ship in coordiante
			for (Coordinate coordinate : sonarArea) {
				Ship ship = currentTurn.findShipAtCoordinate(coordinate);
				if (ship != null) {
					// If ship found, mark as revealed
					defenderBoard.setNewState(coordinate, BoardState.SHIPFUND);
				}
			}
		}
	}

	private boolean isValidCoordinate(Board board, Coordinate coordinate) {
		int size = board.getBoardSize();
		return coordinate.getRow() >= 0 && coordinate.getRow() < size &&
				coordinate.getCol() >= 0 && coordinate.getCol() < size;
	}

}

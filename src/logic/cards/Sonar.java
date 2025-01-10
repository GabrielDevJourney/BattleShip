package src.logic.cards;

import src.logic.game.Game;
import src.logic.models.Board;
import src.logic.models.Card;

import static src.enums.CardType.SONAR;

import src.logic.models.Player;
import src.logic.models.Ship;
import src.enums.BoardState;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Sonar extends Card {
	public Sonar() {
		super(SONAR);
	}

	@Override
	public boolean executeCard(Game game, Player attacker, Player defender, Coordinate center) {
		Board defenderBoard = defender.getBoard();

		if (!isValidCoordinate(defenderBoard, center)) return false;

		boolean shipFound = false;
		List<Coordinate> area = getSonarArea(center, defenderBoard);


		for (Coordinate coordinate : area) {
			Ship ship = findShipAt(coordinate, defenderBoard);
			if (ship != null) {
				defenderBoard.setCellState(coordinate.getRow(), coordinate.getCol(), BoardState.SHIPFUND);
				shipFound = true;
			}
		}
		return shipFound;
	}

	private List<Coordinate> getSonarArea(Coordinate center, Board board) {
		List<Coordinate> area = new ArrayList<>();
		for (int row = center.getRow() - 1; row <= center.getRow() + 1; row++) {
			for (int col = center.getCol() - 1; col <= center.getCol() + 1; col++) {
				Coordinate coord = new Coordinate(row, col);
				if (isValidCoordinate(board, coord)) {
					area.add(coord);
				}
			}
		}
		return area;
	}

	private Ship findShipAt(Coordinate coord, Board board) {
		return board.getShips().stream()
				.filter(ship -> ship.getCoordinates().contains(coord))
				.findFirst()
				.orElse(null);
	}

	private boolean isValidCoordinate(Board board, Coordinate coord) {
		int size = board.getSize();
		return coord.getRow() >= 0 && coord.getRow() < size &&
				coord.getCol() >= 0 && coord.getCol() < size;
	}
}
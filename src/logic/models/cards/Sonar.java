package src.logic.models.cards;

import src.enums.CardType;
import src.logic.game.Game;
import src.logic.game.BoardService;

import src.logic.models.PlayerService;
import src.logic.models.ships.Ship;
import src.enums.BoardState;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Sonar extends Card {

	@Override
	public CardType getType() {
		return CardType.SONAR;
	}

	@Override
	public boolean executeCard(Game game, PlayerService attacker, PlayerService defender, Coordinate center) {
		BoardService defenderBoard = defender.getBoard();

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

	private List<Coordinate> getSonarArea(Coordinate center, BoardService board) {
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

	private Ship findShipAt(Coordinate coord, BoardService board) {
		return board.getShips().stream()
				.filter(ship -> ship.getCoordinates().contains(coord))
				.findFirst()
				.orElse(null);
	}

	private boolean isValidCoordinate(BoardService board, Coordinate coord) {
		int size = board.getSize();
		return coord.getRow() >= 0 && coord.getRow() < size &&
				coord.getCol() >= 0 && coord.getCol() < size;
	}
}
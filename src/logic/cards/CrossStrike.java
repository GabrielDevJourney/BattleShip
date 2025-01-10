package src.backend.cards;

import src.backend.game.Turn;
import src.backend.models.Board;
import src.backend.models.Card;
import static src.enums.CardType.CROSS_STRIKE;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;


public class CrossStrike extends Card {
	public CrossStrike() {
		super(CROSS_STRIKE);
	}

	@Override
	public void executeCard(Turn currentTurn, Coordinate coordinateCenter) {
		int coordinateCenterRow = coordinateCenter.getRow();
		int coordinateCenterCol = coordinateCenter.getCol();
		Board defenderBoard = currentTurn.getDefenderBoard();

		List<Coordinate> coordinatesToAttack = new ArrayList<>();
		for (int row = coordinateCenterRow; row <= coordinateCenterRow + 1; row++) {
			//and previous col until next col
			for (int col = coordinateCenterCol; col <= coordinateCenterCol + 1; col++) {
				//create each coordiante that belong to 2x2 area
				Coordinate coordinate = new Coordinate(row, col);
				if (isValidCoordinate(defenderBoard, coordinate) && defenderBoard.isTargetable(coordinate)) {
					coordinatesToAttack.add(coordinate);
				}
			}
		}

		for (Coordinate coordinate : coordinatesToAttack) {
			currentTurn.attack(defenderBoard, coordinate);
		}
	}

	private boolean isValidCoordinate(Board board, Coordinate coordinate) {
		int size = board.getBoardSize();
		return coordinate.getRow() >= 0 && coordinate.getRow() < size &&
				coordinate.getCol() >= 0 && coordinate.getCol() < size;
	}
}

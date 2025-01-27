package src.logic.cards;

import src.logic.game.Game;
import src.logic.models.Board;
import src.logic.models.Card;
import static src.enums.CardType.CROSS_STRIKE;

import src.logic.models.Player;
import src.utils.Coordinate;


public class CrossStrike extends Card {
	public CrossStrike() {
		super(CROSS_STRIKE);
	}

	@Override
	public boolean executeCard(Game game, Player attacker, Player defender, Coordinate center) {
		Board defenderBoard = defender.getBoard();
		int centerRow = center.getRow();
		int centerCol = center.getCol();
		boolean canHit = false;

		for (int row = centerRow; row <= centerRow + 1; row++) {
			for (int col = centerCol; col <= centerCol + 1; col++) {
				Coordinate coordinate = new Coordinate(row, col);
				if (isValidCoordinate(defenderBoard, coordinate)) {
					game.executeAttack(coordinate);  // Use game's attack logic
					canHit = true;
				}
			}
		}

		return canHit;
	}

	private boolean isValidCoordinate(Board board, Coordinate coordinate) {
		int size = board.getSize();
		return coordinate.getRow() >= 0 && coordinate.getRow() < size &&
				coordinate.getCol() >= 0 && coordinate.getCol() < size;
	}
}
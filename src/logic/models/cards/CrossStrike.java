package src.logic.models.cards;

import src.enums.CardType;
import src.logic.game.Game;
import src.logic.game.BoardService;

import src.logic.models.PlayerService;
import src.utils.Coordinate;


public class CrossStrike extends Card {

	@Override
	public CardType getType() {
		return CardType.CROSS_STRIKE;
	}

	@Override
	public boolean executeCard(Game game, PlayerService attacker, PlayerService defender, Coordinate center) {
		BoardService defenderBoard = defender.getBoard();
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

	private boolean isValidCoordinate(BoardService board, Coordinate coordinate) {
		int size = board.getSize();
		return coordinate.getRow() >= 0 && coordinate.getRow() < size &&
				coordinate.getCol() >= 0 && coordinate.getCol() < size;
	}
}
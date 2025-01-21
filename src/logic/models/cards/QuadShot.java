package src.logic.models.cards;
import src.enums.BoardState;
import src.enums.CardType;
import src.logic.game.Game;
import src.logic.game.BoardService;

import src.logic.models.PlayerService;
import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuadShot extends Card {
	private static final int NUMBER_OF_SHOTS = 4;

	@Override
	public CardType getType() {
		return CardType.QUAD_SHOT;
	}

	@Override
	public boolean executeCard(Game game, PlayerService attacker, PlayerService defender, Coordinate center) {
		BoardService defenderBoard = defender.getBoard();
		List<Coordinate> validTargets = getValidTargets(defenderBoard);
		return executeRandomAttacks(game, validTargets);
	}

	private List<Coordinate> getValidTargets(BoardService board) {
		List<Coordinate> targets = new ArrayList<>();
		for (int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				Coordinate coordinate = new Coordinate(row, col);
				if (board.getCellState(row, col) == BoardState.WATER) {
					targets.add(coordinate);
				}
			}
		}
		return targets;
	}

	private boolean executeRandomAttacks(Game game, List<Coordinate> validTargets) {
		Collections.shuffle(validTargets);
		int shots = Math.min(NUMBER_OF_SHOTS, validTargets.size());
		boolean canHit = false;

		for (int i = 0; i < shots; i++) {
			game.executeAttack(validTargets.get(i));
			canHit = true;
		}

		return canHit;
	}
}
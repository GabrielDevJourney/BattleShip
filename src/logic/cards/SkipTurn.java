package src.logic.cards;

import src.logic.game.Game;
import src.logic.models.Card;
import src.logic.models.Player;
import src.utils.Coordinate;

import static src.enums.CardType.SKIP_TURN;

public class SkipTurn extends Card {
	public SkipTurn() {
		super(SKIP_TURN);
	}

	@Override
	public boolean executeCard(Game game, Player attacker, Player defender, Coordinate center) {
		// Game will handle skipping turn in handleNextRound
		game.handleSkipTurn();
		return false;
	}
}

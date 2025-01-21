package src.logic.models.cards;

import src.enums.CardType;
import src.logic.game.Game;
import src.logic.models.PlayerService;
import src.utils.Coordinate;

public class SkipTurn extends Card {

	@Override
	public CardType getType() {
		return CardType.SKIP_TURN;
	}

	@Override
	public boolean executeCard(Game game, PlayerService attacker, PlayerService defender, Coordinate center) {
		// Game will handle skipping turn in handleNextRound
		game.handleSkipTurn();
		return false;
	}
}

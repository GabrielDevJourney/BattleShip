package src.backend.cards;

import src.backend.game.Turn;
import src.backend.models.Card;
import src.utils.Coordinate;

import static src.enums.CardType.SKIP_TURN;

public class SkipTurn extends Card {

	public SkipTurn() {
		super(SKIP_TURN);
	}

	@Override
	public void executeCard(Turn currentTurn, Coordinate coordinateCenter) {
		currentTurn.setSkippedTurn(true);
	}

}

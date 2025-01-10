package src.backend.models;

import src.backend.game.Turn;
import src.enums.CardType;
import src.utils.Coordinate;

public abstract class Card {
	private final CardType type;
	private boolean available;

	public Card(CardType type) {
		this.type = type;
	}

	public CardType getType() {
		return type;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public abstract void executeCard(Turn currentTurn, Coordinate coordinateCenter);
}

package src.logic.models;

import src.logic.game.Game;
import src.enums.CardType;
import src.utils.Coordinate;

public abstract class Card {
	private final CardType type;
	private boolean available;
	private int lastRoundOfUsage = -1;

	protected Card(CardType type) {
		this.type = type;
		this.available = true;
	}

	// Getters/Setters
	public CardType getType() { return type; }
	public boolean isAvailable() { return available; }
	public int getLastRoundOfUsage() {
		return lastRoundOfUsage;
	}
	public void setLastRoundOfUsage(int lastRoundOfUsage) {
		this.lastRoundOfUsage = lastRoundOfUsage;
	}
	public void setAvailable(boolean available) { this.available = available; }

	// New execute method for cards that works with Game and Players directly instead of Turn
	public abstract boolean executeCard(Game game, Player attacker, Player defender, Coordinate center);
}

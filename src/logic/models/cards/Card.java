package src.logic.models.cards;

import src.logic.game.Game;
import src.enums.CardType;
import src.logic.models.PlayerService;
import src.utils.Coordinate;

public abstract class Card {
	private boolean available;
	private int lastRoundOfUsage = -1;

	protected Card() {
		this.available = true;
	}

	// Getters/Setters
	public abstract CardType getType();









	public boolean isAvailable() { return available; }
	public int getLastRoundOfUsage() {
		return lastRoundOfUsage;
	}
	public void setLastRoundOfUsage(int lastRoundOfUsage) {
		this.lastRoundOfUsage = lastRoundOfUsage;
	}
	public void setAvailable(boolean available) { this.available = available; }

	// New execute method for cards that works with Game and Players directly instead of Turn
	public abstract boolean executeCard(Game game, PlayerService attacker, PlayerService defender, Coordinate center);
}

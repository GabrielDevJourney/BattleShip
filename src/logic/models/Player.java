package src.logic.models;

import src.enums.CardType;

import java.util.HashMap;
import java.util.Map;

public class Player {
	private final String name;
	private final Board board;
	private int aliveShipsCount;
	private final Map<CardType, Card> cards;
	private boolean hasLost = false;

	public Player(String name, int boardSize, int shipCount) {
		this.name = name;
		this.board = new Board(boardSize);
		this.aliveShipsCount = shipCount;
		this.cards = new HashMap<>();
	}

	// Getters/Setters
	public String getName() { return name; }

	public Board getBoard() { return board; }

	public int getAliveShipsCount() { return aliveShipsCount; }

	public Map<CardType, Card> getCards() { return cards; }

	public void addCard(CardType type, Card card) { cards.put(type, card); }

	public Card getCard(CardType type) { return cards.get(type); }

	public boolean hasLost() { return hasLost; }
	public void decrementShipsCount() {
		aliveShipsCount--;
		if(aliveShipsCount <= 0){
			hasLost = true;
		}
	}
}
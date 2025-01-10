package src.backend.models;

import src.enums.CardType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	//todo thin about moving all logic apart from data/get/set to Game class

	private final String name;
	private ArrayList<Ship>  playerShips = new ArrayList<>();
	private boolean hasLost;
	private int aliveShipsCounter;
	private Map<CardType,Card> cards = new HashMap<>();


	public Player(String name, int aliveShipsCounter) {
		this.name = name;
		this.aliveShipsCounter = aliveShipsCounter;
	}

	public int getAliveShipsCounter() {
		return aliveShipsCounter;
	}

	public ArrayList<Ship> getPlayerShips() {
		return playerShips;
	}

	public boolean isHasLost() {
		return hasLost;
	}

	public String getName() {
		return name;
	}

	public Card getCard(CardType cardType) {
		return cards.get(cardType);
	}

	public Map<CardType, Card> getCards() {
		return cards;
	}

/*	public boolean hasCard(CardType cardType) {
		return cards.getOrDefault(cardType, 0) > 0;
	}*/

	/*public void setCardQuantity(CardType type, int quantity) {
		cards.put(type, quantity);
	}
*/
	public boolean checkHasLost(){
		return aliveShipsCounter == 0;
	}

	public void decreaseAliveShipsCounter(){
		aliveShipsCounter --;
	}



}

package src.logic.models.gameplay;

import src.enums.CardType;
import src.logic.models.cards.Card;

import java.util.Map;

public class Player {
    private String name;
    private int aliveShipsCount;
    private Map<CardType, Card> cards;
    private boolean hasLost = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAliveShipsCount() {
        return aliveShipsCount;
    }

    public void setAliveShipsCount(int aliveShipsCount) {
        this.aliveShipsCount = aliveShipsCount;
    }

    public boolean isHasLost() {
        return hasLost;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    public Map<CardType, Card> getCards() {
        return cards;
    }

    public void setCards(Map<CardType, Card> cards) {
        this.cards = cards;
    }
}

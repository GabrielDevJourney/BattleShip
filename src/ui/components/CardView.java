package src.ui.components;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import src.logic.models.cards.Card;
import src.enums.CardType;

import java.util.Map;
import java.util.function.Consumer;

public class CardView extends VBox{
	private Card cardSelected;
	private Consumer<Card> onCardSelected;


	public CardView(Map<CardType, Card> playerCards) {
		super(10);
		setupCardButtons(playerCards);
	}

	private void setupCardButtons(Map<CardType, Card> playerCards) {
		// Create button for each card
		for (Card card : playerCards.values()) {
			Button cardBtn = new Button(card.getType().toString());
			cardBtn.setOnAction(e -> handleCardSelection(card));

			// Disable if card not available
			cardBtn.setDisable(!card.isAvailable());

			getChildren().add(cardBtn);
		}
	}

	public void setOnCardSelected(Consumer<Card> handler) {
		this.onCardSelected = handler;
	}

	private void handleCardSelection(Card cardSelected) {
		if (cardSelected.isAvailable()) {
			this.cardSelected = cardSelected;
			if (onCardSelected != null) {
				onCardSelected.accept(cardSelected);
			}
		}
	}

	public void updateCards(Map<CardType, Card> playerCards) {
		// Clear existing buttons
		getChildren().clear();

		// Recreate buttons with updated card states
		for (Card card : playerCards.values()) {
			Button cardBtn = new Button(card.getType().toString());
			cardBtn.setOnAction(e -> handleCardSelection(card));

			// Disable if card not available
			cardBtn.setDisable(!card.isAvailable());

			getChildren().add(cardBtn);
		}
	}
}

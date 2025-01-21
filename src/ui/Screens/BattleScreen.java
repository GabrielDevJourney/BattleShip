package src.ui.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import src.enums.BoardState;
import src.enums.CardType;
import src.logic.game.Game;
import src.logic.game.BoardService;
import src.logic.models.cards.Card;
import src.ui.UiManager;
import src.ui.components.BoardView;
import src.ui.components.CardView;
import src.utils.Coordinate;

import java.util.Map;

public class BattleScreen {
	// Fields grouped by purpose
	private final UiManager uiManager;
	private final Game game;
	private Scene battleScene;

	// Layout containers
	private VBox mainLayout;
	private HBox boardContainer;
	private HBox cardsContainer;
	private VBox currentPlayerContainer;

	// UI components
	private BoardView boardView;
	private CardView cardView;
	private Card selectedCard;
	private Text playerNameText;

	public BattleScreen(UiManager uiManager, Game game) {
		this.uiManager = uiManager;
		this.game = game;
		createBattleScreen();
	}

	public Scene getScene() {
		return battleScene;
	}


	private void createBattleScreen() {
		mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);

		createCurrentPlayerContainer();
		createBoardContainer();
		createCardContainer();

		mainLayout.getChildren().addAll(
				currentPlayerContainer,
				boardContainer,
				cardsContainer
		);

		battleScene = new Scene(mainLayout);
		loadStylesheet();
		boardView.setOnCellClicked(this::handleCellClick);
	}

	private void loadStylesheet() {
		String cssPath = "file:///Users/mindera/Documents/minderaSchool/BattleShip/src/ui/styles/battleScreenStyles.css";
		try {
			battleScene.getStylesheets().add(cssPath);
		} catch (Exception e) {
			System.err.println("Failed to load CSS file: " + cssPath);
			e.printStackTrace();
		}
	}

	private void createCurrentPlayerContainer() {
		currentPlayerContainer = new VBox(10);
		currentPlayerContainer.setAlignment(Pos.TOP_CENTER);

		playerNameText = new Text(game.getCurrentPlayer().getName() + "'s Turn");
		playerNameText.getStyleClass().add("title-text");

		currentPlayerContainer.getChildren().add(playerNameText);
	}

	private void createBoardContainer() {
		boardContainer = new HBox(10);
		boardContainer.setAlignment(Pos.CENTER);
		boardContainer.getStyleClass().add("board-container");

		boardView = new BoardView(game.getOtherPlayer().getBoard().getSize());
		boardContainer.getChildren().add(boardView);
	}

	private void createCardContainer() {
		cardsContainer = new HBox(10);
		cardsContainer.setAlignment(Pos.CENTER);
		cardsContainer.getStyleClass().add("cards-container");

		cardView = new CardView(game.getCurrentPlayer().getCards());
		cardView.setOnCardSelected(card -> this.selectedCard = card);

		cardsContainer.getChildren().add(cardView);
	}

	// Update methods
	public void updateBoard(BoardService board) {
		boardView.updateBoardForBattle(board);
	}

	public void updateTurnIndicator(String playerName) {
		playerNameText.setText(playerName + "'s Turn");
	}

	public void updateCards(Map<CardType, Card> cards) {
		cardView.updateCards(cards);
	}

	private void handleCellClick(Coordinate coordinate) {
		BoardService opponentBoard = game.getOtherPlayer().getBoard();
		BoardState currentState = opponentBoard.getCellState(coordinate.getRow(), coordinate.getCol());

		if (currentState == BoardState.HIT || currentState == BoardState.MISS || currentState == BoardState.SUNK) {
			return;
		}

		if (selectedCard != null) {
			executeCardAttack(coordinate);
		} else {
			executeAttack(coordinate);
		}
	}

	private void executeCardAttack(Coordinate coordinate) {
		if (game.useCard(selectedCard.getType(), coordinate)) {
			BoardService opponentBoard = game.getOtherPlayer().getBoard();
			updateBoard(opponentBoard);
			updateCards(game.getCurrentPlayer().getCards());
		}
		selectedCard = null;
	}

	private void executeAttack(Coordinate coordinate) {
		boolean isHit = game.executeAttack(coordinate);
		BoardService opponentBoard = game.getOtherPlayer().getBoard();

		boardView.updateCellState(coordinate, opponentBoard.getCellState(coordinate.getRow(), coordinate.getCol()));

		if (!isHit) {
			handleTurnSwitch();
		}

		if (game.isGameOver()) {
			uiManager.showGameOverDialog(game);
		}
	}

	private void handleTurnSwitch() {
		game.switchToNextPlayerBattle();
		playerNameText.setText(game.getCurrentPlayer().getName() + "'s Turn");
		updateBoard(game.getOtherPlayer().getBoard());
		cardView.updateCards(game.getCurrentPlayer().getCards());
	}

}
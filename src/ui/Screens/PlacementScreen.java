package src.ui.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import src.logic.game.Game;
import src.logic.models.Board;
import src.logic.models.Ship;
import src.enums.BoardState;
import src.enums.ShipType;
import src.ui.UiManager;
import src.ui.components.BoardView;
import src.ui.components.CardView;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlacementScreen {
	private final UiManager uiManager;
	private final Game game;
	private Scene placementScene;

	// Layout containers
	private VBox mainLayout;
	private HBox boardContainer;
	private VBox placementRulesContainer;
	private VBox playerNameContainer;

	// UI components
	private BoardView boardView;
	private CardView cardView;
	private Dialog<ShipType> shipDialog;

	// Placement state
	private List<List<Coordinate>> allPlacements;
	private ShipType selectedShip;

	public PlacementScreen(UiManager uiManager, Game game) {
		this.uiManager = uiManager;
		this.game = game;

		if (!game.getSettings().isManualPlacement()) {
			uiManager.switchToBattle(game);
			return;
		}

		createPlacementScreen();
		initializeShipDialog();
	}

	public Scene getScene() {
		return placementScene;
	}


	private void createPlacementScreen() {
		mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);

		createPlayerNameContainer();
		createBoardContainer();
		createCardContainer();
		createPlacementRulesContainer();

		mainLayout.getChildren().addAll(
				playerNameContainer,
				boardContainer,
				cardView,
				placementRulesContainer
		);

		placementScene = new Scene(mainLayout);
		loadStylesheet();
	}

	private void loadStylesheet() {
		String cssPath = "file:///Users/mindera/Documents/minderaSchool/BattleShip/src/ui/styles/placementScreenStyle.css";
		try {
			placementScene.getStylesheets().add(cssPath);
		} catch (Exception e) {
			System.err.println("Failed to load CSS file: " + cssPath);
			e.printStackTrace();
		}
	}

	private void createPlayerNameContainer() {
		playerNameContainer = new VBox(10);
		playerNameContainer.setAlignment(Pos.TOP_CENTER);

		Text playerName = new Text(game.getCurrentPlayerName());
		playerName.getStyleClass().add("title-text");

		playerNameContainer.getChildren().add(playerName);
	}

	private void createBoardContainer() {
		boardContainer = new HBox(10);
		boardContainer.setAlignment(Pos.CENTER);
		boardContainer.getStyleClass().add("board-container");

		boardView = new BoardView(game.getBoardSize());
		boardView.setOnCellClicked(this::handleCellClick);

		boardContainer.getChildren().add(boardView);
	}

	private void createCardContainer() {
		cardView = new CardView(game.getCurrentPlayer().getCards());
	}

	private void createPlacementRulesContainer() {
		placementRulesContainer = new VBox(10);
		placementRulesContainer.setAlignment(Pos.CENTER);

		Text header = new Text("Placement Rules");
		header.getStyleClass().add("title-text");

		Text rules = new Text("""
				1. Click on a cell to open the ship selection menu
				2. Choose a ship from the available options
				3. Valid placement options will be highlighted
				4. Click on a highlighted cell to place your ship
				5. Ships cannot touch each other
				6. Repeat until all ships are placed""");
		rules.getStyleClass().add("simple-text");
		rules.setTextAlignment(TextAlignment.LEFT);

		placementRulesContainer.getChildren().addAll(header, rules);
	}

	private void initializeShipDialog() {
		shipDialog = new Dialog<>();
		VBox shipMenu = new VBox(10);
		shipDialog.setTitle("Choose a ship for placement!");
		shipDialog.getDialogPane().setContent(shipMenu);
		shipDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
	}

	private void handleCellClick(Coordinate coordinate) {
		if (allPlacements == null) {
			openShipMenu(coordinate);
		} else {
			confirmPlacement(coordinate);
		}
		updateScreen();
	}

	private void openShipMenu(Coordinate clickedCoordinate) {
		VBox shipMenu = (VBox) shipDialog.getDialogPane().getContent();
		shipMenu.getChildren().clear();

		game.getAvailableShips().entrySet().stream()
				.filter(entry -> entry.getValue() > 0)
				.sorted(Comparator.comparingInt(entry -> -entry.getKey().getShipSize()))
				.forEach(entry -> {
					Button button = new Button(entry.getKey().getName() + " (" + entry.getValue() + ")");
					button.setOnAction(event -> {
						handleShipSelection(entry.getKey(), clickedCoordinate);
						shipDialog.close();
					});
					shipMenu.getChildren().add(button);
				});

		shipDialog.showAndWait();
	}

	private void handleShipSelection(ShipType shipType, Coordinate coordinate) {
		boardView.clearShadows();
		selectedShip = shipType;

		allPlacements = ShipPlacementManager.getPossiblePlacements(
				coordinate,
				selectedShip.getShipSize(),
				boardView.getBoardSize(),
				game.getPlacedShips()
		);

		if (allPlacements != null && !allPlacements.isEmpty()) {
			boardView.displayAllShadowPlacements(allPlacements);
		}
	}

	private void confirmPlacement(Coordinate clickedCoordinate) {
		if (allPlacements == null) return;

		for (List<Coordinate> placement : allPlacements) {
			if (placement.contains(clickedCoordinate) && isValidPlacement(placement)) {
				boardView.clearShadows();
				placeShip(placement);
				checkPlayerPlacementComplete();

				allPlacements = null;
				selectedShip = null;
				return;
			}
		}

		boardView.clearShadows();
		allPlacements = null;
		selectedShip = null;
	}

	private boolean isValidPlacement(List<Coordinate> placement) {
		List<List<Coordinate>> existingShips = game.getCurrentPlayer().getBoard().getShips()
				.stream()
				.map(Ship::getCoordinates)
				.collect(Collectors.toList());

		return !ShipPlacementManager.isShipOverlapping(placement, existingShips) &&
				!ShipPlacementManager.isShipTooClose(placement, existingShips);
	}

	private void placeShip(List<Coordinate> placement) {
		Ship ship = game.createShip(selectedShip);
		ship.setCoordinates(placement);
		game.fillPlayerShips(ship, game.getCurrentPlayer());

		placement.forEach(coordinate -> {
			game.getCurrentPlayer().getBoard().setCellState(
					coordinate.getRow(),
					coordinate.getCol(),
					BoardState.SHIP
			);
			boardView.updateCellState(coordinate, BoardState.SHIP);
		});

		game.decrementShipCount(selectedShip);
	}

	private void checkPlayerPlacementComplete() {
		Map<ShipType, Integer> availableShips = game.getCurrentPlayerAvailableShips();

		if (availableShips.values().stream().allMatch(count -> count == 0)) {
			if (game.hasNextPlayer()) {
				game.switchToNextPlayerPlacement();
				boardView.clearBoard();
				updateScreen();
			} else {
				uiManager.switchToBattle(game);
			}
		}
	}

	private void updateScreen() {
		Text playerName = (Text) playerNameContainer.getChildren().get(0);
		playerName.setText(game.getCurrentPlayerName());

		Board currentBoard = game.getCurrentPlayer().getBoard();
		updateBoard(currentBoard);
		cardView.updateCards(game.getCurrentPlayer().getCards());

		uiManager.updatePlacementScreen(game);
		mainLayout.requestLayout();
	}

	// Public update methods for UiManager
	public void updateBoard(Board board) {
		boardView.updateBoardForPlacement(game.getCurrentPlayer().getBoard());
	}

	public void updateTurnIndicator() {
		Text playerName = (Text) playerNameContainer.getChildren().get(0);
		playerName.setText(game.getCurrentPlayerName() + "'s Turn");
	}

	public void updateCards() {
		cardView.updateCards(game.getCurrentPlayer().getCards());
	}

}
package src.ui.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import src.backend.game.Game;
import src.enums.BoardState;
import src.enums.ShipType;
import src.ui.UiManager;
import src.ui.components.BoardView;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.Comparator;
import java.util.List;

public class PlacementScreen {
	private final UiManager uiManager;//todo for going to battle screen
	private Scene placementScene;
	private VBox mainLayout;
	private HBox boardContainer;
	private VBox placementRulesContainer;
	private VBox playerNameContainer;
	private BoardView boardView;
	private Game game;
	private Dialog<ShipType> shipDialog;
	private List<List<Coordinate>> allPlacements;
	private ShipType selectedShip;

	public PlacementScreen(UiManager uiManager, Game game) {
		this.uiManager = uiManager;
		this.game = game;
		initializeShipsDialog();
		createPlacementScreen();
	}

	public Scene getScene() {
		return placementScene;
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

	//* UI CREATION METHODS

	private void initializeShipsDialog() {
		shipDialog = new Dialog<>();
		VBox shipmenu = new VBox(10);
		shipDialog.setTitle("Choose a ship for placement!");
		shipDialog.getDialogPane().setContent(shipmenu);
		shipDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
	}

	private void createPlacementScreen() {
		mainLayout = new VBox(20);
		mainLayout.setAlignment(Pos.CENTER);

		createPlayerNameContainer();
		createBoardContainer();
		createPlacementRulesContainer();

		mainLayout.getChildren().addAll(
				playerNameContainer,
				boardContainer,
				placementRulesContainer
		);

		placementScene = new Scene(mainLayout);

		String cssPath = "file:///Users/mindera/Documents/mindera/BattleShip/src/ui/styles/placementScreenStyle.css";
		try {
			placementScene.getStylesheets().add(cssPath);
			System.out.println("CSS file loaded successfully");
		} catch (Exception e) {
			System.err.println("Failed to load CSS file: " + cssPath);
			e.printStackTrace();
		}
	}

	//* HANDLE UI CREATION
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
		boardContainer.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;" +
				"-fx-border-radius: 10px;");

		boardView = new BoardView(game.getBoardSize());
		boardView.setOnCellClicked(this::handleCellClick);


		boardContainer.getChildren().add(boardView);
	}

	private void createPlacementRulesContainer() {
		placementRulesContainer = new VBox(10);
		placementRulesContainer.setAlignment(Pos.CENTER);

		Text header = new Text("Placement Rules");
		header.getStyleClass().add("title-text");

		Text rules = new Text("""
				1. Click on a cell to open the ship selection menu.
				2. Choose a ship from the available options.
				3. Valid placement options will be highlighted.
				4. Click on a highlighted cell to place your ship.
				5. Ships cannot touch each other. Always leave at least one empty cell between ships.
				6. Repeat until all ships are placed.""");
		rules.getStyleClass().add("simple-text");
		rules.setTextAlignment(TextAlignment.LEFT);

		placementRulesContainer.getChildren().addAll(header, rules);
	}


	//* HANDLE SHIPS PREVIEW PLACEMENTS
	private void handleShipSelection(ShipType selectedShip, Coordinate clickedCoordinate) {
		this.selectedShip = selectedShip;
		allPlacements = ShipPlacementManager.getPossiblePlacements(
				clickedCoordinate,
				selectedShip.getShipSize(),
				boardView.getBoardSize(),
				game.getPlacedShips()
		);

		if (allPlacements != null) {
			boardView.displayAllShadowPlacements(allPlacements);
		}
	}

	private void confirmPlacement(Coordinate clickedCoordinate) {
		for (List<Coordinate> placement : allPlacements) {
			if (placement.contains(clickedCoordinate)) {
				if (!ShipPlacementManager.isShipOverlapping(placement, game.getPlacedShips()) &&
						!ShipPlacementManager.isShipTooClose(placement, game.getPlacedShips())) {
					placement.forEach(coordinate ->
							boardView.updateCellState(coordinate, BoardState.SHIP)
					);
					game.placeShip(placement);  // Add the new ship to the game's placed ships
					boardView.clearShadows();
					game.decrementShipCount(selectedShip);
					allPlacements = null;
					selectedShip = null;
					return;
				} else {
					return;
				}
			}
		}
		// If clicked outside valid placements, clear shadows
		cancelPlacement();
	}

	private void cancelPlacement() {
		boardView.clearShadows();
		allPlacements = null;
		selectedShip = null;
	}

	private void handleCellClick(Coordinate clickedCoordinate) {
		if (allPlacements == null) {
			openShipMenu(clickedCoordinate);
		} else {
			confirmPlacement(clickedCoordinate);
		}
	}
}

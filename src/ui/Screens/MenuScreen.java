package src.ui.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import src.logic.game.Game;
import src.logic.game.GameSettings;
import src.enums.BoardSizeOption;
import src.enums.BoardState;
import src.enums.PlacementOption;
import src.enums.ShipQuantityOption;
import src.ui.UiManager;



public class MenuScreen {
	private final UiManager uiManager;
	private Scene menuScene;

	// Layout containers
	private VBox mainLayout;
	private VBox gameSettingsVBox;
	private HBox gameSettingsContainer;
	private GridPane gameInfoContainer;
	private VBox gameInfoVbox;
	private HBox bottomButtonsContainer;

	// Settings containers
	private VBox boardSizeContainer;
	private VBox shipQuantityContainer;
	private VBox placementTypeContainer;

	// Info containers
	private VBox shipsInfoContainer;
	private VBox cardsInfoContainer;
	private VBox symbolsInfoContainer;
	private VBox controlsInfoContainer;

	// Settings controls
	private ComboBox<BoardSizeOption> boardSizeComboBox;
	private ComboBox<ShipQuantityOption> shipQuantityComboBox;
	private ComboBox<PlacementOption> placementComboBox;

	// Settings state
	private BoardSizeOption boardSizeOption;
	private ShipQuantityOption shipQuantityOption;
	private PlacementOption placementOption;

	public MenuScreen(UiManager uiManager) {
		this.uiManager = uiManager;
		initializeDefaultSettings();
		createMenuScreen();
	}

	public Scene getScene() {
		return menuScene;
	}


	private void initializeDefaultSettings() {
		boardSizeOption = BoardSizeOption.SIZE_10;
		shipQuantityOption = ShipQuantityOption.SHIPS_10;
		placementOption = PlacementOption.MANUAL;
	}

	private void createMenuScreen() {
		mainLayout = new VBox(20);
		mainLayout.setStyle("-fx-padding: 15;");
		mainLayout.setAlignment(Pos.TOP_CENTER);

		createGameSettingsContainer();
		createGameInfoContainer();
		createBottomButtonsContainer();

		mainLayout.getChildren().addAll(
				gameSettingsVBox,
				gameInfoVbox,
				bottomButtonsContainer
		);

		menuScene = new Scene(mainLayout);
		loadStylesheet();
		setupEventHandlers();
	}

	private void loadStylesheet() {
		String cssPath = "file:///Users/mindera/Documents/minderaSchool/BattleShip/src/ui/styles/menuScreenStyles.css";
		try {
			menuScene.getStylesheets().add(cssPath);
		} catch (Exception e) {
			System.err.println("Failed to load CSS file: " + cssPath);
			e.printStackTrace();
		}
	}

	private void createGameSettingsContainer() {
		gameSettingsVBox = new VBox(10);
		gameSettingsVBox.setAlignment(Pos.TOP_CENTER);

		Text title = new Text("GAME SETTINGS");
		title.getStyleClass().add("game-settings-title-text");

		gameSettingsContainer = new HBox();
		gameSettingsContainer.setAlignment(Pos.CENTER);
		gameSettingsContainer.getStyleClass().add("game-settings-containers");

		createBoardSizeContainer();
		createShipQuantityContainer();
		createPlacementTypeContainer();

		gameSettingsContainer.getChildren().addAll(
				boardSizeContainer,
				shipQuantityContainer,
				placementTypeContainer
		);

		gameSettingsVBox.getChildren().addAll(title, gameSettingsContainer);
	}

	private void createBoardSizeContainer() {
		boardSizeContainer = new VBox();
		boardSizeContainer.setAlignment(Pos.CENTER);
		boardSizeContainer.getStyleClass().add("game-settings-containers");

		Text title = new Text("BOARD SIZE");
		title.getStyleClass().add("title-text");

		boardSizeComboBox = new ComboBox<>();
		boardSizeComboBox.getItems().addAll(BoardSizeOption.values());
		boardSizeComboBox.setValue(BoardSizeOption.SIZE_10);
		boardSizeComboBox.getStyleClass().add("option-text");

		boardSizeContainer.getChildren().addAll(title, boardSizeComboBox);
	}

	private void createShipQuantityContainer() {
		shipQuantityContainer = new VBox();
		shipQuantityContainer.setAlignment(Pos.CENTER);
		shipQuantityContainer.getStyleClass().add("game-settings-containers");

		Text title = new Text("SHIPS QUANTITY");
		title.getStyleClass().add("title-text");

		shipQuantityComboBox = new ComboBox<>();
		shipQuantityComboBox.getItems().addAll(ShipQuantityOption.values());
		shipQuantityComboBox.setValue(ShipQuantityOption.SHIPS_10);

		shipQuantityContainer.getChildren().addAll(title, shipQuantityComboBox);
	}

	private void createPlacementTypeContainer() {
		placementTypeContainer = new VBox();
		placementTypeContainer.setAlignment(Pos.CENTER);
		placementTypeContainer.getStyleClass().add("game-settings-containers");

		Text title = new Text("PLACEMENT TYPE");
		title.getStyleClass().add("title-text");

		placementComboBox = new ComboBox<>();
		placementComboBox.getItems().addAll(PlacementOption.values());
		placementComboBox.setValue(PlacementOption.MANUAL);

		placementTypeContainer.getChildren().addAll(title, placementComboBox);
	}

	private void createGameInfoContainer() {
		gameInfoVbox = new VBox(10);
		gameInfoVbox.setAlignment(Pos.TOP_CENTER);
		gameInfoVbox.getStyleClass().add("game-info-box");

		Text title = new Text("GAME INFO");
		title.getStyleClass().add("game-settings-title-text");

		gameInfoContainer = new GridPane();
		gameInfoContainer.setHgap(20);
		gameInfoContainer.setVgap(20);
		gameInfoContainer.getStyleClass().add("game-info-grid-container");

		createShipsInfoContainer();
		createCardsInfoContainer();
		createSymbolsInfoContainer();
		createControlsInfoContainer();

		gameInfoContainer.add(shipsInfoContainer, 1, 0);
		gameInfoContainer.add(cardsInfoContainer, 2, 0);
		gameInfoContainer.add(symbolsInfoContainer, 1, 1);
		gameInfoContainer.add(controlsInfoContainer, 2, 1);

		gameInfoVbox.getChildren().addAll(title, gameInfoContainer);
	}

	private void createShipsInfoContainer() {
		shipsInfoContainer = new VBox();
		shipsInfoContainer.setAlignment(Pos.TOP_CENTER);
		shipsInfoContainer.getStyleClass().add("game-info-containers");

		Text title = new Text("SHIPS");
		title.getStyleClass().add("title-text");

		Text cloudRuler = new Text("Cloud Ruler (Size 5)");
		Text convoyShepherd = new Text("Convoy Shepherd (Size 4)");
		Text abyssalAssassin = new Text("Abyssal Assassin (Size 3)");
		Text dauntlessDefender = new Text("Dauntless Defender (Size 3)");
		Text shellfireJuggernaut = new Text("Shellfire Juggernaut (Size 2)");

		cloudRuler.getStyleClass().add("simple-text");
		convoyShepherd.getStyleClass().add("simple-text");
		abyssalAssassin.getStyleClass().add("simple-text");
		dauntlessDefender.getStyleClass().add("simple-text");
		shellfireJuggernaut.getStyleClass().add("simple-text");

		shipsInfoContainer.getChildren().addAll(
				title, cloudRuler, convoyShepherd,
				abyssalAssassin, dauntlessDefender, shellfireJuggernaut
		);
	}

	private void createCardsInfoContainer() {
		cardsInfoContainer = new VBox();
		cardsInfoContainer.setAlignment(Pos.TOP_CENTER);
		cardsInfoContainer.getStyleClass().add("game-info-containers");

		Text title = new Text("CARDS");
		title.getStyleClass().add("title-text");

		Text crossStrike = new Text("Cross Strike (2x2 area shot)");
		Text quadShot = new Text("Quad Shot (4 random shots)");
		Text sonarPulse = new Text("Sonar Pulse (3x3 area ships info)");
		Text skipTurn = new Text("Skip Turn (enemy next turn skip)");

		crossStrike.getStyleClass().add("simple-text");
		quadShot.getStyleClass().add("simple-text");
		sonarPulse.getStyleClass().add("simple-text");
		skipTurn.getStyleClass().add("simple-text");

		cardsInfoContainer.getChildren().addAll(
				title, crossStrike, quadShot, sonarPulse, skipTurn
		);
	}

	private void createSymbolsInfoContainer() {
		symbolsInfoContainer = new VBox();
		symbolsInfoContainer.setAlignment(Pos.TOP_CENTER);
		symbolsInfoContainer.getStyleClass().add("game-info-containers");

		Text title = new Text("GAME SYMBOLS");
		title.getStyleClass().add("title-text");

		Text water = new Text("Water - " + BoardState.WATER.getBoardState());
		Text miss = new Text("Miss - " + BoardState.MISS.getBoardState());
		Text hit = new Text("Hit - " + BoardState.HIT.getBoardState());
		Text sunk = new Text("Sunk - " + BoardState.SUNK.getBoardState());
		Text ship = new Text("Ship - " + BoardState.SHIP.getBoardState());

		water.getStyleClass().add("simple-text");
		miss.getStyleClass().add("simple-text");
		hit.getStyleClass().add("simple-text");
		sunk.getStyleClass().add("simple-text");
		ship.getStyleClass().add("simple-text");

		symbolsInfoContainer.getChildren().addAll(
				title, water, miss, hit, sunk, ship
		);
	}

	private void createControlsInfoContainer() {
		controlsInfoContainer = new VBox();
		controlsInfoContainer.setAlignment(Pos.TOP_CENTER);
		controlsInfoContainer.getStyleClass().add("game-info-containers");

		Text title = new Text("CONTROLS");
		title.getStyleClass().add("title-text");

		Text useCards = new Text("Use Cards - Buttons of each card");
		Text mouseInteractiveGameText = new Text("To interact use mouse");

		useCards.getStyleClass().add("simple-text");
		mouseInteractiveGameText.getStyleClass().add("simple-text");

		controlsInfoContainer.getChildren().addAll(
				title,
				useCards,
				mouseInteractiveGameText
		);
	}

	private void createBottomButtonsContainer() {
		bottomButtonsContainer = new HBox(20);
		bottomButtonsContainer.setAlignment(Pos.CENTER);
		bottomButtonsContainer.getStyleClass().add("bottom-buttons-container");

		Button startGameButton = new Button("Start");
		startGameButton.getStyleClass().add("bottom-button");
		startGameButton.setOnAction(e -> startGame());

		Button exitButton = new Button("Exit");
		exitButton.getStyleClass().add("bottom-button");
		exitButton.setOnAction(e -> uiManager.closeGame());

		bottomButtonsContainer.getChildren().addAll(startGameButton, exitButton);
	}

	private void setupEventHandlers() {
		boardSizeComboBox.setOnAction(event -> updateSettings());
		shipQuantityComboBox.setOnAction(event -> updateSettings());
		placementComboBox.setOnAction(event -> updateSettings());
	}

	private void updateSettings() {
		boardSizeOption = boardSizeComboBox.getValue();
		shipQuantityOption = shipQuantityComboBox.getValue();
		placementOption = placementComboBox.getValue();
	}

	private void startGame() {
		updateSettings();
		GameSettings gameSettings = new GameSettings(
				boardSizeOption,
				shipQuantityOption,
				placementOption
		);
		Game game = new Game(gameSettings);
		uiManager.startPlacement(game);
	}
}
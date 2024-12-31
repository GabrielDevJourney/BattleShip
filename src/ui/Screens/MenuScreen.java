package src.ui.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.backend.game.Game;
import src.backend.game.GameSettings;
import src.constants.AllConstants;
import src.enums.BoardSizeOption;
import src.enums.BoardState;
import src.enums.PlacementOption;
import src.enums.ShipQuantityOption;
import src.ui.UiManager;

import java.net.URL;

import static javafx.scene.paint.Color.ALICEBLUE;

//todo change spacing between gameSettingsContainer containers to fix alignment this
//todo in css file spacig might not be proper done


public class MenuScreen {
	private final UiManager uiManager;
	private Scene menuScene;
	private VBox mainLayout;

	// Game Settings Containers
	private VBox gameSettingsVBox;
	private HBox gameSettingsContainer;
	private VBox boardSizeContainer;
	private VBox shipQuantityContainer;
	private VBox placementTypeContainer;

	// Game Settings Controls
	private ComboBox<BoardSizeOption> boardSizeComboBox;
	private ComboBox<ShipQuantityOption> shipQuantityComboBox;
	private ComboBox<PlacementOption> placementComboBox;

	// Game Info Containers
	private VBox gameInfoVbox;

	private GridPane gameInfoContainer;
	private VBox shipsInfoContainer;
	private VBox cardsInfoContainer;
	private VBox symbolsInfoContainer;
	private VBox controlsInfoContainer;

	private Text shipsTitleText;
	private Text cloudRulerText;
	private Text convoyShepherdText;
	private Text abyssalAssassinText;
	private Text dauntlessDefenderText;
	private Text shellfireJuggernautText;

	private Text cardsTitleText;
	private Text crossStrikeText;
	private Text quadShotText;
	private Text skipTurnText;
	private Text sonarPulseText;

	private Text gameSymbolsTitleText;
	private Text waterText;
	private Text hitText;
	private Text missText;
	private Text sunkText;
	private Text shipText;

	private Text controlsTitleText;
	private Text navigateText;
	private Text selectAndFireText;
	private Text rotateShipText;
	private Text useCardsText;
	private Text menuText;


	// Bottom Buttons Container
	private HBox bottomButtonsContainer;

	//For final game settings
	BoardSizeOption boardSizeOption;
	ShipQuantityOption shipQuantityOption;
	PlacementOption placementOption;

	public MenuScreen(UiManager uiManager) {
		this.uiManager = uiManager;

		//default settings if player just starts game without changing any thing
		boardSizeOption = BoardSizeOption.SIZE_10;
		shipQuantityOption = ShipQuantityOption.SHIPS_10;
		placementOption = PlacementOption.MANUAL;

		createMenuScreenScene();
	}

	public Scene getScene() {
		return menuScene;
	}

	private void createMenuScreenScene() {
		mainLayout = new VBox(20);
		mainLayout.setStyle("-fx-padding: 15;");
		mainLayout.setAlignment(Pos.TOP_CENTER);

		menuScene = new Scene(mainLayout);

		String cssPath = "file:///Users/mindera/Documents/mindera/BattleShip/src/ui/styles/menuScreenStyles.css";
		try {
			menuScene.getStylesheets().add(cssPath);
			System.out.println("CSS file loaded successfully");
		} catch (Exception e) {
			System.err.println("Failed to load CSS file: " + cssPath);
			e.printStackTrace();
		}


		createGameSettingsMainContainer();
		createGameInfoMainContainer();
		createBottomBtnsMainContainer();
		setupEventHandlers();
		testLayout();

		mainLayout.getChildren().addAll(
				gameSettingsVBox,
				gameInfoVbox,
				bottomButtonsContainer
		);
	}

	//* GAME SETTINGS

	private void createGameSettingsMainContainer() {
		gameSettingsVBox = new VBox(10); // Create VBox with spacing
		gameSettingsVBox.setAlignment(Pos.TOP_CENTER);

		Text gameSettingsTitle = new Text("GAME SETTINGS");
		gameSettingsTitle.getStyleClass().add("game-settings-title-text");

		// Create HBox for game settings
		gameSettingsContainer = new HBox();

		gameSettingsContainer.setAlignment(Pos.CENTER);
		gameSettingsContainer.getStyleClass().add("game-settings-containers");

		createBoardSizeSettingContainer();
		createShipQuantitySettingContainer();
		createPlacementTypeSettingContainer();

		gameSettingsContainer.getChildren().addAll(
				boardSizeContainer,
				shipQuantityContainer,
				placementTypeContainer
		);

		// Add title and HBox to VBox
		gameSettingsVBox.getChildren().addAll(gameSettingsTitle, gameSettingsContainer);
	}

	private void createBoardSizeSettingContainer() {
		boardSizeContainer = new VBox();

		boardSizeContainer.setAlignment(Pos.CENTER);
		boardSizeContainer.getStyleClass().add("game-settings-containers");

		Text boardSizeTitle = new Text("BOARD SIZE");
		boardSizeTitle.getStyleClass().add("title-text");

		boardSizeComboBox = new ComboBox<>();
		boardSizeComboBox.getItems().addAll(BoardSizeOption.values());
		boardSizeComboBox.setValue(BoardSizeOption.SIZE_10);
		boardSizeComboBox.getStyleClass().add("option-text");

		boardSizeContainer.getChildren().addAll(
				boardSizeTitle,
				boardSizeComboBox
		);
	}

	private void createShipQuantitySettingContainer() {
		shipQuantityContainer = new VBox();

		shipQuantityContainer.setAlignment(Pos.CENTER);
		shipQuantityContainer.getStyleClass().add("game-settings-containers");

		Text shipQuantityTitle = new Text("SHIPS QUANTITY");
		shipQuantityTitle.getStyleClass().add("title-text");

		shipQuantityComboBox = new ComboBox<>();
		shipQuantityComboBox.getItems().addAll(ShipQuantityOption.values());
		shipQuantityComboBox.setValue(ShipQuantityOption.SHIPS_10);

		shipQuantityContainer.getChildren().addAll(
				shipQuantityTitle,
				shipQuantityComboBox
		);
	}

	private void createPlacementTypeSettingContainer() {
		placementTypeContainer = new VBox();

		placementTypeContainer.setAlignment(Pos.CENTER);
		placementTypeContainer.getStyleClass().add("game-settings-containers");

		Text placementTitle = new Text("PLACEMENT TYPE");
		placementTitle.getStyleClass().add("title-text");

		placementComboBox = new ComboBox<>();
		placementComboBox.getItems().addAll(PlacementOption.values());
		placementComboBox.setValue(PlacementOption.MANUAL);

		placementTypeContainer.getChildren().addAll(
				placementTitle,
				placementComboBox
		);
	}

	//* GAME INFO
	private void createGameInfoMainContainer() {
		gameInfoVbox = new VBox(10);
		gameInfoVbox.setAlignment(Pos.TOP_CENTER);
		gameInfoVbox.getStyleClass().add("game-info-box");

		Text gameInfoTitle = new Text("GAME INFO");
		gameInfoTitle.getStyleClass().add("game-settings-title-text");

		gameInfoContainer = new GridPane();
		gameInfoContainer.setHgap(20);
		gameInfoContainer.setVgap(20);
		gameInfoContainer.getStyleClass().add("game-info-grid-container");


		createShipsInfoContainer();
		createCardsInfoContainer();
		createGameSymbolsInfoContainer();
		createControlsInfoContainer();

		//grid pane placement (obj, col,row)
		gameInfoContainer.add(shipsInfoContainer,1,0);
		gameInfoContainer.add(cardsInfoContainer,2,0);
		gameInfoContainer.add(symbolsInfoContainer,1,1);
		gameInfoContainer.add(controlsInfoContainer,2,1);

		gameInfoVbox.getChildren().addAll(
				gameInfoTitle,
				gameInfoContainer
		);

	}

	private void createShipsInfoContainer() {
		shipsInfoContainer = new VBox();

		shipsInfoContainer.setAlignment(Pos.TOP_CENTER);
		shipsInfoContainer.getStyleClass().add("game-info-containers");

		shipsTitleText = new Text("SHIPS");
		shipsTitleText.getStyleClass().add("title-text");

		cloudRulerText = new Text("Cloud Ruler (Size 5)");
		convoyShepherdText = new Text("Convoy Shepherd (Size 4)");
		abyssalAssassinText = new Text("Abyssal Assassin (Size 3)");
		dauntlessDefenderText = new Text("Dauntless Defender (Size 3)");
		shellfireJuggernautText = new Text("Shellfire Juggernaut (Size 2)");

		cloudRulerText.getStyleClass().add("simple-text");
		convoyShepherdText.getStyleClass().add("simple-text");
		abyssalAssassinText.getStyleClass().add("simple-text");
		dauntlessDefenderText.getStyleClass().add("simple-text");
		shellfireJuggernautText.getStyleClass().add("simple-text");

		shipsInfoContainer.getChildren().addAll(
				shipsTitleText,
				cloudRulerText,
				convoyShepherdText,
				abyssalAssassinText,
				dauntlessDefenderText,
				shellfireJuggernautText
		);
	}

	private void createCardsInfoContainer() {
		cardsInfoContainer = new VBox();

		cardsInfoContainer.setAlignment(Pos.TOP_CENTER);
		cardsInfoContainer.getStyleClass().add("game-info-containers");

		cardsTitleText = new Text("CARDS");
		cardsTitleText.getStyleClass().add("title-text");

		crossStrikeText = new Text("Cross Strike (2x2 area shot)");
		quadShotText = new Text("Quad Shot (4 random shots)");
		sonarPulseText = new Text("Sonar Pulse (3x3 area ships info)");
		skipTurnText = new Text("Skip Turn (enemy next turn skip)");

		crossStrikeText.getStyleClass().add("simple-text");
		quadShotText.getStyleClass().add("simple-text");
		sonarPulseText.getStyleClass().add("simple-text");
		skipTurnText.getStyleClass().add("simple-text");

		cardsInfoContainer.getChildren().addAll(
				cardsTitleText,
				crossStrikeText,
				quadShotText,
				sonarPulseText,
				skipTurnText
		);
	}

	private void createGameSymbolsInfoContainer() {
		symbolsInfoContainer = new VBox();

		symbolsInfoContainer.setAlignment(Pos.TOP_CENTER);
		symbolsInfoContainer.getStyleClass().add("game-info-containers");


		gameSymbolsTitleText = new Text("GAME SYMBOLS");
		gameSymbolsTitleText.getStyleClass().add("title-text");

		waterText = new Text("Water - " + BoardState.WATER.getBoardState());
		missText = new Text("Miss - " + BoardState.MISS.getBoardState());
		hitText = new Text("Hit - " + BoardState.HIT.getBoardState());
		sunkText = new Text("Sunk - " + BoardState.SUNK.getBoardState());
		shipText = new Text("Ship - " + BoardState.SHIP.getBoardState());

		waterText.getStyleClass().add("simple-text");
		missText.getStyleClass().add("simple-text");
		hitText.getStyleClass().add("simple-text");
		sunkText.getStyleClass().add("simple-text");
		shipText.getStyleClass().add("simple-text");

		symbolsInfoContainer.getChildren().addAll(
				gameSymbolsTitleText,
				waterText,
				missText,
				hitText,
				sunkText,
				shipText
		);
	}

	private void createControlsInfoContainer() {
		controlsInfoContainer = new VBox();

		controlsInfoContainer.setAlignment(Pos.TOP_CENTER);
		controlsInfoContainer.getStyleClass().add("game-info-containers");

		controlsTitleText = new Text("CONTROLS");
		controlsTitleText.getStyleClass().add("title-text");

		navigateText = new Text("Navigate - ↑↓←→");
		selectAndFireText = new Text("Select/Fire - ENTER");
		rotateShipText = new Text("Rotate Ship - R");
		useCardsText = new Text("Use Cards - 1-4");
		menuText = new Text("Menu/Exit - ESC");

		navigateText.getStyleClass().add("simple-text");
		selectAndFireText.getStyleClass().add("simple-text");
		rotateShipText.getStyleClass().add("simple-text");
		useCardsText.getStyleClass().add("simple-text");
		menuText.getStyleClass().add("simple-text");

		controlsInfoContainer.getChildren().addAll(
				controlsTitleText,
				navigateText,
				selectAndFireText,
				rotateShipText,
				useCardsText,
				menuText
		);
	}


	//*BOTTOM BTNS
	private void createBottomBtnsMainContainer() {
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


	//*HANDLE MOUSE EVENTS AND SETTINGS PROPAGATION TO OTHER CLASSES

	private void setupEventHandlers() {
		boardSizeComboBox.setOnAction(e -> updateSettings());
		shipQuantityComboBox.setOnAction(e -> updateSettings());
		placementComboBox.setOnAction(e -> updateSettings());
	}

	private void updateSettings() {
		System.out.println("Current Settings:");
		System.out.println("Board Size: " + boardSizeComboBox.getValue());
		System.out.println("Ship Quantity: " + shipQuantityComboBox.getValue());
		System.out.println("Placement Type: " + placementComboBox.getValue());

		boardSizeOption = boardSizeComboBox.getValue();
		shipQuantityOption = shipQuantityComboBox.getValue();
		placementOption = placementComboBox.getValue();
	}

	private void startGame() {
		updateSettings();

		// For now, we'll just print the final selections
		System.out.println("Starting game with:");
		System.out.println("Board Size: " + boardSizeComboBox.getValue());
		System.out.println("Ship Quantity: " + shipQuantityComboBox.getValue());
		System.out.println("Placement Type: " + placementComboBox.getValue());

		// method to pass above final player decision of game settings create proper method in game
		//todo still need to handle the click action itself
		GameSettings gameSettings = new GameSettings(boardSizeOption, shipQuantityOption, placementOption);
		Game game = new Game(gameSettings);
		uiManager.switchToPlacement();
	}


	//*STYLIG TESTING
	//!this stying is being used need to apply it properly after
	private void testLayout() {
		shipsInfoContainer.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;" +
				"-fx-border-radius: 10px;");
		cardsInfoContainer.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;" +
				"-fx-border-radius: 10px;");
		symbolsInfoContainer.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;" +
				"-fx-border-radius: 10px;");
		controlsInfoContainer.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid;" +
				"-fx-border-radius: 10px;");
	}

}

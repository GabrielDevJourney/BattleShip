package src.ui;

import javafx.stage.Stage;
import src.backend.game.Game;
import src.ui.Screens.BattleScreen;
import src.ui.Screens.MenuScreen;
import src.ui.Screens.PlacementScreen;

public class UiManager {
	private Stage mainStage;
	private MenuScreen menuScreen;
	private PlacementScreen placementScreen;
	private BattleScreen battleScreen;

	public UiManager(Stage stage) {
		this.mainStage = stage;
		initializeScreens();
		mainStage.setScene(menuScreen.getScene());
	}

	private void initializeScreens() {
		menuScreen = new MenuScreen(this); // Pass UiManager instead of Stage
		battleScreen = new BattleScreen(this);
	}

	public void switchToPlacement() {
		mainStage.setScene(placementScreen.getScene());
	}

	public void switchToBattle() {
		mainStage.setScene(battleScreen.getScene());
	}

	public void closeGame() {
		mainStage.close();
	}

	//pass game to be able to initialize board in placement screen with proper settings
	public void startPlacement(Game game) {
		placementScreen = new PlacementScreen(this, game);
		switchToPlacement();
	}
}

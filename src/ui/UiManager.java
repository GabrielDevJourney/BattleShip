package src.ui;

import javafx.stage.Stage;
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
		placementScreen = new PlacementScreen(this);
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
}

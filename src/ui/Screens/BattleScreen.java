package src.ui.Screens;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import src.ui.UiManager;

public class BattleScreen {
	private final UiManager uiManager;
	private Scene placementScene;
	private VBox mainLayout;

	public BattleScreen(UiManager uiManager) {
		this.uiManager = uiManager;
	}

	public Scene getScene() {
		return placementScene;
	}
}

package src.ui.Screens;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ui.UiManager;

public class PlacementScreen {
	private final UiManager uiManager;
	private Scene placementScene;
	private VBox mainLayout;

	public PlacementScreen(UiManager uiManager) {
		this.uiManager = uiManager;
	}

	public Scene getScene() {
		return placementScene;
	}
}

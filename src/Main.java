package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ui.UiManager;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		UiManager uiManager = new UiManager(stage);
		stage.setWidth(1000);
		stage.setHeight(1200);
		stage.setResizable(false);
		stage.setTitle("BattleShip");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
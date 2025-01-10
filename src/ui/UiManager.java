package src.ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import src.logic.game.Game;
import src.logic.models.Player;
import src.ui.Screens.BattleScreen;
import src.ui.Screens.MenuScreen;
import src.ui.Screens.PlacementScreen;

public class UiManager {
	// Core components
	private final Stage mainStage;
	private MenuScreen menuScreen;
	private PlacementScreen placementScreen;
	private BattleScreen battleScreen;

	public UiManager(Stage stage) {
		this.mainStage = stage;
		initializeMenuScreen();
	}

	// Screens initialization
	private void initializeMenuScreen() {
		menuScreen = new MenuScreen(this);
		mainStage.setScene(menuScreen.getScene());
	}

	public void startPlacement(Game game) {
		placementScreen = new PlacementScreen(this, game);

		if (game.getSettings().isManualPlacement()) {
			switchToPlacement();
		} else {
			switchToBattle(game);
		}
	}

	// Screens transitions
	public void switchToPlacement() {
		mainStage.setScene(placementScreen.getScene());
	}

	public void switchToBattle(Game game) {
		battleScreen = new BattleScreen(this, game);
		mainStage.setScene(battleScreen.getScene());
	}

	public void switchToMenu() {
		menuScreen = new MenuScreen(this);
		mainStage.setScene(menuScreen.getScene());
	}

	// Screen updates
	public void updatePlacementScreen(Game game) {
		if (placementScreen != null) {
			placementScreen.updateBoard();
			placementScreen.updateTurnIndicator();
			placementScreen.updateCards();
		}
	}

	// Game state
	public void showGameOverDialog(Game game) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("Game Finished!");

		Player winner = game.getCurrentPlayer().hasLost() ?
				game.getOtherPlayer() : game.getCurrentPlayer();

		alert.setContentText(winner.getName() + " has won the game!");
		alert.showAndWait().ifPresent(response -> switchToMenu());
	}

	public void closeGame() {
		mainStage.close();
	}
}
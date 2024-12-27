package src.Input;

import com.googlecode.lanterna.input.KeyStroke;
import src.Enums.InputType;
import src.Game.Game;
import src.UI.UiHandler;

import java.io.IOException;


public class InputHandler {
	private UiHandler uiHandler;
	private Game game;

	public InputHandler(Game game, UiHandler uiHandler) {
		this.game = game;
		this.uiHandler = uiHandler;
	}

	public KeyStroke readInput() throws IOException {
		return uiHandler.getScreen().readInput();
	}

	public InputType filterRawInputType() throws IOException {
		KeyStroke userInput = readInput();

		if (userInput != null) {
			switch (userInput.getKeyType()) {
				case Enter -> {
					return InputType.CONFIRM;
				}
				case ArrowDown -> {
					return InputType.MOVE_CURSOR;
				}
				case ArrowUp -> {
					return InputType.MOVE_CURSOR;
				}
				case ArrowRight -> {
					return InputType.MOVE_CURSOR;
				}
				case ArrowLeft -> {
					return InputType.MOVE_CURSOR;
				}
				case Escape -> {
					return InputType.CANCEL;
				}
			}
		}


		return null;
	}

	public InputType validateAndProcessInput() throws IOException {

		InputType inputType = filterRawInputType();

		if(inputType == null) return null;

		return switch (game.getCurrentPhaseType()) {
			case SETUP -> handleSetupInput(inputType);
			case SHIP_PLACEMENT -> handleShipPlacementInput(inputType);
			case BATTLE -> handleBattleInput(inputType);
		};
	}

	private InputType handleShipPlacementInput(InputType input) {
		if(!game.getCurrentPhaseType().containsInputType(input)) {
			return null;
		}

		// Only convert CONFIRM to PLACE_SHIP if in placement phase
		if(input == InputType.CONFIRM) {
			return InputType.PLACE_SHIP;
		}

		return input;
	}

	private InputType handleBattleInput(InputType input) {
		if(!game.getCurrentPhaseType().containsInputType(input)) {
			return null;
		}

		// Convert CONFIRM to FIRE_SHOT in battle phase
		if(input == InputType.CONFIRM) {
			return InputType.FIRE_SHOT;
		}

		return input;
	}

	private InputType handleSetupInput(InputType input) {
		if(!game.getCurrentPhaseType().containsInputType(input)) {
			return null;
		}

		return input; // No conversions needed in setup
	}

	public void close() throws IOException {
		uiHandler.closeScreen();
		uiHandler.closeTerminal();
	}
}

package src.Enums;

import java.util.EnumSet;

import static src.Enums.InputType.*;

public enum GamePhaseType {
	SETUP(EnumSet.of(MENU_SELECT,CANCEL,CONFIRM)),
	SHIP_PLACEMENT(EnumSet.of(SELECT_CELL,PLACE_SHIP,CONFIRM,CANCEL)),
	BATTLE(EnumSet.of(SELECT_CELL,FIRE_SHOT,USE_CARD,CANCEL));

	private EnumSet<InputType> inputTypes;

	GamePhaseType(EnumSet<InputType> inputTypes) {
		this.inputTypes = inputTypes;
	}

	public boolean containsInputType(InputType inputType){
		return this.inputTypes.contains(inputType);
	}
}

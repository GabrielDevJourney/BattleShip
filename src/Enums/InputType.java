package src.Enums;

public enum InputType {
	/**
	 * 1. - Based on type of input being used i can control the flow of game better and not have as much bug neither
	 * so much code handle inputs from players, allowing me to based on phase of game only allow certain types of inputs
	 * 2. - So for example when is time for ship placement there will be no way to attack enemy if he has already placed
	 * his, or for example cards will only be allowed to be played if Use_Card type is current to be used
	 */
	MOVE_CURSOR,
	SELECT_CELL,
	PLACE_SHIP,
	FIRE_SHOT,
	USE_CARD,
	MENU_SELECT,
	CANCEL,
	CONFIRM;

}

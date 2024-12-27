package src.Game;

import src.Enums.GamePhaseType;
import src.Player.Player;

public class Game {
	private Player[] players = new Player[2];
	private GamePhaseType currentPhaseType;

	/*
	when creating players i need to pass the current game boats quantity to set alivedShipCounter
	 */
	/*
		Game creates ship instance
		GameValidator checks placement
		If valid, Ship.fillCoordinates() stores positions
		Player adds validated ship to their list
	 */

	public GamePhaseType getCurrentPhaseType() {
		return currentPhaseType;
	}
}

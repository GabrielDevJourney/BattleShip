package src.backend.game;

import src.backend.models.Board;
import src.backend.models.Player;

public class Turn {

	private Player attacker;
	private Player defender;
	private int turnCounter;


	public boolean isTurnEven() {
		return turnCounter % 2 == 0;
	}

	public void handleTurnPlayers() {
		if (isTurnEven()) {
			attacker = Game.getPlayers()[0];
			defender = Game.getPlayers()[1];
		} else {
			attacker = Game.getPlayers()[1];
			defender = Game.getPlayers()[0];
		}
	}

	public void attack(Board attackerBoard, Board defenderBoard) {
		//attackerBoard
	}
}

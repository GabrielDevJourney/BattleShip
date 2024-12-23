package src.Game;

import src.Player.Player;

public class Board {
	private int[][] gameBoardMatrix = new int[5][5];//? TODO 5x5 board later to be choosen by players
	private Player[] players = new Player[2];
	//board needs to have both its own size and players, then players have their ships which they store but are handle
	//by the board itself

	//*GAME FLOW
	//todo initialize board array with WATER from enum
	//todo get player ship placement
	//todo generate bot ship placement
	//todo validation for valid placement of players ships,this means pass both players ships array and check for any
	// overlap ships meaning all coordonates positions must be different
		//? comparing each ship array of coordonates? could i store each ship owns coordonate and then have a list or
		//? array to store all player ships positions



}

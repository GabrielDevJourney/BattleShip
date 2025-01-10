package src.backend.models;

import src.enums.BoardState;
import src.utils.Coordinate;

import java.util.List;

public class Board {
	private BoardState[][] board; // State of each coordinate
	private int boardSize;

	public Board(int boardSize) {
		this.board = new BoardState[boardSize][boardSize];
		this.boardSize = boardSize;
		initializeBoardState();
	}

	public int getBoardSize() {
		return boardSize;
	}

	private void initializeBoardState() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y] = BoardState.WATER; // Initialize all positions to WATER
			}
		}
	}

	public BoardState getCurrentState(Coordinate coordinate) {
		return board[coordinate.getRow()][coordinate.getCol()];
	}

	public void setNewState(Coordinate coordinate, BoardState newState) {
		board[coordinate.getRow()][coordinate.getCol()] = newState;
	}

	public boolean isTargetable(Coordinate coordinate) {
		return getCurrentState(coordinate) == BoardState.WATER; // Only targetable if it's WATER
	}

	public void placeShip(Coordinate[] shipCoordinates) {
		for (Coordinate coord : shipCoordinates) {
			board[coord.getRow()][coord.getCol()] = BoardState.SHIP; // Place ship on the board
		}
	}
}

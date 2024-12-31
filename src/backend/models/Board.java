package src.backend.models;

import src.enums.BoardState;
import src.utils.Coordinate;

public class Board {

	private String[][] board;

	public Board(int boardSize) {
		this.board = new String[boardSize][boardSize];
	}

	public String getCurrentState(Coordinate coordinate){
		return board[coordinate.getRow()][coordinate.getCol()];
	}


	public void setNewState(Coordinate coordinate,String newState) {
		board[coordinate.getRow()][coordinate.getCol()] = newState;
	}

	/**
	 * Initialize board state:
	 * 1. Iterate through all board positions and initialize water state
	 * Note: Using row[].length allows for future rectangular boards
	 */

	//todo might go back to previous code
	public void initializeBoardState(int row, int col) {
		for (int x = row; x < board.length; x++) {
			//going to all cols of given row
			for (int y = col; y < board[row].length; y++) {
				board[x][y] = BoardState.WATER.getBoardState();
			}
		}
	}

	/**
	 * Board state flow:
	 * 1. Coordinate is selected by player
	 * 2. Check if targetable (must be WATER)
	 * 3. Update state based on hit/miss
	 */
	public boolean isTargetable(Coordinate coordinate){
		String coordinateState = getCurrentState(coordinate);

		if(coordinateState.equals(BoardState.WATER.getBoardState())){
			return true;
		}else{
			System.out.println("Can't target this coordinate already hited!!");
			return false;
		}
	}

	public void updateBoardState(Coordinate coordinate, BoardState newState){
		if(isTargetable(coordinate)){
			setNewState(coordinate,newState.getBoardState());
		}
	}



}

package src.logic.game;

import src.enums.BoardState;

public class BoardService {
	private ShipService shipService
	private final int size;

	public BoardService(int size) {
		this.size = size;
		this.cells = new BoardState[size][size];
		initializeEmptyCells();
	}

	// Getters/Setters
	public int getSize() { return size; }
	public BoardState getCellState(int row, int col) { return cells[row][col]; }
	public void setCellState(int row, int col, BoardState state) { cells[row][col] = state; }

	private void initializeEmptyCells() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cells[i][j] = BoardState.WATER;
			}
		}
	}

}
package src.logic.models;

import src.enums.BoardState;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private final BoardState[][] cells;
	private final List<Ship> ships;
	private final int size;

	public Board(int size) {
		this.size = size;
		this.cells = new BoardState[size][size];
		this.ships = new ArrayList<>();
		initializeEmptyCells();
	}

	// Getters/Setters
	public int getSize() { return size; }
	public List<Ship> getShips() { return ships; }
	public void addShip(Ship ship) { ships.add(ship); }
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
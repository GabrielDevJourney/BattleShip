package src.utils;

import java.util.Objects;

public class Coordinate {
	private int row;
	private int col;
	private boolean isOccupied;

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result  = false;
		if(obj instanceof Coordinate coordinate){
			result = (getRow() == coordinate.getRow() && getCol() == coordinate.getCol());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}
}

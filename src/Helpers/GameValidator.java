package src.Helpers;

import java.util.ArrayList;

public class GameValidator {

	public boolean validateCoordinates(ArrayList<Coordinate> coordinates){
		//would want to check all single coordinates and see if all have same row or col
		//can have methods validateRows and validateCols
		return validateHorizontalPlacement(coordinates) || validateVerticalPlacement(coordinates);
	}

	private boolean validateHorizontalPlacement(ArrayList<Coordinate> coordinates){
		// Validates horizontal placement:
		// 1. All coordinates must share same row
		// 2. Columns must be consecutive (incrementing/decrementing by 1)
		boolean equalRows = coordinates.stream()
						.map(Coordinate::getRow)
						.distinct()
						.count() == 1;


		boolean consecutiveCols = coordinates.stream()
				.map(Coordinate::getCol)
				//check if col1 is same has col2 when ++ -- if yes then pass col2 to next operation if not break
				//because it means that coordinates will not be consecutive (1,2) (1,4) then ship
				.reduce((col1,col2) -> col1 + 1 == col2 || col1 - 1 == col2 ? col2 : col1)
				.isPresent();

		return equalRows && consecutiveCols;
	}

	private boolean validateVerticalPlacement(ArrayList<Coordinate> coordinates){
		// Validates vertical placement:
		// 1. All coordinates must share same col
		// 2. Rows must be consecutive (incrementing/decrementing by 1)
		boolean equalCols = coordinates.stream()
						.map(Coordinate::getCol)
						.distinct()
						.count() == 1;


		boolean consecutiveRows = coordinates.stream()
				.map(Coordinate::getRow)
				//check if row1 is same has row2 when ++ -- if yes then pass col2 to next operation if not break
				//because it means that coordinates will not be consecutive (1,2) (3,2) then not vertical placement
				.reduce((row1,row2) -> row1 + 1 == row2 || row1 - 1 == row2 ? row2 : row1)
				.isPresent();

		return equalCols && consecutiveRows;
	}
}

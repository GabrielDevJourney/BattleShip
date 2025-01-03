package src.ui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import src.enums.BoardState;
import src.utils.Coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BoardView extends GridPane {

	private final int boardSize;
	private Label[][] cells;  // For displaying cell states (water/ship/shadow)
	private Consumer<Coordinate> onCellClicked;

	public BoardView(int boardSize) {
		this.boardSize = boardSize;
		initializeGrid();
	}

	public int getBoardSize() {
		return boardSize;
	}

	private void initializeGrid() {
		//setup in placement screen the player board view to utilize label to be able to display proper emojis
		cells = new Label[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Label cell = new Label(BoardState.WATER.getBoardState());
				cell.getStyleClass().add("grid-cell");
				cells[i][j] = cell;
				add(cell, j, i);

				final int row = i;
				final int col = j;
				cell.setOnMouseClicked(event -> {
					if (onCellClicked != null) {
						onCellClicked.accept(new Coordinate(row, col));
					}
				});
			}
		}
	}

	public void displayAllShadowPlacements(List<List<Coordinate>> allPlacements) {
		clearShadows();
		allPlacements.forEach(placement -> {
			placement.forEach(coordinate -> {
				cells[coordinate.getRow()][coordinate.getCol()].getStyleClass().add("shadow-placement");

			});

		});
	}

	public void clearShadows() {
		Arrays.stream(cells).flatMap(Arrays::stream)
				.forEach(cell -> cell.getStyleClass().remove("shadow-placement"));
	}

	public void updateCellState(Coordinate coordinate, BoardState state) {
		cells[coordinate.getRow()][coordinate.getCol()].setText(state.getBoardState());
	}

	public void setOnCellClicked(Consumer<Coordinate> onCellClicked) {
		this.onCellClicked = onCellClicked;
	}
}

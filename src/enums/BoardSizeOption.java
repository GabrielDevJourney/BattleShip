package src.enums;

public enum BoardSizeOption {
	/*SMALL(15,5),
	MEDIUM(20,15),
	LARGE(25,20);

	private int boardWidth;
	private int boardHeight;

	BoardSizeOption(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}*/
	SIZE_10(10),
	SIZE_15(15),
	SIZE_20(20);

	private int boardSize;

	BoardSizeOption(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getBoardSize() {
		return boardSize;
	}
}

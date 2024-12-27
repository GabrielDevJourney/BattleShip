package src.Enums;

public enum ScreenSizeType {
	/*SMALL(15,5),
	MEDIUM(20,15),
	LARGE(25,20);

	private int boardWidth;
	private int boardHeight;

	ScreenSizeType(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}*/
	SMALL(10),
	MEDIUM(15),
	LARGE(20);

	private int boardSize;

	ScreenSizeType(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getBoardSize() {
		return boardSize;
	}
}

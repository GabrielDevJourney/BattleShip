package src.enums;

public enum BoardState {
	WATER("🌊"),//🌊
	MISS("💨"),//💨
	HIT("🎯"),//🎯
	SUNK("☠\uFE0F"),//☠️
	SHIP("🚢");//🚢

	private final String boardState;

	BoardState(String boardState) {
		this.boardState = boardState;
	}

	public String getBoardState() {
		return boardState;
	}
}

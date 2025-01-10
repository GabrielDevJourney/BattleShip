package src.enums;

public enum BoardState {
	WATER("🌊"),//🌊
	MISS("💨"),//💨
	HIT("🎯"),//🎯
	SUNK("\uD83E\uDEE0"),//🫠
	SHIP("🚢"),//🚢
	SHIPFUND("🔍");

	private final String boardState;

	BoardState(String boardState) {
		this.boardState = boardState;
	}

	public String getBoardState() {
		return boardState;
	}
}

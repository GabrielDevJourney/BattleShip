package src.Enums;

import src.Constants.GameSymbols;

public enum BoardState {
	WATER(GameSymbols.WATER_EMOJI),//🌊
	MISS(GameSymbols.MISS_EMOJI),//💨
	HIT(GameSymbols.HIT_EMOJI),//🎯
	SUNK(GameSymbols.SUNK_EMOJI),//☠️
	SHIP(GameSymbols.SHIP_EMOJI);//🚢

	private final String boardState;

	BoardState(String boardState) {
		this.boardState = boardState;
	}

	public String getBoardState() {
		return boardState;
	}
}

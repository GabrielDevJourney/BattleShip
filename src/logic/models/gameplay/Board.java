package src.logic.models.gameplay;

import src.enums.BoardState;

public class Board {
    private BoardState[][] boardState;
    private int size;

    public BoardState[][] getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardState[][] boardState) {
        this.boardState = boardState;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

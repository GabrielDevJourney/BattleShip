package src.Ship;

import src.Helpers.Coordinate;
import src.Enums.ShipType;

public abstract class Ship {
	protected int size;
	protected int hitCounter;
	protected boolean isSunk;
	protected Coordinate[] positions;
	protected ShipType type;

	public Ship(ShipType type) {
		this.type = type;
		this.size = type.getShipSize();
	}

	public int getSize() {
		return size;
	}

	public boolean isSunk() {
		return isSunk;
	}

	public Coordinate[] getPositions() {
		return positions;
	}

	public void occupyPositions(Coordinate[] coordinates){
		//todo call this in scanner to populate ship its owns position if they are valid, meaning i need to call this
		// to store coordinates only after checking if they are free
		//todo i will need to store both row and col
	}

	public void increaseHits(){
		hitCounter++;
	}

	//validate ship positions if true then cant place, false can place
	//also each coordinate obj will have both row and col so
	public boolean isPositionOccupied(int row, int col){

		Coordinate checkCoordinate = new Coordinate(row,col);

		for(Coordinate coordinate : positions){
			if(coordinate.equals(checkCoordinate)){
				return true;
			}
		}
		return false;
	}

}

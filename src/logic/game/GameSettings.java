package src.logic.game;

import src.enums.BoardSizeOption;
import src.enums.PlacementOption;
import src.enums.ShipQuantityOption;

public class GameSettings {
	private final BoardSizeOption boardSize;
	private final ShipQuantityOption shipQuantity;
	private final PlacementOption placementType;

	// Constructor
	public GameSettings(BoardSizeOption boardSize, ShipQuantityOption shipsQuantity, PlacementOption placement) {
		this.boardSize = boardSize;
		this.shipQuantity = shipsQuantity;
		this.placementType = placement;
	}

	// Getters
	public int getBoardSize() { return boardSize.getBoardSize(); }
	public int getShipQuantity() { return shipQuantity.getShipQuantity(); }
	public boolean isManualPlacement() { return placementType == PlacementOption.MANUAL; }
}

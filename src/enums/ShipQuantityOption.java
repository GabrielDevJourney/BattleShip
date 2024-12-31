package src.enums;

public enum ShipQuantityOption {
	SHIPS_10(10),
	SHIPS_15(15),
	SHIPS_20(20);

	private final int shipQuantity;

	ShipQuantityOption(int shipQuantity) {
		this.shipQuantity = shipQuantity;
	}

	public int getShipQuantity() {
		return shipQuantity;
	}
}

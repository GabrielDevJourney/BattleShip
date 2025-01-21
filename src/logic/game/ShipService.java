package src.logic.game;

import src.enums.ShipType;

import java.util.Map;

public class ShipService {
    private int shipQuantity;

    public ShipService(int shipQuantity) {
        this.shipQuantity = shipQuantity;
    }

    public int getShipQuantity() {
        return shipQuantity;
    }

    public void setShipQuantity(int shipQuantity) {
        this.shipQuantity = shipQuantity;
    }

    public Map<ShipType, Integer> getAvailableShips() {
        return availableShips;
    }
}

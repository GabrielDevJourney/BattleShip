package src.Enums;

public enum ShipType {
	CLOUDRULER("Cloud Ruler",5),//carrier
	CONVOYSHEPHER("Convoy Shepherd",4),//destroyer
	ABYSSALASSASIN("Abyssal Assassin",3),//submarine
	DAUNTLESSDEFENDER("Dauntless Defender",3),//cruiser
	SHELLFIREJUGGERNAUT("Shellfire Juggernaut",2);//battleship

	private final int shipSize;
	private final String name;

	ShipType(String name, int shipSize) {
		this.shipSize = shipSize;
		this.name = name;
	}

	public int getShipSize() {
		return shipSize;
	}

	public String getName() {
		return name;
	}
}

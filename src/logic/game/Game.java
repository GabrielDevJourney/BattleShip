package src.logic.game;

import src.enums.BoardState;
import src.enums.CardType;
import src.enums.ShipType;
import src.logic.models.cards.*;
import src.logic.models.ships.*;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {
	private static final int ROUNDS_UNTIL_CARD_BACK = 3;

	private int currentRound = 1;
	private boolean skipNextTurn = false;

	private PlayerService playerService;
	private BoardService boardService;
	private ShipService shipService;

	public Game(GameSettings settings) {
		this.playerService = new PlayerService();
		this.boardService = new BoardService(settings.getBoardSize());
		this.shipService = new ShipService(settings.getShipQuantity());

		initializeGame();
	}








	public Map<ShipType, Integer> getCurrentPlayerAvailableShips() {
		return availableShips;
	}




	private void initializeGame() {
		playerService.initializePlayers();
		//FIXME: ???
		initializeAvailableShips(settings.getShipQuantity());
		initializeCards();

		if (!settings.isManualPlacement()) {
			randomPlacement();
		}
	}



	private void initializeAvailableShips(int totalShips) {
		if (totalShips == 10) {
			setupShips(0);
		} else if (totalShips == 15) {
			setupShips(1);
		}
	}

	private void setupShips(int numberOfShips) {
		availableShips.put(ShipType.CLOUDRULER, 1 + numberOfShips);
		availableShips.put(ShipType.CONVOYSHEPHER, 2 + numberOfShips);
		availableShips.put(ShipType.ABYSSALASSASIN, 2 + numberOfShips);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 2 + numberOfShips);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 3 + numberOfShips);
	}




	public boolean placeShip(ShipType type, List<Coordinate> coordinates) {
		if (!isValidShipPlacement(type, coordinates)) {
			return false;
		}

		Ship ship = createShip(type);
		ship.setCoordinates(coordinates);

		BoardService board = currentPlayer.getBoard();
		board.addShip(ship);

		coordinates.forEach(c ->
				board.setCellState(c.getRow(), c.getCol(), BoardState.SHIP));

		availableShips.put(type, availableShips.get(type) - 1);
		return true;
	}

	private boolean isValidShipPlacement(ShipType type, List<Coordinate> coordinates) {
		if (availableShips.get(type) <= 0) return false;

		List<List<Coordinate>> existingShips = currentPlayer.getBoard().getShips().stream()
				.map(Ship::getCoordinates)
				.collect(Collectors.toList());

		return !ShipPlacementManager.isShipOverlapping(coordinates, existingShips) &&
				!ShipPlacementManager.isShipTooClose(coordinates, existingShips);
	}

	public boolean executeAttack(Coordinate coordinate) {
		BoardService defenderBoard = getOtherPlayer().getBoard();
		Ship targetShip = findShipAtCoordinate(coordinate, defenderBoard);
		BoardState currentState = defenderBoard.getCellState(coordinate.getRow(), coordinate.getCol());

		if (currentState == BoardState.SHIP || currentState == BoardState.SHIPFUND) {
			defenderBoard.setCellState(coordinate.getRow(), coordinate.getCol(), BoardState.HIT);
			targetShip.incrementHits();

			if (targetShip.isSunk()) {
				targetShip.getCoordinates().forEach(c ->
						defenderBoard.setCellState(c.getRow(), c.getCol(), BoardState.SUNK));
				getOtherPlayer().decrementShipsCount();
			}
			return true;
		} else if (currentState == BoardState.WATER) {
			defenderBoard.setCellState(coordinate.getRow(), coordinate.getCol(), BoardState.MISS);
			return false;
		}
		return false;
	}

	private Ship findShipAtCoordinate(Coordinate coordinate, BoardService board) {
		return board.getShips().stream()
				.filter(ship -> ship.getCoordinates().contains(coordinate))
				.findFirst()
				.orElse(null);
	}

	public boolean useCard(CardType cardType, Coordinate coordinate) {
		Card card = currentPlayer.getCard(cardType);

		isCardUsable(card, cardType);

		card.setAvailable(false);
		card.setLastRoundOfUsage(currentRound);
		card.executeCard(this, currentPlayer, getOtherPlayer(), coordinate);
		return true;
	}

	private boolean isCardUsable(Card card, CardType type) {
		// Check if card can be used (3 rounds passed)
		return (!card.isAvailable() || (card.getLastRoundOfUsage() != -1 &&
				currentRound - card.getLastRoundOfUsage() < ROUNDS_UNTIL_CARD_BACK));
	}

	public void handleSkipTurn() {
		skipNextTurn = true;
	}

	private void handleNextRound() {
		if (!skipNextTurn) {
			currentPlayerIndex = (currentPlayerIndex + 1) % 2;
			currentPlayer = players[currentPlayerIndex];
		} else {
			skipNextTurn = false;
		}
		currentRound++;
		checkCardsDealing();
	}

	public void switchToNextPlayerPlacement() {
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		currentPlayer = players[currentPlayerIndex];
		resetAvailableShips();
	}

	public void switchToNextPlayerBattle() {
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		currentPlayer = players[currentPlayerIndex];
	}

	private void resetAvailableShips() {
		initializeAvailableShips(shipService.getShipQuantity());
	}

	private void checkCardsDealing() {
		// Check each player's cards
		for (CardType type : CardType.values()) {
			Card card = currentPlayer.getCard(type);
			if (!card.isAvailable() &&
					currentRound - card.getLastRoundOfUsage() >= ROUNDS_UNTIL_CARD_BACK) {
				card.setAvailable(true);
			}
		}
	}

	private Card createCard(CardType type) {


	public Ship createShip(ShipType type) {
		return switch (type) {
			case CLOUDRULER -> new CloudRuler();
			case CONVOYSHEPHER -> new ConvoyShepherd();
			case ABYSSALASSASIN -> new AbyssalAssassin();
			case DAUNTLESSDEFENDER -> new DauntlessDefender();
			case SHELLFIREJUGGERNAUT -> new ShellfireJuggernaut();
		};
	}

	public void fillPlayerShips(Ship ship, PlayerService player) {
		player.getBoard().addShip(ship);
	}

	public void decrementShipCount(ShipType type) {
		availableShips.put(type, availableShips.get(type) - 1);
	}

	private void randomPlacement() {
		for (int i = 0; i <= 1; i++) {
			resetAvailableShips();

			Map<ShipType, Integer> playerShips = new HashMap<>(availableShips);
			for (ShipType shipType : ShipType.values()) {
				int shipCount = playerShips.getOrDefault(shipType, 0);
				for (int j = 0; j < shipCount; j++) {
					placeRandomShip(shipType);
				}
			}
		}
		currentPlayer = players[0];
		currentPlayerIndex = 0;
	}

	//TODO: is it possible to not conclude placement? maxAttempts = 100
	private boolean placeRandomShip(ShipType type) {
		int maxAttempts = 100;
		int attempts = 0;

		while (attempts < maxAttempts) {
			attempts++;
			int row = (int) (Math.random() * currentPlayer.getBoard().getSize());
			int col = (int) (Math.random() * currentPlayer.getBoard().getSize());

			List<List<Coordinate>> possiblePlacements =
					getPossiblePlacements(new Coordinate(row, col), type);

			if (possiblePlacements != null && !possiblePlacements.isEmpty()) {
				if (placeShip(type, possiblePlacements.get(0))) return true;
			}
		}
		return false;
	}
}

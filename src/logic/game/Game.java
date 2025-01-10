package src.logic.game;

import src.enums.BoardState;
import src.enums.CardType;
import src.enums.ShipType;
import src.logic.cards.CrossStrike;
import src.logic.cards.QuadShot;
import src.logic.cards.SkipTurn;
import src.logic.cards.Sonar;
import src.logic.models.Board;
import src.logic.models.Card;
import src.logic.models.Player;
import src.logic.models.Ship;
import src.logic.ships.*;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
	// Core game state
	private final GameSettings settings;
	private final Player[] players = new Player[2];
	private Player currentPlayer;
	private int currentPlayerIndex = 0;

	// Game progression
	private int currentRound = 1;
	private boolean skipNextTurn = false;
	private static final int ROUNDS_UNTIL_CARD_BACK = 3;

	// Resources management
	private Map<ShipType, Integer> availableShips = new HashMap<>();
	private Map<CardType, Integer> cardsAndRoundOfUsage = new HashMap<>();

	public Game(GameSettings settings) {
		this.settings = settings;
		initializeGame();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getOtherPlayer() {
		return currentPlayer == players[0] ? players[1] : players[0];
	}

	public Map<ShipType, Integer> getAvailableShips() {
		return availableShips;
	}

	public boolean isGameOver() {
		return players[0].hasLost() || players[1].hasLost();
	}

	public boolean hasNextPlayer() {
		return currentPlayerIndex < 1;
	}

	public String getCurrentPlayerName() {
		return currentPlayer.getName();
	}

	public int getBoardSize() {
		return settings.getBoardSize();
	}

	public List<List<Coordinate>> getPlacedShips() {
		return currentPlayer.getBoard().getShips().stream()
				.map(Ship::getCoordinates)
				.collect(Collectors.toList());
	}

	public Map<ShipType, Integer> getCurrentPlayerAvailableShips() {
		return availableShips;
	}

	public GameSettings getSettings() {
		return settings;
	}

	public List<List<Coordinate>> getPossiblePlacements(Coordinate start, ShipType type) {
		List<List<Coordinate>> existingShips = currentPlayer.getBoard().getShips().stream()
				.map(Ship::getCoordinates)
				.collect(Collectors.toList());

		return ShipPlacementManager.getPossiblePlacements(
				start,
				type.getShipSize(),
				getBoardSize(),
				existingShips
		);
	}

	private void initializeGame() {
		initializePlayers();
		initializeAvailableShips(settings.getShipQuantity());
		initializeCards();

		if (!settings.isManualPlacement()) {
			randomPlacement();
		}
	}

	private void initializePlayers() {
		players[0] = new Player("PLAYER1", settings.getBoardSize(), settings.getShipQuantity());
		players[1] = new Player("PLAYER2", settings.getBoardSize(), settings.getShipQuantity());
		currentPlayer = players[0];
	}

	private void initializeAvailableShips(int totalShips) {
		if (totalShips == 10) setupTenShips();
		else if (totalShips == 15) setupFifteenShips();
	}

	private void setupTenShips() {
		availableShips.put(ShipType.CLOUDRULER, 1);
		availableShips.put(ShipType.CONVOYSHEPHER, 2);
		availableShips.put(ShipType.ABYSSALASSASIN, 2);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 2);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 3);
	}

	private void setupFifteenShips() {
		availableShips.put(ShipType.CLOUDRULER, 1);
		availableShips.put(ShipType.CONVOYSHEPHER, 2);
		availableShips.put(ShipType.ABYSSALASSASIN, 2);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 2);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 3);
	}

	private void initializeCards() {
		for (Player player : players) {
			for (CardType type : CardType.values()) {
				Card card = createCard(type);
				card.setAvailable(true);
				player.addCard(type, card);
			}
		}
	}

	public boolean placeShip(ShipType type, List<Coordinate> coordinates) {
		if (!isValidShipPlacement(type, coordinates)) {
			return false;
		}

		Ship ship = createShip(type);
		ship.setCoordinates(coordinates);

		Board board = currentPlayer.getBoard();
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
		Board defenderBoard = getOtherPlayer().getBoard();
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

	private Ship findShipAtCoordinate(Coordinate coordinate, Board board) {
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
		initializeAvailableShips(settings.getShipQuantity());
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
		return switch (type) {
			case SONAR -> new Sonar();
			case QUAD_SHOT -> new QuadShot();
			case CROSS_STRIKE -> new CrossStrike();
			case SKIP_TURN -> new SkipTurn();
		};
	}

	public Ship createShip(ShipType type) {
		return switch (type) {
			case CLOUDRULER -> new CloudRuler();
			case CONVOYSHEPHER -> new ConvoyShepherd();
			case ABYSSALASSASIN -> new AbyssalAssassin();
			case DAUNTLESSDEFENDER -> new DauntlessDefender();
			case SHELLFIREJUGGERNAUT -> new ShellfireJuggernaut();
		};
	}

	public void fillPlayerShips(Ship ship, Player player) {
		player.getBoard().addShip(ship);
	}

	public void decrementShipCount(ShipType type) {
		availableShips.put(type, availableShips.get(type) - 1);
	}

	private void randomPlacement() {
		for (Player player : players) {
			currentPlayer = player;
			currentPlayerIndex = (player == players[0]) ? 0 : 1;
			resetAvailableShips();

			Map<ShipType, Integer> playerShips = new HashMap<>(availableShips);
			for (ShipType shipType : ShipType.values()) {
				int shipCount = playerShips.getOrDefault(shipType, 0);
				for (int i = 0; i < shipCount; i++) {
					placeRandomShip(shipType);
				}
			}
		}
		currentPlayer = players[0];
		currentPlayerIndex = 0;
	}

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

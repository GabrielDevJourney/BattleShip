package src.backend.game;

import src.backend.cards.CrossStrike;
import src.backend.cards.QuadShot;
import src.backend.cards.SkipTurn;
import src.backend.cards.Sonar;
import src.backend.models.Board;
import src.backend.models.Card;
import src.backend.models.Player;
import src.backend.models.Ship;
import src.backend.ships.*;
import src.enums.BoardState;
import src.enums.CardType;
import src.enums.ShipType;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.*;

public class Game {

	private Player[] players = new Player[2];
	private Player currentPlayer;
	private int currentPlayerIndex = 0;
	private final GameSettings gameSettings;
	private int boardSize;
	private int shipQuantity;
	private Map<ShipType, Integer> availableShips;
	private List<List<Coordinate>> placedShips = new ArrayList<>();
	private Board[] boards;
	private Map<CardType, Integer> cardsAndRoundOfUsage = new HashMap<>();
	private int currentRound = 1;
	private static final int ROUNDS_UNTIL_CARD_BACK = 3;
	private Turn currentTurn;
	private Board player1Board;
	private Board player2Board;


	public Game(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
		this.availableShips = new HashMap<>();
		this.boards = new Board[2];
		initializeGame();
	}

	public Map<ShipType, Integer> getAvailableShips() {
		return availableShips;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public List<List<Coordinate>> getPlacedShips() {
		return new ArrayList<>(placedShips);
	}

	public Board getCurrentPlayerBoard() {
		return boards[currentPlayerIndex];
	}

	public String getCurrentPlayerName() {
		return currentPlayer.getName();
	}

	public Player[] getPlayers() {
		return players;
	}


	public Board getAttackerBoard(Player attacker) {
		if (attacker == getPlayers()[0]) {
			return boards[0];
		} else {
			return boards[1];
		}
	}

	public Board getDefenderBoard(Player defender) {
		if (defender == getPlayers()[0]) {
			return boards[1];  // Opposite of attacker's board
		} else {
			return boards[0];
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Turn getCurrentTurn() {
		return currentTurn;
	}

	public boolean isEvenRound() {
		return currentRound % 2 == 0;
	}
	//*INITIALIZE GAME METHOD

	private void initializeGame() {
		this.boardSize = gameSettings.getBoardSize();
		this.shipQuantity = gameSettings.getShipQuantity();
		initializePlayers();
		initializeAvailableShips(shipQuantity);
		initializeBoards();
		initializeCards();
		startTurn();
	}

	private void initializePlayers() {
		players[0] = new Player("PLAYER1", shipQuantity);
		players[1] = new Player("PLAYER2", shipQuantity);

		currentPlayer = players[0];
	}

	private void initializeAvailableShips(int totalShips) {
		switch (totalShips) {
			case 10 -> setupTenShips();
			case 15 -> setupFifteenShips();
			case 20 -> setupTwentyShips();
		}
	}

	private void initializeBoards() {
		player1Board = new Board(boardSize);
		player2Board = new Board(boardSize);

		boards[0] = player1Board;
		boards[1] = player2Board;
	}

	private void initializeCards() {
		for (Player player : players) {
			Map<CardType, Card> playerCards = player.getCards();
			for (CardType type : CardType.values()) {
				Card card = createCard(type);  // Use your existing createCard method
				card.setAvailable(true);  // Make sure cards start available
				playerCards.put(type, card);
			}
		}
	}

	//*SETUP INITIAL AVAILABLE SHIPS METHODS

	private void setupTenShips() {
		availableShips.put(ShipType.CLOUDRULER, 1);
		availableShips.put(ShipType.CONVOYSHEPHER, 2);
		availableShips.put(ShipType.ABYSSALASSASIN, 2);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 2);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 3);
	}

	private void setupFifteenShips() {
		availableShips.put(ShipType.CLOUDRULER, 2);
		availableShips.put(ShipType.CONVOYSHEPHER, 3);
		availableShips.put(ShipType.ABYSSALASSASIN, 3);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 3);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 4);
	}

	private void setupTwentyShips() {
		availableShips.put(ShipType.CLOUDRULER, 3);
		availableShips.put(ShipType.CONVOYSHEPHER, 4);
		availableShips.put(ShipType.ABYSSALASSASIN, 4);
		availableShips.put(ShipType.DAUNTLESSDEFENDER, 4);
		availableShips.put(ShipType.SHELLFIREJUGGERNAUT, 5);
	}


	//*PLAYER SHIPS MANAGEMENT

	public void fillPlayerShips(Ship ship, Player player) {
		player.getPlayerShips().add(ship);
	}


	public Ship createShip(ShipType shipType) {
		return switch (shipType) {
			case CLOUDRULER -> new CloudRuler();
			case CONVOYSHEPHER -> new ConvoyShepherd();
			case ABYSSALASSASIN -> new AbyssalAssassin();
			case DAUNTLESSDEFENDER -> new DauntlessDefender();
			case SHELLFIREJUGGERNAUT -> new ShellfireJuggernaut();
		};
	}

	public void decrementShipCount(ShipType shipType) {
		int count = availableShips.get(shipType);
		if (count > 0) {
			availableShips.put(shipType, count - 1);
		}
	}


	public void placeShip(List<Coordinate> shipCoordinates) {
		placedShips.add(shipCoordinates);
	}

	//*HANDLE PLAYERS TURN FOR PLACEMENT
	public boolean hasNextPlayer() {
		return currentPlayerIndex < 1;
	}

	public void nextPlayer() {
		currentPlayerIndex++;
		currentPlayer = players[currentPlayerIndex];
		//initialize ships for next players since is a new player placing its own ships, so also clear placed ones so
		// next player will have a whole clear board and not having a clear board states but under the hood having
		// placed ships full from previous player
		placedShips.clear();
		availableShips.clear();
		initializeAvailableShips(shipQuantity);
	}


	//* HANDLE CARDS

	public boolean useCard(Player player, CardType type) {
		Card card = player.getCard(type);
		if (card != null && card.isAvailable()) {
			card.setAvailable(false);
			cardsAndRoundOfUsage.put(type, currentRound);
			return true;
		}
		return false;
	}

	public void checkCardsDealing() {
		//todo see better how does set works
		Set<CardType> cardTypes = new HashSet<>(cardsAndRoundOfUsage.keySet());
		for (CardType cardType : cardTypes) {
			int usedRound = cardsAndRoundOfUsage.get(cardType);
			if (currentRound == usedRound + ROUNDS_UNTIL_CARD_BACK) {
				currentPlayer.getCard(cardType).setAvailable(true);
				cardsAndRoundOfUsage.remove(cardType);
			}
		}
	}


	//* TURN LOGIC
	public void incrementRound() {
		currentRound++;
		checkCardsDealing();
	}

	public void startTurn() {
		Player enemy = (currentPlayer == players[0]) ? players[1] : players[0];
		currentTurn = new Turn(currentPlayer, enemy, boards);
	}

	public void executeAttack(Coordinate coordinateToAttack) {
		currentTurn.attack(currentTurn.getDefenderBoard(), coordinateToAttack);
		handleNextRound();
	}

	private void handleNextRound() {
		if (!currentTurn.isSkippedTurn()) {
			// Switch players
			currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
			currentPlayerIndex = (currentPlayerIndex == 0) ? 1 : 0;
		}
		incrementRound();
		startTurn();
	}

	private void handleSkipTurn() {
		if (currentTurn.isSkippedTurn()) {
			incrementRound();
			startTurn();
		}
	}

	public boolean useCardAndAttack(CardType cardType, Coordinate coordinate) {
		if (useCard(currentPlayer, cardType)) {
			Card card = createCard(cardType);
			executeCardEffect(card, coordinate);
			return true;
		}
		return false;
	}

	private void executeCardEffect(Card card, Coordinate coordinate) {
		card.executeCard(currentTurn, coordinate);
		handleNextRound();
	}

	private Card createCard(CardType cardType) {
		switch (cardType) {
			case SONAR -> {
				return new Sonar();
			}
			case QUAD_SHOT -> {
				return new QuadShot();
			}
			case CROSS_STRIKE -> {
				return new CrossStrike();
			}
			case SKIP_TURN -> {
				return new SkipTurn();
			}
		}
		return null;
	}


	// Add to Game class
	public void randomPlacement() {
		// For each player
		for (Player player : players) {
			currentPlayer = player;
			currentPlayerIndex = (player == players[0]) ? 0 : 1;
			Board playerBoard = boards[currentPlayerIndex];  // Get correct board

			// For each ship type and quantity
			for (Map.Entry<ShipType, Integer> entry : availableShips.entrySet()) {
				ShipType type = entry.getKey();
				int quantity = entry.getValue();

				// Place required quantity of each ship
				for (int i = 0; i < quantity; i++) {
					boolean placed = false;
					while (!placed) {
						int row = (int) (Math.random() * boardSize);
						int col = (int) (Math.random() * boardSize);
						Coordinate start = new Coordinate(row, col);

						List<List<Coordinate>> possiblePlacements =
								ShipPlacementManager.getPossiblePlacements(start, type.getShipSize(), boardSize, placedShips);

						if (possiblePlacements != null && !possiblePlacements.isEmpty()) {
							List<Coordinate> shipCoordinates = possiblePlacements.get(0);

							// Update board state
							for (Coordinate coord : shipCoordinates) {
								playerBoard.setNewState(coord, BoardState.SHIP);
							}

							// Create and setup ship
							Ship ship = createShip(type);
							ship.fillCoordinates(shipCoordinates);
							player.getPlayerShips().add(ship);

							placeShip(shipCoordinates);
							placed = true;
						}
					}
				}
			}
			placedShips.clear();
		}

		// Reset to first player
		currentPlayer = players[0];
		currentPlayerIndex = 0;
		startTurn();
	}
}

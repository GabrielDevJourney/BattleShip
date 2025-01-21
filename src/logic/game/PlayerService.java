package src.logic.game;

import src.enums.CardType;
import src.enums.ShipType;
import src.logic.models.cards.Card;
import src.logic.models.gameplay.Player;
import src.logic.models.ships.Ship;
import src.utils.Coordinate;
import src.utils.ShipPlacementManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerService {
    private Player[] players = new Player[2];
    //    private BoardService boardService;
    private int currentPlayer;

    public PlayerService() {
        players[0] = new Player("PLAYER1", settings.getBoardSize(), settings.getShipQuantity());
        players[1] = new Player("PLAYER2", settings.getBoardSize(), settings.getShipQuantity());
        currentPlayer = 0;
//        this.boardService = new BoardService(board);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

//    public src.logic.models.PlayerService getOtherPlayer() {
//        return currentPlayer == players[0] ? players[1] : players[0];
//    }

    public boolean isGameOver() {
        return players[0].isHasLost() || players[1].isHasLost();
    }

//    public boolean hasNextPlayer() {
//        return currentPlayerIndex < 1;
//    }

//    public String getCurrentPlayerName() {
//        return currentPlayer.getName();
//    }

    public List<List<Coordinate>> getPlacedShips() {
        return currentPlayer.getBoard().getShips().stream()
                .map(Ship::getCoordinates)
                .collect(Collectors.toList());
    }

    public List<List<Coordinate>> getPossiblePlacements(Coordinate start, ShipType type) {
        List<List<Coordinate>> existingShips = currentPlayer.getBoard().getShips().stream()
                .map(Ship::getCoordinates)
                .collect(Collectors.toList());

        return ShipPlacementManager.getPossiblePlacements(
                start,
                type.getShipSize(),
                existingShips
        );
    }

    private void initializeCards() {
        for (Player player : players) {
            for (CardType type : CardType.values()) {
                Card card = createCard(type); // enum
                card.setAvailable(true);
                player.setCards(Map.of(card.getType(), card));
            }
        }
    }
}

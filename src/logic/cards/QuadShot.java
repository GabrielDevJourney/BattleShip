package src.backend.cards;
import src.backend.game.Turn;
import src.backend.models.Board;
import src.backend.models.Card;

import static src.enums.CardType.QUAD_SHOT;

import src.utils.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuadShot extends Card {
	private static final int NUMBER_OF_SHOTS = 4;

	public QuadShot() {
		super(QUAD_SHOT);
	}

	@Override
	public void executeCard(Turn currentTurn, Coordinate coordinateCenter) {
		Board defenderBoard = currentTurn.getDefenderBoard();
		List<Coordinate> availableCoordinates = getAvailableCoordinates(defenderBoard);
		executeRandomAttacks(currentTurn, defenderBoard, availableCoordinates);
	}

	private List<Coordinate> getAvailableCoordinates(Board defenderBoard) {
		List<Coordinate> availableCoordinates = new ArrayList<>();
		int boardSize = defenderBoard.getBoardSize();

		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				Coordinate coordinate = new Coordinate(row, col);
				if (defenderBoard.isTargetable(coordinate)) {
					availableCoordinates.add(coordinate);
				}
			}
		}
		return availableCoordinates;
	}

	private void executeRandomAttacks(Turn currentTurn, Board defenderBoard, List<Coordinate> availableCoordinates) {
		//give 4 first coordiantes and then can check if they are 1 cell apart from hitted
		Collections.shuffle(availableCoordinates);
		Random random = new Random();
		//if there aren't enough coordinate to shoot at this means if we do 4 random shoots and only 2 avaible then
		// will be only shooting those 2 available
		int shotsToExecute = Math.min(NUMBER_OF_SHOTS, availableCoordinates.size());
		for (int i = 0; i < shotsToExecute; i++) {
			int index = random.nextInt(availableCoordinates.size());
			//remove coordinate so next iteration doesnt have it
			Coordinate target = availableCoordinates.remove(index);
			currentTurn.attack(defenderBoard, target);
		}
	}
}
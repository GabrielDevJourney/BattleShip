package src.UI;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import src.Constants.GameSymbols;
import src.Constants.ScreenSizes;
import src.Enums.ScreenSizeType;
import src.Game.Board;
import src.Helpers.Coordinate;

import java.awt.*;
import java.io.IOException;

public class UiHandler {
	/*
	Screen initialization
	Terminal configuration
	TextGraphics setup
	Screen refresh management
	Cleanup methods
	 */
	private DefaultTerminalFactory factory = new DefaultTerminalFactory();
	private Terminal terminal;
	private Screen screen;
	private TextGraphics screenWriter;
	private TerminalSize terminalSize = new TerminalSize(200,60);
	private Board board;

	public void init() throws IOException, InterruptedException {
		factory.setInitialTerminalSize(terminalSize);
		terminal = factory.createTerminal();
		screen = new TerminalScreen(terminal);
		screen.startScreen();
		screenWriter = screen.newTextGraphics();
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public Screen getScreen() {
		return screen;
	}   

	public TextGraphics getScreenWriter() {
		return screenWriter;
	}

	//*  METHODS
	public void closeTerminal() throws IOException {
		terminal.close();
	}

	public void closeScreen() throws  IOException{
		screen.close();
	}

	public void refreshScreen() throws IOException{
		//This method will take the content from the back-buffer and move it into the front-buffer
		// making the changes visible to the terminal in the process.
		screen.refresh();
	}

	public void clearScreen() throws  IOException {
		//Erases all the characters on the screen, effectively giving you a blank area.
		screen.clear();
	}

	/**
	 * this will be as generic as possible to draw boards based on the inital setup in menu phase
	 * so from type choosen it will be stored to be latter used to draw proper sized boardRectangles
	 */
	/*public void drawBoardRectangle(ScreenSizeType screenSizeType){
		int height = screenSizeType.getBoardHeight() + ScreenSizes.MARGIN;
		int width = screenSizeType.getBoardWidth() + ScreenSizes.MARGIN;

		TerminalPosition position = ScreenSizes.enemyBoardTopLeftPosition;

		TerminalSize size = new TerminalSize(width, height);
		TextCharacter character = new TextCharacter('*');
		TextCharacter borderChar = TextCharacter.fromCharacter('□')[0];

		screenWriter.drawRectangle(position,size,borderChar);
	}*/

	public void drawBoardRectangle(ScreenSizeType screenSizeType) {
		int boardSize = screenSizeType.getBoardSize();

		//todo might remove
		board = new Board(boardSize);

		int size = boardSize + ScreenSizes.MARGIN * 2; // Add margin on both sides

		TerminalPosition position = ScreenSizes.enemyBoardTopLeftPosition;
		TerminalSize rectangleSize = new TerminalSize(size * 2, size); // Double width for square cells

		TextCharacter borderChar = TextCharacter.fromCharacter('□')[0];

		screenWriter.drawRectangle(position, rectangleSize, borderChar);

		// Draw row labels (letters)
		for (int i = 0; i < boardSize; i++) {
			int col = position.getColumn() + 1;
			char label = (char) ('A' + i);
			screenWriter.setCharacter(col, position.getRow() + ScreenSizes.MARGIN + i, label);
		}

		// Draw column labels (numbers)
		for (int i = 0; i < boardSize; i++) {
			int row = position.getRow() + 1;
			String label = String.valueOf(i + 1);
			screenWriter.putString(position.getColumn() + ScreenSizes.MARGIN * 2 + i * 2, row, label);
		}

		int boardRow = position.getRow() + ScreenSizes.MARGIN;
		int boardCol = position.getColumn() + ScreenSizes.MARGIN * 2;
		board.initializeBoardState(0, 0);

		// Draw board state
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				int x = boardCol + col * 2;
				int y = boardRow + row;
				Coordinate coord = new Coordinate(row, col);
				String state = GameSymbols.WATER_EMOJI;

				if (state == null || state.isEmpty()) {
					state = "🌊"; // Use water emoji as default
				}
				screenWriter.putString(x, y, state);
			}
		}
	}


}

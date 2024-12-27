package src;

import src.Enums.ScreenSizeType;
import src.UI.UiHandler;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			UiHandler ui = new UiHandler();
			ui.init();

			// Test different board sizes
			/*
			ui.drawBoardRectangle(ScreenSizeType.SMALL);
			ui.refreshScreen();

			Thread.sleep(2000);

			ui.drawBoardRectangle(ScreenSizeType.MEDIUM);
			ui.refreshScreen();

			Thread.sleep(2000); // Pause to see result*/

			ui.drawBoardRectangle(ScreenSizeType.LARGE);
			ui.refreshScreen();

			Thread.sleep(2000);

			System.in.read();

			ui.closeScreen();
			ui.closeTerminal();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

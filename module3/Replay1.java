package module3;

import java.util.Scanner;
import ui.UserInterfaceFactory;
import ui.OthelloReplayUserInterface;
import ui.UIAuxiliaryMethods;

public class Replay1 {
	
	static final int ROW_INDEX = 1;
	static final char COLUMN_INDEX= 'a';

	OthelloReplayUserInterface ui;

	Replay1() {
		ui = UserInterfaceFactory.getOthelloReplayUI();
		UserInterfaceFactory.enableLowResolution(true);
	}

	void play(String player, int waitTime, int column, int row) {
		ui.wait(waitTime);
		
		if (player.equals("white")) {
			ui.place(column, row, ui.WHITE);
		} else {
			ui.place(column, row, ui.BLACK);
		}
		
		ui.showChanges();
	}
	
	void parseLine(Scanner lineScanner) {
		String player = lineScanner.next();
		int waitTime = lineScanner.nextInt();
		String move = lineScanner.next();
		
		if (move.equals("move")) {
			char col = lineScanner.next().charAt(0);
			int column = col - COLUMN_INDEX;
			int row = lineScanner.nextInt() - ROW_INDEX;
			
			play(player, waitTime, column, row);
		} else {
			ui.wait(waitTime);
		}
	}

	void startSetup() {
		ui.place(3, 3, ui.WHITE);
		ui.place(4, 4, ui.WHITE);
		ui.place(3, 4, ui.BLACK);
		ui.place(4, 3, ui.BLACK);
		ui.showChanges();
	}

	void start() {
		startSetup();
		
		Scanner fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
		
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			parseLine(lineScanner);
		}
	}

	public static void main(String[] argv) {
		new Replay1().start();
	}
}
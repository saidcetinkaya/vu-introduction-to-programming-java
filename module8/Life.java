package module8;

import java.util.Scanner;

import ui.LifeUserInterface;
import ui.UserInterfaceFactory;
import ui.UIAuxiliaryMethods;

public class Life {

	static final int COLUMN = 9;
	static final int ROW = 9;
	static final int WAIT_TIME = 500;
	static final int TWO = 2;
	static final int THREE = 3;
	static final char DEAD = ' ';
	static final char ALIVE = 'x';

	LifeUserInterface ui;
	Scanner fileScanner;
	Generations generations;
	Generation startGeneration;
	int maxGeneration;
	int maxRangeOfOscillation;

	Life() {
		ui = UserInterfaceFactory.getLifeUI(COLUMN, ROW);
		fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
		UserInterfaceFactory.enableLowResolution(true);
		generations = new Generations();
	}

	void printAll(Generations generations) {
		for (int i = 0; i < generations.numberOfGenerations; i++) {
			print(generations.generations[i]);
		}
	}

	void print(Generation generation) {
		ui.clear();
		ui.showChanges();
		ui.wait(WAIT_TIME);
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {

				if (generation.generation[i][j] == DEAD) {
					ui.place(j, i, ui.DEAD);
				} else {
					ui.place(j, i, ui.ALIVE);
				}
			}
		}

		ui.showChanges();
		ui.wait(WAIT_TIME);
	}

	boolean isAlive(int y, int x, int i, int j, Generation generation) {
		boolean isAlive = false;
		boolean notOutRow = y + i >= 0 && y + i < ROW;
		boolean notOutColumn = x + j >= 0 && x + j < COLUMN;
		boolean notItself = !(i == 0 && j == 0);

		if (notOutRow && notOutColumn && notItself) {
			isAlive = generation.generation[y + i][x + j] == ALIVE;
		}

		return isAlive;
	}

	int countAliveNeigbours(char cell, Generation generation, int y, int x) {
		int neighbour = 0;
		//Check every cells in 3x3 matrix except itself.
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (isAlive(y, x, i, j, generation)) {
					neighbour++;
				}
			}
		}
		return neighbour;
	}

	void createGenerations(Generation generation) {
		Generation nextGeneration = new Generation();

		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				char cell = generation.generation[i][j];
				int neighbour = countAliveNeigbours(cell, generation, i, j);

				if (cell == DEAD && neighbour == THREE) {
					nextGeneration.generation[i][j] = ALIVE;
				} else if (cell == 'x' && (neighbour == TWO || neighbour == THREE)) {
					nextGeneration.generation[i][j] = ALIVE;
				} else {
					nextGeneration.generation[i][j] = DEAD;
				}
			}
		}

		generations.add(nextGeneration);
	}

	boolean isMaxGeneration(int i) {
		if (i != maxGeneration) {
			return false;
		}
		ui.printf("The figure exceeds the maximum number of generations.");
		return true;
	}

	boolean isOscillator(int i) {
		Generation generation = generations.generations[i];
		//stops if maxRangeOfOscillation is exceeded or index equals to -1.
		for (int j = 1; j < maxRangeOfOscillation + 1 && i - j > -1; j++) {
			if (generation.compare(generations.generations[i - j])) {
				if (j == 1) {
					ui.printf("The figure has become a still figure.");
				} else {
					ui.printf("The figure has become an oscillator, its period is %d.", j);
				}
				return true;
			}
		}

		return false;
	}

	boolean isFigureDied(int i) {
		Generation generation = generations.generations[i];

		for (int j = 0; j < ROW; j++) {
			for (int k = 0; k < COLUMN; k++) {
				if (generation.generation[j][k] == 'x') {
					return false;
				}
			}
		}
		ui.printf("The figure has died.");
		return true;
	}

	boolean notStop(int i) {
		if (isFigureDied(i) || isOscillator(i) || isMaxGeneration(i)) {
			return false;
		}
		return true;
	}

	void game() {
		for (int i = 0; notStop(i); i++) {
			createGenerations(generations.generations[i]);
		}
	}

	Generation readScanner(Scanner file) {
		maxGeneration = file.nextInt();
		maxRangeOfOscillation = file.nextInt();
		file.nextLine();//skips \n
		Generation generation = new Generation();

		for (int i = 0; i < ROW; i++) {
			String line = file.nextLine();

			for (int j = 0; j < COLUMN; j++) {
				generation.generation[i][j] = line.charAt(j);
			}
		}

		ui.showChanges();
		return generation;
	}

	void start() {
		startGeneration = readScanner(fileScanner);
		generations.add(startGeneration);
		game();
		printAll(generations);
	}

	public static void main(String[] argv) {
		new Life().start();
	}
}
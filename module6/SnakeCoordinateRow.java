package module6;

import java.io.PrintStream;

public class SnakeCoordinateRow {

	static final int MAX_NUMBER_OF_COORDINATES = 1000;

	SnakeCoordinate[] snake;
	PrintStream out;
	int lengthOfSnake;

	SnakeCoordinateRow() {
		snake = new SnakeCoordinate[MAX_NUMBER_OF_COORDINATES];
		out = new PrintStream(System.out);
		lengthOfSnake = 0;
	}

	void add(SnakeCoordinate coordinate) {
		snake[lengthOfSnake] = coordinate;
		lengthOfSnake += 1;
	}

	void update(SnakeCoordinate head) {

		for (int i = lengthOfSnake - 1; 0 < i; i--) {
			snake[i] = snake[i - 1];
		}

		snake[0] = head;
	}
	
}
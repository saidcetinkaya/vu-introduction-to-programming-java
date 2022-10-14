package module6;

import java.util.Scanner;
import ui.Event;
import ui.SnakeUserInterface;
import ui.UserInterfaceFactory;
import ui.UIAuxiliaryMethods;

public class Snake {

	static final int COLUMN = 32;
	static final int ROW = 24;
	static final int MIN_BITE_LENGTH = 5;
	static final int MIN_WALL_AMOUNT = 1;
	static final String ARROW = "arrow";
	static final String RIGHT = "R";
	static final String LEFT = "L";
	static final String UP = "U";
	static final String DOWN = "D";
	static final String ALARM = "alarm";

	SnakeUserInterface ui;
	SnakeCoordinateRow snakeArr;
	SnakeCoordinateRow wall;
	SnakeCoordinate apple;
	String direction;
	Event event;
	double framesPerSecond;
	
	Snake() {
		ui = UserInterfaceFactory.getSnakeUI(COLUMN, ROW);
		UserInterfaceFactory.enableLowResolution(true);
		framesPerSecond = 10;
		direction = RIGHT;
		snakeArr = new SnakeCoordinateRow();
		wall = new SnakeCoordinateRow();
	}
	
	void restartGame() {
		clear();
		ui.showChanges();
		ui.setFramesPerSecond(0);
		
		direction = RIGHT;
		snakeArr = new SnakeCoordinateRow();
		wall = new SnakeCoordinateRow();

		ui.clearStatusBar();
		start();
	}
	
	void end() {
		ui.printf("Game Over");
		
		String question = "I want to play a game XD";
		boolean answer = UIAuxiliaryMethods.askUserForBoolean(question);
		
		if (answer) {
			restartGame();
		} else {
			System.exit(0);
		}
	}

	boolean eatApple() {
		boolean column = snakeArr.snake[0].column == apple.column;
		boolean row = snakeArr.snake[0].row == apple.row;
		boolean apple = column && row;

		return apple;
	}

	void refresh(SnakeCoordinate tail) {
		
		if (eatApple()) {
			apple = appleMaker();
			place();
			snakeArr.add(tail);
		} else {
			place();
		}
	}
	
	int keepCoordinateInRange(int coordinate, int range) {
		
		if (coordinate < 0) {
			coordinate %= range;
			coordinate += range;
		} else {
			coordinate %= range;
		}
		
		return coordinate;
	}
	
	SnakeCoordinate newHead() {
		SnakeCoordinate head;
		int headColumn = snakeArr.snake[0].column;
		int headRow = snakeArr.snake[0].row;

		if (direction.equals(LEFT)) {
			head = new SnakeCoordinate(keepCoordinateInRange(headColumn - 1, COLUMN), headRow);
		} else if (direction.equals(UP)) {
			head = new SnakeCoordinate(headColumn, keepCoordinateInRange(headRow - 1, ROW));
		} else if (direction.equals(DOWN)) {
			head = new SnakeCoordinate(headColumn, keepCoordinateInRange(headRow + 1, ROW));
		} else {
			head = new SnakeCoordinate(keepCoordinateInRange(headColumn + 1, COLUMN), headRow);
		}
		
		return head;
	}

	void playDirection() {
		SnakeCoordinate tail = snakeArr.snake[snakeArr.lengthOfSnake - 1];
		SnakeCoordinate head = newHead();
		
		snakeArr.update(head);
		refresh(tail);
	}

	String counterDirection() {
		
		if (direction.equals(LEFT)) {
			return RIGHT;
		} else if (direction.equals(RIGHT)) {
			return LEFT;
		} else if (direction.equals(UP)) {
			return DOWN;
		} else {
			return UP;
		}
	}

	void processEvent() {
		String data = event.data;

		if (!data.equals(counterDirection())) {
			direction = data;
		}
	}

	boolean checkObject(SnakeCoordinateRow object, int length){
		
		if (object.lengthOfSnake < length) {
			return true;
		} 
		
		boolean column;
		boolean row;
		
		for (int i = length - 1; i < object.lengthOfSnake; i++) {
			column = snakeArr.snake[0].column == object.snake[i].column;
			row = snakeArr.snake[0].row == object.snake[i].row;
			
			if (column && row) {
				return false;
			}
		}
		
		return true;
	}
	
	boolean notWall() {
		return checkObject(wall, MIN_WALL_AMOUNT);
	}

	boolean selfBite() {
		return checkObject(snakeArr, MIN_BITE_LENGTH);
	}

	void play() {
		ui.setFramesPerSecond(framesPerSecond);
		Event formerEvent = null;

		while (selfBite() && notWall()) {
			event = ui.getEvent();

			if (event.name.equals(ARROW) && formerEvent.name.equals(ALARM)) {
				processEvent();
			} else if (event.name.equals(ALARM)) {
				playDirection();
			}
			formerEvent = event;
		}
		
		end();
	}

	void placeObject(SnakeCoordinateRow object, int snakeWall) {
		int column;
		int row;

		for (int i = 0; i < object.lengthOfSnake; i++) {
			column = object.snake[i].column;
			row = object.snake[i].row;
			ui.place(column, row, snakeWall);
		}
	}
	
	void clear() {
		for (int row = 0; row < ROW; row++) {
			for (int column = 0; column < COLUMN; column++) {
				ui.place(column, row, ui.EMPTY);
			}
		}
	}

	void place() {
		clear();
		placeObject(wall, ui.WALL);
		placeObject(snakeArr, ui.SNAKE);
		ui.place(apple.column, apple.row, ui.FOOD);
		ui.showChanges();
	}

	boolean appleOnObject(SnakeCoordinate apple, SnakeCoordinateRow object) {
		boolean column;
		boolean row;

		for (int i = 0; i < object.lengthOfSnake; i++) {
			column = object.snake[i].column == apple.column;
			row = object.snake[i].row == apple.row;
			if (column && row) {
				return true;
			}
		}

		return false;
	}

	SnakeCoordinate appleMaker() {
		int appleColumn = UIAuxiliaryMethods.getRandom(0, COLUMN);
		int appleRow = UIAuxiliaryMethods.getRandom(0, ROW);
		SnakeCoordinate result = new SnakeCoordinate(appleColumn, appleRow);

		if (appleOnObject(result, wall) || appleOnObject(result, snakeArr)) {
			result = appleMaker();
		}

		return result;
	}

	void defaultSetup() {
		SnakeCoordinate head = new SnakeCoordinate(0, 0);
		SnakeCoordinate tail = new SnakeCoordinate(0, 1);
		
		snakeArr.add(head);
		snakeArr.add(tail);
		apple = appleMaker();
		place();
	}
	
	SnakeCoordinateRow readScanner(Scanner objectScanner) {
		int column;
		int row;
		SnakeCoordinate objectPart;
		SnakeCoordinateRow result = new SnakeCoordinateRow();
		
		while(objectScanner.hasNext()) {
			column = objectScanner.nextInt();
			row = objectScanner.nextInt();
			objectPart = new SnakeCoordinate(column, row);
			result.add(objectPart);
		}
		return result;
	}
	
	void levelSetup(Scanner snakeScanner, Scanner wallScanner) {
		snakeArr = readScanner(snakeScanner);
		wall = readScanner(wallScanner);
		apple = appleMaker();
		place();
	}

	void playWithLevel(Scanner fileScanner){
		fileScanner.useDelimiter("=");
		
		String snakeStart = fileScanner.next();
		direction = fileScanner.next();
		String wall = fileScanner.next();
		Scanner snakeScanner = new Scanner(snakeStart);
		Scanner wallScanner = new Scanner(wall);
		
		levelSetup(snakeScanner, wallScanner);
		play();
	}

	void start() {
		String question = "Do you want to play with level";
		boolean answer = UIAuxiliaryMethods.askUserForBoolean(question);
		
		if (answer) {
			Scanner fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
			playWithLevel(fileScanner);
		} else {
			defaultSetup();
			play();
		}
	}

	public static void main(String[] argv) {
		new Snake().start();
	}
}
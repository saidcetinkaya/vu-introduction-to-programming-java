package module7;

import java.util.Scanner;

import ui.LabyrinthUserInterface;
import ui.UserInterfaceFactory;
import ui.UIAuxiliaryMethods;

public class LongestPath {

	static final int COLUMN = 32;
	static final int ROW = 24;
	static final int WAIT_TIME = 10;

	LabyrinthUserInterface ui;
	Coordinate start;
	Coordinate finish;
	CoordinateRow path;
	CoordinateRow wall;
	Scanner fileScanner;
	Paths paths;
	int numberOfIteration;

	LongestPath() {
		ui = UserInterfaceFactory.getLabyrinthUI(COLUMN, ROW);
		fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
		UserInterfaceFactory.enableLowResolution(true);
		path = new CoordinateRow();
		wall = new CoordinateRow();
		paths = new Paths();
		numberOfIteration=0;
	}

	void placeObject(CoordinateRow object, int snakeWall) {
		int column;
		int row;

		for (int i = 0; i < object.lengthOfPath; i++) {
			column = object.path[i].column;
			row = object.path[i].row;
			ui.place(column, row, snakeWall);
		}
	}

	boolean isFinish(Coordinate last) {
		boolean column = last.column == finish.column;
		boolean row = last.row == finish.row;
		return column && row;
	}
	
	void playRest(CoordinateRow path, int colModifier, int rowModifier) {
		int index = path.lengthOfPath - 1;
		int nextColumn = path.path[index].column + colModifier;
		int nextRow = path.path[index].row + rowModifier;
		Coordinate next = new Coordinate(nextColumn, nextRow);
		CoordinateRow newPath = new CoordinateRow();
		
		newPath.copy(path);
		newPath.add(next);
		place(newPath);//next point
		
		if (isFinish(next)) {
			paths.add(newPath);// then add to paths. but not path.
		}
		play(newPath);//new possibilities
		place(path);//trace back
		ui.wait(WAIT_TIME);//slow down trace back
	}

	void playUp(CoordinateRow path, int column, int row) {
		playRest(path, 0, -1); //Play Up
		if (isOnObject(column, row, path, -1, 0) && isOnObject(column, row, wall, -1, 0) && isInRange(column, row, -1, 0)) {
			playRest(path, -1, 0); //Play Left
		} else if (isOnObject(column, row, path, 0, 1) && isOnObject(column, row, wall, 0, 1) && isInRange(column, row, 0, 1)) {
			playRest(path, 0, 1); //Play Down
		} else if (isOnObject(column, row, path, 1, 0) && isOnObject(column, row, wall, 1, 0) && isInRange(column, row, 1, 0)) {
			playRest(path, 1, 0); //Play Right
		}
	}

	void playRight(CoordinateRow path, int column, int row) {
		playRest(path, 1, 0); //Play Right
		if (isOnObject(column, row, path, -1, 0) && isOnObject(column, row, wall, -1, 0) && isInRange(column, row, -1, 0)) {
			playRest(path, -1, 0); //Play Left
		} else if (isOnObject(column, row, path, 0, 1) && isOnObject(column, row, wall, 0, 1) && isInRange(column, row, 0, 1)) {
			playRest(path, 0, 1); //Play Down
		}else if (isOnObject(column, row, path, 0, -1) && isOnObject(column, row, wall, 0, -1) && isInRange(column, row, 0, -1)){
			playRest(path, 0, -1); //Play Up
		}
	}

	void playDown(CoordinateRow path, int column, int row) {
		playRest(path, 0, 1); //Play Down
		if (isOnObject(column, row, path, -1, 0) && isOnObject(column, row, wall, -1, 0) && isInRange(column, row, -1, 0)) {
			playRest(path, -1, 0); //Play Left
		} else if (isOnObject(column, row, path, 1, 0) && isOnObject(column, row, wall, 1, 0) && isInRange(column, row, 1, 0)) {
			playRest(path, 1, 0); //Play Right
		} else if (isOnObject(column, row, path, 0, -1) && isOnObject(column, row, wall, 0, -1) && isInRange(column, row, 0, -1)){
			playRest(path, 0, -1); //Play Up
		}
	}

	void playLeft(CoordinateRow path, int column, int row) {
		playRest(path, -1, 0); //Play Left
		if (isOnObject(column, row, path, 0, 1) && isOnObject(column, row, wall, 0, 1) && isInRange(column, row, 0, 1)) {
			playRest(path, 0, 1); //Play Down
		} else if (isOnObject(column, row, path, 1, 0) && isOnObject(column, row, wall, 1, 0) && isInRange(column, row, 1, 0)) {
			playRest(path, 1, 0); //Play Right
		} else if (isOnObject(column, row, path, 0, -1) && isOnObject(column, row, wall, 0, -1) && isInRange(column, row, 0, -1)){
			playRest(path, 0, -1); //Play Up
		}
	}
	
	boolean isInRange(int column, int row, int colModifier, int rowModifier) {
		boolean col = column + colModifier < COLUMN && column + colModifier >= 0;
		boolean ro = row + rowModifier < ROW && row + rowModifier >= 0;

		return col && ro;
	}

	boolean isOnObject(int column, int row, CoordinateRow object, int colModifier, int rowModifier) {
		boolean col;
		boolean ro;

		for (int i = 0; i < object.lengthOfPath; i++) {
			col = object.path[i].column == column + colModifier;
			ro = object.path[i].row == row + rowModifier;
			if (col && ro) {
				return false;
			}
		}

		return true;
	}

	void playDirection(CoordinateRow path) {
		int index = path.lengthOfPath - 1;
		int column = path.path[index].column;//Last coordinates of path
		int row = path.path[index].row;
		
		if (isOnObject(column, row, path, -1, 0) && isOnObject(column, row, wall, -1, 0) && isInRange(column, row, -1, 0)) {
			playLeft(path, column, row);
		} else if (isOnObject(column, row, path, 0, 1) && isOnObject(column, row, wall, 0, 1) && isInRange(column, row, 0, 1)) {
			playDown(path, column, row);
		} else if (isOnObject(column, row, path, 1, 0) && isOnObject(column, row, wall, 1, 0) && isInRange(column, row, 1, 0)) {
			playRight(path, column, row);
		} else if (isOnObject(column, row, path, 0, -1) && isOnObject(column, row, wall, 0, -1) && isInRange(column, row, 0, -1)){
			playUp(path, column, row);
		}
		return;//start of trace back
	}

	void place(CoordinateRow path) {
		ui.clear();
		placeObject(wall, ui.WALL);
		placeObject(path, ui.PATH);
		ui.encircle(finish.column, finish.row);
		ui.encircle(start.column, start.row);
		ui.showChanges();
	}

	void play(CoordinateRow path) {
		ui.wait(WAIT_TIME);
		place(path);
		playDirection(path);
		numberOfIteration++;
	}

	CoordinateRow readRow(Scanner objectScanner) {
		int column;
		int row;
		Coordinate objectPart;
		CoordinateRow result = new CoordinateRow();

		while (objectScanner.hasNext()) {
			column = objectScanner.nextInt();
			row = objectScanner.nextInt();
			objectPart = new Coordinate(column, row);
			result.add(objectPart);
		}
		return result;
	}

	CoordinateRow readScanner(Scanner file) {
		file.useDelimiter("=");
		Scanner startScanner = new Scanner(file.next());
		Scanner finishScanner = new Scanner(file.next());
		Scanner wallScanner = new Scanner(file.next());
		CoordinateRow path = readRow(startScanner);
		
		start = new Coordinate(path.path[0].column, path.path[0].row);
		finish = new Coordinate(finishScanner.nextInt(), finishScanner.nextInt());
		wall = readRow(wallScanner);
		
		return path;
	}

	void start() {
		CoordinateRow startPath = readScanner(fileScanner);
		play(startPath);
		path = paths.longest();
		place(path);
		ui.printf("Length %d, iteration %d", path.lengthOfPath, numberOfIteration);
	}

	public static void main(String[] argv) {
		new LongestPath().start();
	}
}
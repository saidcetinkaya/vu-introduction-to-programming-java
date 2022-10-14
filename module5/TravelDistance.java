package module5;

import java.io.PrintStream;
import java.util.Scanner;
import ui.UIAuxiliaryMethods;

public class TravelDistance {

	Scanner fileScanner;
	PrintStream out;

	TravelDistance() {
		fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
		out = new PrintStream(System.out);
	}
	
	void print(CoordinateRow row) {
		int x;
		int y;
		
		out.printf("The correct route is:\n");

		for(int i = 0; i < row.numberOfCoordinates; i++) {
			x = row.travelPoints[i].x;
			y = row.travelPoints[i].y;
			out.printf("%d,%d\n", x, y);
		}
		
		out.printf("\nThe total distance is %.02f", row.distance());
	}

	Coordinate readCoordinates(Scanner coordinate) {
		coordinate.useDelimiter(",");

		int coordinateX = coordinate.nextInt();
		int coordinateY = coordinate.nextInt();
		
		return new Coordinate(coordinateX, coordinateY);
	}

	CoordinateRow readScanner(Scanner coordinate) {
		CoordinateRow result = new CoordinateRow();
		Scanner coordinateScanner;
		Coordinate next;
		boolean isBackOrFront = false;

		while (coordinate.hasNext()) {
			coordinateScanner = new Scanner(coordinate.next());
			next = readCoordinates(coordinateScanner);

			if (isBackOrFront) {
				result.addToFront(next);
			} else {
				result.addToBack(next);
			}
			
			isBackOrFront = coordinate.hasNext() ? coordinate.next().equals("f") : false;
		}

		return result;
	}

	void start() {
		CoordinateRow route = readScanner(fileScanner);

		print(route);
	}

	public static void main(String[] argv) {
		new TravelDistance().start();
	}
}
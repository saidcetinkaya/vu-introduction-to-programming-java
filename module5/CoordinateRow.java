package module5;

public class CoordinateRow {

	static final int MAX_NUMBER_OF_COORDINATES = 400;

	Coordinate[] travelPoints;
	int numberOfCoordinates;

	CoordinateRow() {
		travelPoints = new Coordinate[MAX_NUMBER_OF_COORDINATES];
		numberOfCoordinates = 0;
	}

	void addToBack(Coordinate coordinate) {
		travelPoints[numberOfCoordinates] = coordinate;
		numberOfCoordinates += 1;
	}

	void addToFront(Coordinate coordinate) {
		for (int i = numberOfCoordinates; 0 < i; i--) {
			travelPoints[i] = travelPoints[i - 1];
		}

		travelPoints[0] = coordinate;
		numberOfCoordinates += 1;
	}

	double distance() {
		double result = 0;

		for (int i = 0; i + 1 < numberOfCoordinates; i++) {
			double run = Math.pow((travelPoints[i].x - travelPoints[i + 1].x), 2);
			double rise = Math.pow((travelPoints[i].y - travelPoints[i + 1].y), 2);

			result += Math.sqrt(run + rise);
		}

		return result;
	}
}
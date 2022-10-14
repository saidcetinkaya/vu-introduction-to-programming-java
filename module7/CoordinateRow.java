package module7;

public class CoordinateRow {

	static final int MAX_NUMBER_OF_COORDINATES = 1000;

	Coordinate[] path;
	int lengthOfPath;

	CoordinateRow() {
		path = new Coordinate[MAX_NUMBER_OF_COORDINATES];
		lengthOfPath = 0;
	}

	void add(Coordinate coordinate) {
		path[lengthOfPath] = coordinate;
		lengthOfPath++;
	}
	
	void copy(CoordinateRow object) {
		Coordinate copy;
		for (int i = 0; i < object.lengthOfPath; i++) {
			copy = new Coordinate(object.path[i].column, object.path[i].row);
			add(copy);
		}
	}
	
}
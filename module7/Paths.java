package module7;

public class Paths {

	static final int MAX_NUMBER_OF_COORDINATES = 1000;

	CoordinateRow[] paths;
	int numberOfPaths;

	Paths() {
		paths = new CoordinateRow[MAX_NUMBER_OF_COORDINATES];
		numberOfPaths = 0;
	}

	void add(CoordinateRow path) {
		paths[numberOfPaths] = path;
		numberOfPaths++;
	}

	CoordinateRow longest() {
		CoordinateRow result = paths[0];
		
		for (int i = 1; i < numberOfPaths; i++) {
			if(result.lengthOfPath < paths[i].lengthOfPath) {
				result = paths[i];
			}
		}
		
		return result;
	}
}
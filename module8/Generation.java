package module8;

public class Generation {

	static final int MAX_NUMBER_OF_ROW = 9;
	static final int MAX_NUMBER_OF_COLUMN = 9;

	char[][] generation;

	Generation() {
		generation = new char[MAX_NUMBER_OF_ROW][MAX_NUMBER_OF_COLUMN];
	}
	
	boolean compare(Generation otherGeneration) {
		for(int i = 0; i < MAX_NUMBER_OF_ROW; i++) {
			for(int j = 0; j < MAX_NUMBER_OF_ROW; j++) {
				if(otherGeneration.generation[i][j] != generation[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
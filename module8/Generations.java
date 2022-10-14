package module8;

public class Generations {

	static final int MAX_NUMBER_OF_GENERATIONS = 1000;

	Generation[] generations;
	int numberOfGenerations;

	Generations() {
		generations = new Generation[MAX_NUMBER_OF_GENERATIONS];
		numberOfGenerations = 0;
	}

	void add(Generation generation) {
		generations[numberOfGenerations] = generation;
		numberOfGenerations++;
	}
}
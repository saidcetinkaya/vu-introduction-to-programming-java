package module4;

import java.io.PrintStream;
import java.util.Scanner;
import ui.UIAuxiliaryMethods;

public class Administration {

	PrintStream out;
	Scanner in;

	Administration() {
		out = new PrintStream(System.out);
		in = new Scanner(System.in);
	}
	
	void checkNamePrint(String names) {
		if (names != null) {
			in = new Scanner(names);
			in.useDelimiter(",");
			
			while (in.hasNext()) {
				out.printf("\t%s\n", in.next());
			}
		} else {
			out.printf("\tNo matches found\n");
		}
	}
	
	void printSimilarScores(Scanner similarityScoresScanner, String names) {
		similarityScoresScanner.useDelimiter("=");
		
		int matchRate;
		
		out.printf("\t");
		
		while (similarityScoresScanner.hasNext()) {
			matchRate = similarityScoresScanner.nextInt();
			
			out.printf("%s", matchRate == 0 ? "_" : matchRate < 20 ? "-" : "^");
		}
		
		out.printf("\n");
		
		checkNamePrint(names);
	}

	void similarScores(Scanner secondLineScanner) {
		secondLineScanner.useDelimiter(";");
		
		String similarityScores = secondLineScanner.next();
		String names = secondLineScanner.hasNext() ? secondLineScanner.next() : null;
		
		Scanner similarityScoresScanner = new Scanner(similarityScores);
		
		printSimilarScores(similarityScoresScanner, names);
	}
	
	void checkScorePrintGpa(String name, double sumOfScores, int numberOfScores) {
		double average = sumOfScores / numberOfScores;
		if (average >= 5.5 && average < 6.0) {
			out.printf("%s has an average of 6-\n", name);
		} else if (average % 1 >= 0.25 && average % 1 < 0.75) {
			average = (int) sumOfScores / numberOfScores + 0.5;
			out.printf("%s has an average of %.01f\n", name, average);
		} else if ((sumOfScores / numberOfScores) % 1 >= 0.75) {
			average = (int) sumOfScores / numberOfScores + 1;
			out.printf("%s has an average of %.01f\n", name, average);
		} else {
			average = (int) sumOfScores / numberOfScores;
			out.printf("%s has an average of %.01f\n", name, average);
		}
	}

	void printNameGpa(String name, Scanner scoreScanner) {
		scoreScanner.useDelimiter(" ");

		int numberOfScores = 0;
		double sumOfScores = 0;

		while (scoreScanner.hasNext()) {
			sumOfScores += scoreScanner.nextInt();
			numberOfScores++;
		}
		
		checkScorePrintGpa(name, sumOfScores, numberOfScores);
	}

	void studentGpa(Scanner firstLineScanner) {
		firstLineScanner.useDelimiter("_");

		String name = firstLineScanner.next();
		String scores = firstLineScanner.next();

		Scanner scoreScanner = new Scanner(scores);

		printNameGpa(name, scoreScanner);
	}

	void start() {
		Scanner fileScanner = UIAuxiliaryMethods.askUserForInput().getScanner();
		String firstLine;
		String secondLine;
		
		while (fileScanner.hasNext()) {
			firstLine = fileScanner.nextLine();
			secondLine = fileScanner.nextLine();
			
			Scanner firstLineScanner = new Scanner(firstLine);
			Scanner secondLineScanner = new Scanner(secondLine);
			
			studentGpa(firstLineScanner);
			similarScores(secondLineScanner);
		}
	}

	public static void main(String[] argv) {
		new Administration().start();
	}
}
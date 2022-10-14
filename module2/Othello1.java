package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Othello1 {

	static final int SQUARES = 64;

	Scanner in;
	PrintStream out;

	Othello1() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter the number of white pieces on the board: ");
		int white = in.nextInt();
		out.printf("Enter the number of black pieces on the board: ");
		int black = in.nextInt();

		double blackTablePercentage = (double) black / SQUARES * 100;
		double blackWhitePercentage = (double) black / (black + white) * 100;
		out.printf(
				"The percentage of black pieces on the board is: "
						+ "%.02f%%\nThe percentage of black pieces of all the " + "pieces on the board is:  %.02f%%",
				blackTablePercentage, blackWhitePercentage);
	}

	public static void main(String[] argv) {
		new Othello1().start();
	}

}
package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Othello2 {

	static final int COMPUTER_THINKING_TIME = 1000;

	Scanner in;
	PrintStream out;

	Othello2() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter the time the black player thought: ");
		int blackTime = in.nextInt();
		out.printf("Enter the time the white player thought: ");
		int whiteTime = in.nextInt();
		int result;
		if (blackTime <= COMPUTER_THINKING_TIME) {
			result = whiteTime / 1000;
		} else {
			result = blackTime / 1000;
		}
		int hours = result / 3600;
		int minutes = (result % 3600) / 60;
		int seconds = result % 60;
		out.printf("The time the human player has spent thinking is: " + "%02d:%02d:%02d.", hours, minutes, seconds);
	}

	public static void main(String[] argv) {
		new Othello2().start();
	}

}
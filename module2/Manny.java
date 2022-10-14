package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Manny {

	static final int MIN_DONATION_AMOUNT = 50;

	Scanner in;
	PrintStream out;

	Manny() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		double donate;
		do {
			out.printf("Enter the amount you want to donate: ");
			donate = in.nextDouble();
		} while (donate < MIN_DONATION_AMOUNT);
		out.printf("Thank you very much for your contribution of %.02f euro.", donate);
	}

	public static void main(String[] argv) {
		new Manny().start();
	}
}
package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class VAT {

	static final double VAT_RATE = 1.21;

	Scanner in;
	PrintStream out;

	VAT() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter the price of an article including VAT: ");
		double price = in.nextDouble() / VAT_RATE;
		out.printf("This article will cost %.02f euro without %.2f%% " + "VAT.", price, (VAT_RATE - 1) * 110);
	}

	public static void main(String[] args) {
		new VAT().start();
	}

}
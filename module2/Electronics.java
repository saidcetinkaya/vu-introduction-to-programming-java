package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Electronics {

	static final double DISCOUNT_RATE = 15.0 / 100;

	Scanner in;
	PrintStream out;

	Electronics() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter the price of the first article: ");
		int first = in.nextInt();
		out.printf("Enter the price of the second article: ");
		int second = in.nextInt();
		out.printf("Enter the price of the third article: ");
		int third = in.nextInt();
		int max;
		if (first >= second && first >= third) {
			max = first;
		} else if (second >= third) {
			max = second;
		} else {
			max = third;
		}
		double discount = max * DISCOUNT_RATE;
		double total = first + second + third - discount;
		out.printf("Discount: %.02f\n", discount);
		out.printf("Discount: %.02f", total);
	}

	public static void main(String[] argv) {
		new Electronics().start();
	}

}
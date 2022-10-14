package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class SecondSmallest {

	Scanner in;
	PrintStream out;

	SecondSmallest() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter three or more numbers: ");
		int possibility1 = in.nextInt();
		int possibility2 = in.nextInt();
		int nextNumber;
		while (in.hasNext()) {
			nextNumber = in.nextInt();
			if (possibility1 > possibility2 && possibility2 > nextNumber) {
				possibility1 = nextNumber;
			} else if (possibility1 < possibility2 && possibility2 > nextNumber) {
				possibility2 = nextNumber;
			} else if (possibility1 > possibility2 && possibility2 < nextNumber && possibility1 > nextNumber) {
				possibility1 = nextNumber;
			}
		}
		if (possibility1 > possibility2) {
			out.printf("The second smallest number is:%d", possibility1);
		} else {
			out.printf("The second smallest number is:%d", possibility2);
		}
	}

	public static void main(String[] argv) {
		new SecondSmallest().start();
	}
}
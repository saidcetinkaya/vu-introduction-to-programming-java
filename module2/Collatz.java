package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Collatz {
	
	Scanner in;
	PrintStream out;

	Collatz() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}
	
	void start() {
		out.printf("Enter a number: ");
		int number = in.nextInt();
		while(number != 1 && !(number <= 0)) {
			if (number % 2 == 0) {
				number /= 2;
			} else {
				number = number * 3 + 1;
			}
			out.printf("%d\n", number);
		}
	}

	public static void main(String[] argv) {
		new Collatz().start();
	}
}
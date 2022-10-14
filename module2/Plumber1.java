package module2;

import java.io.PrintStream;
import java.util.Scanner;

public class Plumber1 {

	static final double CALL_OUT_COST = 16.00;

	Scanner in;
	PrintStream out;

	Plumber1() {
		in = new Scanner(System.in);
		out = new PrintStream(System.out);
	}

	void start() {
		out.printf("Enter the hourly wages: ");
		double hourlyWages = in.nextDouble();
		out.printf("Enter the number of billable hours: ");
		int billableHours = in.nextInt();

		double totalCost = CALL_OUT_COST + hourlyWages * billableHours;
		out.printf("The total cost of this repair is: €%.02f", totalCost);
	}

	public static void main(String[] argv) {
		new Plumber1().start();
	}

}
package module2;

import java.io.PrintStream;

public class Alphabet {

	PrintStream out;

	Alphabet() {
		out = new PrintStream(System.out);
	}

	void start() {
		for (char i = 'a'; i <= 'z'; i++) {
			out.printf("%c", i);
		}
	}

	public static void main(String[] argv) {
		new Alphabet().start();
	}
}
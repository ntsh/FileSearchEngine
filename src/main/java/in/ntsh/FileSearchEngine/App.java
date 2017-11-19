package in.ntsh.FileSearchEngine;

import java.io.File;
import java.util.Scanner;

public class App {

	private static final Scanner KEYBOARD_SCANNER = new Scanner(System.in);

	public static void main(final String[] args) {

		String directory = getDirectoryPath(args);

		// Search
		showSearchConsole();
	}

	/**
	 * Get Directory path from argument passed, else get directory path from
	 * user if no argument is passed
	 */
	private static String getDirectoryPath(final String[] args) {
		String indexableDirectory;
		if (args.length == 0) {
			System.out.print("Directory> ");
			indexableDirectory = KEYBOARD_SCANNER.nextLine();
		} else {
			indexableDirectory = args[0];
		}

		return indexableDirectory;
	}

	private static void showSearchConsole() {

		while (true) {
			System.out.print("Search> ");
			KEYBOARD_SCANNER.nextLine();
		}
	}
}

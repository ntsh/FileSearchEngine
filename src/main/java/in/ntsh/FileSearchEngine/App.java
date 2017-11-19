package in.ntsh.FileSearchEngine;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class App {

	private static final Scanner KEYBOARD_SCANNER = new Scanner(System.in);

	public static void main(final String[] args) {

		String directoryPath = getDirectoryPath(args);
		DirectoryIndexer indexer = new DirectoryIndexer(directoryPath);
		InvertedIndex index = null;
		try {
			index = indexer.getIndex();
			// Search
			DirectorySearcher engine = new DirectorySearcher(index);
			showSearchConsole(engine);
		} catch (IOException e) {
			System.out.println("Error reading text files");
			e.printStackTrace();
		}
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

	private static void showSearchConsole(DirectorySearcher engine) {

		while (true) {
			System.out.print("Search> ");
			String keywords = KEYBOARD_SCANNER.nextLine();
			List<Entry<String, Integer>> results = engine.search(keywords);
			printResults(results);
		}
	}

	private static void printResults(List<Entry<String, Integer>> results) {
		results.forEach(System.out::println);
	}
}

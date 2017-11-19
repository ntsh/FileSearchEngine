package in.ntsh.FileSearchEngine;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {

	private static final Scanner KEYBOARD_SCANNER = new Scanner(System.in);

	public static void main(final String[] args) {
		try {
			final String directoryPath = getDirectoryPath(args);

			System.out.println("Indexing files in directory " + directoryPath);
			// Index
			final DirectoryIndexer indexer = new DirectoryIndexer(directoryPath);
			final InvertedIndex index = indexer.getIndex();

			// Search
			final DirectorySearcher engine = new DirectorySearcher(index);
			showSearchConsole(engine);
		} catch (final IOException e) {
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

	private static void showSearchConsole(final DirectorySearcher engine) {
		while (true) {
			System.out.print("Search> ");
			final String keywords = KEYBOARD_SCANNER.nextLine();
			final List<SearchResult> results = engine.search(keywords);
			printResults(results);
		}
	}

	private static void printResults(final List<SearchResult> results) {
		results.forEach(SearchResultPrinter::print);
	}
}

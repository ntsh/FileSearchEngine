package in.ntsh.FileSearchEngine;

public class SearchResultPrinter {

	public static void print(final SearchResult result) {
		System.out.println(result.getFileName() + " : " + result.getWeight() + "%");
	}
}

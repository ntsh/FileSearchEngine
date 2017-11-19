package in.ntsh.FileSearchEngine;

public class SearchResultPrinter {

	public static void print(SearchResult result) {
		System.out.println(result.getFileName() + " : " + result.getWeight() + "%");
	}
}

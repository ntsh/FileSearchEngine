package in.ntsh.FileSearchEngine;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DirectorySearcher {

	private final InvertedIndex index;
	private SearchResults results;

	public DirectorySearcher(final InvertedIndex index) {
		this.index = index;
	}

	public List<SearchResult> search(final String words) {
		this.results = new SearchResults();

		// Get unique words and search in the map
		final Pattern pattern = Pattern.compile("\\W+");

		pattern.splitAsStream(words)
		.distinct()
		.forEach(this::searchWord);

		// Rank the result
		return this.results.getRankedResults();
	}

	private void searchWord(final String word) {
		final Map<String, Integer> map = this.index.getPostingsForWord(word.toLowerCase());
		if (map == null) {
			return;
		}
		map.forEach((file, frequency) -> {
			this.results.addResult(file);
		});
	}
}

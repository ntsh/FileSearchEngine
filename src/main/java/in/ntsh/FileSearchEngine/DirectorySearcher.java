package in.ntsh.FileSearchEngine;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DirectorySearcher {

	private final InvertedIndex index;
	private SearchResults results;
	private int keywordsCount;

	public DirectorySearcher(final InvertedIndex index) {
		this.index = index;
	}

	public List<SearchResult> search(final String words) {
		this.keywordsCount = 0;

		// Create SearchResult object to hold results
		this.results = new SearchResults();

		// Get unique words
		final Stream<String> keywords = this.getKeywords(words);

		// Search for word in index
		keywords.forEach(this::searchWord);

		// Rank the result
		return this.results.getRankedResults(this.keywordsCount);
	}

	private Stream<String> getKeywords(final String words) {
		final Pattern pattern = Pattern.compile("\\W+");
		return pattern.splitAsStream(words).distinct();
	}

	private void searchWord(final String word) {
		this.keywordsCount++;

		final Map<String, Integer> map = this.index.getPostingsForWord(word.toLowerCase());
		if (map == null) {
			return; // Word not present in the index
		}
		map.forEach((file, frequency) -> {
			this.results.addResult(file);
		});
	}
}

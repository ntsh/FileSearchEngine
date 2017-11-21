package in.ntsh.FileSearchEngine;

import java.util.List;
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

		final List<Posting> postings = this.index.getPostingsForWord(word.toLowerCase());
		if (postings == null) {
			return; // Word not present in the index
		}
		postings.forEach(posting -> {
			this.results.addResult(posting.getFileName());
			//Frequency of file in a posting is ignored in this algorithm
		});
	}
}

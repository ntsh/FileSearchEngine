package in.ntsh.FileSearchEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to hold the search results in the form of file name and
 * their weighted result
 *
 */
public class SearchResults {
	private final Map<String, Integer> fileFrequencyMap;

	public SearchResults() {
		this.fileFrequencyMap = new HashMap<String, Integer>();
	}

	/**
	 * Adds the file to existing results.
	 * If a file already exists, it's weight increases by 1.
	 * @param fileName
	 */
	public void addResult(final String fileName) {
		Integer count = this.fileFrequencyMap.get(fileName);
		if (count == null) {
			count = 0;
		}
		this.fileFrequencyMap.put(fileName, count + 1);
	}

	/**
	 * Returns the results sorted by most important result on top.
	 * @return
	 */
	public List<SearchResult> getRankedResults(final int keywordsCount) {
		final List<SearchResult> sortedResults = this.fileFrequencyMap.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
				.limit(Config.RESULTS_COUNT)
				.map(entry -> {
					final Integer weight = this.getWeight(entry.getValue(), keywordsCount);
					return new SearchResult(entry.getKey(), weight);
				})
				.collect(Collectors.toList());
		return sortedResults;
	}

	private Integer getWeight(final Integer value, final int count) {
		final Integer weight = value * 100 / count;
		return weight;
	}
}
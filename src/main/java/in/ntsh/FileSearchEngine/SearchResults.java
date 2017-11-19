package in.ntsh.FileSearchEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Class to hold the search results in the form of file name and
 * their weighted result
 *
 */
public class SearchResults {
	private Map<String, Integer> fileOccurenceMap;

	public SearchResults() {
		this.fileOccurenceMap = new HashMap<String, Integer>();
	}

	/**
	 * Adds the file to existing results.
	 * If a file already exists, it's weight increases by 1.
	 * @param fileName
	 */
	public void addResult(final String fileName) {
		Integer count = this.fileOccurenceMap.get(fileName);
		if (count == null) {
			count = 0;
		}
		this.fileOccurenceMap.put(fileName, count + 1);
	}

	/**
	 * Returns the results sorted by most important result on top.
	 * @return
	 */
	public List<Entry<String, Integer>> getRankedResults() {
		final List<Entry<String, Integer>> sortedResults = this.fileOccurenceMap.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Integer> comparingByValue()
						.reversed())
				.limit(10)
				.collect(Collectors.toList());
		return sortedResults;
	}
}
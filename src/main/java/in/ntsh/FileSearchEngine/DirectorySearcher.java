package in.ntsh.FileSearchEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DirectorySearcher {

	private InvertedIndex index;
	private Map<String, Integer> fileOccurenceMap;

	public DirectorySearcher(InvertedIndex index) {
		this.index = index;
	}

	public List<Entry<String, Integer>> search(String words) {
		fileOccurenceMap = new HashMap<String, Integer>();

		// Get unique words and search in the map
		Pattern pattern = Pattern.compile("\\W+");

		pattern.splitAsStream(words)
		.distinct()
		.forEach(this::searchWord);

		// Rank the result
		List<Entry<String, Integer>> results = fileOccurenceMap.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Integer> comparingByValue()
						.reversed())
				.limit(10)
				.collect(Collectors.toList());
		return results;
	}

	private void searchWord(String word) {
		Map<String, Integer> map = this.index.get(word.toLowerCase());
		if (map == null) {
			return;
		}
		map.forEach((file, frequency) -> {
			Integer count = fileOccurenceMap.get(file);
			if (count == null) {
				count = 0;
			}
			fileOccurenceMap.put(file, count + 1);
		});
	}
}

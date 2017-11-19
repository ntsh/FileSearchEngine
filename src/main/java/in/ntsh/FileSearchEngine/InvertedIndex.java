package in.ntsh.FileSearchEngine;

import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {

	private Map<String, Map<String, Integer>> index;

	public InvertedIndex() {
		this.index = new HashMap<String, Map<String, Integer>>();
	}

	/**
	 * Indexes the file for a word
	 *
	 * @param word
	 * @param file
	 */
	public void indexWordForFile(final String word, final String file) {
		// Get Existing index for the word or create a new list
		Map<String, Integer> fileList = index.get(word);
		if (fileList == null) {
			fileList = new HashMap<String, Integer>();
			fileList.put(file, 1);
		} else {
			// Add file and count to index of the word
			Integer countOfWords = fileList.get(file);
			if (countOfWords == null) {
				countOfWords = 0;
			}
			fileList.put(file, countOfWords + 1);
		}
		// Put back word's index to Directory's index
		index.put(word, fileList);
	}

	public Map<String, Integer> get(String word) {
		return this.index.get(word);
	}
}
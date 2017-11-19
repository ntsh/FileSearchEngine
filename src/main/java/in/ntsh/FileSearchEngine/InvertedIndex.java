package in.ntsh.FileSearchEngine;

import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {

	private final Map<String, Map<String, Integer>> index;

	public InvertedIndex() {
		this.index = new HashMap<String, Map<String, Integer>>();
	}

	/**
	 * Indexes the file for a word
	 *
	 * @param word
	 * @param file
	 */
	public void indexWordInFile(final String word, final String file) {
		// Get Existing index for the word or create a new list
		Map<String, Integer> fileList = this.index.get(word);
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
		this.index.put(word, fileList);
	}

	/**
	 * Returns the Map of files, frequency for a given word from the index
	 * @param word
	 */
	public Map<String, Integer> getPostingsForWord(final String word) {
		return this.index.get(word);
	}
}
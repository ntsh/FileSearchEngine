package in.ntsh.FileSearchEngine.Index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {

	private final Map<String, Map<String, Integer>> index;
	private final Object lock = new Object();

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
		synchronized(lock) {
			// Get Existing index for the word or create a new list
			Map<String, Integer> fileMap = this.index.get(word);
			if (fileMap == null) {
				fileMap = new HashMap<String, Integer>();
				fileMap.put(file, 1);
			} else {
				// Add file and count to index of the word
				Integer countOfWords = fileMap.get(file);
				if (countOfWords == null) {
					countOfWords = 0;
				}
				fileMap.put(file, countOfWords + 1);
			}
			// Put back word's index to Directory's index
			this.index.put(word, fileMap);
		}
	}

	/**
	 * Returns the List of postings for a given word from the index
	 * @param word
	 */
	public List<Posting> getPostingsForWord(final String word) {
		synchronized(lock) {
			final Map<String, Integer> fileMap = this.index.get(word);

			final List<Posting> postings = new ArrayList<Posting>();
			if(fileMap != null) {
				fileMap.forEach((file, frequency) -> postings.add(new Posting(file, frequency)));
			}

			return postings;
		}
	}
}
package in.ntsh.FileSearchEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class indexes a directory of text files
 */
class DirectoryIndexer {

	private final Path path;
	private Map<String, Map<String, Integer>> index;

	private final Object lock = new Object();

	/**
	 * @param directoryPath : path of the directory to be indexed
	 * @throws InvalidPathException
	 */
	public DirectoryIndexer(final String directoryPath) throws InvalidPathException {
		this.path = Paths.get(directoryPath);
	}

	/**
	 * Indexes the contents of the directory.
	 *
	 * @throws IOException
	 */
	public Map<String, Map<String, Integer>> getIndex() throws IOException {
		this.index = new HashMap<String, Map<String, Integer>>();

		Files.walk(this.path)
		.filter(Files::isRegularFile)
		.forEach(this::indexFile);

		return this.index;
	}

	private void indexFile(final Path file) {
		try {
			Files.lines(file)	// Read line by line
			.flatMap(Pattern.compile("\\W+")::splitAsStream) // convert line to words
			.map(word -> word.toLowerCase())
			.forEach(word -> this.indexWordForFile(word, file.toString())); // Index filename for this word
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void indexWordForFile(final String word, final String file) {
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
		synchronized(this.lock) {
			this.index.put(word, fileList);
		}
	}
}

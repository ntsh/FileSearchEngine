package in.ntsh.FileSearchEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class indexes a directory of text files
 */
class DirectoryIndexer {

	private final Path path;
	private Map<String, List<String>> index;

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
	public Map<String, List<String>> getIndex() throws IOException {
		this.index = new HashMap<String, List<String>>();

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
			.forEach(word -> this.indexWord(word, file)); // Index filename for this word
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void indexWord(final String word, final Path file) {
		// Get Existing index for the word or create a new list
		List<String> fileList = this.index.get(word);
		if (fileList == null) {
			fileList = new ArrayList<String>();
		}

		// Add file to the list of indices of the word
		fileList.add(file.toString());

		// Put back word's index to Directory's index
		synchronized(this.lock) {
			this.index.put(word, fileList);
		}
	}
}

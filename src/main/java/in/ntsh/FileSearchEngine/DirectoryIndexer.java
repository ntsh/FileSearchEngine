package in.ntsh.FileSearchEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * This class indexes a directory of text files
 */
class DirectoryIndexer {

	private final Path path;
	private InvertedIndex index = new InvertedIndex();
	final Object lock = new Object();

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
	public InvertedIndex getIndex() throws IOException {
		this.index = new InvertedIndex();

		Files.walk(this.path)
				.filter(Files::isRegularFile)
				.parallel()
				.forEach(this::indexFile);

		return this.index;
	}

	private void indexFile(final Path file) {
		try (Stream<String> lines = Files.lines(file)) {
			lines.flatMap(Pattern.compile("\\W+")::splitAsStream)
					.map(word -> word.toLowerCase())
					.forEach(word -> this.indexWordForFile(word, file.toString()));
		} catch (final Exception e) {
			return; // Ignore files which can't be read as text
		}
	}

	private void indexWordForFile(final String word, final String file) {
		synchronized (lock) {
			index.indexWordForFile(word, file);
		}
	}
}

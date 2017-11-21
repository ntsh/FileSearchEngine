package in.ntsh.FileSearchEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DirectoryIndexerTest {

	private static String FILE_HELLO = "src/test/java/in/ntsh/FileSearchEngine/resources/hello.txt";
	private static String FILE_GREETING = "src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt";
	private static String FILE_WORLD = "src/test/java/in/ntsh/FileSearchEngine/resources/world.txt";

	private InvertedIndex index;

	@Before
	public void setUp() throws Exception {
		final String path = "src/test/java/in/ntsh/FileSearchEngine/resources/";
		final DirectoryIndexer indexer = new DirectoryIndexer(path);
		this.index = indexer.getIndex();
	}

	@Test
	public void testIndexForSingleWord() {
		final List<Posting> postings = this.index.getPostingsForWord("hello");
		assertTrue(findPostingForFile(FILE_HELLO, postings) != null);
		assertTrue(findPostingForFile(FILE_GREETING, postings) != null);
		assertFalse(findPostingForFile(FILE_WORLD, postings) != null);
	}

	@Test
	public void testFileCountForWordRepeatedInAFile() {
		final List<Posting> fileListForGreat = this.index.getPostingsForWord("great");
		assertEquals(2, fileListForGreat.size());
	}

	@Test
	public void testOccurenceCountForWordRepeatedInAFile() {
		final List<Posting> postings = this.index.getPostingsForWord("great");
		final Posting posting = findPostingForFile(FILE_GREETING, postings);
		assertEquals(2, posting.getCount());
	}

	@Test
	public void testIndexForWordWithSpecialCharacter() {
		final List<Posting> postings = this.index.getPostingsForWord("great");

		assertTrue(findPostingForFile(FILE_WORLD, postings) != null);
		assertTrue(findPostingForFile(FILE_GREETING, postings) != null);
		assertFalse(findPostingForFile(FILE_HELLO, postings) != null);
	}

	@Test
	public void testEmptyIndex() {
		final List<Posting> postings = this.index.getPostingsForWord("randomword");
		assertEquals(0, postings.size());
	}

	@Test(expected = IOException.class)
	public void testInvalidDirectoryPath() throws IOException {
		final DirectoryIndexer indexer = new DirectoryIndexer("/invalid/path");
		indexer.getIndex();
	}

	private Posting findPostingForFile(String file, List<Posting> list) {
		for (Posting posting : list) {
			if (posting.getFileName().equals(file)) {
				return posting;
			}
		}
		return null;
	}
}

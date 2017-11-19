package in.ntsh.FileSearchEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DirectoryIndexerTest {

	private Map<String, Map<String, Integer>> index;

	@Before
	public void setUp() throws Exception {
		final String path = "src/test/java/in/ntsh/FileSearchEngine/resources/";
		final DirectoryIndexer indexer = new DirectoryIndexer(path);
		this.index = indexer.getIndex();
	}

	@Test
	public void testIndexForSingleWord() {
		final Map<String, Integer> fileListForHello = this.index.get("hello");
		assertTrue(fileListForHello.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/hello.txt"));
		assertTrue(fileListForHello.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt"));
		assertFalse(fileListForHello.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/world.txt"));
	}

	@Test
	public void testFileCountForWordRepeatedInAFile() {
		final Map<String, Integer> fileListForGreat = this.index.get("great");
		assertEquals(2, fileListForGreat.size());
	}

	@Test
	public void testOccurenceCountForWordRepeatedInAFile() {
		final Map<String, Integer> fileListForGreat = this.index.get("great");
		final Integer count = fileListForGreat.get("src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt");
		assertEquals(2, count.intValue());
	}

	@Test
	public void testIndexForWordWithSpecialCharacter() {
		final Map<String, Integer> fileListForWorld = this.index.get("world");
		assertTrue(fileListForWorld.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/hello.txt"));
		assertTrue(fileListForWorld.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/world.txt"));
		assertFalse(fileListForWorld.containsKey("src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt"));
	}

	@Test
	public void testEmptyIndex() {
		final Map<String, Integer> fileListForNonExistentWord = this.index.get("randomword");
		assertTrue(fileListForNonExistentWord == null);
	}

	@Test(expected = IOException.class)
	public void testInvalidDirectoryPath() throws IOException {
		final DirectoryIndexer indexer = new DirectoryIndexer("/invalid/path");
		indexer.getIndex();
	}
}

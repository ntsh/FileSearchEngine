package in.ntsh.FileSearchEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DirectoryIndexerTest {

	private Map<String, List<String>> index;

	@Before
	public void setUp() throws Exception {
		final String path = "src/test/java/in/ntsh/FileSearchEngine/resources/";
		final DirectoryIndexer indexer = new DirectoryIndexer(path);
		this.index = indexer.getIndex();
	}

	@Test
	public void testIndexForSingleWord() {
		final List<String> fileListForHello = this.index.get("hello");
		assertTrue(fileListForHello.contains("src/test/java/in/ntsh/FileSearchEngine/resources/hello.txt"));
		assertTrue(fileListForHello.contains("src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt"));
		assertFalse(fileListForHello.contains("src/test/java/in/ntsh/FileSearchEngine/resources/world.txt"));
	}

	@Test
	public void testIndexCountForWordRepeatedInAFile() {
		final List<String> fileListForGreat = this.index.get("great");
		assertEquals(2, fileListForGreat.size());
	}

	@Test
	public void testIndexForWordWithSpecialCharacter() {
		final List<String> fileListForWorld = this.index.get("world");
		assertTrue(fileListForWorld.contains("src/test/java/in/ntsh/FileSearchEngine/resources/hello.txt"));
		assertTrue(fileListForWorld.contains("src/test/java/in/ntsh/FileSearchEngine/resources/world.txt"));
		assertFalse(fileListForWorld.contains("src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt"));
	}

	@Test
	public void testEmptyIndex() {
		final List<String> fileListForNonExistentWord = this.index.get("randomword");
		assertTrue(fileListForNonExistentWord == null);
	}

	@Test(expected = IOException.class)
	public void testInvalidDirectoryPath() throws IOException {
		final DirectoryIndexer indexer = new DirectoryIndexer("/invalid/path");
		indexer.getIndex();
	}
}

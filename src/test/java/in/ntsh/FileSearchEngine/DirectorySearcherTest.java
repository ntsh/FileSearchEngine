package in.ntsh.FileSearchEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class DirectorySearcherTest {

	private DirectorySearcher searcher;
	private List<Entry<String, Integer>> results;

	@Before
	public void setUp() throws Exception {
		final String path = "src/test/java/in/ntsh/FileSearchEngine/resources/";
		final DirectoryIndexer indexer = new DirectoryIndexer(path);
		final InvertedIndex index = indexer.getIndex();
		this.searcher = new DirectorySearcher(index);
		this.results = this.searcher.search("great day");
	}

	@Test
	public void testSearchResultsCount() {
		assertEquals(2, this.results.size());
	}

	@Test
	public void testTopSearchResult() {
		final Entry<String, Integer> topResult = this.results.get(0);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt";
		assertTrue(expectedFileName.equals(topResult.getKey()));
		assertEquals(2, topResult.getValue().intValue());
	}

	@Test
	public void testSecondSearchResult() {
		final Entry<String, Integer> secondResult = this.results.get(1);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/world.txt";
		assertTrue(expectedFileName.equals(secondResult.getKey()));
		assertEquals(1, secondResult.getValue().intValue());
	}

	@Test
	public void testSearchTwiceShouldReturnSameResults() {
		final List<Entry<String, Integer>> resultsReRun = this.searcher.search("great day");

		// Test top result is same as before
		final Entry<String, Integer> topResult = resultsReRun.get(0);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt";
		assertTrue(expectedFileName.equals(topResult.getKey()));
		assertEquals(2, topResult.getValue().intValue());

		// Test 2nd result is same as before
		final Entry<String, Integer> secondResult = resultsReRun.get(1);
		final String expectedFileName2ndResult = "src/test/java/in/ntsh/FileSearchEngine/resources/world.txt";
		assertTrue(expectedFileName2ndResult.equals(secondResult.getKey()));
		assertEquals(1, secondResult.getValue().intValue());
	}
}

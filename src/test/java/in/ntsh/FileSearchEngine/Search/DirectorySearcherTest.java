package in.ntsh.FileSearchEngine.Search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import in.ntsh.FileSearchEngine.Index.DirectoryIndexer;
import in.ntsh.FileSearchEngine.Index.InvertedIndex;
import in.ntsh.FileSearchEngine.Search.DirectorySearcher;
import in.ntsh.FileSearchEngine.Search.SearchResult;

public class DirectorySearcherTest {

	private DirectorySearcher searcher;
	private List<SearchResult> results;

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
		final SearchResult topResult = this.results.get(0);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt";
		assertTrue(expectedFileName.equals(topResult.getFileName()));
		assertEquals(100, topResult.getWeight().intValue());
	}

	@Test
	public void testSecondSearchResult() {
		final SearchResult secondResult = this.results.get(1);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/world.txt";
		assertTrue(expectedFileName.equals(secondResult.getFileName()));
		assertEquals(50, secondResult.getWeight().intValue());
	}

	@Test
	public void testSearchTwiceShouldReturnSameResults() {
		final List<SearchResult> resultsReRun = this.searcher.search("great day");

		// Test top result is same as before
		final SearchResult topResult = resultsReRun.get(0);
		final String expectedFileName = "src/test/java/in/ntsh/FileSearchEngine/resources/greeting.txt";
		assertTrue(expectedFileName.equals(topResult.getFileName()));
		assertEquals(100, topResult.getWeight().intValue());

		// Test 2nd result is same as before
		final SearchResult secondResult = resultsReRun.get(1);
		final String expectedFileName2ndResult = "src/test/java/in/ntsh/FileSearchEngine/resources/world.txt";
		assertTrue(expectedFileName2ndResult.equals(secondResult.getFileName()));
		assertEquals(50, secondResult.getWeight().intValue());
	}

	@Test
	public void testNoResult() {
		final List<SearchResult> noResults = this.searcher.search("UnknownWord");
		assertTrue(noResults.isEmpty());
	}
}

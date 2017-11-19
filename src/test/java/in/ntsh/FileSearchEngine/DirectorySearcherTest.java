package in.ntsh.FileSearchEngine;

import org.junit.Before;
import org.junit.Test;

public class DirectorySearcherTest {

	private DirectorySearcher searcher;

	@Before
	public void setUp() throws Exception {
		final String path = "src/test/java/in/ntsh/FileSearchEngine/resources/";
		final DirectoryIndexer indexer = new DirectoryIndexer(path);
		final InvertedIndex index = indexer.getIndex();
		this.searcher = new DirectorySearcher(index);
	}

	@Test
	public void testSearch() {
		this.searcher.search("hello world");
	}

}

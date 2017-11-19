package in.ntsh.FileSearchEngine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class InvertedIndexTest {

	private InvertedIndex index;

	@Before
	public void setUp() throws Exception {
		this.index = new InvertedIndex();
	}

	@Test
	public void testItIndexesWordForFile() {
		final String word = "hello";
		final String file = "hello.txt";

		this.index.indexWordInFile(word, file);

		final Map<String, Integer> map = this.index.getPostingsForWord(word);
		assertTrue(map.containsKey(file));
	}

	@Test
	public void testItIndexesCorrectCountForWord() {
		final String word = "hello";
		final String file = "hello.txt";

		this.index.indexWordInFile(word, file);
		this.index.indexWordInFile(word, file);

		final Map<String, Integer> map = this.index.getPostingsForWord(word);
		final Integer count = map.get(file);
		assertEquals(2, count.intValue());
	}

	@Test
	public void testEmptyIndex() {
		final Map<String, Integer> map = this.index.getPostingsForWord("hello");
		assertNull(map);
	}

}

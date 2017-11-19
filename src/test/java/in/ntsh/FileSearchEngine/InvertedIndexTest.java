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
		String word = "hello";
		String file = "hello.txt";

		this.index.indexWordInFile(word, file);

		Map<String, Integer> map = this.index.getPostingsForWord(word);
		assertTrue(map.containsKey(file));
	}

	@Test
	public void testItIndexesCorrectCountForWord() {
		String word = "hello";
		String file = "hello.txt";

		this.index.indexWordInFile(word, file);
		this.index.indexWordInFile(word, file);

		Map<String, Integer> map = this.index.getPostingsForWord(word);
		Integer count = map.get(file);
		assertEquals(2, count.intValue());
	}

	@Test
	public void testEmptyIndex() {
		Map<String, Integer> map = this.index.getPostingsForWord("hello");
		assertNull(map);
	}

}

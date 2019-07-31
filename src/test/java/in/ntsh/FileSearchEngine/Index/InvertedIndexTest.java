package in.ntsh.FileSearchEngine.Index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import in.ntsh.FileSearchEngine.Index.InvertedIndex;
import in.ntsh.FileSearchEngine.Index.Posting;

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

		final List<Posting> postings = this.index.getPostingsForWord(word);
		assertTrue(this.findPostingForFile(file, postings) != null);
	}

	@Test
	public void testItIndexesCorrectCountForWord() {
		final String word = "hello";
		final String file = "hello.txt";

		this.index.indexWordInFile(word, file);
		this.index.indexWordInFile(word, file);

		final List<Posting> postings = this.index.getPostingsForWord(word);
		final Posting posting = this.findPostingForFile(file, postings);
		final int count = posting.getCount();
		assertEquals(2, count);
	}

	@Test
	public void testEmptyIndex() {
		final List<Posting> postings = this.index.getPostingsForWord("hello");
		assertTrue(postings.isEmpty());
	}

	private Posting findPostingForFile(final String file, final List<Posting> list) {
		for (final Posting posting : list) {
			if (posting.getFileName().equals(file)) {
				return posting;
			}
		}
		return null;
	}

}

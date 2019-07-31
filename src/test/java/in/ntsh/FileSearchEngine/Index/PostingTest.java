package in.ntsh.FileSearchEngine.Index;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.ntsh.FileSearchEngine.Index.Posting;

public class PostingTest {

	private static String FILE_NAME = "file.txt";
	private Posting posting;

	@Before
	public void setUp() throws Exception {
		this.posting = new Posting(FILE_NAME, 5);
	}

	@Test
	public void testGetFileName() {
		assertEquals(FILE_NAME, this.posting.getFileName());
	}

	@Test
	public void testGetCount() {
		assertEquals(5, this.posting.getCount());
	}

	@Test
	public void testIncrementCount() {
		this.posting.incrementCount();
		assertEquals(6, this.posting.getCount());
	}

}

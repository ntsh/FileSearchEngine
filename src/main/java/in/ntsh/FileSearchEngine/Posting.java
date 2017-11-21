package in.ntsh.FileSearchEngine;

/**
 * This class holds file and frequency data.
 * In future, could also add position array as well.
 */
public class Posting {

	private String fileName;
	private int count;

	Posting(final String fileName) {
		new Posting(fileName, 0);
	}

	Posting(final String fileName, final Integer frequency) {
		this.fileName = fileName;
		this.count = frequency;
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getCount() {
		return this.count;
	}

	public void incrementCount() {
		this.count++;
	}

}

package in.ntsh.FileSearchEngine;

/**
 * This class holds file and frequency data.
 * In future, could also add position array as well.
 */
public class Posting {

	private String fileName;
	private int count;
	
	Posting(String fileName) {
		new Posting(fileName, 0);
	}
	
	Posting(String fileName, Integer frequency) {
		this.fileName = fileName;
		this.count = frequency;
	}

	public String getFileName() {
		return fileName;
	}

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		this.count++;
	}
	
}

package in.ntsh.FileSearchEngine;

public class SearchResult {

	private String fileName;

	private Integer weight;
	
	public SearchResult(final String fileName, final Integer weight) {
		this.fileName = fileName;
		this.weight = weight;
	}
	
	public String getFileName() {
		return fileName;
	}

	public Integer getWeight() {
		return weight;
	}
}

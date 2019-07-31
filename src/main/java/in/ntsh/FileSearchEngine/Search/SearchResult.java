package in.ntsh.FileSearchEngine.Search;

public class SearchResult {

	private final String fileName;

	private final Integer weight;

	public SearchResult(final String fileName, final Integer weight) {
		this.fileName = fileName;
		this.weight = weight;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Integer getWeight() {
		return this.weight;
	}
}

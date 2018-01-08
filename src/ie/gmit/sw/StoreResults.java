package ie.gmit.sw;

import java.util.HashMap;
import java.util.Set;

public class StoreResults {
    private int jobNumber;
    private String title;
    private HashMap<String, String> docsResults;

    public StoreResults(int jobNumber, String documentName) {
	super();
	this.jobNumber = jobNumber;
	this.title = documentName;
	docsResults = new HashMap<>();
    }

    public int getJobNumber() {
	return jobNumber;
    }

    public String getTitle() {
	return title;
    }

    public void addResults(HashMap<String, String> results) {
	docsResults.putAll(results);
	;
    }

    public Set<String> getDocuments() {
	return docsResults.keySet();
    }

    public int getResultsCount() {
	return docsResults.size();
    }

    public String getResult(String title) {
	return docsResults.get(title);
    }
}

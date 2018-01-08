package ie.gmit.sw;

import java.io.BufferedReader;

//pass uploaded document to Worker

public class Job {
	private int jobNumber;
	private String title;
	private BufferedReader document;

	public Job(int jobNumber, String title, BufferedReader document) {
		super();
		this.jobNumber = jobNumber;
		this.title = title;
		this.document = document;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BufferedReader getDocument() {
		return document;
	}

	public void setDocument(BufferedReader document) {
		this.document = document;
	}

}

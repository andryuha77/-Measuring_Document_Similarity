package ie.gmit.sw;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Worker extends MainWorker {

	private static ArrayBlockingQueue<Job> inQueue;
	private static ConcurrentHashMap<Integer, StoreResults> outQueue;
	private int workerNumber;
	private Job job;
	private Document document;
	private List<Document> documents;
	private StoreResults results;

	// Worker runnable method using Minhash class Facade gets words from
	// BufferedReader

	@Override
	public void run() {
		workerNumber = Util.getWorkerNumber();
		inQueue = Util.getInQueue();
		outQueue = Util.getOutQueue();
		try {
			job = inQueue.take();
			Util.logMessage(
					String.format("Worker number %d start work on job number: %d", workerNumber, job.getJobNumber()));
			MinHash minHash = new MinHash(Util.getDb());
			try {
				documents = minHash.retreiveDocuments();
				document = new Document(job.getTitle());
				results = new StoreResults(job.getJobNumber(), job.getTitle());
				Set<String> words = minHash.getWords(job.getDocument());
				Set<Integer> hashes = minHash.getHashes(words, Util.getShingleSize());
				if (documents.isEmpty()) {
					document.setHashFunctions(minHash.getHashFunctions(Util.getHashFunctionsSize()));
				} else {
					document.setHashFunctions(documents.get(0).getHashFunctions());
				}
				document.setMinHashes(minHash.getMinHashes(hashes, document.getHashFunctions()));
				results.addResults(minHash.compareDocument(document, documents));
				outQueue.put(job.getJobNumber(), results);
				if (!minHash.isAlreadySaved()) {
					minHash.storeDocument(document);
				}
			} catch (IOException e) {
				Util.logMessage(String.format("MinHash caused exception processing %s Error: %s", job.getTitle(),
						e.getMessage()));
			}
		} catch (InterruptedException e) {
			Util.logMessage(String.format("Worker number: %d processing job number: %d, Document: %s caused error: %s",
					workerNumber, job.getJobNumber(), job.getTitle(), e.getMessage()));
		}
	}
}

package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MinHash {

	private DocumentInterface db;
	private ProcessDocument pd;

	public MinHash(DocumentInterface db) {
		this.db = db;
		pd = new ProcessDocument();
	}

	// Retreive all the objects from database

	public List<Document> retreiveDocuments() {
		return db.getDocuments();
	}

	// Store a Document object to database via Db4Controller class

	public void storeDocument(Document document) {
		db.storeDocument(document);
	}

	// Split BufferedReader document into separate words

	public Set<String> getWords(BufferedReader document) throws IOException {
		return pd.getWords(document);
	}

	// Process Set of Words into Set of Hash Codes.

	public Set<Integer> getHashes(Set<String> words, int shingleSize) {
		return pd.getHashes(words, shingleSize);
	}

	public Set<Integer> getHashFunctions(int numOfHashes) {
		return pd.getHashFunctions(numOfHashes);
	}

	// Generate Set of Integers representing hash code with minimum value

	public Set<Integer> getMinHashes(Set<Integer> hashes, Set<Integer> hashFunctions) {
		Set<Integer> minHashes = pd.getMinHashes(hashes, hashFunctions);
		minHashes.add(pd.getMinHash());
		return minHashes;
	}

	// compare document against other documents

	public HashMap<String, String> compareDocument(Document document, List<Document> documents) {
		return pd.compareDocument(document, documents);
	}

	public boolean isAlreadySaved() {
		return pd.isAlreadySaved();
	}

}

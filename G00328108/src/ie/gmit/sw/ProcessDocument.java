package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ProcessDocument {
	private int minHash = Integer.MAX_VALUE;
	private boolean alreadyExist = false;

	// Process BufferedReader document into separate words

	public Set<String> getWords(BufferedReader document) throws IOException {
		String line;
		Set<String> allWords = new TreeSet<>();
		while ((line = document.readLine()) != null) {
			allWords.addAll(new ArrayList<String>(Arrays.asList(line.split(" "))));
		}
		return allWords;
	}

	// Words into Set of Hash Codes.

	public Set<Integer> getHashes(Set<String> words, int shingleSize) {
		minHash = Integer.MAX_VALUE;
		StringBuilder shingleBuilder = new StringBuilder();
		int shingleCount = 0;
		Set<Integer> shingleHashCodes = new TreeSet<>();
		for (String word : words) {
			shingleBuilder = shingleBuilder.append(word);
			if (shingleCount == shingleSize) {
				String shingle = shingleBuilder.toString();
				int hashCode = shingle.hashCode();
				shingleHashCodes.add(hashCode);
				if (minHash > hashCode)
					minHash = hashCode;
				shingleCount = 0;
				shingleBuilder = new StringBuilder();
			} else {
				shingleCount++;
			}
		}
		return shingleHashCodes;
	}

	public Set<Integer> getHashFunctions(int numOfHashes) {
		Set<Integer> hashFunctions = new TreeSet<Integer>();
		Random random = new Random();
		for (int i = 1; i < numOfHashes; i++) // Shingles size minus nimHash from .hashCode function.
		{
			hashFunctions.add(random.nextInt());
		}
		return hashFunctions;
	}

	public int getMinHash() {
		return minHash;
	}

	public HashMap<String, String> compareDocument(Document document, List<Document> documents) {
		alreadyExist = false;
		HashMap<String, String> results = new HashMap<>();
		int hashFunctionsCount = document.getHashFunctionsSize();
		for (Document tmpDocument : documents) {
			Set<Integer> retainAll = new TreeSet<>(document.getMinHashes());
			retainAll.retainAll(tmpDocument.getMinHashes());
			double similarity = (double) retainAll.size() / hashFunctionsCount * 100;
			// Prevent duplicate saving
			if (similarity == 100.0 && document.getTitle().equals(tmpDocument.getTitle()))
				alreadyExist = true;
			results.put(tmpDocument.getTitle(), String.valueOf(similarity));
		}
		return results;
	}

	public Set<Integer> getMinHashes(Set<Integer> hashes, Set<Integer> hashFunctions) {
		Set<Integer> minHashes = new TreeSet<>();
		for (int hashFunction : hashFunctions) {
			int min = Integer.MAX_VALUE;
			for (Integer hash : hashes) {
				int minHash = hash ^ hashFunction; // Bitwise XOR the word hashCode with the hashFunction
				if (minHash < min)
					min = minHash;
			}
			minHashes.add(min); // Only store the word with the minimum hash value for each hash function
		}
		return minHashes;
	}

	public boolean isAlreadySaved() {
		return alreadyExist;
	}
}

package edu.smith.cs.csc212.p8;

import java.util.List;

/**
 * Why do we need Trees/HashSets at all? It's because long lists are measurably terrible!
 * @author jfoley
 *
 */
public class WhyNotJustAList {

	/**
	 * A practical demonstration... takes minutes on my machine.
	 * Notice that it starts fast and gets slower over time
	 * ->> because the later words are at the end of the list!
	 * @param args - unused command line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = CheckSpelling.loadDictionary();
		double N = (double) listOfWords.size();


		// --- linear list timing:
		// This takes a long time to run!
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (int i=0; i<listOfWords.size(); i++) {
			if (i % 3000 == 0) {
				double frac = i / N;
				int percent = (int) (frac * 100);
				System.out.println("Progress: "+percent+"%");
			}
			String w = listOfWords.get(i);
			if (listOfWords.contains(w)) {
				found++;
			}
		}
		
		long endLookup = System.nanoTime();
		
		// print information:
		double fractionFound = found / N;
		double timeSpentPerItem = (endLookup - startLookup) / N;
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println("Linear Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");		
	}

}

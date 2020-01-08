package edu.smith.cs.csc212.speller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/**
 * Do your work for the Fake Dataset part here.
 * 
 * @author jfoley
 *
 */
public class FakeDatasetExperiment {
	/**
	 * Maybe this will be a nice helper method. How do you "ruin" a correctly
	 * spelled word?
	 * 
	 * @param realWord - a word from the dictionary, perhaps chosen at random.
	 * @return an incorrectly-spelled word. Maybe you deleted a letter or added one?
	 */
	public static String makeFakeWord(String realWord) {
		throw new RuntimeException("TODO");
	}

	/**
	 * Create a list of words that contains some dictionary words in proportion to
	 * some non-dictionary words. 
	 * 
	 * @param yesWords    - the words in the dictionary.
	 * @param numSamples  - the number of total words to select.
	 * @param fractionYes - the fraction of words that are in the dictionary -- with
	 *                    0.5, half would be spelled correctly and half would be
	 *                    incorrect.
	 * @return a new list where size is numSamples.
	 */
	public static List<String> createMixedDataset(List<String> yesWords, int numSamples, double fractionYes) {
		// Hint to the ArrayList that it will need to grow to numSamples size:
		List<String> output = new ArrayList<>(numSamples);
		
		
		Random rand = new Random();
		
		//  select numSamples * fractionYes words from yesWords; create the rest as
		for (int i = 0; i< fractionYes *numSamples; i++) {
			int index = rand.nextInt(yesWords.size());
				output.add(yesWords.get(index));
		}
		//create the rest of the words
		for (int i =0; i < (1- fractionYes)*numSamples; i++) {
			String noWords = generateNoWords();
			output.add(noWords);
		}
		System.out.println("output is" + output.get(0) + " & " + output.get(output.size()-1));
		// no words.
		return output;
	}
	
	public static String generateNoWords() {
		Random rand = new Random();
		int length = rand.nextInt(10);
		length = length + 1;
		String s = "";
		for (int i =0;i < length; i++) {
			int charRand = rand.nextInt(123 - 97);
			charRand = charRand + 97;
			s =s +(char)charRand;
		}
		return s;
	}

	/**
	 * This is **an** entry point of this assignment.
	 * 
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = CheckSpelling.loadDictionary();

		// --- Create a bunch of data structures for testing:
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		
		// --- OK, so that was a biased experiment (the answer to every question was yes!).
		// Let's try 10% yesses.
		for (int i = 0; i < 10; i++) {
			// --- Create a dataset of mixed hits and misses with p=i/10.0
			List<String> hitsAndMisses = createMixedDataset(listOfWords, 10_000, i / 10.0);

			System.out.println("i="+i);
			// --- Time the data structures.
			CheckSpelling.timeLookup(hitsAndMisses, treeOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, hashOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, bsl);
			CheckSpelling.timeLookup(hitsAndMisses, trie);
			CheckSpelling.timeLookup(hitsAndMisses, hm100k);
		}
	}
}

package edu.smith.cs.csc212.speller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class contains experimentation code.
 * @author jfoley
 *
 */
public class CheckSpelling {
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		// Read all lines from a file:
		// This file has one word per line.
		List<String> words = WordSplitter.readUTF8File("src/main/resources/words")
				.lines()
				.collect(Collectors.toList());
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			}
		}
		
		long endLookup = System.nanoTime();
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println("  "+dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	/**
	 * This is **an** entry point of this assignment.
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		
		// --- Create a bunch of data structures for testing:
		long startTreeSet =   System.nanoTime();
		TreeSet<String> treeOfWords = new TreeSet<>();
		for (String w: listOfWords) {
			treeOfWords.add(w);
		}
		long endTreeSet = System.nanoTime();
		double timeTreeSet = (endTreeSet - startTreeSet) / 1e9;
		System.out.println("TreeSet Time " + listOfWords.size() + " entries in " + timeTreeSet +" seconds.");
		
		long startHashSet =   System.nanoTime();
		
		HashSet<String> hashOfWords = new HashSet<>();
		for (String w: listOfWords) {
			hashOfWords.add(w);
		}
		long endHashSet = System.nanoTime();
		double timeHashSet = (endHashSet - startHashSet) / 1e9;
		System.out.println("HashSet Time " + hashOfWords.size() + " entries in " + timeHashSet +" seconds.");
		
		
		
		long startSortedStringListSet =   System.nanoTime();
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		long endSortedStringListSet = System.nanoTime();
		double timeSortedStringListSet = (endSortedStringListSet - startSortedStringListSet) / 1e9;
		System.out.println("SortedStringListSet Time " + listOfWords.size() + " entries in " + timeSortedStringListSet +" seconds.");
		
		
		
		CharTrie trie = new CharTrie();
		long startCharTrie =   System.nanoTime();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		long endCharTrie = System.nanoTime();
		double timeCharTrie = (endCharTrie - startCharTrie) / 1e9;
		System.out.println("CharTrie Time " + listOfWords.size() + " entries in " + timeCharTrie +" seconds.");
		
		
		LLHash hm100k = new LLHash(100000);
		long startLLHash =   System.nanoTime();
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		long endLLHash = System.nanoTime();
		double timeLLHash = (endLLHash - startLLHash) / 1e9;
		System.out.println("LLHashSet Time " + listOfWords.size() + " entries in " + timeLLHash +" seconds.");
		
		// --- Make sure that every word in the dictionary is in the dictionary:
		//     This feels rather silly, but we're outputting timing information!
		timeLookup(listOfWords, treeOfWords);
		timeLookup(listOfWords, hashOfWords);
		timeLookup(listOfWords, bsl);
		timeLookup(listOfWords, trie);
		timeLookup(listOfWords, hm100k);
		
		
		
	
		// --- print statistics about the data structures:
		System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());

		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		System.out.println("Done!");
	}
}

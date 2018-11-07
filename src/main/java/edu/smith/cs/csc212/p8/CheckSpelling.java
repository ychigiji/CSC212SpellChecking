package edu.smith.cs.csc212.p8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CheckSpelling {
	public static List<String> loadDictionary() {
		long start = System.currentTimeMillis();
		List<String> words;
		try {
			words = Files.readAllLines(new File("src/main/resources/words").toPath());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't find dictionary.", e);
		}
		long end = System.currentTimeMillis();
		double time = (end - start) / 1e3;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
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
		System.out.println(dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	
	public static void main(String[] args) {
		List<String> listOfWords = loadDictionary();
		
		// Timing for loading these words into a TreeMap.
		long startTree = System.currentTimeMillis();
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		long endTree = System.currentTimeMillis();
		double treeTime = (endTree - startTree) / 1e3;

		// Timing for loading these words into a HashMap.
		long startHash = System.currentTimeMillis();
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		long endHash = System.currentTimeMillis();
		double hashTime = (endHash - startHash) / 1e3;

		System.out.println("Times: hash: "+hashTime+ " tree: "+treeTime);
		
		// Make sure that every word in the dictionary is in the dictionary:
		timeLookup(listOfWords, treeOfWords);
		timeLookup(listOfWords, hashOfWords);
		
		// Looking up in a list is so slow, we need to sample:
		System.out.println("Start of list: ");
		timeLookup(listOfWords.subList(0, 1000), listOfWords);
		System.out.println("End of list: ");
		timeLookup(listOfWords.subList(listOfWords.size()-100, listOfWords.size()), listOfWords);
		
		// Binary-Search List:
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		timeLookup(listOfWords, bsl);
		
		System.out.println("Done!");
	}
}

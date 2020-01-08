package edu.smith.cs.csc212.speller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * @author jfoley
 *
 */
public class BookExperiment {
	
	static List<String> loadTextFileToWords(String fileName) {
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = WordSplitter.readUTF8File(fileName)) {
			reader.lines().forEach((String line) -> {
				for (String word : WordSplitter.splitTextToWords(line)) {
					words.add(word);
				}
			});	
		} catch (IOException e) {
			throw new RuntimeException("Error reading file: "+fileName, e);
		}
		
		return words;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> wordsInFrankenstein = loadTextFileToWords("src/main/resources/frankenstein.txt");
		System.out.println("Frankenstein contains approximately: "+wordsInFrankenstein.size()+" words.");

		//Load the dictionary
		List<String> listOfWords = CheckSpelling.loadDictionary();
		
		
		//Create a bunch of data structures for testing;
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie();
		for (String w: listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		for (String w: listOfWords){
			hm100k.add(w);
		}
		
		//Time Data Structures
		CheckSpelling.timeLookup(wordsInFrankenstein, treeOfWords);
		CheckSpelling.timeLookup(wordsInFrankenstein, hashOfWords);
		CheckSpelling.timeLookup(wordsInFrankenstein, bsl);
		CheckSpelling.timeLookup(wordsInFrankenstein, trie);
		CheckSpelling.timeLookup(wordsInFrankenstein, hm100k);

		// Cjecking if a word is misspellet
		List<String> misSpelt = new ArrayList<>();
		
		int found =0;
		for(String w : wordsInFrankenstein) {
			if (hashOfWords.contains(w)) {
				found++;
			} else {
				misSpelt.add(w);
			}
		}
		//Ratio of misspelled
		System.out.println("The size of the mispelled words is" + misSpelt.size());
		System.out.println("Some of the mispelled words are" + misSpelt);

	}
}

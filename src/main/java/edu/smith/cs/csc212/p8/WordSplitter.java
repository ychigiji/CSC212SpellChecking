package edu.smith.cs.csc212.p8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class provides a word splitter that will do a pretty good job for your game.
 * @author jfoley
 *
 */
public class WordSplitter {

	/**
	 * This is needed for my solution of splitting into words.
	 * Regular expressions are a type of graph.
	 */
	private static Pattern spacesOrPunctuation = 
			Pattern.compile("(\\s+|\\p{Punct})");
	
	/**
	 * I'm giving you a version of this that is slightly better than
	 * the String.split(" ") that I used in lecture.
	 * 
	 * @param text - text that may contain 0 or more words.
	 * @return words - the words in the input text.
	 */
	public static List<String> splitTextToWords(String text) {
		List<String> words = new ArrayList<String>();
		
		for (String token : spacesOrPunctuation.split(text.toLowerCase())) {
			token = token.trim();
			if (token.isEmpty()) {
				continue;
			}
			
			words.add(token);
		}
		
		return words;
	}
	
	/**
	 * Convince Java to open the given file with a UTF-8 encoding; ignoring whatever your OS (e.g, Windows) tells it is the default.
	 * @param fileName - the path to the file, e.g., "src/main/resources/words"
	 * @return a buffered-reader object; kind of like a {@code Iterator<String>}
	 */
	public static BufferedReader readUTF8File(String fileName) {
		File path = new File(fileName);
		if (!path.exists() || !path.isFile() || !path.canRead()) {
			throw new RuntimeException("Couldn't read file: "+fileName+" are you sure you typed the path right?");
		}
		try {
			return new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException("Error opening file: "+fileName, e);
		}
	}

}

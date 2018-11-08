package edu.smith.cs.csc212.p8;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This is a Character Trie that stores Strings!
 * @author jfoley
 *
 */
public class CharTrie extends AbstractSet<String> {
	/**
	 * The nodes don't actually store characters in them - the links do.
	 */
	Node root = new Node();
	/**
	 * This gets updated to account for the size.
	 */
	int size = 0;

	public void insert(String word) {
		LinkedList<Character> chars = new LinkedList<>();
		for (char c : word.toCharArray()) {
			chars.add(c);
		}
		root.insert(chars);
		size++;
	}
	
	public boolean contains(Object o) {
		String word = (String) o;
		LinkedList<Character> chars = new LinkedList<>();
		for (char c : word.toCharArray()) {
			chars.add(c);
		}
		return root.find(chars);
	}
	
	private static class Node {
		boolean terminal;
		Node[] links;
		
		public Node() {
			this.terminal = false;
			this.links = new Node[27];
		}
		
		public int getLinkIndex(char c) {
			char lower = Character.toLowerCase(c);
			if (lower == '-') {
				return this.links.length - 1;
			}
			if (lower > 'z' || lower < 'a') {
				throw new RuntimeException("Bad character: "+lower);
			}
			return lower - 'a';	
		}
		
		public void insert(LinkedList<Character> chars) {
			if (chars.isEmpty()) {
				this.terminal = true;
			} else {
				int link = getLinkIndex(chars.pollFirst());
				if (links[link] == null) {
					links[link] = new Node();
				}
				links[link].insert(chars);
			}
		}
		
		public boolean find(LinkedList<Character> chars) {
			if (chars.isEmpty()) {
				return this.terminal;
			} else {
				int link = getLinkIndex(chars.pollFirst());
				if (links[link] == null) {
					return false;
				}
				return links[link].find(chars);
			}
		}
		
		public int countNodes() {
			int count = 1;
			return count;
		}
	}
	
	/**
	 * How do you count the nodes in this Trie?
	 * Recursion!
	 * @return the number of nodes in the trie.
	 */
	public int countNodes() {
		return root.countNodes();
	}

	/**
	 * We would need to create an object that kept the recursion state around.
	 * We will talk about depth-first search (part of the solution to this) next week.
	 */
	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException("Trie Traversal is really hard.");
	}

	/**
	 * Just keeping track of the size is cheap. We could also count terminal nodes...
	 */
	@Override
	public int size() {
		return size;
	}
}

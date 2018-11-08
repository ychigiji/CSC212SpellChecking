# CSC212P8

In this assignment, we will compare and contrast different data structures for creating a SpellChecker.

## Rubric (100)

This assignment is out of 100 points. There are two pure coding sections, adding up to (20) points. The rest involves a little more data analysis.

### (30) Report & Reflection.

This assignment is more like a scientific investigation. Prepare a report with answers to these questions, and submit it as a PDF.

In your report, have a conclusion section where you recommend one of these data structures for "Spell-Checking". Justify your selection.

### (15) Measure Creation (Insertion) Speed.

We create a number of data structures: HashSet, TreeSet, SortedStringListSet, CharTrie. Figure out how many nanoseconds per insert are required. This will involve studying my ``System.nanoTime()`` timing code. Since nanoseconds are metric, they are 10^-9 seconds, or 1e-9 in Java notation (hence my division by 1e9 to convert to seconds).

- How long does it take to fill each data structure? 
- Plot insertion time per element for each of these data structures.


### (20) Plot Query Speed

I have devised a method ``timeLookup`` that calculates per-item query time for all the words in a structure. It also prints out the "fraction found" of the dataset. 

- Construct a dataset that has Strings that are both in and not in the dictionary.
- For full credit, devise a method to inject some percentage of hits and misses. Create a line plot as the percentage of hits goes from (0.0 to 1.0) in steps of 0.1, where each line is a different data structure.

### (15) Spell-check a Project Gutenberg book
- What is the ratio that's "mis-spelled"?
- Are the query speeds the same over real-world data?
- What are some of the words that are "mis-spelled"?
- I gave you ``WordSplitter`` again.

### (10) CharTrie.countNodes()

Study the ``CharTrie`` implementation and complete the countNodes method on the ``CharTrie.Node`` class. A recursive solution will be simplest.

You might want to create a unit test so that you count the nodes of a CharTrie that you can draw by hand. (So you know if you get it correct).

### (10) SortedStringListSet.binarySearch

Right now, this data structure merely calls Java's built-in ``Collections.binarySearch``. Replace it with your own implementation.

For bonus points, find out why [most binary search implementations are incorrect](https://ai.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html). Try to fix it in your implementation.


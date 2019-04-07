  
/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Ankith Kandikonda
 * avk393
 * 16215
 * Esha Makwana	
 * eam4298
 * 16215
 * Slip days used: <0>
 * Git URL: https://github.com/EE422C/project-3-wordladder-pair-51
 * Spring 2019
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	static int rungNum; 
	static ArrayList<String> input= new ArrayList<String>(); 
	static String main_start;
	static String main_end;
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		input.clear();
		input = parse(kb);
		main_start = input.get(0).toLowerCase();
		main_end = input.get(1).toLowerCase();
		rungNum = 0;
		if (main_start.equals(main_end)) {
			printLadder(input);
		}

		ArrayList<String> wordLadder = getWordLadderBFS(main_start, main_end);
		printLadder(wordLadder);
		rungNum = 0;
		ArrayList<String> wordLadder1 = getWordLadderDFS(main_start, main_end);
		printLadder(wordLadder1);
	}
	
	public static void initialize() {
		rungNum = 0;
		main_start = null;
		main_end = null;
		input.clear();
	}

	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word.
	 * If command is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> words = new ArrayList<String>();
		String in = keyboard.next();
		in = in.toLowerCase();
		if (in.equals("/quit")) {
			return words;
		}
		words.add(in);
		words.add(keyboard.next());
		return words;
	}

	public static int recursiveDFS(String start, String end, ArrayList<WordTree> wordTree, int index) {
		if(start.equals(end)) return index;

		WordTree obj = wordTree.get(index);
		ArrayList<Integer> children = obj.getChildren();

		for (int i=0; i<children.size(); i++) {
			String child = wordTree.get(children.get(i)).getWord();
			int node = recursiveDFS(child,end,wordTree,children.get(i));
			if(node!=-1) return node;
		}

		return -1;
	}

	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		start = start.toLowerCase();
		end = end.toLowerCase();
		main_start = start;
		main_end = end;
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		Set<String> dict = makeDictionary();
		ArrayList<WordTree> wordTree = WordTree.makeTree(dict,start);

		int index = recursiveDFS(start,end,wordTree, 0);
		if(index==-1) return null;
		ArrayList<String> wordLadder = traverseList(wordTree,wordTree.get(index),start);

		// need to reverse wordLadder?
		return wordLadder; // replace this line later with real return
	}

	// not sure about implementation
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toLowerCase();
		end = end.toLowerCase();
		main_start = start;
		main_end = end;
		Set<String> dict = makeDictionary();
		ArrayList<WordTree> wordTree = WordTree.makeTree(dict,start);
		WordTree obj;

		Iterator<WordTree> it = wordTree.iterator();
		// loops through word tree to find end word
		while(true){
			if(it.hasNext()) {
				obj = it.next();
				if(obj.getWord().equals(end)) break;
			}
			else return null;
		}

		// backtracks through wordTree given the final word and returns word ladder
		ArrayList<String> wordLadder = traverseList(wordTree,obj,start);
		return wordLadder;
	}


	public static void printLadder(ArrayList<String> ladder) {
		if (ladder == null) {
			System.out.println("no word ladder can be found between "+ main_start +" and " + main_end + ".");
			return;
		}
		System.out.println("a " + rungNum + "-rungword ladder exists between " + main_start + " and " + main_end + ".");
		Iterator<String> it = ladder.iterator();
		while(it.hasNext()) {
			String obj = (String)it.next();
			obj.toLowerCase();
			System.out.println(obj);
		}
	}

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}

	public static ArrayList<String> traverseList(ArrayList<WordTree> wordTree, WordTree obj, String start){
		List<String> map = new LinkedList<>();
		ArrayList<String> wordLadder = new ArrayList<>();

		if(obj==null) return null;
		// backtracking through tree to find path back to root
		// saving each string through backtrack to add to word ladder
		while(obj.getParentIndex()!=-1){
			map.add(obj.getWord());
			obj = wordTree.get(obj.getParentIndex());
		}
		map.add(start);
		// reversing map
		for (int i=map.size()-1; i>=0; i--){
			wordLadder.add(map.get(i));
			rungNum++;
		}
		return wordLadder;
	}
}

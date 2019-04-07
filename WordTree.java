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

public class WordTree {
    private String word;
    private int level;
    private int parentIndex;        // index of
    ArrayList<Integer> children;


    public WordTree(String word, int level, int parentIndex) {
        this.word = word;
        this.level = level;
        this.parentIndex = parentIndex;
        children = new ArrayList<Integer>();
    }

    public String getWord() { return word; }

    public int getLevel() { return level;  }

    public int getParentIndex() { return parentIndex; }

    public ArrayList<Integer> getChildren() { return children; }

    public void addChild(Integer child) { children.add(child); }

    public static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diff++;
                if(diff>1) return false;
            }
        }

        if(diff==1) return true;
        else return true;
    }

    public static ArrayList<WordTree> makeTree(Set<String> dict, String start) {
        if(dict == null) return null;                               // catch null case
        ArrayList<WordTree> wordTrees = new ArrayList<WordTree>();      // tree structure
        Queue<WordTree> list = new LinkedList<>();
        Iterator<String> it = dict.iterator();

        //adding root node (start word) to tree
        WordTree parentWord = new WordTree(start, 0, -1);
        wordTrees.add(parentWord);
        list.add(parentWord);
        int parentIndex = 0;
        int index = 1;

        // looping through dictionary and add children to ArrayList WordTree
        // list has words we have added to tree but not checked and need to find children for if possible
        while(true) {
            String dictWord;
            while (it.hasNext()) {
                dictWord = it.next().toLowerCase();
                if (differByOne(parentWord.word, dictWord)==true) {
                    WordTree word = new WordTree(dictWord, parentWord.level+1, parentIndex);
                    wordTrees.add(word);
                    parentWord.addChild(index);         // add to list of children for parent
                    list.add(word);                     // add to queue of words to traverse
                    it.remove();                        // remove node from dictionary so we don't create duplicate nodes
                    index++;                            // keep track of index of node
                }
            }
            list.remove(parentWord);                    // remove word from queue
            parentIndex++;

            if(!list.isEmpty()) {
                parentWord = list.element();
                it = dict.iterator();       // how to reset the iterator?
            }
            else break;
        }

        return wordTrees;
    }

}

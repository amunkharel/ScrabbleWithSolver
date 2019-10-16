/**
 * Project 3 - CS351, Fall 2019, Dictionary to store words from the file
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary{

    /** Filename of the dictionary */
    private String filename;

    /** Trie objects to store the words */
    private Trie root = new Trie();

    /** Number of letters in English words*/
    private int size = 26;

    /**
     * Constructor for Dictionary
     *
     * @param String fileName, name of the file
     */
    public Dictionary(String filename) {
        this.filename = filename;
    }

    /**
     * Reads words from the file and stores it in
     * the trie data structure
     */
    public void readAndStoreDictionaryInTrie() {
        int counter = 0;
        String pathname = "resources/" + filename;

        try {
            Scanner sc = new Scanner(new File(pathname));
            while(sc.hasNext()) {
                String str = sc.nextLine();
                if(!str.isEmpty()) {
                    root.insertWord(root, str);
                    counter++;
                }
            }

            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if a word is valid or not
     *
     * @param String word, word passed to be checked
     * @return boolean, checks if a move is valid or not
     */
    public boolean isValidMove(String word ) {
        if(root.isValidWord(word, root)) {
            return true;
        }
        return  false;
    }

    /**
     * Returns all the valid words if a word has
     * one wildcard
     *
     * @param String word, word with one wildcard
     * @return boolean[] , returns boolean where word is valid
     * if boolean [0] is true, 'a' is a valid word instead of wildcard
     */
    public boolean[] validWordsForOneWildCard(String word) {
        boolean [] validwords  = new boolean[size];
        validwords = root.validWordsForOneWildCard(word, root);
        return validwords;
    }

    /**
     * Returns all the valid words if a word has
     * two wildcard
     *
     * @param String word, word with two wildcard
     * @return boolean[][] , returns boolean where word is valid
     * if boolean [0][1] is true, 'a' and 'b' is a valid word instead of wildcards
     */
    public boolean [][] validWordsForTwoWildCard(String word) {
        boolean [][] validwords = new boolean[size][size];
        validwords = root.validWordsForTwoWildCard(word, root);
        return  validwords;
    }

}
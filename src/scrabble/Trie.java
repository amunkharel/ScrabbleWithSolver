/**
 * Project 3 - CS351, Fall 2019, Trie for storing words
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;

public class Trie {

    /** Number of letters */
    private int size = 26;

    /**Child of a parent trie */
    private Trie[] child = new Trie[size];

    /** Checks if a word is valid*/
    boolean isValidWord;


    /**
     * Constructor for Trie.
     *
     */
    public Trie() {
        isValidWord = false;

        for(int i = 0; i < size; i ++) {
            child[i] = null;
        }
    }

    /**
     * Inserts word into the data structure
     *
     * @param Trie root,  Root of the Trie
     * @param String word, Word to be inserted
     */

    public void insertWord(Trie root, String word) {
        int n = word.length();
        word = word.toLowerCase();
        Trie trie = root;
        for(int i = 0; i < n; i++) {
            int index = word.charAt(i) - 'a';

            if(trie.child[index] == null) {
                trie.child[index] = new Trie();
            }

            trie = trie.child[index];
        }
        trie.isValidWord = true;
    }

    /**
     * Checks if a word is valid or not
     *
     * @param Trie root,  Root of the Trie
     * @param String word, Word to be inserted
     * @return boolean, returns true if valid word and vice versa
     */
    public boolean isValidWord(String word, Trie root) {
        int n = word.length();
        word = word.toLowerCase();

        Trie trie = root;

        for(int i = 0; i < n; i ++) {
            int index = word.charAt(i) - 'a';
            if(trie.child[index] == null) {
                return false;
            }

            trie = trie.child[index];
        }

        if(trie.isValidWord) {
            return true;
        }

        return false;
    }

    /**
     * Returns a list of valid word with one wildcard
     *
     * @param Trie root,  Root of the Trie
     * @param String word, Word to be inserted
     * @return boolean[], valid words for a word with one wildcard
     */
    public boolean[] validWordsForOneWildCard(String word, Trie root) {
        boolean [] validwords  = new boolean[size];
        String newWord = "";
        for(int i = 0; i < size; i++) {
            int int_character = 'a' + i;
            char character = (char) int_character;

            newWord = word.replace('*', character);

            validwords[i] = this.isValidWord(newWord, root);
        }

        return validwords;
    }

    /**
     * Returns a list of valid word with two wildcard
     *
     * @param Trie root,  Root of the Trie
     * @param String word, Word to be inserted
     * @return boolean[][], valid words for a word with two wildcard
     */
    public boolean [][] validWordsForTwoWildCard(String word, Trie root) {
        boolean [][] validwords = new boolean[size][size];
        int firstCardIndex = 0;

        boolean firstFound = false;
        int secondCardIndex = 0;

        for (int a = 0; a < word.length(); a++) {
            if(word.charAt(a) == '*' && firstFound == false) {
                firstCardIndex = a;
                firstFound = true;
            }

            if(firstFound && word.charAt(a) == '*') {
                secondCardIndex = a;
            }
        }

        for(int i = 0; i < size; i++) {

            String newWord = "";
            String secondNewWord = "";
            int int_first_character = 'a' + i;
            char character = (char) int_first_character;

            newWord = word.substring(0, firstCardIndex) + character
                    + word.substring(firstCardIndex + 1);
            for(int j = 0; j < size; j++) {
                int int_second_character = 'a' + j;
                character = (char) int_second_character;

                secondNewWord = newWord.substring(0, secondCardIndex) +
                        character + newWord.substring(secondCardIndex + 1);

                validwords[i][j] = this.isValidWord(secondNewWord, root);

            }

        }


        return validwords;
    }

}

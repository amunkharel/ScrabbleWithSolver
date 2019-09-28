package scrabble;

public class Trie {

    private int size = 26;

    private Trie[] child = new Trie[size];

    boolean isValidWord;


    public Trie() {
        isValidWord = false;

        for(int i = 0; i < size; i ++) {
            child[i] = null;
        }
    }

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

                if(validwords[i][j]) {
                    System.out.println(i + " " + j);
                    System.out.println(secondNewWord);
                }
            }

        }


        return validwords;
    }

}

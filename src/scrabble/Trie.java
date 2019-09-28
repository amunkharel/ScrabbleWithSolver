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


}

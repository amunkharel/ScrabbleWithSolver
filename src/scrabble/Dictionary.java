package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Source -- https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
public class Dictionary{
    private String filename;
    private Trie root = new Trie();
    private int size = 26;

    public Dictionary(String filename) {
        this.filename = filename;
    }

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

                //System.out.println(str);
            }

            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean isValidMove(String word ) {
        if(root.isValidWord(word, root)) {
            return true;
        }
        return  false;
    }

    public boolean[] validWordsForOneWildCard(String word) {
        boolean [] validwords  = new boolean[size];
        validwords = root.validWordsForOneWildCard(word, root);
        return validwords;
    }

    public boolean [][] validWordsForTwoWildCard(String word) {
        boolean [][] validwords = new boolean[size][size];
        validwords = root.validWordsForTwoWildCard(word, root);
        return  validwords;
    }

}


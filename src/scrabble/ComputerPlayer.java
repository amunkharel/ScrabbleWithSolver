package scrabble;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer {
    private String tray = "abc";
    private Board board ;
    private char [][] boardArray;
    private Dictionary dictionary;
    private Bag bag;
    private List<Cordinate> validCordinates;

    public ComputerPlayer(Board board, Dictionary dictionary, Bag bag) {

        validCordinates  = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
    }

    public void setTray(String tray) {
        this.tray = tray;
    }

    public void getValidCordinate() {
        validCordinates = board.getValidCordinates();
    }

    public void makeCombinationandMoves() {
        List<String> letterMix = new ArrayList<>();

        letterMix.add(tray);

        int combinations = 1;
        while(combinations < tray.length()){
            char[] stringToArray = tray.toCharArray();
            int a = stringToArray.length - combinations;
            int b = stringToArray.length;
            makeCombination(stringToArray, b, a, letterMix);
            combinations++;
        }

        List<String> letterPermutations = new ArrayList<>();

        for(String combination: letterMix) {
            makePermutation(combination, "", letterPermutations);
        }

        System.out.println(letterPermutations);
        System.out.println(letterPermutations.size());
    }

    public void makeCombination(char[] arr, int a, int b, List<String> letterCombinations){
        char data[] = new char[b];
        combinations(arr, data, 0, a-1, 0, b, letterCombinations);
    }

    public void combinations(char[] arr, char[] data, int start, int end, int index, int r, List<String> letterCombinations){
        if (index == r)
        {
            String startComb = "";
            for (int j=0; j<r; j++) {
                startComb = startComb + data[j];
            }
            if(!letterCombinations.contains(startComb)) {
                letterCombinations.add(startComb);
            }
            return;
        }
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinations(arr, data, i+1, end, index+1, r, letterCombinations);
        }
    }

    public void makePermutation(String str, String finalComb, List<String>letterPermuts){
        if (str.length() == 0) {
            //System.out.println(finalComb);

            if(!letterPermuts.contains(finalComb)) {
                letterPermuts.add(finalComb);
            }
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String ros = str.substring(0, i) + str.substring(i + 1);
            makePermutation(ros, finalComb + ch, letterPermuts);
        }
    }

    public void makeIntialMoves(String word) {
        int maxLeftIndex = 0;
        boolean leftIndexFound = false;
        for (int i = 0; i < validCordinates.size(); i++) {
            int x = validCordinates.get(i).getX();
            int y = validCordinates.get(i).getY();
            if(isFree(x, y - 1)) {
                for(int j = y; j >= 0; j-- ) {
                    if(isFree(x,j) && !leftIndexFound) {
                        maxLeftIndex++;
                    }
                    else {
                        leftIndexFound = true;
                    }
                }
                if(word.length() <= maxLeftIndex) {

                    int stoppingIndex = y - maxLeftIndex + 1;
                    System.out.println(stoppingIndex);
                    System.out.println(x + " " + y);
                    //makeleftMove(word, x, y);
                }
            }
        }
    }

    public void makeleftMove(String word, int row, int column) {
        int numberOfWildCard = 0;
        String completeWord = word;
        //int wordStartIndex = column - word.length() + 1;
        for(int i = column + 1; i < board.getSize(); i++) {
            if(isFree(row, i)) {
                break;
            }
            else  {
                completeWord = completeWord + boardArray[row][i];
            }
        }
        System.out.println(completeWord);

        for(int j = 0; j < completeWord.length(); j ++) {
            if(completeWord.charAt(j) == '*') {
                numberOfWildCard++;
            }
        }

        System.out.println(numberOfWildCard);
        /*System.out.println(dictionary.isValidMove(completeWord));

        if(dictionary.isValidMove(completeWord)) {

        } */


    }

    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3' || boardArray[x][y] == '4' ) {
            return true;
        }
        return  false;
    }
}

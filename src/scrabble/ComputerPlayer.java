package scrabble;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer {

    private int numberOfAlphabets;
    private String tray = "le*mdoe";
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
        this.numberOfAlphabets = 26;
    }

    public void setTray(String tray) {
        this.tray = tray;
    }

    public void getValidCordinate() {
        validCordinates = board.getValidCordinates();
        /*for(int i = 0; i < validCordinates.size(); i++) {
            System.out.println(validCordinates.get(i).getX() +
                    " " + validCordinates.get(i).getY());
        } */
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

            makeIntialMoves(finalComb);

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
        int maxRightIndex = 0;
        boolean leftIndexFound = false;
        for (int i = 0; i < validCordinates.size(); i++) {
            int x = validCordinates.get(i).getX();
            int y = validCordinates.get(i).getY();

            if(!boardOutOfIndex(x, y-1)) {
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
                        makeleftMove(word, x, y);
                    }
                }
            }

            if(!boardOutOfIndex(x, y+1)) {
                if(isFree(x, y + 1)) {
                    for(int j = y; j < board.getSize(); j++ ) {
                        if(isFree(x,j) ) {
                            maxRightIndex++;
                        }
                        else {
                            break;
                        }
                    }

                    if(word.length() <= maxRightIndex) {
                        makeRightMove(word, x, y);
                    }
                }
            }

        }
    }

    public void makeRightMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard = new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard = new boolean[numberOfAlphabets][numberOfAlphabets];

        int [] wildcardIndex = new int[2];
        int numberOfWildCard = 0;
        String completeWord = "";

        int wordStartIndex = column;
        int newWordStartIndex = wordStartIndex;
        int wordStopIndex = column + word.length() - 1;
        int newWordStopIndex = wordStopIndex;
        if(!boardOutOfIndex(row, wordStartIndex - 1) ) {
            if(!isFree(row, wordStartIndex - 1)) {
                for(int i = wordStartIndex - 1; i >= 0; i -- ) {
                    if(isFree(row, i)) {
                        break;
                    }
                    else{
                        newWordStartIndex = i;
                    }
                }
            }
        }

        for(int j = newWordStartIndex; j < wordStartIndex; j++) {
            completeWord = completeWord + boardArray[row][j];
        }

        completeWord = completeWord + word;

        if(!boardOutOfIndex(row, wordStopIndex + 1)) {
            if(!isFree(row, wordStopIndex + 1)) {
                for(int i = wordStopIndex + 1; i < board.getSize(); i++) {
                    if(isFree(row, i)) {
                        break;
                    }
                    else {
                        completeWord = completeWord + boardArray[row][i];
                        newWordStopIndex = i;
                    }
                }
            }
        }

        int a = 0;
        for(int j = 0; j < word.length(); j ++) {

            if(word.charAt(j) == '*') {
                numberOfWildCard++;
                wildcardIndex[a] = j;
                a++;
            }
        }

        if(numberOfWildCard == 0) {
            if(dictionary.isValidMove(completeWord)) {
                checkForValidInAllDirection(row, wordStartIndex, word);
            }
        }

        else if(numberOfWildCard == 1) {
            validWordsOneWildCard = dictionary.validWordsForOneWildCard(completeWord);

            for (int k = 0; k < numberOfAlphabets; k ++) {
                if(validWordsOneWildCard[k]) {
                    String newWord = "";
                    int int_first_character = 'A' + k;
                    char character = (char) int_first_character;

                    newWord = word.substring(0, wildcardIndex[0]) + character
                            + word.substring(wildcardIndex[0] + 1);

                    checkForValidInAllDirection(row, wordStartIndex, newWord);
                }
            }
        }

        else if(numberOfWildCard == 2) {
            validWordsTwoWildCard = dictionary.validWordsForTwoWildCard(completeWord);
            for(int k = 0; k < numberOfAlphabets; k++) {
                for(int l = 0; l < numberOfAlphabets; l++) {
                    if(validWordsTwoWildCard[k][l]) {
                        String newWord = "";
                        int int_first_character = 'A' + k;
                        char first_char = (char) int_first_character;
                        int int_second_character = 'A' + l;
                        char second_char = (char) int_second_character;

                        newWord = word.substring(0, wildcardIndex[0]) + first_char +
                                word.substring(wildcardIndex[0] + 1, wildcardIndex[1])
                                + second_char + word.substring(wildcardIndex[1] + 1);

                        checkForValidInAllDirection(row, wordStartIndex, newWord);
                    }
                }
            }
        }




    }

    public void makeleftMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard  = new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard = new boolean[numberOfAlphabets][numberOfAlphabets];
        int [] wildcardIndex = new int[2];

        int numberOfWildCard = 0;

        String completeWord = "";


        int wordStartIndex = column - word.length() + 1;
        int newWordStartIndex = wordStartIndex;

        if(!boardOutOfIndex(row, wordStartIndex - 1) ) {
            if(!isFree(row, wordStartIndex - 1)) {
                for(int i = wordStartIndex - 1; i >= 0; i -- ) {
                    if(isFree(row, i)) {
                        break;
                    }
                    else{
                        newWordStartIndex = i;
                    }
                }
            }
        }

        for(int j = newWordStartIndex; j < wordStartIndex; j++) {
            completeWord = completeWord + boardArray[row][j];
        }

        completeWord = completeWord + word;

        for(int i = column + 1; i < board.getSize(); i++) {
            if(isFree(row, i)) {
                break;
            }
            else  {
                completeWord = completeWord + boardArray[row][i];
            }
        }


        int a = 0;
        for(int j = 0; j < word.length(); j ++) {

            if(word.charAt(j) == '*') {
                numberOfWildCard++;
                wildcardIndex[a] = j;
                a++;
            }
        }

        if(numberOfWildCard == 0) {
            if(dictionary.isValidMove(completeWord)) {
                checkForValidInAllDirection(row, wordStartIndex, word);
            }
        }

        else if(numberOfWildCard == 1) {
            validWordsOneWildCard = dictionary.validWordsForOneWildCard(completeWord);

            for (int k = 0; k < numberOfAlphabets; k ++) {
                if(validWordsOneWildCard[k]) {
                    String newWord = "";
                    int int_first_character = 'A' + k;
                    char character = (char) int_first_character;

                    newWord = word.substring(0, wildcardIndex[0]) + character
                            + word.substring(wildcardIndex[0] + 1);

                    checkForValidInAllDirection(row, wordStartIndex, newWord);
                }
            }
        }

        else if(numberOfWildCard == 2) {
            validWordsTwoWildCard = dictionary.validWordsForTwoWildCard(completeWord);
            for(int k = 0; k < numberOfAlphabets; k++) {
                for(int l = 0; l < numberOfAlphabets; l++) {
                    if(validWordsTwoWildCard[k][l]) {
                        String newWord = "";
                        int int_first_character = 'A' + k;
                        char first_char = (char) int_first_character;
                        int int_second_character = 'A' + l;
                        char second_char = (char) int_second_character;

                        newWord = word.substring(0, wildcardIndex[0]) + first_char +
                                word.substring(wildcardIndex[0] + 1, wildcardIndex[1])
                                + second_char + word.substring(wildcardIndex[1] + 1);

                        checkForValidInAllDirection(row, wordStartIndex, newWord);
                    }
                }
            }
        }

    }

    public void checkForValidInAllDirection(int row, int column, String word) {
        int wordLength = word.length();
        int col = column;
        boolean isValidInAllDirection = true;
        for(int i = 0; i < wordLength; i++) {
            if(!validMoveTopDown(word.charAt(i), row, col)) {
                isValidInAllDirection = false;
                break;
            }
            col++;
        }
        if(isValidInAllDirection) {
            System.out.println(row + " " + column);
            System.out.println(word);
        }
    }

    public boolean validMoveTopDown(char character, int row, int column) {
        int startIndex = 0;
        int stopIndex = 0;
        String completeWord = "";

        if(!boardOutOfIndex(row - 1, column) && isFree(row -1 , column) &&
                !boardOutOfIndex(row + 1, column) &&isFree(row + 1, column)) {
            return true;
        }

        else if(!boardOutOfIndex(row - 1, column) && isFree(row - 1, column) &&
                !boardOutOfIndex(row + 1, column) && !isFree(row + 1, column)) {
            completeWord = completeWord + character;
            for(int i = row + 1; i < board.getSize(); i++) {
                if(isFree(i, column)){
                    break;
                }
                else {
                    completeWord = completeWord + boardArray[i][column];
                }
            }

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else  {
                return false;
            }
        }

        else if(!boardOutOfIndex(row + 1, column) && isFree(row + 1, column) &&
                !boardOutOfIndex(row -1 , column) && !isFree(row -1 , column)) {
            startIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(isFree(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }
            for(int i = startIndex; i < row; i++) {
                completeWord = completeWord +boardArray[i][column];
            }
            completeWord = completeWord + character;
            if(dictionary.isValidMove(completeWord)) {
                return true;
            }

            else {
                return false;
            }
        }

        else if(!boardOutOfIndex(row + 1, column) && !isFree(row + 1, column) &&
                !boardOutOfIndex(row + 1, column) && !isFree(row -1 , column)) {
            startIndex = row;
            stopIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(isFree(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }

            for(int i = row + 1; i < board.getSize(); i++) {
                if(isFree(i, column)){
                    break;
                }
                else {
                    stopIndex ++;
                }
            }

            for(int i = startIndex; i < row; i++) {
                completeWord = completeWord +boardArray[i][column];
            }
            completeWord = completeWord + character;

            for (int i = row + 1; i <= stopIndex; i++) {
                completeWord = completeWord + boardArray[i][column];
            }

            if(dictionary.isValidMove(completeWord)) {
                return  true;
            }
            else {
                return false;
            }

        }

        return false;
    }


    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3' || boardArray[x][y] == '4' ) {
            return true;
        }
        return  false;
    }

    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > board.getSize() - 1 || j > board.getSize() - 1){
            return true;
        }
        return  false;
    }
}

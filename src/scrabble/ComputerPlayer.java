/**
 * Project 3 - CS351, Fall 2019, Computer Player who plays the game
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */
package scrabble;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComputerPlayer {

    /** Number of Alphabets in the English letters*/
    private int numberOfAlphabets;

    /** Tray of the board*/
    private String tray;

    /** Duplicate tray of the board*/
    private String duplicateTray;

    /** Board Object of the game*/
    private Board board ;

    /** 2d board array*/
    private char [][] boardArray;

    /** Dictionary used to make valid words*/
    private Dictionary dictionary;

    /** Bag of the game*/
    private Bag bag;

    /** List of valid cordinates where moves can be played*/
    private List<Cordinate> validCordinates;

    /** All the permutations of letters in the tray*/
    private List<String> letterPermutations = new ArrayList<>();

    /** Score object to get score*/
    private Score score;

    /**
     * Constructor for Computer Player
     *
     * @param Board board, Board where game is played
     * @param Dictionary dictionary, dictionary used in the game
     * @param Bag bag, Bag of Tiles
     * @param Score score, Score tracker for the game
     */
    public ComputerPlayer(Board board, Dictionary dictionary, Bag bag,
                          Score score) {

        validCordinates  = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
        this.numberOfAlphabets = 26;
        this.score = score;
    }

    /**
     * Sets tray for the computer
     *
     * @param String tray, Sets tray for the computer
     */
    public void setTray(String tray) {
        this.tray = tray;
    }

    /**
     * Asks for tiles from the bag
     */

    public void setTrayFromBag() {
        tray = "";
        duplicateTray = "";
        List<Tile> tiles = new ArrayList<Tile>();
        tiles = bag.giveTilesToTray(7);
        for (int i = 0; i < tiles.size(); i++ ) {
            tray =  tray + tiles.get(i).getLetter();
            duplicateTray = duplicateTray + tiles.get(i).getLetter();
        }
    }


    /**
     * Starts the AI of the game
     * Clears the previous created letter permutations first
     * get new valid cordinates where moves can be made
     * make combinations and permutations
     */
    public void startAI() {
        letterPermutations.clear();
        validCordinates = board.getValidCordinates();
        makeCombinations();
        for (int i = 0; i < letterPermutations.size(); i++) {
            makeIntialMoves(letterPermutations.get(i));
        }
    }

    /**
     * Makes combinations for letters in the tray
     * reference- https://www.geeksforgeeks.org/
     */
    public void makeCombinations() {
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

        for(String combination: letterMix) {
            makePermutation(combination, "", letterPermutations);
        }
    }

    /**
     * Make combination for each combination in the letter
     * reference - https://www.geeksforgeeks.org/
     */
    public void makeCombination(char[] arr, int a, int b,
                                List<String> letterCombinations){
        char data[] = new char[b];
        combinations(arr, data, 0, a-1, 0, b, letterCombinations);
    }

    /**
     * Make combinations for each combination in the letter
     * reference - https://www.geeksforgeeks.org/
     */
    public void combinations(char[] arr, char[] data,
                             int start, int end, int index,
                             int r, List<String> letterCombinations){
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

    /**
     * Make permutations for each combination in the letter
     * reference - https://www.geeksforgeeks.org/
     */
    public void makePermutation(String str, String finalComb,
                                List<String>letterPermuts){
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

    /**
     * Checks where a move of a permutation of word can
     * be made in the board (left, right, top, bottom)
     * If our word can fit in the gap, it allows the word to go through
     * that part of the board
     * @param String word, Permutation word of the tray
     */
    public void makeIntialMoves(String word) {
        int maxLeftIndex = 0;
        int maxRightIndex = 0;
        int maxTopIndex = 0;
        int maxBottomIndex = 0;
        for (int i = 0; i < validCordinates.size(); i++) {
            maxLeftIndex = 0;
            maxRightIndex = 0;
            maxTopIndex = 0;
            maxBottomIndex = 0;

            int x = validCordinates.get(i).getX();
            int y = validCordinates.get(i).getY();

            if(!boardOutOfIndex(x, y-1)) {
                if(isFree(x, y - 1)) {
                    for(int j = y; j >= 0; j-- ) {
                        if(isFree(x,j)) {
                            maxLeftIndex++;
                        }
                        else {
                            break;
                        }
                    }

                    //if valid makes leftMove
                    if(word.length() <= maxLeftIndex) {
                        maxLeftIndex = 0;
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
                    //if valid makes Right Move
                    if(word.length() <= maxRightIndex) {
                        maxRightIndex = 0;
                        makeRightMove(word, x, y);
                    }
                }
            }

            if(!boardOutOfIndex(x - 1, y)) {
                if(isFree(x -1, y)) {
                    for(int j = x; j >= 0; j-- ) {
                        if(!isFree(j,y) ) {
                            break;
                        }
                        else {
                            maxTopIndex++;
                        }
                    }

                    //if valid makes Top Move
                    if(word.length() <= maxTopIndex ) {
                        maxTopIndex  = 0;
                        makeTopMove(word, x, y);
                    }
                }
            }

            if(!boardOutOfIndex(x + 1, y)) {
                if(isFree(x + 1, y)) {
                    for(int j = x; j < board.getSize(); j++ ) {
                        if(isFree(j,y) ) {
                            maxBottomIndex++;
                        }
                        else {
                            break;
                        }
                    }

                    //if valid makes Bottom Move
                    if(word.length() <= maxBottomIndex) {
                        maxBottomIndex = 0;
                        makeBottomMove(word, x, y);
                    }
                }
            }




        }
    }

    /**
     * Starting makes Bottom moves and checks if move made in the bottom
     * is valid and then checks if bottom move is valid
     * in all direction or not
     *
     * @param String word, word made for bottom
     * @param int row, starting row
     * @param int column, starting column
     */
    public void makeBottomMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard = new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard =
                new boolean[numberOfAlphabets][numberOfAlphabets];

        int [] wildcardIndex = new int[2];
        int numberOfWildCard = 0;
        String completeWord = "";

        int wordStartIndex = row;
        int newWordStartIndex = wordStartIndex;
        int wordStopIndex = row + word.length() - 1;
        int newWordStopIndex = wordStopIndex;

        if(!boardOutOfIndex(wordStartIndex - 1, column) ) {
            if(!isFree(wordStartIndex - 1, column)) {
                for(int i = wordStartIndex - 1; i >= 0; i -- ) {
                    if(isFree(i, column)) {
                        break;
                    }
                    else{
                        newWordStartIndex = i;
                    }
                }
            }
        }

        for(int j = newWordStartIndex; j < wordStartIndex; j++) {
            completeWord = completeWord + boardArray[j][column];
        }

        completeWord = completeWord + word;

        if(!boardOutOfIndex(wordStopIndex + 1,  column)) {
            if(!isFree(wordStopIndex + 1, column)) {
                for(int i = wordStopIndex + 1; i < board.getSize(); i++) {
                    if(isFree(i, column)) {
                        break;
                    }
                    else {
                        completeWord = completeWord + boardArray[i][column];
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

        //above makes complete word to check it in the dictionary
        //if valid word with/without wildcard, checks if it is valid
        //in all direction using another function

        if(numberOfWildCard == 0) {
            if(dictionary.isValidMove(completeWord)) {
                checkForValidInAllDirectionTopDown(wordStartIndex,
                        column, word);
            }
        }

        else if(numberOfWildCard == 1) {
            validWordsOneWildCard =
                    dictionary.validWordsForOneWildCard(completeWord);

            for (int k = 0; k < numberOfAlphabets; k ++) {
                if(validWordsOneWildCard[k]) {
                    String newWord = "";
                    int int_first_character = 'A' + k;
                    char character = (char) int_first_character;

                    newWord = word.substring(0, wildcardIndex[0]) + character
                            + word.substring(wildcardIndex[0] + 1);

                    checkForValidInAllDirectionTopDown(wordStartIndex,
                            column, newWord);
                }
            }
        }

        else if(numberOfWildCard == 2) {
            validWordsTwoWildCard =
                    dictionary.validWordsForTwoWildCard(completeWord);
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

                        checkForValidInAllDirectionTopDown(wordStartIndex,
                                column, newWord);
                    }
                }
            }
        }

    }

    /**
     * Starting makes Top moves and checks if move made in the top
     * is valid and then checks if top move is valid
     * in all direction or not
     * Same logic as bottom move
     *
     * @param String word, word made for top
     * @param int row, starting row
     * @param int column, starting column
     */
    public void makeTopMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard =
                new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard =
                new boolean[numberOfAlphabets][numberOfAlphabets];

        int [] wildcardIndex = new int[2];
        int numberOfWildCard = 0;
        String completeWord = "";

        int wordStartIndex = row - word.length() + 1;

        int newWordStartIndex = wordStartIndex;

        if(!boardOutOfIndex(wordStartIndex -1 , column) ) {

            if(!isFree(wordStartIndex - 1,  column)) {
                for(int i = wordStartIndex - 1; i >= 0; i -- ) {
                    if(isFree(i, column)) {
                        break;
                    }
                    else{
                        newWordStartIndex = i;
                    }
                }
            }
        }



        for(int j = newWordStartIndex; j < wordStartIndex; j++) {
            completeWord = completeWord + boardArray[j][column];
        }

        completeWord = completeWord + word;

        for(int i = row + 1; i < board.getSize(); i++) {
            if(isFree(i, column)) {
                break;
            }
            else {
                completeWord = completeWord + boardArray[i][column];
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
                checkForValidInAllDirectionTopDown(wordStartIndex, column, word);
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

                    checkForValidInAllDirectionTopDown(wordStartIndex, column, newWord);

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

                        checkForValidInAllDirectionTopDown(wordStartIndex, column, newWord);
                    }
                }
            }
        }

    }

    /**
     * Starting makes right moves and checks if move made in the right
     * is valid and then checks if right move is valid
     * in all direction or not
     * Same logic as bottom move
     *
     * @param String word, word made for right
     * @param int row, starting row
     * @param int column, starting column
     */
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

    /**
     * Starting makes left moves and checks if move made in the left
     * is valid and then checks if left move is valid
     * in all direction or not
     * Same logic as bottom move
     *
     * @param String word, word made for left
     * @param int row, starting row
     * @param int column, starting column
     */
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

    /**
     * Checks if a left/right word is valid in top and down or not
     * It passes each character from string to check if it is valid
     * in all direction or not
     * checks if valid in all direction or not. If yes, checks the score
     *
     * @param String word, word made for right/left
     * @param int row, starting row
     * @param int column, starting column
     */
    public void checkForValidInAllDirectionTopDown(int row,
                                                   int column, String word) {
        int wordLength = word.length();
        int ro = row;


        boolean isValidInAllDirection = true;

        for(int i = 0; i < wordLength; i++) {
            if(!validMoveLeftRight(word.charAt(i), ro, column)) {
                isValidInAllDirection = false;
                break;
            }
            ro++;
        }


        if(isValidInAllDirection) {
            score.getScore(row, column, 'c', 'v',word);

        }
    }

    /**
     * Checks if a character of top/bottom move is valid
     * on left and right
     *
     * @param char letter, letter being checked
     * @param int row, row of letter
     * @param int column, column of letter
     */
    public boolean validMoveLeftRight(char character, int row, int column) {
        int startIndex = 0;
        int stopIndex = 0;
        String completeWord = "";

        boolean noLeftMove = false;
        boolean noRightMove = false;

        if(boardOutOfIndex(row, column - 1)) {
            noLeftMove = true;
        }

        else {
            if(isFree(row, column  - 1)) {
                noLeftMove = true;
            }
        }

        if(boardOutOfIndex(row, column + 1)) {
            noRightMove = true;
        }

        else {
            if(isFree(row, column + 1)) {
                noRightMove = true;
            }
        }

        if(noLeftMove && noRightMove) {
            return true;
        }


        else if(!noLeftMove && noRightMove) {

            startIndex = column;
            for(int i = column - 1; i >=0; i--) {
                if(isFree(row, i)) {
                    break;
                }
                startIndex--;
            }
            for(int i = startIndex; i < column; i++) {
                completeWord = completeWord +boardArray[row][i];
            }
            completeWord = completeWord + character;

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else {
                return false;
            }
        }

        else if(noLeftMove && !noRightMove) {
            completeWord = completeWord + character;
            for(int i = column + 1; i < board.getSize(); i++) {
                if(isFree(row, i)){
                    break;
                }
                else {
                    completeWord = completeWord + boardArray[row][i];
                }
            }

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }

            else {
                return false;
            }

        }

        else if(!noLeftMove && !noRightMove) {
            startIndex = column;
            stopIndex = column;

            for(int i = column - 1; i >= 0; i--) {
                if(isFree(row, i)) {
                    break;
                }

                else {
                    startIndex--;
                }
            }

            for (int i = column + 1; i < board.getSize(); i++) {
                if(isFree(row,i )) {
                    break;
                }
                else {
                    stopIndex++;
                }
            }

            for(int i = startIndex; i < column; i++) {
                completeWord = completeWord +boardArray[row][i];
            }
            completeWord = completeWord + character;

            for (int i = column + 1; i <= stopIndex; i++) {
                completeWord = completeWord + boardArray[row][i];
            }

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else  {
                return false;
            }
        }



        return false;
    }

    /**
     * Checks if word made left and right is valid or not
     * in all direction using other function
     * If is valid in all direction, checks the score
     *
     * @param String word, word made for right
     * @param int row, starting row
     * @param int column, starting column
     */
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
            score.getScore(row, column, 'c', 'h', word );
        }
    }


    /**
     * Checks if a character of left/right move is valid
     * on top and bottom
     *
     * @param char letter, letter being checked
     * @param int row, row of letter
     * @param int column, column of letter
     */

    public boolean validMoveTopDown(char character, int row, int column) {

        int startIndex = 0;
        int stopIndex = 0;
        String completeWord = "";
        boolean noTopMove = false;
        boolean noBottomMove = false;

        if(boardOutOfIndex(row - 1, column)) {
            noTopMove = true;
        }

        else {
            if(isFree(row - 1, column)) {
                noTopMove = true;
            }
        }

        if(boardOutOfIndex(row + 1, column)) {
            noBottomMove = true;
        }

        else {
            if(isFree(row + 1, column)) {
                noBottomMove = true;
            }
        }


        if(noTopMove && noBottomMove) {
            return true;
        }

        else if(noTopMove && !noBottomMove) {
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

        else if(noBottomMove && !noTopMove) {
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

        else if(!noBottomMove && !noTopMove) {
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

    /**
     * Places the best move got from all game to the board
     * Restarts the currentScore
     */
    public void placeBestMove() {
        char direction = score.getCompBestMoveDirection();
        int row = score.getComputerBestMoveRow();
        int column = score.getComputerBestMoveColumn();
        String word = score.getCompBestCurrentWord();


        int currentBestScore = score.getCompCurrentBestScore();

        if(currentBestScore != 0) {
            updateTray(word);
            board.placeMove(row, column, direction, word);
        }

        score.resetCurrentCompScore();
    }

    /**
     * Checks if a given spot in the board is free for computer to play
     *
     * @param int x, row of board
     * @param int y, column of board
     * @return boolean, true if is free and vice versa
     */
    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3'
                || boardArray[x][y] == '4' ||
        boardArray[x][y] == '@' || boardArray[x][y] == '!'
                || boardArray[x][y] == '$') {
            return true;
        }
        return  false;
    }

    /**
     * Checks if the given spot of the board is out of index
     * and returns true or false
     * @param int i, row of board
     * @param int j, column of board
     * @return, true if is out of index and vice versa
     */
    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > board.getSize() - 1 || j > board.getSize() - 1){
            return true;
        }
        return  false;
    }

    /**
     * Updates the tray of the computer after the move is made
     *
     * Takes in the word and removes it from the tray
     * @param String word, Word played by the computer
     */
    public void updateTray(String word) {
        int replaceNeeded = 0;
        List<Integer> trayIndex = new ArrayList<Integer>();
        for (int i = 0; i < tray.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                if(word.charAt(j) == tray.charAt(i)) {
                    if(!trayIndex.contains(i)) {
                        trayIndex.add(i);
                    }
                }

                if(letterIsCapital(word.charAt(j)) && tray.charAt(i) == '*') {
                    if(!trayIndex.contains(i)) {
                        trayIndex.add(i);
                    }
                }
            }
        }
        Collections.sort(trayIndex, Collections.reverseOrder());
        for(int i = 0; i < trayIndex.size(); i++) {
            tray = charRemoveAt(tray, trayIndex.get(i));
        }

        replaceNeeded = 7 - tray.length();

        List<Tile> tiles = new ArrayList<Tile>();
        tiles = bag.giveTilesToTray(replaceNeeded);
        for (int i = 0; i < tiles.size(); i++ ) {
            tray =  tray + tiles.get(i).getLetter();
        }


    }

    /**
     * Checks if a character played by the computer is
     * capital. If capital returns true
     *
     * @param char letter, letter being checked
     * @return boolean, true if a letter is capital
     */
    public boolean letterIsCapital(char character) {
        int asci = character - 'A';

        if(asci <= 25 && asci >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Removes character at a given index
     * from a string and returns the string
     *
     * @param String str, String from which character
     *               is being deleted
     * @param int p, index of the character
     * @return String with the character removed
     */
    public  String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }
}

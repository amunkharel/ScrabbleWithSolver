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
    private List<String> letterPermutations = new ArrayList<>();
    private Score score;

    public ComputerPlayer(Board board, Dictionary dictionary, Bag bag, Score score) {

        validCordinates  = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
        this.numberOfAlphabets = 26;
        validCordinates = board.getValidCordinates();
        this.score = score;
    }

    public void setTray(String tray) {
        this.tray = tray;
    }


    public void startAI() {
        makeCombinations();
        for (int i = 0; i < letterPermutations.size(); i++) {
            makeIntialMoves(letterPermutations.get(i));
        }
    }

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

                    if(word.length() <= maxTopIndex ) {
                        maxTopIndex  = 0;
                        //makeTopMove(word, x, y);
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
                    if(word.length() <= maxBottomIndex) {
                        maxBottomIndex = 0;
                        //makeBottomMove(word, x, y);
                    }
                }
            }




        }
    }

    public void makeBottomMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard = new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard = new boolean[numberOfAlphabets][numberOfAlphabets];

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

    public void makeTopMove(String word, int row, int column) {
        boolean [] validWordsOneWildCard = new boolean[numberOfAlphabets];
        boolean [][] validWordsTwoWildCard = new boolean[numberOfAlphabets][numberOfAlphabets];

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

    public void checkForValidInAllDirectionTopDown(int row, int column, String word) {
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
            System.out.println("");
            System.out.println(row + " " + column + " Vertical");
            score.getScore(row, column, 'c', 'v', word );
            System.out.println(word);
        }
    }

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
            System.out.println("");
            System.out.println(row + " " + column + " Horizontal");
            score.getScore(row, column, 'c', 'h', word );
            System.out.println(word);
        }
    }

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


    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3' || boardArray[x][y] == '4' ||
        boardArray[x][y] == '@' || boardArray[x][y] == '!' || boardArray[x][y] == '$') {
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

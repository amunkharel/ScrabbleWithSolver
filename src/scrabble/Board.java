/**
 * Project 3 - CS351, Fall 2019, Board where moves are made in the game
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;
import java.util.ArrayList;
import java.util.List;

public class Board {

    /** Size of row and column of the board*/
    private int size;

    /** 2d board array of the game */
    private char [] [] board;

    /** 2d duplicate board array for the game*/
    private char [][] duplicateboard;

    /** List of valid cordinates where move has to be made*/
    private List<Cordinate> validCordinates;

    /** Swap is intiliazed on the game or not*/
    private boolean swapInitialize;

    /**
     * Constructor for Board Class
     *
     * @param int size, Size of the board
     */
    public Board(int size) {
        this.size = size;
        validCordinates = new ArrayList<Cordinate>();
        this.board = new char[size][size];
        duplicateboard = new char[size][size];
        swapInitialize = false;
    }

    /**
     * Puts letter, premium squares, or empty squares on the board
     *
     * @param int row, Row of the board
     * @param int column, Column of the board
     * @param char character, character in the board
     */
    public void insertScoreAndIntialLetters(int row, int column, char character) {
        this.board[row][column] = character;
        this.duplicateboard[row][column] = character;
    }

    /**
     * Returns if swap is intialized or not
     *
     * @return boolean, returns true if swap is initialized and vice versa
     */
    public boolean isSwapInitialize() {
        return swapInitialize;
    }

    /**
     * Gives boolean value if swap is intialized or not
     *
     * @param boolean swapInitialize, true or false to make
     *                swap Initialized or not
     */
    public void setSwapInitialize(boolean swapInitialize) {
        this.swapInitialize = swapInitialize;
    }

    /**
     * Prints the board for testing purpose
     */
    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Prints the duplicate board for testing purpose
     */
    public void printDuplicateBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(duplicateboard[i][j] + " ");
            }


            System.out.println("");
        }
    }

    /**
     * Returns all the possible coordinates where atleast one move
     * has to be made
     *
     * @return List<Cordinate>, returns list of all cordinates
     * where valid move can be made
     */
    public List<Cordinate> getValidCordinates() {
        validCordinates.clear();
        boolean cordinateAdded = false;
        boolean hasCharacter = false;

        //if board is empty, returns (7,7) as validCordinate
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!isFreeRealBoard(i, j)) {
                    hasCharacter = true;
                }
            }
        }

        if(!hasCharacter) {
            validCordinates.add(new Cordinate(7, 7));
            return validCordinates;
        }

        //Algorithm to generate valid cordinates
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {


                if(board[i][j] == '-' || board[i][j] == '2' || board[i][j] == '3' ||
                        board[i][j] == '4' || board[i][j] == '$' || board[i][j] == '@'
                                || board[i][j] == '!' ) {
                    if(!boardOutOfIndex(i, j-1)) {
                        if(board[i][j - 1] != '-' && board[i][j - 1] != '3'
                                && board[i][j - 1] != '2' && board[i][j - 1] != '4'
                                && board[i][j - 1] != '$' && board[i][j - 1] != '@'
                                && board[i][j - 1] != '!') {
                            validCordinates.add(new Cordinate(i, j));
                            cordinateAdded = true;
                        }
                    }

                     if(!boardOutOfIndex(i, j+1) && !cordinateAdded) {
                        if(board[i][j + 1] != '-' && board[i][j + 1] != '3'
                                && board[i][j + 1] != '2' && board[i][j + 1] != '4'
                                && board[i][j + 1] != '$' && board[i][j + 1] != '@'
                                && board[i][j + 1] != '!') {
                            validCordinates.add(new Cordinate(i, j));
                            cordinateAdded = true;
                        }
                    }

                     if(!boardOutOfIndex(i - 1, j) && !cordinateAdded) {
                        if(board[i-1][j] != '-' && board[i-1][j] != '3'
                                && board[i-1][j] != '2' && board[i-1][j] != '4'
                                && board[i - 1][j] != '$' && board[i - 1][j] != '@'
                                && board[i - 1][j] != '!') {
                            validCordinates.add(new Cordinate(i , j ));
                            cordinateAdded = true;
                        }
                    }

                    if(!boardOutOfIndex(i +1, j) && !cordinateAdded) {
                        if(board[i+1][j] != '-' && board[i+1][j] != '3'
                                && board[i+1][j] != '2' && board[i+1][j] != '4'
                                && board[i + 1][j] != '$' && board[i + 1][j] != '@'
                                && board[i + 1][j] != '!') {
                            validCordinates.add(new Cordinate(i , j ));
                        }
                    }

                }
                cordinateAdded = false;

            }
        }

        return validCordinates;
    }

    /**
     * Checks if the given row and column is out of index
     *
     * @param int i, row of the board
     * @param int j, column of the board
     * @return boolean, return true if board is out of index and vice versa
     */
    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > size - 1 || j > size - 1){
            return true;
        }
        return  false;
    }

    /**
     * Returns the 2d array of board
     *
     * @return char [][], 2d array of board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Returns size of board
     *
     * @return int, size of board
     */
    public int getSize() {
        return size;
    }


    /**
     * Places move for computer
     *
     * @param int row, Starting row for move
     * @param int column, Starting column for move
     * @param char direction, Direction of the move
     * @param String word, word to be placed
     */
    public void placeMove(int row, int column, char direction, String word) {
        if(direction == 'h') {
            for (int i = 0; i < word.length(); i++) {
                board[row][column + i] = word.charAt(i);
            }
        }

        if(direction == 'v') {
            for (int i = 0; i < word.length(); i++) {
                board[row + i][column] = word.charAt(i);
            }
        }

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                duplicateboard[i][j] = board[i][j];
            }
        }
    }

    /**
     * Places letter on Duplicate Board
     *
     * @param int row, row of the board
     * @param int column, column of the board
     * @param char letter, letter to be placed
     */
    public void placeLetterDuplicateBoard(int row, int column, char letter) {
        duplicateboard[row][column] = letter;
    }

    /**
     * Returns 2d array of duplicate Board
     * @return char [][] getDuplicateBoard, 2d array of duplicate board
     */
    public char [][] getDuplicateboard () {
        return  duplicateboard;
    }

    public boolean isFree(int x, int y) {
        if(duplicateboard[x][y] == '-' || duplicateboard[x][y] == '1' ||
                duplicateboard[x][y] == '2' || duplicateboard[x][y] == '3'
                || duplicateboard[x][y] == '4' ||
                duplicateboard[x][y] == '@' ||
                duplicateboard[x][y] == '!' || duplicateboard[x][y] == '$') {
            return true;
        }
        return  false;
    }

    /**
     * Checks if the spot in the board is free to add letters or not
     *
     * @param int x, row of the board
     * @param int y, column of the board
     * @return boolean, returns true if given spot is free and vice versa
     */
    public boolean isFreeRealBoard(int x, int y) {
        if(board[x][y] == '-' || board[x][y] == '1' ||
                board[x][y] == '2' || board[x][y] == '3' || board[x][y] == '4' ||
                board[x][y] == '@' || board[x][y] == '!' || board[x][y] == '$') {
            return true;
        }
        return  false;
    }

    /**
     * Reverts the duplicate board to original board
     * Done when we use undo button
     *
     */
    public void revertDuplicateBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                duplicateboard[i][j] = board[i][j];
            }
        }
    }
}

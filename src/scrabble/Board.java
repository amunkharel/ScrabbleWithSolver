package scrabble;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int size;

    private char [] [] board;

    private List<Cordinate> validCordinates;

    public Board(int size) {
        this.size = size;
        validCordinates = new ArrayList<Cordinate>();
        this.board = new char[size][size];
        this.board = board;
    }

    public void insertScoreAndIntialLetters(int row, int column, char character) {
        this.board[row][column] = character;
    }



    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public List<Cordinate> getValidCordinates() {


        boolean cordinateAdded = false;
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

    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > size - 1 || j > size - 1){
            return true;
        }
        return  false;
    }

    public char[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

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
    }
}

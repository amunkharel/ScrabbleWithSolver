/**
 * Project 3 - CS351, Fall 2019, Console to solve the standard input
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;

import java.io.*;

public class Console {

    /** Name of the dictionary file in the resource folder*/
    private String dictionaryFileName;


    /**
     * Sets the dictionary File Name
     *
     * @param String, dictionaryFileName, name of dictionary
     */
    public void setDictionaryFileName(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }


    /**
     * Starts solving the console board using all the other classes
     */
    public void startSolving() {
        int xCount = 0;
        int yCount = 0;
        int boardSize = 0;


        BufferedReader reader;

        try {

            reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            Bag bag = new Bag();
            Dictionary dictionary = new Dictionary(dictionaryFileName);
            dictionary.readAndStoreDictionaryInTrie();
            Board board = new Board(0);
            Score score = new Score(bag, board);
            ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);
            while(line != null) {
                while (line.length() != 7) {

                    //until it finds another tray which is of length 7

                    //if it finds the size of the board
                    if(line.length() == 1 || line.length() == 2) {
                        boardSize = Integer.valueOf(line);
                        board = new Board(boardSize);
                    }
                    else {
                        int length = line.length();

                        int i = 0;
                        while (i < length) {

                            //insert the string inside my board object's array
                            if(line.substring(i, i+2).equals("3.")) {
                                board.insertScoreAndIntialLetters(xCount,
                                        yCount, '@');
                            }

                            else if(line.substring(i, i+2).equals("4.")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '$');
                            }

                            else if(line.substring(i, i+2).equals("2.")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '!');
                            }

                            else if(line.substring(i, i+2).equals(".2")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '2');
                            }

                            else if(line.substring(i, i+2).equals(".3")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '3');
                            }

                            else if(line.substring(i, i+2).equals(".4")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '4');
                            }

                            else if(line.substring(i, i+2).equals("..")) {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount, '-');
                            }

                            else {
                                board.insertScoreAndIntialLetters(
                                        xCount, yCount,
                                        line.substring(i, i+2).charAt(1));
                            }
                            yCount++;
                            i = i + 3;
                        }
                        yCount = 0;
                        xCount++;

                    }

                    line = reader.readLine();

                    //solves the game
                    if(line.length() == 7) {
                        score = new Score(bag, board);
                        p2 = new ComputerPlayer(board, dictionary, bag, score);
                        p2.setTray(line);
                        board.printBoard();
                        System.out.println("");
                        System.out.println("");
                        xCount = 0;
                        yCount = 0;

                        p2.startAI();

                        System.out.println("Direction v for vertical," +
                                " h for horizontal - " +
                                score.getCompBestMoveDirection());
                        System.out.println("Starting Row " +
                                score.getComputerBestMoveRow());
                        System.out.println("Starting Column " +
                                score.getComputerBestMoveColumn());
                        System.out.println("Word " +
                                score.getCompBestCurrentWord());
                        System.out.println("Score " +
                                score.getCompCurrentBestScore());
                        System.out.println("");

                        p2.placeBestMove();
                        board.printBoard();

                        System.out.println("");
                        System.out.println("");
                    }
                }
                line = reader.readLine();
            }


            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

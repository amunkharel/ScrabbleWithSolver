package scrabble;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Console {
    private String dictionaryFileName;

    private String inputFileName;


    public void setDictionaryFileName(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void startSolving() {
        int xCount = 0;
        int yCount = 0;
        int boardSize = 0;


        String filename = inputFileName;

        String pathname = "resources/" + filename;

        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader(pathname));
            String line = reader.readLine();
            Bag bag = new Bag();
            Dictionary dictionary = new Dictionary(dictionaryFileName);
            dictionary.readAndStoreDictionaryInTrie();
            Board board = new Board(0);
            Score score = new Score(bag, board);
            ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);
            while(line != null) {
                while (line.length() != 7) {
                    if(line.length() == 1 || line.length() == 2) {
                        boardSize = Integer.valueOf(line);
                        board = new Board(boardSize);
                    }
                    else {
                        int length = line.length();

                        int i = 0;
                        while (i < length) {
                            if(line.substring(i, i+2).equals("3.")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '@');
                            }

                            else if(line.substring(i, i+2).equals("4.")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '$');
                            }

                            else if(line.substring(i, i+2).equals("2.")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '!');
                            }

                            else if(line.substring(i, i+2).equals(".2")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '2');
                            }

                            else if(line.substring(i, i+2).equals(".3")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '3');
                            }

                            else if(line.substring(i, i+2).equals(".4")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '4');
                            }

                            else if(line.substring(i, i+2).equals("..")) {
                                board.insertScoreAndIntialLetters(xCount, yCount, '-');
                            }

                            else {
                                board.insertScoreAndIntialLetters(xCount, yCount, line.substring(i, i+2).charAt(1));
                            }
                            yCount++;
                            i = i + 3;
                        }
                        yCount = 0;
                        xCount++;

                    }

                    line = reader.readLine();

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

                        System.out.println("Direction v for vertical, h for horizontal - " + score.getCompBestMoveDirection());
                        System.out.println("Starting Row " + score.getComputerBestMoveRow());
                        System.out.println("Starting Column " + score.getComputerBestMoveColumn());
                        System.out.println("Word " + score.getCompBestCurrentWord());
                        System.out.println("Score " + score.getCompCurrentBestScore());
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

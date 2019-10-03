package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        int xCount = 0;
        int yCount = 0;
        int boardSize = 0;


        String filename = "example_input.txt";

        String pathname = "resources/" + filename;

        try {
            Bag bag = new Bag();
            Dictionary dictionary = new Dictionary("sowpods.txt");
            dictionary.readAndStoreDictionaryInTrie();
            Board board = new Board(0);
            Score score = new Score(bag, board);
            ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);

            Scanner sc = new Scanner(new File(pathname));
            while(sc.hasNextLine()) {
                String line = sc.nextLine();

                while(!line.isEmpty() && sc.hasNextLine()) {
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

                        line = sc.nextLine();

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

                            System.out.println("Direction " + score.getCompBestMoveDirection());
                            System.out.println("Row " + score.getComputerBestMoveRow());
                            System.out.println("Column " + score.getComputerBestMoveColumn());
                            System.out.println("Word " + score.getCompBestCurrentWord());
                            System.out.println("Score " + score.getCompCurrentBestScore());

                            p2.placeBestMove();
                            board.printBoard();

                            System.out.println("");
                            System.out.println("");
                        }
                    }
                    line = sc.nextLine();
                }
            }

            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*Board board = new Board(15);

        board.printBoard();

        System.out.println("");
        System.out.println("");

        Dictionary dictionary = new Dictionary("sowpods.txt");
        dictionary.readAndStoreDictionaryInTrie();


        Bag bag = new Bag();
        Score score = new Score(bag, board);

        ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);

        //score.getScore(4, 10, 'c', 'v', "modeleD");
        p2.startAI();

        p2.placeBestMove();
        board.printBoard();
        //board.printBoard(); */
    }

}

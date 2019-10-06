package scrabble;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main extends Application {

    private int sizeOfBoard = 15;
    private Bag bag = new Bag();
    private Dictionary dictionary = new Dictionary("sowpods.txt");
    private  Board board = new Board(sizeOfBoard);
    private Score score = new Score(bag, board);
    private ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);
    private  HumanPlayer p1 = new HumanPlayer(board, dictionary, bag, score);

    private BorderPane bp = new BorderPane();
    private Canvas canvas = new Canvas(750, 750);

    private Scene scene = new Scene(bp, 800, 800);

    private GUI gui = new GUI(p1, canvas, board, bag, score);




    @Override
    public void start(Stage stage) throws Exception {
        dictionary.readAndStoreDictionaryInTrie();

        int xCount = 0;
        int yCount = 0;
        int boardSize = 0;


        String filename = "scrabble_board.txt";

        String pathname = "resources/" + filename;

        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader(pathname));
            String line = reader.readLine();
            while(line != null) {

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
            }

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        score = new Score(bag, board);
        p2 = new ComputerPlayer(board, dictionary, bag, score);
        p1 = new HumanPlayer(board, dictionary, bag, score);
        p2.setTrayFromBag();
        p1.setTrayFromBag();
        gui = new GUI(p1, canvas, board, bag, score);
        gui.updateCanvas();

        bp.setCenter(canvas);
        stage.setScene(scene);

        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

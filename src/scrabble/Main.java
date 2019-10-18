/**
 * Project 3 - CS351, Fall 2019, Main Controller class to run the game
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */
package scrabble;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main extends Application {


    /** Size of the board*/
    private int sizeOfBoard = 15;

    /** Bag for the game*/
    private Bag bag = new Bag();

    /** Dictionary for the game*/
    private Dictionary dictionary = new Dictionary("sowpods.txt");

    /** Board for the game*/
    private  Board board = new Board(sizeOfBoard);

    /** Scoring tracker for the game*/
    private Score score = new Score(bag, board);

    /** Computer player for the game*/
    private ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);

    /** Human Player for the game*/
    private  HumanPlayer p1 = new HumanPlayer(board, dictionary, bag, score);

    /** Border Pane for canvas*/
    private BorderPane bp = new BorderPane();

    /** Canvas for the game*/
    private Canvas canvas = new Canvas(750, 750);

    /** Scene for the stage*/
    private Scene scene = new Scene(bp, 800, 800);

    /** GUI class for the game */
    private GUI gui = new GUI(p1, canvas, board, bag, score);

    /** Event Encapsulator */
    private EventHandler e;

    /** Alert message after the game is over*/
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);




    @Override
    public void start(Stage stage) throws Exception {
        dictionary.readAndStoreDictionaryInTrie();

        int xCount = 0;
        int yCount = 0;
        int boardSize = 0;


        String filename = "scrabble_board.txt";

        String pathname = "resources/" + filename;

        BufferedReader reader;

        //reads the scrabble_board file and store format of the board

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
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '@');
                        }

                        else if(line.substring(i, i+2).equals("4.")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '$');
                        }

                        else if(line.substring(i, i+2).equals("2.")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '!');
                        }

                        else if(line.substring(i, i+2).equals(".2")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '2');
                        }

                        else if(line.substring(i, i+2).equals(".3")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '3');
                        }

                        else if(line.substring(i, i+2).equals(".4")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '4');
                        }

                        else if(line.substring(i, i+2).equals("..")) {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, '-');
                        }

                        else {
                            board.insertScoreAndIntialLetters(xCount,
                                    yCount, line.substring(i, i+2).charAt(1));
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


        //handles the mouse click event and after game is over ends game
        canvas.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                e = new EventHandler(bag, dictionary, board,
                        score, p1, p2, event.getX(), event.getY());
                e.handleEvent();
                if(e.isGameOver()) {
                    if(p1.getTotalScore() > score.getCompTotalScore()) {
                        alert.setTitle("Game Over");
                        alert.setContentText("You have won the game");
                        alert.showAndWait();
                        stage.close();
                    }

                    else if(p1.getTotalScore() < score.getCompTotalScore()) {
                        alert.setTitle("Game Over");
                        alert.setContentText("Computer has won the game");
                        alert.showAndWait();
                        stage.close();
                    }

                    else if(p1.getTotalScore() == score.getCompTotalScore()) {
                        alert.setTitle("Game Over");
                        alert.setContentText("Game is a draw");
                        alert.showAndWait();
                        stage.close();
                    }


                }
            }
        });

        //updates gui for the game
        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gui.updateCanvas();
            }
        };

        animator.start();

        bp.setCenter(canvas);
        stage.setScene(scene);

        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {

        //IF you uncomment this board can be solved from console
        /*Console console = new Console();
        console.setDictionaryFileName(args[0]);
        console.startSolving(); */
        launch(args);
    }
}

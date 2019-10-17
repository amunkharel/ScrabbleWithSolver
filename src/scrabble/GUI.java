/**
 * Project 3 - CS351, Fall 2019, Class that has canvas for the game
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUI {

    /** Human player of the game */
    private  HumanPlayer p1;

    /**  Canvas to draw the GUI*/
    private Canvas canvas;

    /**  Board object of the game*/
    private  Board board;

    /**  2D representation of boardArray*/
    private char [][] boardArray;

    /**  tray of the board*/
    private String tray = "";

    /** Bag from where tile is drawn */
    private  Bag bag;

    /** Score of the game */
    private Score score;

    /**  Graphic context for canvas*/
    private GraphicsContext gc;

    /**
     * Constructor for event handler
     *
     * @param Bag bag,  Bag from where tile is drawn
     * @param Board board, Board where moves are made
     * @param Score score,Score of the players
     * @param HumanPlayer p1,Human Player
     * @param Canvas canvas, canvas where animation is done
     */
    public GUI(HumanPlayer p1, Canvas canvas, Board board, Bag bag,
               Score score) {
        this.p1 = p1;
        this.canvas = canvas;
        this.board = board;
        gc = canvas.getGraphicsContext2D();
        this.bag = bag;
        this.score = score;
    }

    /**
     * Updates the canvas on animation timer
     */
    public void updateCanvas() {
        boardArray = this.board.getDuplicateboard();
        tray = p1.getDuplicateTray();
        gc.setFill(Color.KHAKI);
        gc.fillRect(0,0, 750, 750);


        //draws tiles in the board
        for (int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                drawTileInBoard(i, j, boardArray[i][j]);
            }
        }

        //drwaws letters in the tray
        for(int i = 0; i < tray.length(); i++) {
            drawLetters(tray.charAt(i), i);
        }

        gc.setFill(Color.CORAL);
        gc.fillRect(50, 630, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(50, 630, 100, 50);

        //play button
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        gc.fillText("Play", 70 , 660 );

        gc.setFill(Color.CORAL);
        gc.fillRect(200, 630, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(200, 630, 100, 50);

        if(board.isSwapInitialize()) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeRect(200, 630, 100, 50);
        }

        //Swap button
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        gc.fillText("Swap", 220 , 660 );


        gc.setFill(Color.CORAL);
        gc.fillRect(350, 630, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(350, 630, 100, 50);

        //Undo Button
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        gc.fillText("Undo", 370 , 660 );

        gc.setFill(Color.CORAL);
        gc.fillRect(630, 50, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(630, 50, 100, 50);

        //Human Score
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.fillText("Your Score", 640 , 40 );

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(20));
        gc.fillText(String.valueOf(p1.getTotalScore()), 640 , 80 );

        gc.setFill(Color.CORAL);
        gc.fillRect(630, 150, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(630, 150, 100, 50);

        //Computer Score
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.fillText("Computer Score", 630 , 140 );

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(20));
        gc.fillText(String.valueOf(score.getCompTotalScore()), 640 , 190 );

    }


    /**
     * Draws tiles in the tray
     *
     * @param int column,  Column
     * @param char value, value of letter
     */
    public void drawLetters(char value, int column) {
        if(value == '*') {


            gc.setFill(Color.CHOCOLATE);
            gc.fillRect(40 + 40*column, 700, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40 + 40*column, 700, 40, 40);


            gc.setFill(Color.BLACK);
            gc.setFont(new Font(15));
            gc.fillText(String.valueOf(bag.getScore(value)),
                    40 + 40*column + 20, 700 +5 + 22 + 10);

            if(p1.isTraySelected()) {
                if(p1.getSelectedTray() == column) {
                    gc.setStroke(Color.RED);
                    gc.setLineWidth(2);
                    gc.strokeRect(40 + 40*column, 700, 40, 40);
                }
            }

            for(int i = 0; i < p1.getSwapSelectedTrayIndex().size(); i++) {
                if(column == p1.getSwapSelectedTrayIndex().get(i)) {
                    gc.setStroke(Color.RED);
                    gc.setLineWidth(2);
                    gc.strokeRect(40 + 40*column, 700, 40, 40);
                }
            }
        }

        else {
            gc.setFill(Color.CHOCOLATE);
            gc.fillRect(40 + 40*column, 700, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40 + 40*column, 700, 40, 40);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(30));
            gc.fillText(String.valueOf(value), 40 + 40*column + 5 , 725 );

            gc.setFill(Color.BLACK);
            gc.setFont(new Font(15));
            gc.fillText(String.valueOf(bag.getScore(value)),
                    40 + 40*column + 20, 700 +5 + 22 + 10);

            if(p1.isTraySelected()) {
                if(p1.getSelectedTray() == column) {
                    gc.setStroke(Color.RED);
                    gc.setLineWidth(2);
                    gc.strokeRect(40 + 40*column, 700, 40, 40);
                }
            }


            for(int i = 0; i < p1.getSwapSelectedTrayIndex().size(); i++) {
                if(column == p1.getSwapSelectedTrayIndex().get(i)) {
                    gc.setStroke(Color.RED);
                    gc.setLineWidth(2);
                    gc.strokeRect(40 + 40*column, 700, 40, 40);
                }
            }
        }
    }

    /**
     * Draws tiles in board. Draws premium squares, letters
     * and empty tiles
     *
     * @param int column,  Column of the board
     * @param int row,  Row of the board
     * @param char value, value of the spot on board
     */
    public void drawTileInBoard(int column, int row, char value) {
        if(value == '2') {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
        }

        else if(value == '3') {
            gc.setFill(Color.DARKBLUE);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
        }

        else if(value == '!') {
            gc.setFill(Color.PINK);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
        }

        else if(value == '@') {
            gc.setFill(Color.RED);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
        }
        else if(value == '-') {
            gc.setFill(Color.LIGHTGOLDENRODYELLOW);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
        }

        else {
            gc.setFill(Color.CHOCOLATE);
            gc.fillRect(40*row, 40*column, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40*row, 40*column, 40, 40);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(30));
            gc.fillText(String.valueOf(value), 40*row , 40*column + 25 );

            gc.setFill(Color.BLACK);
            gc.setFont(new Font(15));
            gc.fillText(String.valueOf(bag.getScore(value)), 40*row + 22,
                    40*column + 35);
        }
    }

}

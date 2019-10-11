package scrabble;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUI {
    private  HumanPlayer p1;
    private Canvas canvas;
    private  Board board;
    private char [][] boardArray;
    private String tray = "";
    private  Bag bag;
    private Score score;

    private GraphicsContext gc;

    public GUI(HumanPlayer p1, Canvas canvas, Board board, Bag bag, Score score) {
        this.p1 = p1;
        this.canvas = canvas;
        this.board = board;
        gc = canvas.getGraphicsContext2D();
        this.bag = bag;
        this.score = score;
    }

    public void updateCanvas() {
        boardArray = this.board.getDuplicateboard();
        tray = p1.getDuplicateTray();
        gc.setFill(Color.KHAKI);
        gc.fillRect(0,0, 750, 750);

        for (int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                drawTileInBoard(i, j, boardArray[i][j]);
            }
        }

        for(int i = 0; i < tray.length(); i++) {
            drawLetters(tray.charAt(i), i);
        }

        gc.setFill(Color.CORAL);
        gc.fillRect(50, 630, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(50, 630, 100, 50);

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

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        gc.fillText("Swap", 220 , 660 );


        gc.setFill(Color.CORAL);
        gc.fillRect(350, 630, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(350, 630, 100, 50);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        gc.fillText("Undo", 370 , 660 );

        gc.setFill(Color.CORAL);
        gc.fillRect(630, 50, 100, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(630, 50, 100, 50);

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

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(15));
        gc.fillText("Computer Score", 630 , 140 );

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(20));
        gc.fillText(String.valueOf(score.getCompTotalScore()), 640 , 190 );

    }

    public void drawLetters(char value, int column) {
        if(value == '*') {


            gc.setFill(Color.CHOCOLATE);
            gc.fillRect(40 + 40*column, 700, 40, 40);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(40 + 40*column, 700, 40, 40);


            gc.setFill(Color.BLACK);
            gc.setFont(new Font(15));
            gc.fillText(String.valueOf(bag.getScore(value)), 40 + 40*column + 20, 700 +5 + 22 + 10);

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
            gc.fillText(String.valueOf(bag.getScore(value)), 40 + 40*column + 20, 700 +5 + 22 + 10);

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
            gc.fillText(String.valueOf(bag.getScore(value)), 40*row + 22, 40*column + 35);
        }
    }

}

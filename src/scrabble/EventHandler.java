/**
 * Project 3 - CS351, Fall 2019, Class that encapsulates mouse click
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;
public class EventHandler {

    /** Bag from where tile is drawn */
    private Bag bag;

    /**   Bag from where tile is drawn*/
    private Dictionary dictionary;

    /** Board where moves are made */
    private  Board board;

    /**  Score of the players*/
    private  Score score;

    /**  Human Player*/
    private  HumanPlayer p1;

    /**  Computer Player*/
    private ComputerPlayer p2;

    /** Clicked X Cordinate */
    private double xCor;

    /** Clicked Y Cordinate */
    private double yCor;

    /**  Checks if game is over or not*/
    private boolean gameOver;

    /**
     * Constructor for event handler
     *
     * @param Bag bag,  Bag from where tile is drawn
     * @param Dictionary dictionary,  Bag from where tile is drawn
     * @param Board board, Board where moves are made
     * @param Score score,Score of the players
     * @param HumanPlayer p1,Human Player
     * @param ComputerPlayer p2, Computer Player
     * @param double xCor, Clicked X Cordinate
     * @param double yCor, Clicked Y Cordinate
     */
    public EventHandler(Bag bag, Dictionary dictionary, Board board,
                        Score score, HumanPlayer p1, ComputerPlayer p2,
                        double xCor, double yCor) {
        this.bag = bag;
        this.dictionary = dictionary;
        this.board = board;
        this.score = score;
        this.p1 = p1;
        this.p2 = p2;
        this.xCor = xCor;
        this.yCor = yCor;
        this.gameOver = false;

    }

    /**
     * Handle each clicks and takes it to appropriate functions
     */
    public void handleEvent() {

        //game over condition
        if(bag.getNumberOfTiles() < 7) {
            gameOver = true;
        }
        int row = 0;
        int column = 0;
        int trayNumber = 0;
        if(xCor >= 0 && xCor <= 600  && yCor >= 0 && yCor <= 600) {
            column = (int) xCor/40;
            row = (int) yCor/40;
            clickTile(row, column);
        }

        if(xCor >= 40 && xCor <= 320  && yCor >= 700 && yCor <= 740) {
            trayNumber = (int) (xCor - 40) /40;
            clickTray(trayNumber);
        }

        if(xCor >= 50 && xCor <= 150  && yCor >= 630 && yCor <= 680) {
            playButton();
        }

        if(xCor >= 200 && xCor <= 300  && yCor >= 630 && yCor <= 680) {
            swapButton();
        }

        if(xCor >= 350 && xCor <= 450  && yCor >= 630 && yCor <= 680) {
            undoButton();
        }


    }

    /**
     * When board is clicked puts it into the duplicate board
     *
     * @param int row, row of the board
     * @param int column, column of the board
     */
    public void clickTile(int row, int column) {
        if(p1.isTraySelected()) {
            if(board.isFree(row, column)) {
                p1.putTileToBoard(row, column, p1.getSelectedTray());
                p1.setTraySelected(false);
                p1.setSelectedTray(0);
                p1.setPlacedCordinate(new Cordinate(row, column));
            }
        }
    }

    /**
     * Undoes if tray is put into the board
     */
    public void undoButton() {
        this.undoBoard();
        p1.setTraySelected(false);
        p1.setSelectedTray(0);

    }

    /**
     * When user clicks the play button, checks for valid placement
     * , valid word and allows user to play
     */
    public void playButton() {

        if(p1.checkForValidPlacement() == 'n') {
            undoBoard();
        }

        if(p1.checkForValidPlacement() == 'h') {
            if(p1.checkForValidWordInHorizontal()) {
                p1.updateScore('h');
                p1.placeTilesOnBoard();
                p1.updateTray();
                undoBoard();
                p2.startAI();
                p2.placeBestMove();
            }

            else {
                undoBoard();
                p2.startAI();
                p2.placeBestMove();
            }
        }

        if(p1.checkForValidPlacement() == 'v') {
            if(p1.checkForValidWordInVertical()) {
                p1.updateScore('v');
                p1.placeTilesOnBoard();
                p1.updateTray();
                undoBoard();

                p2.startAI();
                p2.placeBestMove();
            }

            else {
                undoBoard();
                p2.startAI();
                p2.placeBestMove();
            }

        }
    }

    /**
     * Function to swap tiles from your tray
     */
    public void swapButton() {
        if(board.isSwapInitialize()) {
            board.setSwapInitialize(false);
            p1.swapSelectedTiles();
            p1.setSwapSelectedTrayIndexNull();
            p2.startAI();
            p2.placeBestMove();
        }
        else {
            undoBoard();
            p1.setTraySelected(false);
            p1.setSelectedTray(0);
            board.setSwapInitialize(true);
        }


    }

    /**
     * Deals with your tray when it is clicked
     */
    public void clickTray(int trayNumber) {
        if(board.isSwapInitialize()) {
            p1.setSwapSelectedTrayIndex(trayNumber);
        }

        else {
            if(p1.getDuplicateTray().length() - 1 >= trayNumber) {
                if(p1.getTray().charAt(trayNumber) == '*') {
                    p1.setTraySelected(true);
                    p1.setSelectedTray(trayNumber);
                    this.changeWildCardToWords(trayNumber);

                }
                else {
                    p1.setTraySelected(true);
                    p1.setSelectedTray(trayNumber);
                }

            }

        }
    }

    /**
     * Undoes the board when undo button is clicked
     */
    public void undoBoard() {
        board.revertDuplicateBoard();
        p1.revertDuplicateTray();
        p1.setPlacedCordinateToNull();
    }

    /**
     * When wildcard is clicked in the tray, changes it to a letter
     */
    public void changeWildCardToWords(int trayNumber) {
        String newWord = "";

        if(p1.getDuplicateTray().charAt(trayNumber) == '*') {
            newWord = p1.getDuplicateTray().substring(0, trayNumber) + 'A' +
                    p1.getDuplicateTray().substring(trayNumber + 1);
            p1.setDuplicateTray(newWord);
        }

        else {
            int int_char = p1.getDuplicateTray().charAt(trayNumber)  + 1;
            if(int_char <= 90) {
                char character = (char) int_char;

                newWord = p1.getDuplicateTray().substring(0, trayNumber) + character +
                        p1.getDuplicateTray().substring(trayNumber + 1);
                p1.setDuplicateTray(newWord);
            }
        }
    }

    /**
     * Returns true if game is over and vice versa
     *
     * @return boolean, return true if game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }
}

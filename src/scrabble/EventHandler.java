package scrabble;

public class EventHandler {

    private Bag bag;

    private Dictionary dictionary;

    private  Board board;

    private  Score score;

    private  HumanPlayer p1;

    private ComputerPlayer p2;

    private double xCor;

    private double yCor;

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

    }

    public void handleEvent() {
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
            System.out.println("Play Button");
        }

        if(xCor >= 200 && xCor <= 300  && yCor >= 630 && yCor <= 680) {
            swapButton();
        }

        if(xCor >= 350 && xCor <= 450  && yCor >= 630 && yCor <= 680) {
            undoButton();
        }


    }


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

    public void undoButton() {
        System.out.println(p1.checkForValidPlacement());
        this.undoBoard();
        p1.setTraySelected(false);
        p1.setSelectedTray(0);
        p1.setPlacedCordinateToNull();
    }

    public void playButton() {

    }

    public void swapButton() {
        if(board.isSwapInitialize()) {
            board.setSwapInitialize(false);
            p1.swapSelectedTiles();
            p1.setSwapSelectedTrayIndexNull();
        }
        else {
            undoBoard();
            p1.setTraySelected(false);
            p1.setSelectedTray(0);
            board.setSwapInitialize(true);
        }


    }

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

    public void undoBoard() {
        board.revertDuplicateBoard();
        p1.revertDuplicateTray();
    }

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
}

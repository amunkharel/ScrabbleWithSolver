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
        }

        if(xCor >= 40 && xCor <= 320  && yCor >= 700 && yCor <= 740) {
            trayNumber = (int) (xCor - 40) /40;
        }

        if(xCor >= 50 && xCor <= 150  && yCor >= 630 && yCor <= 680) {
            System.out.println("Play Button");
        }

        if(xCor >= 200 && xCor <= 300  && yCor >= 630 && yCor <= 680) {
            System.out.println("Swap Button");
        }

        if(xCor >= 350 && xCor <= 450  && yCor >= 630 && yCor <= 680) {
            System.out.println("Undo Button");
        }


    }


    public void clickTile(int row, int column) {

    }

    public void playButton() {

    }

    public void swapButton() {

    }

    public void clickTray(int trayNumber) {

    }
}

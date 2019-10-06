package scrabble;

import java.util.ArrayList;
import java.util.List;

public class HumanPlayer {

    private int numberOfAlphabets;
    private String tray;
    private String duplicateTray;
    private Board board ;
    private char [][] boardArray;
    private Dictionary dictionary;
    private Bag bag;
    private List<Cordinate> validCordinates;
    private List<String> letterPermutations = new ArrayList<>();
    private Score score;
    private boolean traySelected;
    private int selectedTray;

    public HumanPlayer(Board board, Dictionary dictionary, Bag bag, Score score) {
        validCordinates = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
        this.numberOfAlphabets = 26;
        this.score = score;
        traySelected = false;
    }

    public void setTraySelected(boolean traySelected) {
        this.traySelected = traySelected;
    }

    public boolean isTraySelected() {
        return traySelected;
    }

    public void setSelectedTray(int selectedTray) {
        this.selectedTray = selectedTray;
    }

    public int getSelectedTray() {
        return selectedTray;
    }

    public void setTrayFromBag() {
        tray = "";
        duplicateTray = "";
        List<Tile> tiles = new ArrayList<Tile>();
        tiles = bag.giveTilesToTray(7);
        for (int i = 0; i < tiles.size(); i++ ) {
            tray =  tray + tiles.get(i).getLetter();
            duplicateTray = duplicateTray + tiles.get(i).getLetter();
        }
    }

    public String getDuplicateTray() {
        return  duplicateTray;
    }

    public String getTray() {
        return  tray;
    }
}

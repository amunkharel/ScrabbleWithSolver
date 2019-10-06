package scrabble;

import java.util.ArrayList;
import java.util.Collections;
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
    private char [][] duplicateBoard;
    private List<Integer> swapSelectedTrayIndex = new ArrayList<>();

    public HumanPlayer(Board board, Dictionary dictionary, Bag bag, Score score) {
        validCordinates = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
        this.numberOfAlphabets = 26;
        this.score = score;
        traySelected = false;
        duplicateBoard = board.getDuplicateboard();
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

    public void putTileToBoard(int row, int column, int tileIndex) {
        duplicateBoard[row][column] = duplicateTray.charAt(tileIndex);

        duplicateTray = charRemoveAt(duplicateTray, tileIndex);

    }

    public  String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

    public void revertDuplicateTray() {
        duplicateTray = "";
        for(int i = 0; i < tray.length(); i++) {
            duplicateTray = duplicateTray + tray.charAt(i);
        }
    }

    public void setSwapSelectedTrayIndex(int tileIndex) {
        this.swapSelectedTrayIndex.add(tileIndex);
    }

    public List<Integer> getSwapSelectedTrayIndex() {
        return  this.swapSelectedTrayIndex;
    }

    public void setSwapSelectedTrayIndexNull() {
        this.swapSelectedTrayIndex.clear();
    }

    public void swapSelectedTiles() {
        int numberFromBag = swapSelectedTrayIndex.size();
        for(int i = 0; i < swapSelectedTrayIndex.size(); i++) {
            bag.putBackInBag(duplicateTray.charAt(swapSelectedTrayIndex.get(i)));
        }

        swapSelectedTrayIndex = sortDescending();
        for(int i = 0; i < swapSelectedTrayIndex.size(); i++) {
            duplicateTray = charRemoveAt(duplicateTray, swapSelectedTrayIndex.get(i));
        }

        List<Tile> tiles = new ArrayList<Tile>();

        tiles = bag.giveTilesToTray(numberFromBag);

        for(int i = 0; i < tiles.size(); i++) {
            duplicateTray = duplicateTray + tiles.get(i).getLetter();
        }

        tray = "";

        for (int i = 0; i < duplicateTray.length(); i++) {
            tray = tray + duplicateTray.charAt(i);
        }
    }

    public List<Integer> sortDescending() {
        Collections.sort(swapSelectedTrayIndex, Collections.reverseOrder());
        return swapSelectedTrayIndex;
    }
}

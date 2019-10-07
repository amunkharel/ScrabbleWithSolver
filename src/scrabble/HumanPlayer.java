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
    private List<Cordinate> placedCordinate;
    private boolean firstMove;

    public HumanPlayer(Board board, Dictionary dictionary, Bag bag, Score score) {
        validCordinates = new ArrayList<Cordinate>();
        placedCordinate = new ArrayList<Cordinate>();
        this.board = board;
        boardArray = board.getBoard();
        this.dictionary = dictionary;
        this.bag = bag;
        this.numberOfAlphabets = 26;
        this.score = score;
        traySelected = false;
        duplicateBoard = board.getDuplicateboard();
        firstMove = true;
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
        if(!swapSelectedTrayIndex.contains(tileIndex)) {
            this.swapSelectedTrayIndex.add(tileIndex);
        }
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

    public void setDuplicateTray(String duplicateTray) {
        this.duplicateTray = duplicateTray;
    }

    public void setPlacedCordinate(Cordinate cordinate) {
        placedCordinate.add(cordinate);
    }

    public void setPlacedCordinateToNull() {
        placedCordinate.clear();
    }

    public void printPlacedCordinate() {
        for (int i = 0; i < placedCordinate.size(); i++) {
            System.out.println(placedCordinate.get(i).getX() + " " + placedCordinate.get(i).getY());
        }
    }

    public boolean checkForValidPlacement() {
        if(firstMove) {
            if(placedCordinate.size() < 2) {
                return  false;
            }

            if(!isPlacedOnValidCoordinate()) {
                return false;
            }

            if(isPlacedVerticallyOrHorizontally() == 'n') {
                return false;
            }

            firstMove = false;
        }

        else  {
            if(placedCordinate.size() < 1) {
                return false;
            }

            if(!isPlacedOnValidCoordinate()) {
                return  false;
            }

            if(isPlacedVerticallyOrHorizontally() == 'n') {
                return false;
            }



        }
        return false;
    }

    public boolean isPlacedOnValidCoordinate() {
        validCordinates = board.getValidCordinates();


        for (int i = 0; i < validCordinates.size(); i++) {

            for (int j = 0; j < placedCordinate.size(); j++) {

                if(placedCordinate.get(j).getX() == validCordinates.get(i).getX() &&
                        placedCordinate.get(j).getY() == validCordinates.get(i).getY()) {
                    return true;
                }
            }
        }

        return false;
    }

    public char isPlacedVerticallyOrHorizontally() {
        int startingRow = placedCordinate.get(0).getX();
        int startingColumn = placedCordinate.get(0).getY();
        boolean sameRow = true;
        boolean sameColumn = true;
        for(int i = 1; i < placedCordinate.size(); i++){
            if(placedCordinate.get(i).getX() != startingRow) {
                sameRow = false;
                break;
            }
        }

        for(int i = 1; i < placedCordinate.size(); i++){
            if(placedCordinate.get(i).getY() != startingColumn) {
                sameColumn = false;
                break;
            }
        }

        if(sameColumn) {
            return 'v';
        }

        if(sameRow) {
            return 'h';
        }

        if(!sameColumn && !sameColumn) {
            return 'n';
        }

        return  'n';
    }

    public boolean isValidPlacement(char direction) {
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);


        if(direction == 'h') {
            for (int i = columnIndex.get(0); i < columnIndex.get(columnIndex.size() - 1); i++) {
                if(!columnIndex.contains(i)) {
                    System.out.println(i);
                }
            }
        }

        if(direction == 'v') {
            
        }


        return true;
    }



}

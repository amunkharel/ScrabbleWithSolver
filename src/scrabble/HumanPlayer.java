/**
 * Project 3 - CS351, Fall 2019, Class for human player
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HumanPlayer {

    /** Number of alphabets in english */
    private int numberOfAlphabets;

    /**  Tray of human player*/
    private String tray;

    /** Duplicate Tray for the frontend */
    private String duplicateTray;

    /**  Board object to play the game*/
    private Board board ;

    /**  Representation of 2d board*/
    private char [][] boardArray;

    /**  Dictionary to check and see words*/
    private Dictionary dictionary;

    /** Bag from where tiles are drawn */
    private Bag bag;

    /**  List of valid cordinate where move is possible*/
    private List<Cordinate> validCordinates;

    /**  Letter Permutations*/
    private List<String> letterPermutations = new ArrayList<>();

    /**  Score tracker*/
    private Score score;

    /**  Checks if human tray is clicked or not*/
    private boolean traySelected;

    /**  Checks and see which tile is selected*/
    private int selectedTray;

    /**  2d Representation of duplicate board*/
    private char [][] duplicateBoard;

    /**  Tiles Clicked when swapping is initialized*/
    private List<Integer> swapSelectedTrayIndex = new ArrayList<>();

    /**  Cordinates where tiles are placed*/
    private List<Cordinate> placedCordinate;

    /** Checks if we already had a first move or not */
    private boolean firstMove;

    /**  Current Score of human player*/
    private int currentScore;

    /** Total score of human player */
    private int totalScore;


    /**
     * Constructor for human player
     *
     * @param Bag bag,  Bag from where tile is drawn
     * @param Board board, Board where moves are made
     * @param Score score,Score of the players
     * @param Dictionary dictionary, dictionary for the game
     */
    public HumanPlayer(Board board, Dictionary dictionary,
                       Bag bag, Score score) {
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
        currentScore = 0;
        totalScore = 0;
    }

    /**
     * Sets if a tile in tray selected
     *
     * @param boolean trayselected,  true if tray is clicked in GUI
     */
    public void setTraySelected(boolean traySelected) {
        this.traySelected = traySelected;
    }

    /**
     * Returns if tray is selected or not
     *
     * @return  boolean, true if tray is clicked in GUI
     */
    public boolean isTraySelected() {
        return traySelected;
    }

    /**
     * Sets Tile that is selected in GUI
     *
     * @param int selectedTray,  tile that is selected
     */
    public void setSelectedTray(int selectedTray) {
        this.selectedTray = selectedTray;
    }

    /**
     * Returns selected tile number from the tile
     *
     * @return int, tile that is selected
     */
    public int getSelectedTray() {
        return selectedTray;
    }

    /**
     * Sets all the tiles in the tray from bag
     *
     */
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

    /**
     * Gets string of duplicate Tray element
     *
     * @return String, duplicate tray of the game
     */
    public String getDuplicateTray() {
        return  duplicateTray;
    }

    /**
     * Gets string of  Tray elements
     *
     * @return String,  tray of the game
     */
    public String getTray() {
        return  tray;
    }

    /**
     * Puts tile to the board when user click to board
     *
     * @param int row, row of the board
     * @param int column, column of the board
     * @param int tileIndex, tileIndex in the tray
     */
    public void putTileToBoard(int row, int column, int tileIndex) {
        duplicateBoard[row][column] = duplicateTray.charAt(tileIndex);

        duplicateTray = charRemoveAt(duplicateTray, tileIndex);

    }

    /**
     * Removes character from a string at given index
     *
     * @param String str, string to remove char
     * @param int p, index of string
     * @return String, newly formed string
     */

    public  String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

    /**
     * Reverts duplicate tray to original upon undo
     */
    public void revertDuplicateTray() {
        duplicateTray = "";
        for(int i = 0; i < tray.length(); i++) {
            duplicateTray = duplicateTray + tray.charAt(i);
        }
    }

    /**
     * Sets tileIndex set for swapping
     *
     * @param int tileIndex, tileIndex set for swapping
     */
    public void setSwapSelectedTrayIndex(int tileIndex) {
        if(!swapSelectedTrayIndex.contains(tileIndex)) {
            this.swapSelectedTrayIndex.add(tileIndex);
        }
    }

    /**
     * Returns tileIndexes for swapping
     *
     * @param List<Integer>, returns tileIndexes for swapping
     */
    public List<Integer> getSwapSelectedTrayIndex() {
        return  this.swapSelectedTrayIndex;
    }

    /**
     * Clears the swapSelectedTray Index to Null
     */
    public void setSwapSelectedTrayIndexNull() {
        this.swapSelectedTrayIndex.clear();
    }

    /**
     * Swaps the selected tiles with new Tiles from the bag
     */
    public void swapSelectedTiles() {
        int numberFromBag = swapSelectedTrayIndex.size();
        for(int i = 0; i < swapSelectedTrayIndex.size(); i++) {
            bag.putBackInBag(duplicateTray.charAt(swapSelectedTrayIndex.get(i)));
        }

        swapSelectedTrayIndex = sortDescending();
        for(int i = 0; i < swapSelectedTrayIndex.size(); i++) {
            duplicateTray = charRemoveAt(duplicateTray,
                    swapSelectedTrayIndex.get(i));
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

    /**
     * Swaps a list in descending order
     *
     * @return List<Integer>, sorted list of integers
     */
    public List<Integer> sortDescending() {
        Collections.sort(swapSelectedTrayIndex, Collections.reverseOrder());
        return swapSelectedTrayIndex;
    }

    /**
     * Sets the duplicate tray
     *
     * @param String, duplicate tray of the game
     */
    public void setDuplicateTray(String duplicateTray) {
        this.duplicateTray = duplicateTray;
    }

    /**
     * Sets cordinates once a tile is placed on the board
     *
     * @param Cordinate cordinate, cordinate where tile is placed
     */
    public void setPlacedCordinate(Cordinate cordinate) {
        placedCordinate.add(cordinate);
    }


    /**
     * Clears placedCordinate variables to null
     */
    public void setPlacedCordinateToNull() {
        placedCordinate.clear();
    }

    /**
     * Prints placed cordinates for testing purpose
     */
    public void printPlacedCordinate() {
        for (int i = 0; i < placedCordinate.size(); i++) {
            System.out.println(placedCordinate.get(i).getX() + " " +
                    placedCordinate.get(i).getY());
        }
    }

    /**
     * checks if a placement of tile/tiles is valid or not
     * returns 'h' if valid horizontally
     * returns 'v' if valid vertically
     * else returns 'n' if not valid placement
     *
     * @return char, returns if placement is valid or not
     */
    public char checkForValidPlacement() {
        validCordinates = board.getValidCordinates();
        if(validCordinates.get(0).getX() == 7 &&
                validCordinates.get(0).getY() == 7) {
            firstMove = true;
        }

        else {
            firstMove = false;
        }


        if(firstMove) {
            if(placedCordinate.size() < 2) {
                return  'n';
            }

            if(!isPlacedOnValidCoordinate()) {
                return 'n';
            }

            if(isPlacedVerticallyOrHorizontally() == 'n') {
                return 'n';
            }

            if(isPlacedVerticallyOrHorizontally() == 'h') {
                if(isValidPlacement('h')) {
                    return 'h';
                }
                else {
                    return 'n';
                }
            }

            if(isPlacedVerticallyOrHorizontally() == 'v') {
                if(isValidPlacement('v')) {
                    return 'v';
                }
                else {
                    return 'n';
                }
            }


        }

        else  {
            if(placedCordinate.size() < 1) {
                return 'n';
            }

            if(!isPlacedOnValidCoordinate()) {
                return  'n';
            }

            if(isPlacedVerticallyOrHorizontally() == 'n') {
                return 'n';
            }

            if(isPlacedVerticallyOrHorizontally() == 'h') {
                if(isValidPlacement('h')) {
                    return 'h';
                }
                else {
                    return 'n';
                }
            }

            if(isPlacedVerticallyOrHorizontally() == 'v') {
                if(isValidPlacement('v')) {
                    return 'v';
                }
                else {
                    return 'n';
                }
            }

        }
        return 'n';
    }


    /**
     * Checks if our placement is made on atleast one valid cordinate or not
     *
     * @param boolean, true if placed on atleast one valid cordinate
     */
    public boolean isPlacedOnValidCoordinate() {



        for (int i = 0; i < validCordinates.size(); i++) {

            for (int j = 0; j < placedCordinate.size(); j++) {

                if(placedCordinate.get(j).getX() ==
                        validCordinates.get(i).getX() &&
                        placedCordinate.get(j).getY()
                                == validCordinates.get(i).getY()) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * checks if a placement is placed vertically, horizontally or none
     *
     * @return char, 'v' if vertical, 'h' if horizontal and 'n' if invalid
     */

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

    /**
     * Checks if the valid is made in a valid manner
     *
     * @param char direction, direction where placement is made
     * @return boolean, true if placement is valid
     */
    public boolean isValidPlacement(char direction) {
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);


        if(direction == 'h') {
            for (int i = columnIndex.get(0); i
                    < columnIndex.get(columnIndex.size() - 1); i++) {
                if(!columnIndex.contains(i)) {
                    if(board.isFreeRealBoard(startingRow, i)) {
                        return false;
                    }
                }
            }
        }

        if(direction == 'v') {
            for(int i = rowIndex.get(0); i <
                    rowIndex.get(rowIndex.size() - 1); i++) {
                if(!rowIndex.contains(i)) {
                    if(board.isFreeRealBoard(i, startingColumn)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if a word is valid in horizontal direction
     *
     * @return boolean, returns true if word is
     *                 valid sideways and in all direction
     */
    public boolean checkForValidWordInHorizontal() {
        if(horizontalValidMoveSideWays()) {
            if(horizontalMoveValidInTopDown()) {
                return true;
            }

            else {
                return false;
            }
        }

        else {
            return false;
        }

    }

    /**
     * Checks if a word is valid in vertical direction
     *
     * @return boolean, returns true if word is
     *                 valid sideways and in all direction
     */
    public boolean checkForValidWordInVertical() {


        if(verticalValidMoveSideways()) {
            if(verticalMoveValidInLeftRight()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }


    }

    /**
     * Checks if a word is valid in vertical direction sideways
     *
     * @return boolean, returns true if word is
     *                 valid sideways
     */
    public boolean verticalValidMoveSideways() {

        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        String completeWord = "";
        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxTopIndex = startingRow;

        int maxBottomIndex = rowIndex.get(rowIndex.size() - 1);

        if(!board.boardOutOfIndex(maxTopIndex - 1,  startingColumn)) {
            if(!board.isFreeRealBoard(maxTopIndex - 1, startingColumn)) {
                for(int j = maxTopIndex - 1; j >= 0; j-- ) {
                    if(!board.isFreeRealBoard(j,startingColumn)) {
                        maxTopIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(!board.boardOutOfIndex(maxBottomIndex + 1, startingColumn)) {
            if(!board.isFreeRealBoard(maxBottomIndex + 1,  startingColumn)) {
                for(int j = maxBottomIndex + 1; j < board.getSize(); j++ ) {
                    if(!board.isFreeRealBoard(j,startingColumn)) {
                        maxBottomIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = maxTopIndex; i <= maxBottomIndex; i++) {
            if(rowIndex.contains(i)) {
                completeWord = completeWord + duplicateBoard[i][startingColumn];
            }
            else {
                completeWord = completeWord + boardArray[i][startingColumn];
            }
        }

        if(maxBottomIndex == maxTopIndex) {
            return true;
        }

        else if(dictionary.isValidMove(completeWord)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if a word is valid in vertical direction left and Right
     *
     * @return boolean, returns true if word is
     *                 valid left and right for each character
     */
    public boolean verticalMoveValidInLeftRight() {

        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxTopIndex = startingRow;

        int maxBottomIndex = rowIndex.get(rowIndex.size() - 1);

        boolean isValidInAllDirection = true;
        for(int i = maxTopIndex; i <= maxBottomIndex; i++) {

            if(rowIndex.contains(i)) {
                if(!validMoveLeftRight(duplicateBoard[i][startingColumn], i, startingColumn)) {
                    isValidInAllDirection = false;
                    break;
                }
            }

        }

        if(isValidInAllDirection) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if each character in a vertical word is valid in all direction
     *
     * @param char character, character of a word
     * @param int row, row where letter is
     * @param int column, column where letter is
     * @param boolean, returns true if word is
     *                 valid sideways
     */
    public boolean validMoveLeftRight(char character, int row, int column) {
        int startIndex = 0;
        int stopIndex = 0;
        String completeWord = "";

        boolean noLeftMove = false;
        boolean noRightMove = false;

        if(board.boardOutOfIndex(row, column - 1)) {
            noLeftMove = true;
        }

        else {
            if(board.isFreeRealBoard(row, column  - 1)) {
                noLeftMove = true;
            }
        }

        if(board.boardOutOfIndex(row, column + 1)) {
            noRightMove = true;
        }

        else {
            if(board.isFreeRealBoard(row, column + 1)) {
                noRightMove = true;
            }
        }

        if(noLeftMove && noRightMove) {
            return true;
        }


        else if(!noLeftMove && noRightMove) {

            startIndex = column;
            for(int i = column - 1; i >=0; i--) {
                if(board.isFreeRealBoard(row, i)) {
                    break;
                }
                startIndex--;
            }
            for(int i = startIndex; i < column; i++) {
                completeWord = completeWord +boardArray[row][i];
            }
            completeWord = completeWord + character;

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else {
                return false;
            }
        }

        else if(noLeftMove && !noRightMove) {
            completeWord = completeWord + character;
            for(int i = column + 1; i < board.getSize(); i++) {
                if(board.isFreeRealBoard(row, i)){
                    break;
                }
                else {
                    completeWord = completeWord + boardArray[row][i];
                }
            }

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }

            else {
                return false;
            }

        }

        else if(!noLeftMove && !noRightMove) {
            startIndex = column;
            stopIndex = column;

            for(int i = column - 1; i >= 0; i--) {
                if(board.isFreeRealBoard(row, i)) {
                    break;
                }

                else {
                    startIndex--;
                }
            }

            for (int i = column + 1; i < board.getSize(); i++) {
                if(board.isFreeRealBoard(row,i )) {
                    break;
                }
                else {
                    stopIndex++;
                }
            }

            for(int i = startIndex; i < column; i++) {
                completeWord = completeWord +boardArray[row][i];
            }
            completeWord = completeWord + character;

            for (int i = column + 1; i <= stopIndex; i++) {
                completeWord = completeWord + boardArray[row][i];
            }

            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else  {
                return false;
            }
        }



        return false;
    }

    /**
     * Checks if a word is valid in horizonatal direction sideways
     *
     * @param boolean, returns true if word is
     *                 valid sideways
     */
    public boolean horizontalValidMoveSideWays() {
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        String completeWord = "";
        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxLeftIndex = startingColumn;

        int maxRightIndex = columnIndex.get(columnIndex.size() - 1);

        if(!board.boardOutOfIndex(startingRow, maxLeftIndex -1)) {
            if(!board.isFreeRealBoard(startingRow, maxLeftIndex - 1)) {
                for(int j = maxLeftIndex - 1; j >= 0; j-- ) {
                    if(!board.isFreeRealBoard(startingRow,j)) {
                        maxLeftIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(!board.boardOutOfIndex(startingRow, maxRightIndex +1)) {
            if(!board.isFreeRealBoard(startingRow, maxRightIndex + 1)) {
                for(int j = maxRightIndex + 1; j < board.getSize(); j++ ) {
                    if(!board.isFreeRealBoard(startingRow,j)) {
                        maxRightIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = maxLeftIndex; i <= maxRightIndex; i++) {
            if(columnIndex.contains(i)) {
               completeWord = completeWord + duplicateBoard[startingRow][i];
            }
            else {
                completeWord = completeWord + boardArray[startingRow][i];
            }

        }

        if(maxLeftIndex == maxRightIndex) {
            return true;
        }
        else if(dictionary.isValidMove(completeWord)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if a word is valid in vertical direction in all direction
     *
     * @param boolean, returns true if word is
     *                 valid in all direction
     */
    public boolean horizontalMoveValidInTopDown() {
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxLeftIndex = startingColumn;

        int maxRightIndex = columnIndex.get(columnIndex.size() - 1);

        boolean isValidInAllDirection = true;
        for(int i = maxLeftIndex; i <= maxRightIndex; i++) {

            if(columnIndex.contains(i)) {
                if(!validMoveTopDown(duplicateBoard[startingRow][i], startingRow, i)) {
                    isValidInAllDirection = false;
                    break;
                }
            }

        }

        if(isValidInAllDirection) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Checks if each character in a horizontal word is valid in all direction
     *
     * @param char character, character of a word
     * @param int row, row where letter is
     * @param int column, column where letter is
     * @param boolean, returns true if word is
     *                 valid sideways
     */
    public boolean validMoveTopDown(char character, int row, int column) {

        int startIndex = 0;
        int stopIndex = 0;
        String completeWord = "";
        boolean noTopMove = false;
        boolean noBottomMove = false;

        if(board.boardOutOfIndex(row - 1, column)) {
            noTopMove = true;
        }

        else {
            if(board.isFreeRealBoard(row - 1, column)) {
                noTopMove = true;
            }
        }

        if(board.boardOutOfIndex(row + 1, column)) {
            noBottomMove = true;
        }

        else {
            if(board.isFreeRealBoard(row + 1, column)) {
                noBottomMove = true;
            }
        }


        if(noTopMove && noBottomMove) {
            return true;
        }

        else if(noTopMove && !noBottomMove) {
            completeWord = completeWord + character;
            for(int i = row + 1; i < board.getSize(); i++) {
                if(board.isFreeRealBoard(i, column)){
                    break;
                }
                else {
                    completeWord = completeWord + boardArray[i][column];
                }
            }
            if(dictionary.isValidMove(completeWord)) {
                return true;
            }
            else  {
                return false;
            }
        }

        else if(noBottomMove && !noTopMove) {
            startIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(board.isFreeRealBoard(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }
            for(int i = startIndex; i < row; i++) {
                completeWord = completeWord +boardArray[i][column];
            }
            completeWord = completeWord + character;
            if(dictionary.isValidMove(completeWord)) {
                return true;
            }

            else {
                return false;
            }
        }

        else if(!noBottomMove && !noTopMove) {
            startIndex = row;
            stopIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(board.isFreeRealBoard(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }

            for(int i = row + 1; i < board.getSize(); i++) {
                if(board.isFreeRealBoard(i, column)){
                    break;
                }
                else {
                    stopIndex ++;
                }
            }

            for(int i = startIndex; i < row; i++) {
                completeWord = completeWord +boardArray[i][column];
            }
            completeWord = completeWord + character;

            for (int i = row + 1; i <= stopIndex; i++) {
                completeWord = completeWord + boardArray[i][column];
            }

            if(dictionary.isValidMove(completeWord)) {
                return  true;
            }
            else {
                return false;
            }

        }
        return false;
    }

    /**
     * Updates score after a move is made
     *
     * @param char direction, direction of move
     */
    public void updateScore(char direction) {

        if(placedCordinate.size() == 7) {
            currentScore = currentScore + 50;
        }

        if(direction == 'h') {
            calculateScoreForWordLeftRight();
        }

        if(direction == 'v') {
            calculateScoreForWordTopBottom();
        }

    }

    /**
     * Calculates score for word in vertical
     */
    public  void calculateScoreForWordTopBottom() {
        int multiplier = 1;
        int wordScore = 0;

        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxTopIndex = startingRow;

        int maxBottomIndex = rowIndex.get(rowIndex.size() - 1);

        if(!board.boardOutOfIndex(maxTopIndex - 1,  startingColumn)) {
            if(!board.isFreeRealBoard(maxTopIndex - 1, startingColumn)) {
                for(int j = maxTopIndex - 1; j >= 0; j-- ) {
                    if(!board.isFreeRealBoard(j,startingColumn)) {
                        maxTopIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(!board.boardOutOfIndex(maxBottomIndex + 1, startingColumn)) {
            if(!board.isFreeRealBoard(maxBottomIndex + 1,  startingColumn)) {
                for(int j = maxBottomIndex + 1; j < board.getSize(); j++ ) {
                    if(!board.isFreeRealBoard(j,startingColumn)) {
                        maxBottomIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = maxTopIndex; i <= maxBottomIndex; i++) {
            if(rowIndex.contains(i)) {
                if(containsWordsMultiplier(i, startingColumn)) {
                    multiplier = multiplier *
                            multiplierScore(boardArray[i][startingColumn]);
                }

                else if(containsLetterMultiplier(i, startingColumn)) {
                    wordScore = wordScore +
                            bag.getScore(duplicateBoard[i][startingColumn])
                            * letterMultiplierScore(boardArray[i][startingColumn]);
                }
                else {
                    wordScore = wordScore +
                            bag.getScore(duplicateBoard[i][startingColumn]);
                }
                calculateScoreInLeftRight(i,
                        startingColumn, duplicateBoard[i][startingColumn]);
            }
            else {
                wordScore = wordScore +
                        bag.getScore(boardArray[i][startingColumn]);
            }
        }

        wordScore = wordScore * multiplier;

        currentScore = currentScore + wordScore;

        totalScore = totalScore + currentScore;
        currentScore = 0;


    }

    /**
     * Calculates score in the left and right for vertical move
     *
     * @param int row, row of letter
     * @param int column, column of letter
     * @param char character, letter
     */
    public void calculateScoreInLeftRight
            (int row, int column, char character) {
        int score = 0;
        int multiplier = 1;
        int startIndex = 0;
        int stopIndex = 0;
        boolean noLeftMove = false;
        boolean noRightMove = false;

        if(boardOutOfIndex(row, column - 1)) {
            noLeftMove = true;
        }

        else {
            if(isFree(row, column - 1)) {
                noLeftMove = true;
            }
        }

        if(boardOutOfIndex(row, column + 1)) {
            noRightMove = true;
        }

        else {
            if(isFree(row, column + 1)) {
                noRightMove = true;
            }
        }


        if(noLeftMove && !noRightMove) {
            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            for(int i = column + 1; i < board.getSize(); i++) {
                if(isFree(row, i)){
                    break;
                }
                else {
                    score = score + bag.getScore(boardArray[row][i]);
                }
            }
        }

        else if(noRightMove && !noLeftMove) {
            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            startIndex = column;
            for(int i = column - 1; i >= 0; i--) {
                if(isFree(row, i)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }
            for(int i = startIndex; i < column; i++) {
                score = score + bag.getScore(boardArray[row][i]);
            }

        }


        else if(!noLeftMove && !noRightMove) {

            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            startIndex = column;
            stopIndex = column;
            for(int i = column - 1; i >= 0; i--) {
                if(isFree(row, i)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }

            for(int i = column + 1; i < board.getSize(); i++) {
                if(isFree(row, i)){
                    break;
                }
                else {
                    stopIndex ++;
                }
            }

            for(int i = startIndex; i < column; i++) {
                score = score + bag.getScore(boardArray[row][i]);
            }

            for (int i = column + 1; i <= stopIndex; i++) {
                score = score + bag.getScore(boardArray[row][i]);
            }

        }

        score = score*multiplier;

        currentScore = currentScore + score;


    }

    /**
     * Calculates score in the horizontal move
     */
    public void calculateScoreForWordLeftRight() {
        int multiplier = 1;
        int wordScore = 0;

        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        for(int i = 0; i < placedCordinate.size(); i++) {
            rowIndex.add(placedCordinate.get(i).getX());
            columnIndex.add(placedCordinate.get(i).getY());
        }
        Collections.sort(rowIndex);
        Collections.sort(columnIndex);

        String completeWord = "";
        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);

        int maxLeftIndex = startingColumn;

        int maxRightIndex = columnIndex.get(columnIndex.size() - 1);

        if(!board.boardOutOfIndex(startingRow, maxLeftIndex -1)) {
            if(!board.isFreeRealBoard(startingRow, maxLeftIndex - 1)) {
                for(int j = maxLeftIndex - 1; j >= 0; j-- ) {
                    if(!board.isFreeRealBoard(startingRow,j)) {
                        maxLeftIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(!board.boardOutOfIndex(startingRow, maxRightIndex +1)) {
            if(!board.isFreeRealBoard(startingRow, maxRightIndex + 1)) {
                for(int j = maxRightIndex + 1; j < board.getSize(); j++ ) {
                    if(!board.isFreeRealBoard(startingRow,j)) {
                        maxRightIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = maxLeftIndex; i <= maxRightIndex; i++) {
            if(columnIndex.contains(i)) {
                if(containsWordsMultiplier(startingRow, i)) {
                    multiplier = multiplier * multiplierScore(boardArray[startingRow][i]);
                }

                else if(containsLetterMultiplier(startingRow, i)) {
                    wordScore = wordScore + bag.getScore(duplicateBoard[startingRow][i])
                            * letterMultiplierScore(boardArray[startingRow][i]);
                }
                else {
                    wordScore = wordScore + bag.getScore(duplicateBoard[startingRow][i]);
                }

                calculateScoreInTopBottom(startingRow, i, duplicateBoard[startingRow][i]);
            }
            else {
                wordScore = wordScore + bag.getScore(boardArray[startingRow][i]);
            }

        }

        wordScore = wordScore * multiplier;

        currentScore = currentScore + wordScore;

        totalScore = totalScore + currentScore;
        currentScore = 0;

    }

    /**
     * Calculates score in the top and bottom for horizontal move
     *
     * @param int row, row of letter
     * @param int column, column of letter
     * @param char character, letter
     */
   public void calculateScoreInTopBottom
            (int row, int column, char character) {
        int score = 0;
        int multiplier = 1;
        int startIndex = 0;
        int stopIndex = 0;
        boolean noTopMove = false;
        boolean noBottomMove = false;

        if(boardOutOfIndex(row - 1, column)) {
            noTopMove = true;
        }

        else {
            if(isFree(row - 1, column)) {
                noTopMove = true;
            }
        }

        if(boardOutOfIndex(row + 1, column)) {
            noBottomMove = true;
        }

        else {
            if(isFree(row + 1, column)) {
                noBottomMove = true;
            }
        }



        if(noTopMove && !noBottomMove) {
            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            for(int i = row + 1; i < board.getSize(); i++) {
                if(isFree(i, column)){
                    break;
                }
                else {
                    score = score + bag.getScore(boardArray[i][column]);
                }
            }
        }

        else if(noBottomMove && !noTopMove) {
            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            startIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(isFree(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }
            for(int i = startIndex; i < row; i++) {
                score = score + bag.getScore(boardArray[i][column]);
            }

        }

        else if(!noBottomMove && !noTopMove) {

            if(containsWordsMultiplier(row, column)) {
                multiplier = multiplier * multiplierScore(boardArray[row][column]);
                score = score + bag.getScore(character);
            }
            else if(containsLetterMultiplier(row,column)) {
                score = score + letterMultiplierScore(boardArray[row][column])
                        * bag.getScore(character);
            }
            else  {
                score = score + bag.getScore(character);
            }

            startIndex = row;
            stopIndex = row;
            for(int i = row - 1; i >= 0; i--) {
                if(isFree(i, column)) {
                    break;
                }
                else {
                    startIndex --;
                }
            }

            for(int i = row + 1; i < board.getSize(); i++) {
                if(isFree(i, column)){
                    break;
                }
                else {
                    stopIndex ++;
                }
            }

            for(int i = startIndex; i < row; i++) {
                score = score + bag.getScore(boardArray[i][column]);
            }

            for (int i = row + 1; i <= stopIndex; i++) {
                score = score + bag.getScore(boardArray[i][column]);
            }

        }

        score = score*multiplier;


        currentScore = currentScore + score;


    }

    /**
     * Places tiles on the board
     */
    public void placeTilesOnBoard() {
        int x = 0;
        int y = 0;
        for(int i = 0; i < placedCordinate.size(); i++) {
            x = placedCordinate.get(i).getX();
            y = placedCordinate.get(i).getY();
            board.insertScoreAndIntialLetters(x, y, duplicateBoard[x][y]);
        }
    }

    /**
     * Updates the tray of human player
     */
    public void updateTray() {
        tray = "";
        int replaceNeeded = 0;
        for(int i = 0; i < duplicateTray.length(); i++) {
            tray = tray + duplicateTray.charAt(i);
        }

        replaceNeeded = 7 - tray.length();

        List<Tile> tiles = new ArrayList<Tile>();
        tiles = bag.giveTilesToTray(replaceNeeded);
        for (int i = 0; i < tiles.size(); i++ ) {
            tray =  tray + tiles.get(i).getLetter();
            duplicateTray = duplicateTray + tiles.get(i).getLetter();
        }
    }


    /**
     * Checks if the spot in the board is free to add letters or not
     *
     * @param int x, row of the board
     * @param int y, column of the board
     * @return boolean, returns true if given spot is free and vice versa
     */
    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3' || boardArray[x][y] == '4' ||
                boardArray[x][y] == '@' || boardArray[x][y] == '!' || boardArray[x][y] == '$') {
            return true;
        }
        return  false;
    }

    /**
     * Checks if the spot has word multiplier or not
     *
     * @param int x, row of the board
     * @param int y, column of the board
     * @return boolean, returns true spot contains word multiplier
     */
    public boolean containsWordsMultiplier(int row, int column) {
        if(boardArray[row][column] == '@' || boardArray[row][column] == '$' ||
                boardArray[row][column] == '!') {
            return true;
        }
        return false;
    }

    /**
     * Returns score of multiplier
     *
     * @param char character, return score for character
     * @return int, returns score of multiplier
     */
    public int multiplierScore(char character) {
        if(character == '@') {
            return 3;
        }
        if(character == '!') {
            return  2;
        }

        if(character == '$') {
            return 4;
        }

        return 0;
    }

    /**
     * Checks if spot contains letter multiplier or not
     *
     * @param int x, row of the board
     * @param int y, column of the board
     * @return boolean, returns true if given spot has letter multiplier
     */
    public boolean containsLetterMultiplier(int row, int column) {
        if(boardArray[row][column] == '2' || boardArray[row][column] == '3' ||
                boardArray[row][column] == '4') {
            return true;
        }
        return false;
    }

    /**
     * Returns score of multiplier
     *
     * @param char character, return score for character
     * @return int, returns score of multiplier
     */
    public int letterMultiplierScore(char character) {
        if(character == '3') {
            return 3;
        }
        if(character == '2') {
            return  2;
        }

        if(character == '4') {
            return 4;
        }

        return 0;
    }

    /**
     * Checks if the board is out of index or not
     *
     * @param int i, row of the board
     * @param int j, column of the board
     * @return boolean, returns true if board is out of index and vice versa
     */
    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > board.getSize() - 1 || j > board.getSize() - 1){
            return true;
        }
        return  false;
    }

    /**
     * Returns total score of the player
     *
     * @return int, returns total score of the player
     */
    public int getTotalScore() {
        return totalScore;
    }
}

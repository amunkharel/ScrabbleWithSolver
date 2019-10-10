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
    private int currentScore;
    private int totalScore;

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
        currentScore = 0;
        totalScore = 0;
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

    public char checkForValidPlacement() {
        validCordinates = board.getValidCordinates();
        if(validCordinates.get(0).getX() == 7 && validCordinates.get(0).getY() == 7) {
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

    public boolean isPlacedOnValidCoordinate() {



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

        int startingRow = rowIndex.get(0);
        int startingColumn = columnIndex.get(0);


        if(direction == 'h') {
            for (int i = columnIndex.get(0); i < columnIndex.get(columnIndex.size() - 1); i++) {
                if(!columnIndex.contains(i)) {
                    if(board.isFreeRealBoard(startingRow, i)) {
                        return false;
                    }
                }
            }
        }

        if(direction == 'v') {
            for(int i = rowIndex.get(0); i < rowIndex.get(rowIndex.size() - 1); i++) {
                if(!rowIndex.contains(i)) {
                    if(board.isFreeRealBoard(i, startingColumn)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

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
}

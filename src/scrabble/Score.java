/**
 * Project 3 - CS351, Fall 2019, Scoring class for computer
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */
package scrabble;

public class Score {

    /**Current score for computer */
    private int currentScore;

    /** Total score for computer*/
    private int compTotalScore;

    /** Best Score for computer*/
    private int compCurrentBestScore;

    /** starting row of best move*/
    private int currentBestMoveRowComp;

    /** starting column of best move*/
    private int currentBestMoveColumnComp;

    /** direction of best move*/
    private char compBestMoveDirection;

    /** word for best move*/
    private String compBestCurrentWord;

    /** Bag for drawing tile score*/
    private Bag bag;

    /** Board of the game*/
    private Board board;

    /** 2D representation of the game */
    private char [][] boardArray;

    /**
     * Constructor for scoring
     *
     * @param Bag bag,  Bag from where tile is drawn
     * @param Board board, Board where moves are made
     */
    public Score(Bag bag, Board board) {
        currentScore = 0;
        compTotalScore = 0;
        compCurrentBestScore = 0;
        currentBestMoveColumnComp = 0;
        currentBestMoveRowComp = 0;
        compBestCurrentWord = "";
        compBestMoveDirection = '-';
        this.bag = bag;
        this.board = board;
        boardArray = board.getBoard();

    }

    /**
     * Start getting score of a word
     *
     * @param int row,  starting row
     * @param int column, starting column
     * @param char player, which player
     * @param String word, word used
     */
    public void getScore(int row, int column, char player,
                         char direction, String word) {

        if(word.length() == 7) {
            currentScore = 50;
        }
        if(direction == 'h') {
            calculateScoreForWordLeftRight(row, column, player, word);

            for(int i = 0; i < word.length(); i++) {
                calculateScoreInTopBottom(row, column + i, word.charAt(i));
            }

            recordBestMove(row, column, player, direction, word);
        }

        if(direction == 'v') {
            calculateScoreForTopBottom(row, column, player, word);

            for(int i = 0; i < word.length(); i++) {
                calculateScoreInLeftRight(row + i, column, word.charAt(i));
            }

            recordBestMove(row, column, player, direction, word);
        }


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
                multiplier = multiplier * multiplierScore(boardArray[row]
                        [column]);
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
                multiplier = multiplier * multiplierScore(boardArray
                        [row][column]);
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
     * Calculates score for word in vertical
     * @param int row, row of letter
     * @param int column, column of letter
     * @param String word, word being played
     */
    public void calculateScoreForTopBottom(int row, int column,
                                           char player, String word) {
        int wordScore = 0;
        int multiplier = 1;
        int maxTopIndex = row;
        int maxBottomIndex = row + word.length() - 1;

        if(!boardOutOfIndex(row - 1, column)) {
            if(!isFree(row - 1, column)) {
                for(int j = row - 1; j>= 0; j-- ) {
                    if(!isFree(j, column)) {
                        maxTopIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        if(!boardOutOfIndex(maxBottomIndex + 1,  column)) {
            if(!isFree(maxBottomIndex + 1,  column)) {
                for(int j = maxBottomIndex + 1; j < board.getSize(); j++) {
                    if(!isFree(j, column)) {
                        maxBottomIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = row; i <= row + word.length() - 1; i++) {
            if(containsWordsMultiplier(i, column)) {
                multiplier = multiplier * multiplierScore(boardArray[i][column]);
            }
        }

        for(int i = maxTopIndex; i < row; i++ ) {
            wordScore = wordScore + bag.getScore(boardArray[i][column]);
        }


        int counter = 0;
        for(int i = row; i <= row + word.length() - 1; i ++) {
            if(containsLetterMultiplier(i, column)) {
                wordScore = wordScore + bag.getScore(word.charAt(counter))
                        * letterMultiplierScore(boardArray[i][column]);
            }
            else {
                wordScore = wordScore + bag.getScore(word.charAt(counter));
            }
            counter++;
        }


        for(int i = row + word.length(); i <= maxBottomIndex; i++) {
            wordScore = wordScore + bag.getScore(boardArray[i][column]);
        }

        wordScore = wordScore * multiplier;
        currentScore = currentScore + wordScore;

    }


    /**
     * Calculates score for word in horizontal
     * @param int row, row of letter
     * @param int column, column of letter
     * @param String word, word being played
     */
    public void calculateScoreForWordLeftRight(int row, int column,
                                               char player, String word) {
        int wordScore = 0;
        int multiplier = 1;

        int maxLeftIndex = column;
        int maxRightIndex = column + word.length() - 1;

        if(!boardOutOfIndex(row, column-1)) {
            if(!isFree(row, column - 1)) {
                for(int j = column - 1; j >= 0; j-- ) {
                    if(!isFree(row,j)) {
                        maxLeftIndex = j;
                    }
                    else {
                        break;
                    }
                }

            }
        }

        if(!boardOutOfIndex(row, maxRightIndex + 1)) {
            if(!isFree(row, maxRightIndex + 1)) {
                for(int j = maxRightIndex + 1; j < board.getSize(); j++) {
                    if(!isFree(row, j)) {
                        maxRightIndex = j;
                    }
                    else {
                        break;
                    }
                }
            }
        }

        for(int i = column; i <= column + word.length() - 1; i++) {
            if(containsWordsMultiplier(row, i)) {
                multiplier = multiplier * multiplierScore(boardArray[row][i]);
            }
        }


        for(int i = maxLeftIndex; i < column; i++ ) {
            wordScore = wordScore + bag.getScore(boardArray[row][i]);
        }

        int counter = 0;
        for(int i = column; i <= column + word.length() - 1; i ++) {
            if(containsLetterMultiplier(row, i)) {
                wordScore = wordScore + bag.getScore(word.charAt(counter))
                        * letterMultiplierScore(boardArray[row][i]);
            }
            else {
                wordScore = wordScore + bag.getScore(word.charAt(counter));
            }
            counter++;
        }

        for(int i = column + word.length(); i <= maxRightIndex; i++) {
            wordScore = wordScore + bag.getScore(boardArray[row][i]);
        }

        wordScore = wordScore * multiplier;


        currentScore = currentScore + wordScore;
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
     * Records the best move
     *
     * @param int row, starting row of word
     * @param int column, starting column of word
     * @param char player, which player it is
     * @param char direction, direction of move
     * @param String word, word of the best move
     */
    public void recordBestMove(int row, int column, char player,
                               char direction, String  word) {

        if(player == 'c') {
            if(currentScore > compCurrentBestScore) {
                this.currentBestMoveRowComp = row;
                this.currentBestMoveColumnComp = column;
                this.compBestMoveDirection = direction;
                this.compBestCurrentWord = word;
                compCurrentBestScore = currentScore;
            }
        }

        currentScore = 0;
    }

    /**
     * Resets the current best score
     */
    public void resetCurrentCompScore() {
        compTotalScore = compTotalScore + compCurrentBestScore;
        compCurrentBestScore = 0;
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
                boardArray[x][y] == '2' || boardArray[x][y] == '3'
                || boardArray[x][y] == '4' ||
                boardArray[x][y] == '@' || boardArray[x][y] == '!'
                || boardArray[x][y] == '$') {
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
     * Returns total score of computer
     *
     * @return int, total score of computer
     */
    public int getCompTotalScore() {
        return compTotalScore;
    }

    /**
     * Starting row of best word
     *
     * @return int, row of best word
     */
    public int getComputerBestMoveRow() {
        return  currentBestMoveRowComp;
    }

    /**
     * Starting column of best word
     *
     * @return int, column of best word
     */
    public int getComputerBestMoveColumn() {
        return  currentBestMoveColumnComp;
    }

    /**
     * Direction of best move
     *
     * @return char, direction of best move
     */
    public char getCompBestMoveDirection() {
        return compBestMoveDirection;
    }

    /**
     * Best Word
     *
     * @return String, Best Word
     */
    public String getCompBestCurrentWord() {
        return  compBestCurrentWord;
    }

    /**
     * Current best score of computer
     *
     * @return int, current best score of computer
     */
    public int getCompCurrentBestScore() {
        return compCurrentBestScore;
    }

    /**
     * Sets total score for computer
     *
     * @param int score, adds to total score
     */
    public void setCompTotalScore(int score) {
        this.compTotalScore = compTotalScore + score;
    }
}

package scrabble;

public class Score {

    private int currentScore;

    private int compTotalScore;
    private int compCurrentBestScore;

    private int currentBestMoveRowComp;
    private int currentBestMoveColumnComp;
    private char compBestMoveDirection;
    private String compBestCurrentWord;

    private Bag bag;

    private Board board;

    private char [][] boardArray;

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

    public void getScore(int row, int column, char player, char direction, String word) {

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
    public void calculateScoreForTopBottom(int row, int column, char player, String word) {
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

    public void calculateScoreForWordLeftRight(int row, int column, char player, String word) {
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

    public void recordBestMove(int row, int column, char player, char direction, String  word) {

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

    public void resetCurrentCompScore() {
        compCurrentBestScore = 0;
    }

    public boolean isFree(int x, int y) {
        if(boardArray[x][y] == '-' || boardArray[x][y] == '1' ||
                boardArray[x][y] == '2' || boardArray[x][y] == '3' || boardArray[x][y] == '4' ||
                boardArray[x][y] == '@' || boardArray[x][y] == '!' || boardArray[x][y] == '$') {
            return true;
        }
        return  false;
    }

    public boolean containsWordsMultiplier(int row, int column) {
        if(boardArray[row][column] == '@' || boardArray[row][column] == '$' ||
        boardArray[row][column] == '!') {
            return true;
        }
        return false;
    }

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

    public boolean containsLetterMultiplier(int row, int column) {
        if(boardArray[row][column] == '2' || boardArray[row][column] == '3' ||
                boardArray[row][column] == '4') {
            return true;
        }
        return false;
    }

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


    public boolean boardOutOfIndex(int i, int j) {
        if(i < 0 || j <0 || i > board.getSize() - 1 || j > board.getSize() - 1){
            return true;
        }
        return  false;
    }

    public int getComputerBestMoveRow() {
        return  currentBestMoveRowComp;
    }

    public int getComputerBestMoveColumn() {
        return  currentBestMoveColumnComp;
    }

    public char getCompBestMoveDirection() {
        return compBestMoveDirection;
    }

    public String getCompBestCurrentWord() {
        return  compBestCurrentWord;
    }

    public int getCompCurrentBestScore() {
        return compCurrentBestScore;
    }

    public void setCompTotalScore(int score) {
        this.compTotalScore = compTotalScore + score;
    }
}

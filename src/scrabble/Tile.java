/**
 * Project 3 - CS351, Fall 2019, Tile for the bag
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */

package scrabble;

public class Tile {

    /**Letter of tile */
    private char letter;

    /**Score of tile*/
    private int score;

    /**
     * Constructor for Tile
     *
     * @param char letter, letter for tile
     * @param int score, score for tile
     */
    public Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    /**
     * Gets letter of tile
     *
     * @return char, letter of tile
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Gets score of tile
     *
     * @return int, score of tile
     */
    public int getScore() {
        return score;
    }




}

/**
 * Project 3 - CS351, Fall 2019, Cordinate to store row and column
 * @version Date 2019-10-15
 * @author Amun Kharel
 *
 *
 */
package scrabble;

public class Cordinate {

    /** Row of the cordinate */
    private int x;

    /** Column of the cordinate*/
    private int y;

    /**
     * Cordinate Construction
     *
     * @param int x, row of cordinate
     * @param int y, column of cordinate
     */
    public Cordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get row of the cordinate
     *
     * @return int, row of cordinate
     */
    public int getX() {
        return x;
    }

    /**
     * get column of the cordinate
     *
     * @return int, column of cordinate
     */
    public int getY() {
        return y;
    }
}

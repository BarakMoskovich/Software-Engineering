/**
 * Created by Barak Moskovich
 */
package build;

public class Point {
    private int x, y;

    /**
     * Constructor
     * @param x coordinate
     * @param y  coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set new value to X coordinate
     * @param x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set new value to Y coordinate
     * @param y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Return X coordinate value
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Return Y coordinate value
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Check if other Point equals to this
     * @param point
     * @return true if equal
     */
    public boolean equals(Point point) {
        if (this.x == point.getX() && this.y == point.getY())
            return true;
        return false;
    }

    /**
     * @Return string outpot
     */
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }
}

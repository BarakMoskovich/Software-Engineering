/**
 * Created by Barak Moskovich
 */
package build;

public class Square {
    private Line rightLine, leftLine, bottomLine, topLine;

    /**
     * Constructor
     * @param rightLine
     * @param leftLine
     * @param bottomLine
     * @param topLine
     */
    public Square(Line rightLine, Line leftLine, Line bottomLine, Line topLine) {
        this.rightLine  = rightLine;
        this.leftLine = leftLine;
        this.bottomLine = bottomLine;
        this.topLine = topLine;
    }

    /**
     * Set new value to rightLine
     * @param rightLine
     */
    public void setRightLine(Line rightLine) {
        this.rightLine = rightLine;
    }

    /**
     * Set new value to leftLine
     * @param leftLine
     */
    public void setLeftLine(Line leftLine) {
        this.leftLine = leftLine;
    }

    /**
     * Set new value to bottomLine
     * @param bottomLine
     */
    public void setBottomLine(Line bottomLine) {
        this.bottomLine = bottomLine;
    }

    /**
     * Set new value to topLine
     * @param topLine
     */
    public void setTopLine(Line topLine) {
        this.topLine = topLine;
    }

    /**
     * Get rightLine value
     * @return rightLine
     */
    public Line getRightLine() {
        return this.rightLine;
    }

    /**
     * Get leftLine value
     * @return leftLine
     */
    public Line getLeftLine() {
        return this.leftLine;
    }

    /**
     * Get bottomLine value
     * @return bottomLine
     */
    public Line getBottomLine() {
        return this.bottomLine;
    }

    /**
     * Get topLine value
     * @return topLine
     */
    public Line getTopLine() {
        return this.topLine;
    }

    /**
     * Move horizontal square value
     * @param num
     */
    public void moveHorizontal(int num) {
        this.rightLine.moveHorizontal(num);
        this.leftLine.moveHorizontal(num);
        this.bottomLine.moveHorizontal(num);
        this.topLine.moveHorizontal(num);
    }

    /**
     * Move vertically square value
     * @param num
     */
    public void moveVertically(int num) {
        this.rightLine.moveVertically(num);
        this.leftLine.moveVertically(num);
        this.bottomLine.moveVertically(num);
        this.topLine.moveVertically(num);
    }

    /**
     * Get square area
     * @return area
     */
    public double getArea() {
        return Math.abs( leftLine.getLenght() * bottomLine.getLenght() );
        // Check (?)
    }

    /**
     * Check if is a square
     * @return true if square
     */
    public boolean isSquare() {
        if (bottomLine.getPoint1().getX() != leftLine.getPoint2().getX() || bottomLine.getPoint1().getY() != leftLine.getPoint2().getY())
            return false;

        if (leftLine.getPoint1().getX() != topLine.getPoint2().getX()|| leftLine.getPoint1().getY() != topLine.getPoint2().getY())
            return false;

        if (topLine.getPoint1().getX() != rightLine.getPoint2().getX() || topLine.getPoint1().getY() != rightLine.getPoint2().getY())
            return false;

        if (rightLine.getPoint1().getX() != bottomLine.getPoint2().getX() || rightLine.getPoint1().getY() != bottomLine.getPoint2().getY())
            return false;

        return true;
    }

    /**
     * Check if other Square equals to this
     * @param square
     * @return true if equals
     */
    public boolean equals(Square square) {
        Line right  = square.getRightLine();
        Line left   = square.getLeftLine();
        Line bottom = square.getBottomLine();
        Line top    = square.getTopLine();

        if (rightLine.equals(right) && leftLine.equals(left) && bottomLine.equals(bottom) && topLine.equals(top))
            return true;

        return false;
    }

    /**
     * @return string outpot
     */
    public String toString() {
        return String.format("Side-1 : %s%nSide-2 : %s%nSide-3 : %s%nSide-4 : %s%nthe square area is : %s", this.rightLine.toString(), this.leftLine.toString(), this.bottomLine.toString(), this.topLine.toString(), this.getArea());
    }
}

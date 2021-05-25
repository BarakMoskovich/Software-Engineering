/**
 * Created by Barak Moskovich
 */
package build;

public class Triangle {
    private Line rightLine, leftLine, baseLine;

    /**
     * Constructor
     * @param rightLine
     * @param leftLine
     * @param baseLine
     */
    public Triangle(Line rightLine, Line leftLine, Line baseLine) {
        this.rightLine = rightLine;
        this.leftLine = leftLine;
        this.baseLine = baseLine;
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
     * Set new value to baseLine
     * @param baseLine
     */
    public void setBaseLine(Line baseLine) {
        this.baseLine = baseLine;
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
     * Get baseLine value
     * @return baseLine
     */
    public Line getBaseLine() {
        return this.baseLine;
    }

    /**
     * Move horizontal triangle value
     * @param num
     */
    public void moveHorizontal(int num) {
        rightLine.moveHorizontal(num);
        leftLine.moveHorizontal(num);
        baseLine.moveHorizontal(num);
    }

    /**
     * Move vertically triangle value
     * @param num
     */
    public void moveVertically(int num) {
        rightLine.moveVertically(num);
        leftLine.moveVertically(num);
        baseLine.moveVertically(num);
    }

    /**
     * Get triangle area
     * @return area
     */
    public double getArea() {
        int x1 = this.leftLine.getPoint2().getX();
        int y1 = this.leftLine.getPoint2().getY();
        int x2 = this.leftLine.getPoint1().getX();
        int y2 = this.leftLine.getPoint1().getY();
        int x3 = this.baseLine.getPoint2().getX();
        int y3 = this.baseLine.getPoint2().getY();

        double area = Math.abs(0.5 * (x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2)));
        return area;
    }

    /**
     * Check if is  a triangle
     * @return true if triangle
     */
    public boolean isTriangle() {
        if (this.rightLine.getPoint2().getX() != leftLine.getPoint1().getX() || this.rightLine.getPoint2().getY() != leftLine.getPoint1().getY())
            return false;
        if (this.leftLine.getPoint2().getX() != baseLine.getPoint1().getX() || this.leftLine.getPoint2().getY() != baseLine.getPoint1().getY())
            return false;
        if (this.baseLine.getPoint2().getX() != rightLine.getPoint1().getX() || this.baseLine.getPoint2().getY() != rightLine.getPoint1().getY())
            return false;
        return true;
    }

    /**
     * Check if other Triangle equals to this
     * @param triangle
     * @return
     */
    public boolean equals(Triangle triangle) {
        Line right = triangle.getRightLine();
        Line left  = triangle.getLeftLine();
        Line base  = triangle.getBaseLine();

        if (this.rightLine.equals(right) && this.leftLine.equals(left) && this.baseLine.equals(base))
            return true;

        return false;
    }

    /**
     * @return string output
     */
    public String toString() {
        return String.format("Side-1 : %s%nSide-2 : %s%nSide-3 : %s%nTriganle area %s%n", this.rightLine.toString(), this.leftLine.toString(), this.baseLine.toString(), this.getArea());
    }
}

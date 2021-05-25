/**
 * Created by Barak Moskovich
 */
package build;

import java.lang.Math;

public class Line {
    private Point point1, point2;
    private String color;

    /**
     * Constructor
     * @param point1
     * @param point2
     */
    public Line(Point point1, Point point2, String color) {
        this.point1 = point1;
        this.point2 = point2;
        this.color = color;
    }

    /**
     * Set new value to point1
     * @param point1
     */
    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    /**
     * Set new value to point2
     * @param point2
     */
    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    /**
     * Set new value to color
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get point1 value
     * @return point1
     */
    public Point getPoint1() {
        return this.point1;
    }

    /**
     * Get point2 value
     * @return point2
     */
    public Point getPoint2() {
        return this.point2;
    }

    /**
     * Get color value
     * @return color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Get lenght line
     * @return lenght line
     */
    public double getLenght() {
        double lenght = Math.sqrt( Math.pow( (this.getPoint2().getX() - this.getPoint1().getX()), 2) +
                                   Math.pow( (this.getPoint2().getY() - this.getPoint1().getY()), 2) );
        return lenght;
    }

    /**
     * Move horizontal line value
     * @param num
     */
    public void moveHorizontal(int num) {
        if (this.getPoint1().getX() + num > 0)
            this.getPoint1().setX( this.getPoint1().getX() + num );
        else
            this.getPoint1().setX(0);

        if (this.getPoint2().getX() + num > 0)
            this.getPoint2().setX( this.getPoint2().getX() + num );
        else
            this.getPoint2().setX(0);
    }

    /**
     * Move vertically line value
     * @param num
     */
    public void moveVertically(int num) {
        if (this.getPoint1().getY() + num > 0)
            this.getPoint1().setY( this.getPoint1().getY() + num );
        else
            this.getPoint1().setY(0);

        if (this.getPoint2().getY() + num > 0)
            this.getPoint2().setY( this.getPoint2().getY() + num );
        else
            this.getPoint2().setY(0);
    }

    /**
     * Check if other Line equals to this
     * @param line
     * @return true if equals
     */
    public boolean equals(Line line) {
        if (!line.getColor().equals(this.color))
            return false;

        if (line.getPoint1().getX() == this.getPoint1().getX() && line.getPoint2().getY() == this.getPoint2().getY())
            return true;

        if (line.getPoint1().getX() == this.getPoint2().getX() && line.getPoint1().getY() == this.getPoint2().getY())
            return true;

        return false;
    }

    /**
     * @return string outpot
     */
    public String toString() {
        return String.format("%s , %s , Color : %s, The line lenght is : %f", this.point1.toString(), this.point2.toString(), this.color, this.getLenght());
    }
}

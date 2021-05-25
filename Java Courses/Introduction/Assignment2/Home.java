/**
 * Created by Barak Moskovich
 */
package build;

public class Home {
    private Triangle roof;
    private Square base;

    /**
     * Constructor
     * @param roof
     * @param base
     */
    public Home(Triangle roof, Square base) {
        this.roof = roof;
        this.base = base;
    }

    /**
     * Set new roof value
     * @param roof
     */
    public void setRoof(Triangle roof) {
        this.roof = roof;
    }

    /**
     * Set new base value
     * @param base
     */
    public void setBase(Square base) {
        this.base = base;
    }

    /**
     * Get roof value
     * @return roof
     */
    public Triangle getRoof(){
        return this.roof;
    }

    /**
     * Get base value
     * @return base
     */
    public Square getBase() {
        return this.base;
    }

    /**
     * Move horizontal home value
     * @param num
     */
    public void moveHorizontal(int num) {
        roof.moveHorizontal(num);
        base.moveHorizontal(num);
    }

    /**
     * Move vertically home value
     * @param num
     */
    public void moveVertically(int num) {
        roof.moveVertically(num);
        base.moveVertically(num);
    }

    /**
     * Check if is home (roof + base connect)
     * @return true if home
     */
    public boolean isHome() {
        // equal line ?
        if (!base.getTopLine().getColor().equals(roof.getBaseLine().getColor()))
            return false;


        if (!roof.getLeftLine().getColor().equals("red") || !roof.getRightLine().getColor().equals("red"))
            return false;

        if (base.getTopLine().getPoint1().getX() == roof.getBaseLine().getPoint2().getX() && base.getTopLine().getPoint1().getY() == roof.getBaseLine().getPoint2().getY())
            if (base.getTopLine().getPoint2().getY() == roof.getBaseLine().getPoint1().getY() && base.getTopLine().getPoint2().getY() == roof.getBaseLine().getPoint1().getY())
                return true;

        if (base.getTopLine().getPoint1().getX() == roof.getBaseLine().getPoint1().getX() && base.getTopLine().getPoint1().getY() == roof.getBaseLine().getPoint1().getY())
            if (base.getTopLine().getPoint2().getX() == roof.getBaseLine().getPoint2().getX() && base.getTopLine().getPoint2().getY() == roof.getBaseLine().getPoint2().getY())
                return true;

        return false;
    }

    /**
     * Get home area
     * @return area
     */
    public double getArea() {
        return roof.getArea() + base.getArea();
    }

    /**
     * Check if other Home equals to this
     * @param home
     * @return true if equals
     */
    public boolean equals(Home home) {
        if (this.base.equals(home.getBase()) && this.roof.equals(home.getRoof()))
            return true;
        return false;
    }

    /**
     * @return string output
     */
    public String toString() {
        String toReturn = String.format("The home you build is : %nThe home base is :%n%s The home roof is :%n%sThe home area :%s%n%nGood construction!!! You have succeeded to build your home", this.base.toString(), this.roof.toString(), this.getArea());
        return toReturn;
    }
}

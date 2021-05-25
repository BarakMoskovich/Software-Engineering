/**
 * Created by Barak Moskovich
 */
package build;

import java.util.Scanner;

public class Program {
    /**
     * Check if entry coordinates > 0
     * @param x
     * @param y
     * @return if true return entry Point, if false Point(-1, -1)
     */
    public static Point checkVaildEntry(int x, int y) {
        if (x >= 0 && y >= 0)
            return new Point(x, y);
        return new Point(-1, -1);
    }

    /**
     * Read line from user
     * @param scanner
     * @return Line
     */
    public static Line readLine(Scanner scanner) {
        Point p1 = null, p2 = null;
        String color = null;
        do {
            System.out.print("enter (x1, y1), (x2, y2) and the color : ");
            p1 = checkVaildEntry(scanner.nextInt(), scanner.nextInt());
            p2 = checkVaildEntry(scanner.nextInt(), scanner.nextInt());
            color = scanner.next();
        } while (p1.getX() < 0 || p2.getX() < 0 || p1.getY() < 0 || p2.getY() < 0);

        return new Line(p1, p2, color);
    }

    /**
     * Build roof
     * @param scanner
     * @return Triangle
     */
    public static Triangle buildRoof(Scanner scanner) {
        Line line1, line2, line3;
        Triangle roof;
        do {
            System.out.printf("First provide me the roof detailes\nEnter roof base information\n");
            line1 = readLine(scanner);
            System.out.printf("\nSecondly, provide me the roof right side detailes\nEnter roof right side information\n");
            line2 = readLine(scanner);
            System.out.printf("\nThird provide me the roof left side detailes\nEnter roof left side information\n");
            line3 = readLine(scanner);
            System.out.println();

            roof = new Triangle(line2, line3, line1);
        } while (!roof.isTriangle());
        return roof;
    }

    /**
     * Build base
     * @param scanner
     * @return Square
     */
    public static Square buildBase(Scanner scanner) {
        Square base;
        Line line1, line2, line3, line4;
        do {
            System.out.printf("And now, lets build the home main part!\nFirst provide me the home base details\nEnter home base 1st side information\n");
            line1 = readLine(scanner);
            System.out.printf("\nEnter home base 2nd side information\n");
            line2 = readLine(scanner);
            System.out.printf("\nEnter home base 3rd side information\n");
            line3 = readLine(scanner);
            System.out.printf("\nEnter home base 4th side information\n");
            line4 = readLine(scanner);
            System.out.println();

            base = new Square(line2, line4, line1, line3);
        } while (!base.isSquare());

        return base;
    }

    /**
     * Build home
     * @param scanner
     * @return Home
     */
    public static Home buildHome(Scanner scanner) {
        Triangle roof = buildRoof(scanner);
        Square base = buildBase(scanner);
        Home home;
        do {
            home = new Home(roof, base);
        } while (!home.isHome());

        return home;
    }


    /**
     * Move home horizontal
     * @param home
     * @param scanner
     */
    public static void moveHorizontal(Home home, Scanner scanner) {
        int num;
        System.out.print("Please enter number for moving home vertically: ");
        num = scanner.nextInt();
        home.moveHorizontal(num);
        System.out.print("\nThe new home location and details are:\n" + home.toString() + "\n");
    }

    /**
     * Move home vertically
     * @param home
     * @param scanner
     */
    public static void moveVertically(Home home, Scanner scanner) {
        int num;
        System.out.print("Please enter number for moving home vertically: ");
        num = scanner.nextInt();
        home.moveVertically(num);
        System.out.print("\nThe new home location and details are:\n" + home.toString() + "\n");
    }

    /**
     * Check if two Home object equals
     * @param home1
     * @param home2
     * @return
     */
    public static boolean checkMatch(Home home1, Home home2) {
        return home1.equals(home2);
    }

    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Home Build Program");
        Home home1 = buildHome(scanner);
        System.out.println(home1.toString());
        moveHorizontal(home1, scanner);
        moveVertically(home1, scanner);
        Home home2 = buildHome(scanner);

        if (checkMatch(home1, home2))
            System.out.print("Both houses are the same");
        else
            System.out.print("The houses are not the same");

        scanner.close();

        /********************* Test *********************
         ********* Move X - 10 **** Move Y - 10 *********
         ****** First home ************ Second home *****
         * 10 20 30 20 - green **** 20 30 40 30 - green *
         * 30 20 20 30 - red   **** 40 30 30 40 - red   *
         * 20 30 10 20 - red   **** 30 40 20 30 - red   **
         * ------------------- **** ------------------- *
         * 10 0 30 0   - brown **** 20 10 40 10 - brown *
         * 30 0 30 20  - brown **** 40 10 40 30 - brown *
         * 30 20 10 20 - green **** 40 30 20 30 - green *
         * 10 20 10 0  - brown **** 20 30 20 10 - brown *
         ************************************************/
    }
}
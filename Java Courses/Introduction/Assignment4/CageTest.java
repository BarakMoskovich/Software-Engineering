/**
 * Created by Barak Moskovich
 */
package pensionNSudoku;

public class CageTest{
    public static void testCage() {
        Cage cage = new Cage();
        MyDate date=new MyDate(19, 6, 2015);


        Dog dog=new Dog(15, false, date, "koala", "wolf");
        if(!cage.addDogToCage(dog))
            System.out.println("Error adding dog " + dog.getName());

        Dog dog1=new Dog(50, true, date, "koala2", "wolf");
        if(!cage.addDogToCage(dog1))
            System.out.println("Error adding dog " + dog1.getName());

        if(!cage.addDogToCage(dog1))  //should not be added - already there KOALA2
            System.out.println("Error adding dog " + dog1.getName());

        Dog dog3=new Dog(40, false, date, "koala3", "labrador");
        if(!cage.addDogToCage(dog3)) //should not be added - weight to high KOALA3
            System.out.println("Error adding dog " + dog3.getName());

        Dog dog4=new Dog(15, true, date, "lala", "pincher");
        if(!cage.addDogToCage(dog4)) //should not be added - male already there LALA
            System.out.println("Error adding dog " + dog4.getName());

        Dog dog6=new Dog(15, false, date, "lala2", "pincher");
        if(!cage.addDogToCage(dog6))
            System.out.println("Error adding dog " + dog6.getName());

        Dog dog7=new Dog(15, false, date, "kinly", "pincher");
        if(!cage.addDogToCage(dog7))
            System.out.println("Error adding dog " + dog7.getName());

        System.out.printf(cage.toString());

        Dog dog8 =new Dog(15, false, date, "kinly2", "pincher");
        if(!cage.addDogToCage(dog8)) //should not be added - full
            System.out.println("Error adding dog " + dog8.getName());

        System.out.println("Test removing");
        if(!cage.removeDog(dog4)) // should get error LALA
            System.out.println("Error removing dog " + dog4.getName());

        if(!cage.removeDog(dog))
            System.out.println("Error removing dog " + dog.getName());
        
        System.out.println(cage.toString());
    }
}
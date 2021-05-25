package pensionNSudoku;

public class DogHouseTest {

    public static void testDogPension() {
        // DogHouse gets the name
        DogHouse house = new DogHouse("Green Planet");
        
        // MyDate gets day, month, year
        MyDate d1 = new MyDate(2, 3, 2019);
        MyDate d2 = new MyDate(3, 3, 2019);
        MyDate d3 = new MyDate(4, 3, 2019);
        MyDate d4 = new MyDate(5, 3, 2019);
        MyDate d5 = new MyDate(6, 3, 2019);

        Dog[] dogs = new Dog[10];
        // Dog gets weight, male?, date, name, type
        dogs[0] = new Dog(40, true, d1, "momo", "labrador");
        dogs[1] = new Dog(30, false, d1, "bobo", "pincher");
        dogs[2] = new Dog(35, false, d2, "koko", "golden");
        dogs[3] = new Dog(40, true, d2, "aoao", "puddle");
        dogs[4] = new Dog(17, true, d3, "lolo", "wolf");
        dogs[5] = new Dog(10, false, d3, "popo", "Bulldog");
        dogs[6] = new Dog(30, true, d4, "gogo", "labrador");
        dogs[7] = new Dog(40, false, d4, "soso", "puddle");
        dogs[8] = new Dog(10, false, d5, "toto", "wolf");
        dogs[9] = new Dog(15, true, d5, "coco", "Bulldog");

        // Add the dogs to the dog house
        for (int i = 0; i < dogs.length; i++)
            if (!house.addDog(dogs[i])) 
                System.out.println("Error adding dog " + dogs[i]);

        System.out.println("-------------------------------------------");
        System.out.println("There are " + house.getDogsCount() + " dogs in house");
        System.out.println("There are " + house.getCagesCount() + " cages in house");

        System.out.println("-------------------------------------------");
        System.out.println("House currect status:");
        System.out.println(house);

        // Getting gogo out of the house
        // change d1 make sure no change for momo and bobo
        d1 = new MyDate(10, 3, 2019); // 5 days
        int days = house.outDog(dogs[6].getId(), d1);
        house.makePriceStatment(dogs[6], days);
        
        // Getting momo out of the house
        d1 = new MyDate(12, 3, 2019); // 6 days
        days = house.outDog(dogs[0].getId(), d1);
        house.makePriceStatment(dogs[0], days);

        System.out.println("-------------------------------------------");
        System.out.println("House status after 2 removals");
        System.out.println(house);

        // Adding 2 dogs to the house
        Dog dogN = new Dog(40, true, d1, "fofo", "puddle");
        Dog dogN2 = new Dog(10, true, d1, "eoeo", "wolf");
        if (!house.addDog(dogN))
            System.out.println("Error adding dog " + dogN);
        if (!house.addDog(dogN2))
            System.out.println("Error adding dog " + dogN2);

        System.out.println("-------------------------------------------");
        System.out.println("House status after 2 adds");
        System.out.println(house);
    }
}
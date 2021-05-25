public class Q6B {
    public static int MAX_TOPPINGS = 3;

    abstract static class Component {

        protected String description;
        protected int calories;
        protected boolean hasDough, hasSauce, hasCheese;
        protected int toppingsCount;

        abstract void initialize();

        String getDescription() {
            return description;
        }

        int getCalories() {
            return calories;
        }

        void add(Component component) {
            getStatus(component);
            calories += component.calories;
            description = component.description + ", " + description;
        }

        void getStatus(Component c) {
            hasDough |= c.hasDough;
            hasSauce |= c.hasSauce;
            hasCheese |= c.hasCheese;
            toppingsCount += c.toppingsCount;
        }

        void checkValidity() {
            if (!hasDough) throw new IllegalStateException("Pizza is missing a dough!");
            if (!hasSauce) throw new IllegalStateException("Pizza is missing a sauce!");
        }

        @Override
        public String toString() {
            checkValidity();
            return description + " |\tCalories: " + calories;
        }
    }

    static class Pizza extends Component {
        Pizza() {
            initialize();
        }

        @Override
        void initialize() {
            description = "Pizza";
            calories = 100;
        }

    }

    abstract static class Dough extends Component {
        Dough(Component c) {
            if (c.hasDough)
                throw new IllegalArgumentException("Pizza has dough already!");
            initialize();
            add(c);
            hasDough = true;
        }
    }

    static class ThickDough extends Dough {
        ThickDough(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 350;
            description = "Thick Dough";
        }
    }

    static class ThinDough extends Dough {
        ThinDough(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 150;
            description = "Thin Dough";
        }
    }

    static class ThickGlutenFree extends Dough {
        ThickGlutenFree(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 250;
            description = "Gluten Free Thick Dough";
        }
    }

    static class ThinGlutenFree extends Dough {
        ThinGlutenFree(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 100;
            description = "Gluten Free Thin Dough";
        }
    }

    abstract static class Sauce extends Component {
        Sauce(Component c) {
            if (c.hasSauce)
                throw new IllegalArgumentException("Pizza has sauce already!");
            initialize();
            add(c);
            hasSauce = true;
        }
    }

    static class TomatoSauce extends Sauce {
        TomatoSauce(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 50;
            description = "Tomato Sauce";
        }
    }

    static class TomatoCreamSauce extends Sauce {
        TomatoCreamSauce(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 120;
            description = "Tomato And Cream Sauce";
        }
    }

    static class PestoSauce extends Sauce {

        PestoSauce(Component c) {
            super(c);
        }
        @Override
        void initialize() {
            calories = 110;
            description = "Pesto Sauce";
        }

    }

    static class SpinachCreamSauce extends Sauce {
        SpinachCreamSauce(Component c) {
            super(c);
        }

        @Override
        void initialize() {
            calories = 130;
            description = "Spinach And Cream Sauce";
        }
    }

    abstract static class Cheese extends Component {
        boolean isDoubled;

        Cheese(boolean isDoubled, Component c) {
            if (c.hasCheese)
                throw new IllegalArgumentException("Pizza has enough cheese already!");
            this.isDoubled = isDoubled;
            initialize();
            if (this.isDoubled)
                calories *= 1.99; // nobody is perfect
            add(c);
            hasCheese = true;
        }

        @Override
        void add(Component component) {
            getStatus(component);
            calories += component.calories;
            description = component.description + ", " + (isDoubled ? "Double " : "") + description;
        }
    }

    static class VeganSubstitute extends Cheese {
        VeganSubstitute(boolean isDoubled, Component c) {
            super(isDoubled, c);
        }

        @Override
        void initialize() {
            calories = 100;
            description = "Vegan Substitute Cheese";
        }
    }

    static class Parmesan extends Cheese {
        Parmesan(boolean isDoubled, Component c) {
            super(isDoubled, c);
        }

        @Override
        void initialize() {
            calories = 250;
            description = "Parmesan Cheese";
        }
    }

    abstract static class Topping extends Component {
        boolean isWhole;

        Topping(boolean isWhole, Component c) {
            if (c.toppingsCount >= MAX_TOPPINGS)
                throw new IllegalArgumentException("Pizza has enough extras already!");
            this.isWhole = isWhole;
            initialize();
            if (!this.isWhole)
                calories *= 0.49; // nobody is perfect
            add(c);
            toppingsCount++;
        }

        void add(Component component) {
            getStatus(component);
            calories += component.calories;
            description = component.description + ", " + description + (isWhole ? "" : " on half");
        }
    }

    static class Olives extends Topping {
        Olives(boolean isWhole, Component c) {
            super(isWhole, c);
        }

        @Override
        void initialize() {
            calories = 80;
            description = "Olives";
        }
    }

    static class Onions extends Topping {
        Onions(boolean isWhole, Component c) {
            super(isWhole, c);
        }

        @Override
        void initialize() {
            calories = 45;
            description = "Onions";
        }
    }

    static class Corn extends Topping {
        Corn(boolean isWhole, Component c) {
            super(isWhole, c);
        }

        @Override
        void initialize() {
            calories = 25;
            description = "Corn";
        }
    }


    static class Mozzarella extends Cheese {
        Mozzarella(boolean isDoubled, Component c) {
            super(isDoubled, c);
        }

        @Override
        void initialize() {
            calories = 150;
            description = "Mozzarella Cheese";
        }

    }

    static class Tomatoes extends Topping {
        Tomatoes(boolean isWhole, Component c) {
            super(isWhole, c);
        }

        @Override
        void initialize() {
            calories = 30;
            description = "Tomatoes";
        }
    }

    public static void test1() {
        try {
            System.out.println(
                    new Olives(true,
                            new Corn(false,
                                    new Mozzarella(true,
                                            new TomatoCreamSauce(
                                                    new ThickDough(
                                                            new Pizza())))))
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void test2() {
        try {
            System.out.println(new Pizza());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void test3() {
        try {
            System.out.println(
                    new Olives(true,
                            new Corn(false,
                                    new Tomatoes(false,
                                            new Onions(true,
                                                    new Parmesan(false,
                                                            new PestoSauce(
                                                                    new ThinDough(
                                                                            new Pizza()
                                                                    ))
                                                    )
                                            )
                                    )
                            )
                    )
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }
}
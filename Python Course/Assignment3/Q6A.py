class Component:
    description = ""
    calories = 0
    toppingsCount = 0
    hasDough = False
    hasSauce = False
    hasCheese = False

    def getDescription(self):
        self.checkValidity()
        return self.__class__.description

    def getCalories(self):
        self.checkValidity()
        return self.__class__.calories

    def checkValidity(self):
        if not self.hasDough:
            raise ValueError("Pizza is missing a dough!")
        if not self.hasSauce:
            raise ValueError("Pizza is missing a sauce!")


class Pizza(Component):
    def __init__(self):
        Component.description = "Pizza"
        Component.calories = 0
        Component.toppingsCount = 0
        Component.hasDough = False
        Component.hasSauce = False
        Component.hasCheese = False


# ######################## Dough ####################### #
class DoughDecorator(Component):
    def __init__(self, dough):
        if not Component.hasDough:
            self.component = dough
            Component.hasDough = True
        else:
            raise ValueError("Pizza has Dough already!")

    def getCalories(self):
        return self.component.getCalories() + \
               Component.getCalories(self)

    def getDescription(self):
        return self.component.getDescription() + \
               ', ' + Component.getDescription(self)


class ThickPizza(DoughDecorator):
    description = "Thick Dough"
    calories = 2000

    def __init__(self, dough):
        DoughDecorator.__init__(self, dough)


class ThinPizza(DoughDecorator):
    description = "Thin Dough"
    calories = 1500

    def __init__(self, dough):
        DoughDecorator.__init__(self, dough)


class ThickGlutenFree(DoughDecorator):
    description = "Thick Gluten Free"
    calories = 2500

    def __init__(self, dough):
        DoughDecorator.__init__(self, dough)


class ThinGlutenFree(DoughDecorator):
    description = "Thin Gluten Free"
    calories = 2000

    def __init__(self, dough):
        DoughDecorator.__init__(self, dough)


# ######################## Sauce ####################### #
class SauceDecorator(Component):
    def __init__(self, sauce):
        if not Component.hasSauce:
            self.component = sauce
            Component.hasSauce = True
        else:
            raise ValueError("Pizza has Sauce already!")

    def getCalories(self):
        return self.component.getCalories() + \
               Component.getCalories(self)

    def getDescription(self):
        return self.component.getDescription() + \
               ', ' + Component.getDescription(self)


class TomatoesSauce(SauceDecorator):
    description = "Tomatoes Sauce"
    calories = 250

    def __init__(self, sauce):
        SauceDecorator.__init__(self, sauce)


class TomatoesWithSauce(SauceDecorator):
    description = "Tomatoes With Cream Sauce"
    calories = 300

    def __init__(self, sauce):
        SauceDecorator.__init__(self, sauce)


class SpinachCreamSauce(SauceDecorator):
    description = "Spinach With Cream Sauce"
    calories = 350

    def __init__(self, sauce):
        SauceDecorator.__init__(self, sauce)


class PestoSauce(SauceDecorator):
    description = "Pesto Sauce"
    calories = 100

    def __init__(self, sauce):
        SauceDecorator.__init__(self, sauce)


# ####################### Topping ###################### #
class ToppingDecorator(Component):
    def __init__(self, _whole, topping):
        self.whole = _whole
        if Component.toppingsCount < 3:
            self.component = topping
            Component.toppingsCount += 1
        else:
            raise ValueError("Pizza has enough extras already!")

    def getCalories(self):
        if self.whole:
            return self.component.getCalories() + \
                   Component.getCalories(self)
        else:
            return self.component.getCalories() + \
                   int(Component.getCalories(self) / 2)

    def getDescription(self):
        if self.whole:
            return self.component.getDescription() + \
                   ', ' + Component.getDescription(self)
        else:
            return self.component.getDescription() + \
                   ', Half ' + Component.getDescription(self)


class Olives(ToppingDecorator):
    description = "Olives"
    calories = 35

    def __init__(self, _whole, topping):
        ToppingDecorator.__init__(self, _whole, topping)


class Corn(ToppingDecorator):
    description = "Corn"
    calories = 50

    def __init__(self, _whole, topping):
        ToppingDecorator.__init__(self, _whole, topping)


class Tomatoes(ToppingDecorator):
    description = "Tomatoes"
    calories = 40

    def __init__(self, _whole, topping):
        ToppingDecorator.__init__(self, _whole, topping)


class Onions(ToppingDecorator):
    description = "Onions"
    calories = 25

    def __init__(self, _whole, topping):
        ToppingDecorator.__init__(self, _whole, topping)


# ####################### Cheese ####################### #
class CheeseDecorator(Component):
    def __init__(self, _doubled, cheese):
        if not Component.hasCheese:
            self.doubled = _doubled
            self.component = cheese
            Component.hasCheese = True
        else:
            raise ValueError("Pizza has cheese already!")

    def getCalories(self):
        if self.doubled:
            return self.component.getCalories() + \
                   int(Component.getCalories(self) * 2)
        else:
            return self.component.getCalories() + \
                   Component.getCalories(self)

    def getDescription(self):
        if self.doubled:
            return self.component.getDescription() + \
                   ', Double ' + Component.getDescription(self)
        else:
            return self.component.getDescription() + \
                   ', ' + Component.getDescription(self)


class Mozzarella(CheeseDecorator):
    description = "Mozzarella"
    calories = 400

    def __init__(self, _doubled, cheese):
        CheeseDecorator.__init__(self, _doubled, cheese)


class Parmesan(CheeseDecorator):
    description = "Parmesan"
    calories = 300

    def __init__(self, _doubled, cheese):
        CheeseDecorator.__init__(self, _doubled, cheese)


class VeganSubstitute(CheeseDecorator):
    description = "Vegan Substitute"
    calories = 225

    def __init__(self, _doubled, cheese):
        CheeseDecorator.__init__(self, _doubled, cheese)


if __name__ == "__main__":
    a = Pizza()
    print(PestoSauce(ThickPizza(a)).getDescription())
    b = Pizza()
    print(Tomatoes(False,
                        Corn(True,
                             Parmesan(True,
                                      Olives(False,
                                             PestoSauce(
                                                 ThickPizza(
                                                     b)))))).getDescription())
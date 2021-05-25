class ArrayListIterator:
    def __init__(self, lst):
        self._index = -1
        self._lst = lst

    def __iter__(self):
        return self

    def __next__(self):
        if self._index >= len(self._lst):
            raise StopIteration
        self._index += 1
        return self._lst[self._index]


class ArrayList:
    def __init__(self):
        self.lst = []

    def add(self, item):
        self.lst.append(item)

    def iterator(self):
        return ArrayListIterator(self.lst)


def runA():  # Option A
    cars = ArrayList()
    cars.add("Volvo")
    cars.add("BMW")
    cars.add("Ford")
    cars.add("Mazda")

    it = cars.iterator()

    print(next(it))


def runB():  # Option B
    lst = list()
    lst.append("Volvo")
    lst.append("BMW")
    lst.append("Ford")
    lst.append("Mazda")

    it = iter(lst)

    print(next(it))


if __name__ == "__main__":
    runA()
    # runB()
    pass

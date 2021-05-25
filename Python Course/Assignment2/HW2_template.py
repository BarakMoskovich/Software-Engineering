# Fill in the functions content & think about the return values. (Don't forget to remove the 'pass' statement)
# In questions with classes you can create the class & write the logic inside the function.

# Class
def q1a(input_num):
    class PrimeSmallerThan:
        def __init__(self, x=2):  # add parameters
            self._num = x

        def __iter__(self):
            self._x = 1
            return self

        def __next__(self):
            self._x += 1
            if self._x >= self._num:
                raise StopIteration
            for i in range(self._x, self._num + 1):
                is_prime = True
                for j in range(2, i):
                    if i % j == 0:
                        is_prime = False
                        break
                if is_prime:
                    self._x = i
                    return i
                if i >= self._num:
                    raise StopIteration

    instance = PrimeSmallerThan(input_num)  # add parameters
    return instance


# Generator
def q1b(input_num):
    primes = []
    for i in range(2, input_num + 1):
        is_prime = True
        for j in primes:
            if i % j == 0:
                is_prime = False
                break
        if is_prime:
            primes.append(i)
            yield i


# List comprehension
def q2(n):
    return [i for i in range(2, n) if n % i == 0]


# One line
# m - min, n - max
def q3(m, n):
    return list(
        map(lambda item: "prime" if all(item % num != 0 for num in range(2, item)) else "not prime", range(m, n + 1)))


# Generator
# m - min, n - max
def q4(m, n):
    for x in range(m, n + 1):
        if x < 10:
            pass
        else:
            s = str(x)
            tens = int(s[-2])
            unity = int(s[-1])
            if unity != 0 and tens != 0 and unity % 2 == 0 and (unity % tens) == 0 and len(s) > 1:
                yield x


# One line
def q5(str):
    return {i: str.count(i) for i in [ch for ch in str if ch != ' ']}


# Generator
def q6a(str1, str2):
    for i in range(min(len(str1), len(str2))):
        if str1[i] == str2[i]:
            yield str1[i]


# Class
def q6b(str1, str2):
    class StringsCompare:  # add class functions
        def __init__(self, inp_str1, inp_str2):
            self._str1 = inp_str1
            self._str2 = inp_str2

        def __iter__(self):
            self._small = min(len(self._str1), len(self._str2))
            self._index = -1
            return self

        def __next__(self):
            self._index += 1
            if self._index >= self._small:
                raise StopIteration
            for i in range(self._index, self._small):
                if self._str1[i] == self._str2[i]:
                    self._index = i
                    return self._str1[i]
                if self._index >= self._small:
                    raise StopIteration

    instance = StringsCompare(str1, str2)  # add parameters
    return instance


# One line
def q7(char_list, index_list):
    return ''.join([y for (x, y) in sorted(list(zip(index_list, char_list)))])


# Generator
def q8a(a, b):
    k = 1
    if a > b:
        k = -1
    for x in range(a, b + 1, k):
        yield x

    if k == 1:
        yield from q8a(b - 1, a - 1)
    else:
        yield from q8a(b + 1, a + 1)


# q8b with user choice
def q8b(a, b):
    lower, upper = min(a, b), max(a, b)
    curr_num = lower
    prev = mode = 1

    while True:
        user_choice = yield curr_num

        if user_choice is not None:
            mode = 1 if user_choice >= 0 and mode != 1 else -1 if user_choice < 0 and mode == 1 else mode
            curr_num += 0 if prev != mode else -1 if mode == 1 else 1

        if prev != mode:
            prev = mode
        else:
            prev = mode = 1 if curr_num <= lower else -1 if curr_num >= upper else mode
            curr_num += mode


# If we did not understand the question correctly,
# We have attached the solution to the replacement question
# q8b - Option 2 #
def processor():
    while True:
        value = yield
        print(f'Processing {value}')


# Remove the '#' to run the corresponding test
# Don't forget to fill in the parameters.
if __name__ == "__main__":
    # q1a #
    input_num = 100
    for num in q1a(input_num):
        print(num)

    # q1b #
    # for num in q1b(100):
    #     print(num)

    # q2 #
    # print(q2(24))

    # q3 #
    # print(q3(2, 14))

    # q4 #
    # for c in q4(12, 102):
    #     print(c)

    # q5 #
    # print(q5("this is a simple string"))

    # q6a #
    # for c in q6a("like", "love"):
    #     print(c)

    # q6b #
    # for c in q6b("like", "love"):
    #     print(c)

    # q7 #
    # print(q7(['a', 'h', 'f', 'e', 'y', 'u'], [1, 5, 3, 6, 2, 4]))

    # q8a #
    # for c in q8a(3, 11):
    #     print(c)

    # q8b #
    # generator = q8b(3, 11)
    # counter = 0

    # for i in generator:
    #     if counter == 1:  # Stay - Up
    #         generator.send(32)
    #     if counter == 3:  # Change direction - Down
    #         generator.send(-3)
    #     if counter == 6:  # Change direction - Up
    #         generator.send(23)
    #     if counter == 8:  # Stay - Up
    #         generator.send(3)
    #     if counter == 10:  # Change direction - Down
    #         generator.send(-13)
    #     # if counter == 11:  # Stay - Down
    #     #     generator.send(-13)
    #     if counter == 25:
    #         break
    #     print(i, end=', ')
    #     counter += 1

    # q8b - Option 2 #
    # data_processor = processor()
    #
    # print(type(data_processor))
    #
    # next(data_processor)  # Here
    # for x in range(1, 5):
    #     data_processor.send(x)  # Here

    pass

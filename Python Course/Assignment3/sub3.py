import functools


def Q1(arr: list):
    return len(list(filter(lambda x: len(set(map(type, x))) >= 2, arr)))


def Q2():
    vlst = ['x' + str(num) for num in range(10)]
    lamdic = {}
    for i, l in enumerate(vlst):
        lamdic[l] = lambda x, i=i + 1: x * i
    for v in vlst:
        for i in range(1, len(vlst) + 1):
            print(lamdic[v](i), end=' ')
        print()


############### Q3 ###############
class a():
    pass

    def __init__(self, y):
        self.y = y

    def __call__(self, z):
        if z > self.y:
            return z - self.y
        else:
            return self.y - z


class b(a):
    def __call__(self, z=4):
        if z > self.y:
            return z - self.y
        else:
            return self.y - z
############### Q3 ###############


def swap(i, j):
    return j, i


def Q4(arr):
    for i in range(len(arr) - 1):
        for j in range(0, len(arr) - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = swap(arr[j], arr[j + 1])
    return arr


class Q5A(object):
    instances = {}
    Q5A_counter = 0
    number_of_parameters_passed = 0

    def __init__(self, func):
        Q5A.Q5A_counter += 1
        self.func = func
        self.func_count_calls = 0
        self.instances[func] = self

    def __call__(self, *args, **kwargs):
        print(f'Number of decorator calls: ', self.Q5A_counter)
        self.func_count_calls += 1
        self.number_of_parameters_passed = self.number_of_parameters_passed + len(args) + len(kwargs)
        print(f'\tTotal number of func calls: {self.func_count_calls}\n'
              f'\tTotal parameters passed: {self.number_of_parameters_passed}')

        return self.func(*args, **kwargs)

    # return the number of times this function was called
    # for testing
    def count(self):
        return Q5A.instances[self.func].func_count_calls

    # for testing
    @staticmethod
    def counts():
        return dict([(func.__name__, Q5A.instances[func].func_count_calls) for func in Q5A.instances])


class Q5B(object):
    type_instances = {}
    Q5B_counter = 0
    number_of_parameters_passed = 0

    def __init__(self, func):
        Q5B.Q5B_counter += 1
        self.func = func
        self.func_count_calls = 0

    def __call__(self, *args, **kwargs):
        print(f'Number of decorator calls: ', Q5B.Q5B_counter)
        result = self.func(*args, **kwargs)
        t = type(result).__name__
        self.type_instances.setdefault(t, 0)
        self.type_instances[t] += 1

        self.func_count_calls += 1
        self.number_of_parameters_passed = self.number_of_parameters_passed + len(args) + len(kwargs)
        print(f'\tTotal number of func calls: {self.func_count_calls}\n'
              f'\tTotal parameters passed: {self.number_of_parameters_passed}')
        for k, v in self.type_instances.items():
            print(f'\t{k} : {v}')

        return result


def Q6():
    pass


@Q5A
def foo(abc=list, dbe=5, arr="hello"):
    return "spam"


@Q5A
def d(b="string"):
    return ["d"]


if __name__ == '__main__':
    arr = [[1, 5, 3], ['a', 'v', 3], ["sss", 'b'], [], [[3, 4, 5], ['a']], [(4, 5, 6), [4, 5, 6]]]
    arr_number = [5, 6, 3, 2, 7, 9, 2]
    # print(Q1(arr))
    # Q2()
    # print(Q4(arr_number))
    for _ in range(10):
        print(foo(["abc", "fds"]))

    #
    print(d())
    # print(foo.count())
    # print(Q5A.counts())

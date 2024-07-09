# create python class for customers and employees
class Person:   
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def display(self):
        print(f"Name: {self.name}, Age: {self.age}")

# create a customer class that inherits from the Person class
class Customer(Person):
    def __init__(self, person, balance):
        super().__init__(person.name, person.age)
        self.balance = balance

    def display(self):
        super().display()
        print(f"Balance: {self.balance}")

# create an employee class that inherits from the Person class
class Employee(Person):
    def __init__(self, person, salary):
        super().__init__(person.name, person.age)
        self.salary = salary

    def display(self):
        super().display()
        print(f"Salary: {self.salary}")

if __name__ == '__main__':
    # create a list of customers and employees
    john = Person("John", 25)
    jane = Person("Jane", 30)
    mike = Person("Mike", 35)
    kate = Person("Kate", 40)
    people = [
        Customer(john, 1000),
        Employee(jane, 50000),
        Customer(mike, 2000),
        Employee(kate, 60000)
    ]

    # display information about each person
    for person in people:
        person.display()
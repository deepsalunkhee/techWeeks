
My Understanding of LLD so far .

Pre- requisites (these ae suppose be the basis of all the design but can be changed on the basis of the business needs)

SOLID 

Single Responsibility principle: Every Entity must have a single Responsivity
open close principle: we should be able to extend and entity without having the need to change any existing logic
liskov substitution principle: A child object should be able to replace a parent object without break the code
interface segregation principle: do not force a class to be dependent on the method that it will never use
dependency inversion : a Higher level class should not depend on the lower level class they both be dependent on some abstraction, and abstraction must be independent of details


Abbreviation Ill be using for 3 main type of Design pattern 

Creational Pattern : CP : Defining mechanism to create objects
Structural Pattern : SP: Combining small object to create larger object 
Behavioral Pattern: BP: Defining mechanism through which objects can interact


CP : Singleton

Use when you need to create a single instance of some class as a global access point
ex: crate a db connection

CP : Factory

Use when you have multiple implementation of an interface , rather that creating the object of that implementation create a factory that takes some input parameter , and on basis of that provided you with required object


CP: Builder

Use when you need multiple options of creating object, rather creating tons of constructor use this which allows you to set diff class variable  and then build it when you are done.

CP: Abstract factory

Here we define a interface of factory pattern, which and use it to implement diff factory which will provide group of dependent object ore object that are required in group

CP: Prototype

Used when you need to create a same object multiple , rather performing new operation every time , you can use this pattern to create a clone (deep / shallow  depending on need) .

SP: Adapter

This is kind of bridge between two incompatible interfaces , this is used when when you system interact with multiple systems doing something same in diff ways , so you would want be able to use any system and do a same operation and get same result , but those diff system may diff structure, so you write bridge between your interface and their interrace .

SP: Decorate

When you what to be able to add some properties to some object on runtime, means when the object is created you don't know , what additional properties it will even know , in such cases rather than keeping all the possible properties in single class we have some concrete implementation and some decorate implementation and this decorate implementing takes concrete object or decorate object in constructors to build on top of them

SP: Facade

Provide a single point of access to multiple system, so that the user system do not need to create of object of all the system it needs , just facade of those system and access them as if it was one

SP: Composite

Here you define a categories and try to put all the possible objects you would need in some category , a category can be child of other category , each category is a interface , now you get this tree like structure , and use it to ,personally iI think its pretty neat , so neat that it will get too complex , when you will need like thousands of class.


SP: Proxy

when you have class and some reason you don't want to use it directly every time you need it may be because it it resource heavy, you need to do auth .., then then you implement a class which actually uses this original class and do auth or maintain cache , its kind of a middle man


SP: Bridge

its a fancy name for "we need more abstraction", some time your abstractions implementation may be dependent on someVariable , some total implementation = no of implementation that you might think you need X distinct count(some variable) , solution :create abstraction for that variable and use in the original abstraction .

SP: fly weight

sometime you need to use a object , who has x variable out of which x - 2 are always same only 2 changes , in such scenario , it is better to create class which has 2 variable and a object with those x-2 variable as third variable so you don't create copy of something that you already have

BP: iterator

Used when you have created some collection based entity and you want to user system be able to iterate through it without know what its internal implementation it may be a list , map, set , or something which consider current time to decide what to return next

BP: Observer

when some object has multiple object dependent on it , it maintains a list of observers and notifies them when an update happens , rather than observer asking it is something updated , very inefficient if the number of observers is too huge .

BP : Strategy

Again a fancy name given to a neat way of deciding to do task which have multiple ways of doing  it (ie. diff strategies) using interface to define base structure of strategies and then defining them as separate implementation , defining a proxy which is used to create, set and use strategies 

BP: Command
Used when you want to convert some action into an object, so that you can store that action, pass it around, execute it later, undo it, redo it… basically treat an operation like data.


BP:Templet Method
Used when you have some algorithm whose steps are mostly fixed but a few steps may differ depending on the implementation.  
So you write a base abstract class which has a final method defining the flow (the "template") and inside it calls diff smaller methods.  
The child classes override only those specific steps.

BP:State Pattern

Used when the behavior of an object should change based on its internal state.  
Instead of writing if state == X everywhere, you create separate classes for each state implementing a common interface and let the main object hold a reference to a state object.  
Whenever state changes, you just replace the state object, and behavior changes automatically, making code cleaner and easier to maintain.

BP:Chain of Resposibiltuy
Used when you have multiple handlers who can process a request, but you don’t want to hardcode which handler will process it.  
Each handler knows only the next handler, and when a request comes it either processes it or passes it to the next one.

BP: Visitor 
Used when you have a structure of diff object types (like AST nodes, diff UI components, etc.) and you want to add new operations on them without modifying their classes.  
You create a visitor interface with diff visit methods for each type, and each class in your structure accepts a visitor.


BP:Mediator
Used when multiple objects interact with each other in a very complex way, and you want to avoid all-to-all connections.  
Instead of objects calling each other directly, you introduce a mediator class that handles all communication

BP: Memento
Used when you want to store snapshots of an object's state so you can restore it later (undo/redo kind of functionality).  
The main class creates a memento object which stores its internal state, and some caretaker class stores these mementos without looking inside them.

======================================================
# My Understanding of LLD - Twitter Thread

## Thread 1: Introduction & SOLID Principles

**Tweet 1:** My Understanding of LLD so far 🧵

Pre-requisites (these are supposed to be the basis of all the design but can be changed on the basis of the business needs)

Let's start with SOLID principles:

**Tweet 2:** Single Responsibility Principle: Every Entity must have a single Responsibility

Open Close Principle: we should be able to extend an entity without having the need to change any existing logic

**Tweet 3:** Liskov Substitution Principle: A child object should be able to replace a parent object without breaking the code

Interface Segregation Principle: do not force a class to be dependent on the method that it will never use

**Tweet 4:** Dependency Inversion: a Higher level class should not depend on the lower level class, they both should be dependent on some abstraction, and abstraction must be independent of details

**Tweet 5:** Abbreviations I'll be using for 3 main types of Design patterns:

CP: Creational Pattern - Defining mechanism to create objects SP: Structural Pattern - Combining small objects to create larger objects BP: Behavioral Pattern - Defining mechanism through which objects can interact

---

## Thread 2: Creational Patterns

**Tweet 1:** Creational Patterns (CP) 🧵

Let's dive into patterns that help us create objects efficiently:

**Tweet 2:** CP: Singleton

Use when you need to create a single instance of some class as a global access point

ex: create a db connection

**Tweet 3:** CP: Factory

Use when you have multiple implementations of an interface, rather than creating the object of that implementation, create a factory that takes some input parameter, and on basis of that provides you with required object

**Tweet 4:** CP: Builder

Use when you need multiple options of creating object, rather creating tons of constructors use this which allows you to set diff class variables and then build it when you are done.

**Tweet 5:** CP: Abstract Factory

Here we define an interface of factory pattern, which we use to implement diff factories which will provide group of dependent objects or objects that are required in group

**Tweet 6:** CP: Prototype

Used when you need to create a same object multiple times, rather than performing new operation every time, you can use this pattern to create a clone (deep / shallow depending on need).

---

## Thread 3: Structural Patterns (Part 1)

**Tweet 1:** Structural Patterns (SP) - Part 1 🧵

Patterns that help us combine objects to create larger structures:

**Tweet 2:** SP: Adapter

This is kind of bridge between two incompatible interfaces, this is used when your system interacts with multiple systems doing something same in diff ways, so you would want to be able to use any system and do a same operation

**Tweet 3:** (Adapter continued) and get same result, but those diff systems may have diff structures, so you write bridge between your interface and their interface.

**Tweet 4:** SP: Decorator

When you want to be able to add some properties to some object on runtime, means when the object is created you don't know what additional properties it will even have, in such cases rather than keeping all the possible properties in single class

**Tweet 5:** (Decorator continued) we have some concrete implementation and some decorator implementations and this decorator implementation takes concrete object or decorator object in constructors to build on top of them

**Tweet 6:** SP: Facade

Provide a single point of access to multiple systems, so that the user system does not need to create object of all the systems it needs, just facade of those systems and access them as if it was one

---

## Thread 4: Structural Patterns (Part 2)

**Tweet 1:** Structural Patterns (SP) - Part 2 🧵

More patterns for organizing objects:

**Tweet 2:** SP: Composite

Here you define categories and try to put all the possible objects you would need in some category, a category can be child of other category, each category is an interface, now you get this tree like structure, and use it to,

**Tweet 3:** (Composite continued) personally I think it's pretty neat, so neat that it will get too complex when you will need like thousands of classes.

**Tweet 4:** SP: Proxy

When you have a class and for some reason you don't want to use it directly every time you need it, maybe because it is resource heavy, you need to do auth.., then you implement a class which actually uses this original class

**Tweet 5:** (Proxy continued) and does auth or maintains cache, it's kind of a middle man

**Tweet 6:** SP: Bridge

It's a fancy name for "we need more abstraction", sometimes your abstraction's implementation may be dependent on someVariable, some total implementation = no of implementations that you might think you need X distinct count(some variable),

**Tweet 7:** (Bridge continued) solution: create abstraction for that variable and use in the original abstraction.

**Tweet 8:** SP: Flyweight

Sometimes you need to use an object, which has x variables out of which x - 2 are always same, only 2 change, in such scenario, it is better to create class which has 2 variables and an object with those x-2 variables as third variable

**Tweet 9:** (Flyweight continued) so you don't create copy of something that you already have

---

## Thread 5: Behavioral Patterns (Part 1)

**Tweet 1:** Behavioral Patterns (BP) - Part 1 🧵

Patterns that define how objects interact:

**Tweet 2:** BP: Iterator

Used when you have created some collection based entity and you want the user system to be able to iterate through it without knowing what its internal implementation is, it may be a list, map, set, or something which considers current time

**Tweet 3:** (Iterator continued) to decide what to return next

**Tweet 4:** BP: Observer

When some object has multiple objects dependent on it, it maintains a list of observers and notifies them when an update happens, rather than observers asking if something is updated, very inefficient if the number of observers is too huge.

**Tweet 5:** BP: Strategy

Again a fancy name given to a neat way of deciding to do task which have multiple ways of doing it (i.e. diff strategies) using interface to define base structure of strategies and then defining them as separate implementations,

**Tweet 6:** (Strategy continued) defining a proxy which is used to create, set and use strategies

**Tweet 7:** BP: Command

Used when you want to convert some action into an object, so that you can store that action, pass it around, execute it later, undo it, redo it… basically treat an operation like data.

---

## Thread 6: Behavioral Patterns (Part 2)

**Tweet 1:** Behavioral Patterns (BP) - Part 2 🧵

More interaction patterns:

**Tweet 2:** BP: Template Method

Used when you have some algorithm whose steps are mostly fixed but a few steps may differ depending on the implementation.

**Tweet 3:** (Template Method continued) So you write a base abstract class which has a final method defining the flow (the "template") and inside it calls diff smaller methods. The child classes override only those specific steps.

**Tweet 4:** BP: State Pattern

Used when the behavior of an object should change based on its internal state. Instead of writing if state == X everywhere, you create separate classes for each state implementing a common interface

**Tweet 5:** (State Pattern continued) and let the main object hold a reference to a state object. Whenever state changes, you just replace the state object, and behavior changes automatically, making code cleaner and easier to maintain.

**Tweet 6:** BP: Chain of Responsibility

Used when you have multiple handlers who can process a request, but you don't want to hardcode which handler will process it. Each handler knows only the next handler, and when a request comes it either processes it or passes it to the next one.

---

## Thread 7: Behavioral Patterns (Part 3)

**Tweet 1:** Behavioral Patterns (BP) - Part 3 🧵

Final set of behavioral patterns:

**Tweet 2:** BP: Visitor

Used when you have a structure of diff object types (like AST nodes, diff UI components, etc.) and you want to add new operations on them without modifying their classes.

**Tweet 3:** (Visitor continued) You create a visitor interface with diff visit methods for each type, and each class in your structure accepts a visitor.

**Tweet 4:** BP: Mediator

Used when multiple objects interact with each other in a very complex way, and you want to avoid all-to-all connections. Instead of objects calling each other directly, you introduce a mediator class that handles all communication

**Tweet 5:** BP: Memento

Used when you want to store snapshots of an object's state so you can restore it later (undo/redo kind of functionality). The main class creates a memento object which stores its internal state, and some caretaker class stores these mementos

**Tweet 6:** (Memento continued) without looking inside them.

---

**End of Thread 🎉**

That's my understanding of LLD so far! Hope this helps someone starting their design pattern journey.
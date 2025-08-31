
### BASICS :

##### Software Design Principles

- DRY => Do not Repeat yourself
- KISS => Keep it Simple Stupid
- YAGINI => You aren't gonna need this (Implement when needed)

##### SOLID Principles

- **S**ingle Responsibility principle :

```
A class should have only one reason to change. In other words, a class should only have one job, one responsibility, and one purpose.  
  
If a class takes more than one responsibility, it becomes coupled. This means that if one responsibility changes, the other responsibilities may also be affected, leading to a ripple effect of changes throughout the codebase
```

- **O**pen Closed Principle

```
As per OCP, Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification.  
  
This means that the behavior of a module can be extended without modifying its source code. The goal is to reduce the risk of breaking existing functionality when requirements change.
```

- **L**iskov Substitution Principle

```
If S is a subtype of T, then objects of type T may be replaced with objects of type S **without altering** the correctness of the program.  
This means that any subclass should be substitutable for its parent class without breaking the functionality.  
  
Think of it like this:

- If you write code using a parent class (say `Shape`), and later swap in a child class (using the child class object in place of the parent class object) (like `Circle`), the code should still work without errors or unexpected behavior.
- If the subclass changes behavior in a way that breaks expectations, it violates LSP
```

- **I**nterface segregation principle

```
Don't force a class to depend on methods it does not use.
```

- **D**ependency Inversion Principle

```
**"High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions."**  
  
In simpler words, Rather than high-level classes controlling and depending on the details of lower-level ones, both should rely on **interfaces** or **abstract** classes. This makes your code **flexible**, **testable**, and **easier to maintain**.
```

##### UML Diagrams

```
**UML (Unified Modeling Language)** is a standardized modeling language used to **visualize**, **specify**, **construct**, and **document** the structure and behavior of software systems.  
  
It provides a set of graphic notation techniques to create abstract models of systems, covering both **static** and **dynamic aspects**.
```

###### Types of UML Diagram

###### 1. Structural Diagrams

These describe the static structure of a system — what it contains, how different parts relate to each other, and how data is organized.

They are like the architectural blueprints of the system, showing the foundation, components, and their connections. Structural diagrams focus on the elements that exist in the system (like classes, objects, and hardware), rather than what happens during execution.

###### 2. Behavioral Diagrams

These describe the dynamic behavior of a system — how it behaves over time, how users interact with it, and how parts communicate during execution.

They are like the scripts and animations in a movie — showing what happens, when it happens, and who is involved. Behavioral diagrams focus on actions, interactions, processes, and state changes.

###### Different Structural Diagrams

There are **seven** main types of structural diagrams in UML, each serving a specific purpose in modeling the static aspects of a system:

- **Class Diagram:** Shows classes, their properties, methods, and relationships — a map of the code structure.
- **Object Diagram:** Shows a snapshot of instances of classes and their relationships at a specific point in time.
- **Component Diagram:** Depicts how software components (modules) are organized and connected.
- **Composite Structure Diagram:** Shows the internal parts of a class and how they interact to carry out behavior.
- **Deployment Diagram:** Illustrates how software is physically deployed onto hardware devices or servers.
- **Package Diagram:** Groups related elements (like classes) into packages for better organization.
- **Profile Diagram:** Used to customize UML for specific platforms or domains by extending its elements.

###### Different Behavioral Diagrams

There are **seven** main types of behavioral diagrams in UML, each serving a specific purpose in modeling the dynamic aspects of a system:

- **Use Case Diagram:** Captures what users (actors) can do with the system — its high-level functionalities.
- **Activity Diagram:** Models workflows and business processes — similar to flowcharts.
- **Sequence Diagram:** Shows the order of messages exchanged between objects over time.
- **Communication Diagram:** Emphasizes interactions between objects and how they're connected.
- **State Machine Diagram:** Depicts how an object transitions between states based on events.
- **Interaction Overview Diagram:** Combines features of sequence and activity diagrams to model interaction flow.
- **Timing Diagram:** Focuses on object behavior with respect to time, particularly useful for real-time systems.


### Intro to LLD

```
Design patterns are **standard**, **time-tested solutions** to common software design problems. They are not code templates but **abstract descriptions** or **blueprints** that help developers solve issues in code architecture and system design.  
  
To put it simply: **Design patterns help you avoid reinventing the wheel** when facing recurring design challenges.
```

### Three Categories of Design Pattern

 ##### **1. Creational Patterns**

These focus on object creation mechanisms, trying to create objects in a manner suitable to the situation. They abstract the instantiation process, making the system independent of how its objects are created.  
  
This is similar to the **Factory Pattern**, where the creation logic is hidden from the user and abstracted for flexibility.  
  
Examples include:

- Singleton
- Factory Method
- Abstract Factory
- Builder
- Prototype

##### 2. Structural Patterns

These deal with **object composition** — how classes and objects can be combined to form larger structures while keeping the system flexible and efficient. It helps systems to work together that otherwise could not because of incompatible interfaces.  
  
**Real-World Analogy**  
Suppose you have a modern smartphone (your system) that uses a USB-C charger, but your old power adapter only supports micro-USB. Instead of replacing either device, you use an adapter that connects the two.  
  
That adapter is like a **structural pattern** (specifically, the **Adapter Pattern**) — it allows incompatible components to work together seamlessly without changing their internals.  
  
Examples include:

- Adapter
- Bridge
- Composite
- Decorator
- Facade
- Flyweight
- Proxy

##### 3. Behavioral Patterns

These are concerned with **object interaction and responsibility** — how they communicate and assign responsibilities while ensuring loose coupling.  
  
**Real-World Analogy**  
Think of a restaurant. The waiter takes your order and passes it to the kitchen. You don't talk directly to the chef — the waiter acts as a **mediator** between you and the kitchen.  
  
This reflects the **Mediator Pattern**, which defines an object that controls communication between other objects, preventing tight interdependencies.  
  
Examples include:

- Observer
- Strategy
- Command
- Chain of Responsibility
- Mediator
- State
- Template Method
- Visitor
- Iterator
- Memento
- Interpreter

---

# ==Creational Patterns==

### Singleton Pattern


```
The Singleton Pattern ensures that a class has **only one instance** and provides a **global point of access** to that instance.
```

The Problem It Solves:

In a typical application, creating multiple objects of a class might not be problematic. However, in certain scenarios — like logging, configuration handling, or managing a database connection — you want just one instance to avoid redundancy, excessive memory use, or inconsistent behavior.

For Detailed Example Go to TUF+.


### Factory Pattern

```
The Factory Pattern is a creational design pattern that provides an interface for creating objects but allows subclasses to alter the type of objects that will be created.
```

In simpler terms:

Rather than calling a constructor directly to create an object, we use a factory method to create that object based on some input or condition.

When Should You Use It?

- We can use the Factory Pattern when:
- The client code needs to work with multiple types of objects.
- The decision of which class to instantiate must be made at runtime.
- The instantiation process is complex or needs to be controlled.

### Builder Pattern

```
The **Builder Pattern** is a creational design pattern that separates the construction of a complex object from its representation. This allows you to create different types and representations of an object using the same construction process.  
```
  
**Formal Definition**:  

"Builder pattern builds a complex object step by step. It separates the construction of a complex object from its representation, so that the same construction process can create different representations."  
  
**In simpler terms**:  

Imagine you're ordering a custom burger. You choose the bun, patty, toppings, sauces, and whether you want it grilled or toasted. The chef follows your instructions step by step to build your custom burger. This is what the Builder Pattern does — it lets you construct complex objects by specifying their parts one at a time, giving you flexibility and control over the object creation process.

### Abstract Factory Pattern

```
The Abstract Factory Pattern is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes.
```

In simpler terms:

You use it when you have multiple factories, each responsible for producing objects that are meant to work together.

When Should You Use It?

- Use of the Abstract Factory Pattern is appropriate in the following scenarios:
- When multiple related objects must be created as part of a cohesive set (e.g., a payment gateway and its corresponding invoice generator).
- When the type of objects to be instantiated depends on a specific context, such as country, theme, or platform.
- When client code should remain independent of concrete product classes.
- When consistency across a family of related products must be maintained (e.g., a US payment gateway paired with a US-style invoice).

### Prototype Pattern

```
The Prototype Pattern is a creational design pattern used to clone existing objects instead of constructing them from scratch. It enables efficient object creation, especially when the initialization process is complex or costly.

Formal Definition:
"Prototype pattern creates duplicate objects while keeping performance in mind. It provides a mechanism to copy the original object to a new one without making the code dependent on their classes."
```

In simpler terms:

Imagine you already have a perfectly set-up object — like a well-written email template or a configured game character. Instead of building a new one every time (which can be repetitive and expensive), you just copy the existing one and make small adjustments. This is what the Prototype Pattern does. It allows you to create new objects by copying existing ones, saving time and resources.

Real-life Analogy (Photocopy Machine)

Think of preparing ten offer letters. Instead of typing the same letter ten times, you write it once, photocopy it, and change just the name on each copy. This is how the Prototype Pattern works: start with a base object and produce modified copies with minimal changes.
Understanding


Let's understand better through a common challenge in software systems.

Consider an email notification system where each email instance requires extensive setup—loading templates, configurations, user settings, and formatting. Creating every email from scratch introduces redundancy and inefficiency.

Now imagine having a pre-configured prototype email, and simply cloning it for each user while modifying a few fields (like the name or content). That would save time, reduce errors, and simplify the logic.

Suitable Use Cases:
Apply the Prototype Pattern in these situations:

- Object creation is resource-intensive or complex.
- You require many similar objects with slight variations.
- You want to avoid writing repetitive initialization logic.
- You need runtime object creation without tight class coupling.

---
## ==Structural Pattern==






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



  
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


what are the structural Pattern

```
Structural design patterns are concerned with the **composition of classes and objects**. They focus on how to assemble classes and objects into larger structures while keeping these structures flexible and efficient. Adapter Pattern is one of the most important structural design patterns. Let's understand in depth.
```


### Adapter Pattern

```
The Adapter Pattern allows incompatible interfaces to work together by acting as a **translator** or **wrapper** around an existing class. It converts the interface of a class into another interface that a client expects.  
  
It acts as a bridge between the **Target** interface (expected by the client) and the **Adaptee** (an existing class with a different interface). This structural wrapping enables integration and compatibility across diverse systems.
```

Real-Life Analogy

Imagine traveling from India to Europe. Your mobile charger doesn't fit into European sockets. Instead of buying a new charger, you use a plug adapter. The adapter allows your charger (with its Indian plug) to fit the European socket, enabling charging without modifying either the socket or the charger.


Problem It Solves

- Interface incompatibility between classes.
- Reusability of existing classes without modifying their source code.
- Enables systems to communicate that otherwise couldn't due to differing method signatures.

Similarly, the Adapter Pattern allows objects with incompatible interfaces to collaborate by introducing an adapter.


### Decorator Pattern

```
The Decorator Pattern is a structural design pattern that allows behavior to be added to individual objects, dynamically at runtime, without affecting the behavior of other objects from the same class.

It wraps an object inside another object that adds new behaviors or responsibilities at runtime, keeping the original object's interface intact.
```

Real-Life Analogy

- Think of a coffee shop:
- You order a simple coffee.
- Then, you can add milk, add sugar, add whipped cream, etc.
- You don't need a whole new drink class for every combination.

Each addition wraps the original and adds something more.

Problem It Solves

It solves the problem of class explosion that occurs when you try to use inheritance to add combinations of behavior.

### Facade Pattern

```
The **Facade Pattern** is a **structural design pattern** that provides a simplified, unified interface to a complex subsystem or group of classes.  
  
It acts as a **single entry point** for clients to interact with the system, hiding the underlying complexity and making the system easier to use.
```

Real-Life Analogy

Think of Manual vs. Automatic Car:

- Complex Subsystem (Manual Car): Driving a manual car requires intricate knowledge of multiple components (clutch, gear shifter, accelerator) and their precise coordination to shift gears and drive. It's complex and requires the driver to manage many interactions.

- Facade (Automatic Car): An automatic car acts as a facade. It provides a simplified interface (e.g., "Drive," "Reverse," "Park") to the complex underlying mechanics of gear shifting. The driver (client) no longer needs to manually coordinate the clutch and gears; the automatic transmission handles these complexities internally, making driving much easier.

In short, the manual car exposes the complexity, while the automatic car (the facade) simplifies it for the user


Problem It Solves

It solves the problem of dealing with complex subsystems by hiding the complexities behind a single, unified interface. For example, imagine a movie ticket booking system with:

- PaymentService
- SeatReservationService
- NotificationService
- LoyaltyPointsService
- TicketService

Instead of making the client interact with all of these directly, the Facade Pattern provides a single class like MovieBookingFacade, which internally coordinates all the services.

### Composite Pattern

```
The Composite Pattern is a structural design pattern that allows you to compose objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly.
```

Problem It Solves

The Composite Pattern solves the problem of treating individual objects and groups of objects in the same way. The main problem arises when:

- You want to work with a hierarchy of objects.
- You want the client code to be agnostic to whether it's dealing with a single object or a collection of them

### Proxy Pattern

```
The Proxy Pattern is a structural design pattern that provides a surrogate or placeholder for another object to control access to it.

A proxy acts as an intermediary that implements the same interface as the original object, allowing it to intercept and manage requests to the real object.
```

Real-Life Analogy

Think of a personal assistant:
- A busy CEO may not respond to everyone directly.
- Instead, their assistant takes calls, filters emails, manages the calendar, and only involves the CEO when necessary.
- The assistant controls access to the CEO while still providing essential services to others.

Here, the assistant is the proxy that controls and optimizes access to the real resource (the CEO).


Problem It Solves

It solves the problem of uncontrolled or expensive access to an object. For example, consider a scenario where:

- You have a heavy object like a video player that consumes a lot of resources on initialization.
- You want to delay its creation until it's actually needed (lazy loading).
- Or maybe the object resides on a remote server and you want to add a layer to manage the network communication.

The Proxy Pattern allows you to control access, defer initialization, add logging, caching, or security without modifying the original object.


### Bridge Pattern

```
The Bridge Pattern is a structural design pattern that is used to decouple an abstraction from its implementation so that the two can vary independently.
```

Problem It Solves

When you have multiple dimensions of variability, such as different types of features (abstractions) and multiple implementations of those features, you might end up with a combinatorial explosion of subclasses if you try to use inheritance to handle all combinations. Thus bridge pattern:

- Avoids tight coupling between abstraction and implementation.
- Eliminates code duplication that would occur if every combination of abstraction and implementation had its own class.
- Promotes composition over inheritance, allowing more flexible code evolution.

Real-Life Analogy

- Think of a TV remote and a TV:
- The remote is the abstraction (interface the user interacts with).
- The TV is the implementation (actual functionality).

You can have different types of remotes (basic, advanced) and different brands of TVs (Samsung, Sony). Bridge Pattern allows any remote to work with any TV without creating a separate class for each combination.


### Flyweight Pattern

```
The Flyweight Pattern is a structural design pattern used to minimize memory usage by sharing as much data as possible with similar objects.

It separates the intrinsic (shared) state from the extrinsic (unique) state, so that shared parts of objects are stored only once and reused wherever needed.
```

Real-Life Analogy

Think of trees in a video game. In an open-world video game, you might see thousands of trees:

- All oak trees have the same texture, shape, and behavior (shared/intrinsic).
- But their location, size, or health status may differ (extrinsic).

Rather than loading the same tree model thousands of times, the game engine uses a single shared tree model and passes different parameters when rendering.

Problem It Solves

- It solves the problem of high memory usage when a large number of similar objects are created. For example, imagine a system rendering:
- Thousands of tree objects in a forest
- Each with the same shape and texture but a different location

Instead of creating thousands of identical objects, the Flyweight Pattern lets you share the common parts (shape, texture) and store the unique parts (location) externally, dramatically reducing memory consumption.


Core Concepts

- Intrinsic State: The immutable, shared data stored inside the flyweight. It is independent of context.
- Extrinsic State: The context-specific data passed from the client and not stored in the flyweight.

---
---
## ==Behavioral Design Patterns==

### Iterator Pattern

The Iterator Pattern is a behavioral design pattern that provides a way to access the elements of a collection sequentially without exposing the underlying representation.

Formal Definition

```
The Iterator Pattern is a behavioral design pattern that entrusts the traversal behavior of a collection to a separate design object. It traverses the elements without exposing the underlying operations.

This means whether your collection is an array, a list, a tree, or something custom, you can use an iterator to traverse it in a consistent manner, one element at a time, without worrying about how the data is stored or managed internally.
```

### Observer Pattern

The Observer Pattern is a behavioral design pattern that defines a one-to-many dependency between objects so that when one object (the subject) changes its state, all its dependents (called observers) are notified and updated automatically.

Formal Definition

```
The Observer Pattern is a behavioral design pattern where an object, known as the subject, maintains a list of dependents (observers) and notifies them of any state changes, usually by calling one of their methods.
```

This means if multiple objects are watching another object for updates, they don’t need to keep checking repeatedly. Instead, they get notified as soon as something changes — making the system more efficient and loosely coupled.

Real-Life Analogy

Think of subscribing to a YouTube channel. Once you hit the Subscribe button and turn on notifications, you don’t have to keep visiting the channel to check for new videos. As soon as a new video is uploaded, you get notified instantly.
In this case:

- The channel is the subject.
- The subscribers are the observers.
- The notification is the automatic update mechanism triggered by the subject.

Similarly, in software, when an object (subject) undergoes a change, all registered observers get notified, just like YouTube alerts its subscribers.

### Strategy Pattern

The Strategy Pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one into a separate class, and makes them interchangeable at runtime depending on the context.

Formal Definition

```
The Strategy Pattern is a behavioral design pattern that enables selecting an algorithm's behavior at runtime by defining a set of strategies (algorithms), each encapsulated in its own class, and making them interchangeable via a common interface.
```

It is primarily focused on changing the behavior of an object dynamically, without modifying its class. This promotes better organization of related algorithms and enhances code flexibility and scalability.

Real-Life Analogy

Consider how Uber matches a rider with a driver. The underlying algorithm may change depending on the context, like matching with the nearest driver, giving priority to surge zones, or choosing from an airport queue.
In this case:

- The ride-matching service is the context.
- The different matching algorithms (nearest, surge-priority, airport-queue) are the strategies.
- The strategy interface allows the system to switch between these algorithms seamlessly, depending on real-time conditions.

Similarly, in software, the Strategy Pattern allows a class to use different algorithms or behaviors at runtime, without altering its code structure, just like Uber switches matching strategies based on need.

### Command Pattern

The Command Pattern is a behavioral design pattern that turns a request into a separate object, allowing you to decouple the code that issues the request from the code that performs it.

Formal Definition

```
The Command Pattern is a behavioral design pattern that encapsulates a request as an object, allowing for parameterization of clients with different requests, queuing of requests, and logging of the requests. It lets you add features like undo, redo, logging, and dynamic command execution without changing the core business logic.
```

This allows you to execute commands at a later time, in a flexible manner, without having to interact directly with the request's execution details.

Real-Life Analogy

Think of a remote control used to turn on or off the lights or an air conditioner (AC). When you press a button to turn on the lights or adjust the temperature, you don’t need to understand how the internal circuits work or how the AC receives the signal. You just press the "On" or "Off" button, and the remote control takes care of sending the command.

Similarly, the Command Pattern decouples the sender of a request (the remote control) from the receiver (the light or AC), providing flexibility and simplicity in handling commands.

Four Key Components

- Client – Initiates the request and sets up the command object.
- Invoker – Asks the command to execute the request.
- Command – Defines a binding between a receiver object and an action.
- Receiver – Knows how to perform the actions to satisfy a request.

### Template Pattern

Formal Definition

```
The Template Pattern is a behavioral design pattern that provides a blueprint for executing an algorithm. It allows subclasses to override specific steps of the algorithm, but the overall structure remains the same. This ensures that the invariant parts of the algorithm are not changed, while enabling customization in the variable parts.
```

Real Life Analogy

Imagine you are following a recipe to bake a cake. The overall process of baking a cake (preheat oven, mix ingredients, bake, and cool) is fixed, but the specific ingredients or flavors may vary (chocolate, vanilla, etc.).

The Template Pattern is like the recipe: it defines the basic structure of the process (steps), while allowing the specific ingredients (or steps) to be varied depending on the cake type.

Key Steps in Template Pattern

The Template Pattern generally consists of four key steps:

###### Template Method (Final Method in Base Class)
This method defines the skeleton of the algorithm. It calls the various steps and determines their sequence. This method is final to prevent overriding in subclasses, ensuring that the algorithm’s structure stays consistent.
###### Primitive Operations (Abstract Methods)
These are abstract methods that subclasses must implement. These methods represent the variable parts of the algorithm that may change based on the subclass’s specific requirements.
##### Concrete Operations (Final or Concrete Methods)
These are methods that contain behavior common to all subclasses. They are defined in the base class and are shared by all subclasses.
##### Hooks (Optional Methods with Default Behavior)
Hooks are optional methods in the base class that provide default behavior. Subclasses can override these methods to modify the behavior when needed, but they are not mandatory for all subclasses to implement.

By using the Template Pattern, one can ensure that the common steps of an algorithm remain unchanged while allowing subclasses to modify the specific details of the algorithm.


### State Pattern

Formal Definition
```
The State Pattern is a behavioral design pattern that encapsulates state-specific behavior into separate classes and delegates the behavior to the appropriate state object. This allows the object to change its behavior without altering the underlying code.
```

This pattern makes it easy to manage state transitions by isolating state-specific behavior into distinct classes. It helps achieve loose coupling by ensuring that each state class is independent and can evolve without affecting others.

Real-Life Analogy

Consider a food delivery app. As an order progresses, its state changes through multiple stages:
- The order is placed.
- The order is being prepared.
- A delivery partner is assigned.
- The order is picked up.
- The order is out for delivery.
- Finally, the order is delivered.

At each stage, the app behaves differently:

- In the "Order Placed" state, you can cancel the order.
- In the "Order Preparing" state, you can track the preparation status.
- In the "Delivery Partner Assigned" state, you can see the details of the assigned driver.
- And so on until the order is delivered.

Each of these states represents a distinct phase, and the app's behavior changes based on which state the order is in. The State Pattern manages these transitions seamlessly, with each state class controlling the behavior for that phase. It also follows Open Closed Principle (OCP), as states can be added without modifying the existing code.

### Chain of Responsibility

Formal Definition
```
The Chain of Responsibility Pattern is a behavioral design pattern that transforms particular behaviors into standalone objects called handlers. It allows a request to be passed along a chain of handlers, where each handler decides whether to process the request or pass it to the next handler in the chain.
```

This pattern decouples the sender of a request from its receivers, giving multiple objects a chance to handle the object.

Key Components

This pattern consists of the following components:
- Handler: An abstract class or interface that defines the method for handling requests and a reference to the next handler in the chain.
- Concrete Handler: A class that implements the handler and processes the request if it can. Otherwise, it forwards the request to the next handler.
- Client: The object that sends the request, typically unaware of the specific handler that will process it.

Real-Life Analogy: Customer Support

Imagine you're working in a customer support system. A customer submits a request that can be handled by multiple support teams, such as basic inquiries, technical issues, or billing problems. The Chain of Responsibility Pattern allows the system to forward the request through a chain of support teams (handlers), with each team deciding if they can resolve the issue or pass it along to the next team. This ensures that each team handles only the requests they are best suited to process, and the customer remains unaware of the chain of responsibility.

How It Works

The client sends a request to the first handler in the chain. If that handler can process the request, it does so. If not, it forwards the request to the next handler. This continues until either the request is handled or the end of the chain is reached. The pattern allows for flexibility by enabling new handlers to be added to the chain without altering existing code. Let's now understand the working of the Chain of Responsibility Pattern through a problem statement.


### Visitor Pattern

Formal Defination
```
The **Visitor Pattern** is a behavioral design pattern that **lets you add new operations to existing class hierarchies without modifying the classes themselves**. This is achieved by moving the logic of the operation into a separate class, known as the **"visitor"**.
```

Real-Life Analogy

Imagine a shopping mall where various shops sell different kinds of products. Each shop (element) has a unique way of applying a discount (operation). Rather than having each shop implement its own method for calculating discounts, we create a discount visitor that visits each shop and applies the appropriate discount logic. This way, we can easily add new types of discounts in the future without changing the shop classes.

The Visitor Pattern simplifies complex systems by providing a way to add operations (like discounts) that can be applied to different elements without altering the elements themselves. It decouples operations from the element objects and moves them to separate visitor classes, adhering to the principle of separation of concerns.

### Mediator Pattern

Formal Definition
```
The Mediator Pattern is a behavioral design pattern that centralizes complex communication between objects into a single mediation object. It promotes loose coupling and organizes the interaction between components.
```

Instead of objects communicating directly with each other, they interact through the mediator, which helps simplify and manage their communication.

Real-Life Analogy: Air Traffic Control (ATC)

In an airport, multiple airplanes communicate with the air traffic control (ATC) tower instead of directly with each other. The ATC coordinates their movements, ensuring safe distances and smooth operations. This simplifies communication, as planes rely on the ATC to manage the flow of information, just like the Mediator Pattern centralizes communication between objects in a system.

## Memento Pattern

Formal Definition
```
The Memento Pattern is a behavioral design pattern that allows an object to capture its internal state and restore it later without violating encapsulation. It is especially useful when implementing features like undo/redo or rollback.
```

Key Components

This pattern defines three key components:
Originator: The object whose internal state we want to save and restore.
Memento: A storage object that holds the snapshot of the originator’s state.
Caretaker: The object responsible for requesting the memento and keeping track of it. It neither modifies nor examines the contents of the memento.


Real-Life Analogy: Undo/Redo in Text Editors

Think of the Memento Pattern as an undo/redo mechanism. When you type or edit something in a text editor, the application captures snapshots of the document at different points. Each snapshot (memento) is stored by an external caretaker (like a history stack), and the editor (originator) can revert to these snapshots when needed, without exposing its internal logic.

A key strength of the pattern is that the originator alone is responsible for creating its snapshots, thus preserving encapsulation while still allowing state recovery.

---


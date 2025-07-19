# Java Design Patterns Guide for Beginners

Design patterns are proven solutions to common programming problems. Think of them as blueprints that you can customize to solve recurring design problems in your code.

## Table of Contents
- [Creational Patterns](#creational-patterns) - How objects are created
- [Structural Patterns](#structural-patterns) - How objects are composed
- [Behavioral Patterns](#behavioral-patterns) - How objects interact and communicate

---

## Creational Patterns
*These patterns deal with object creation mechanisms*

### Singleton (Creational)
**What**: Ensures only one instance of a class exists throughout the application  
**Like**: The President of a country - there's only one at any given time  
**When**: Need global access to a shared resource (database connection, logger, configuration)

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;
    
    // Private constructor prevents external instantiation
    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/mydb";
    }
    
    // Thread-safe singleton with lazy initialization
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public void connect() {
        System.out.println("Connected to: " + connectionString);
    }
}

// Usage
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();
System.out.println(db1 == db2); // true - same instance
```

**Key Benefits**: 
- Controlled access to sole instance
- Reduced memory footprint
- Global access point

**Common Use Cases**: Database connections, loggers, configuration managers, thread pools

---

### Factory Method (Creational)
**What**: Creates objects without specifying their exact class  
**Like**: A restaurant kitchen - you order "pizza" but don't need to know which chef makes it  
**When**: You need to create objects but want to let subclasses decide which class to instantiate

```java
// Product interface
interface Vehicle {
    void start();
}

// Concrete products
class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car engine started!");
    }
}

class Motorcycle implements Vehicle {
    @Override
    public void start() {
        System.out.println("Motorcycle engine started!");
    }
}

// Creator (Factory)
abstract class VehicleFactory {
    // Factory method - subclasses decide implementation
    public abstract Vehicle createVehicle();
    
    // Template method using factory method
    public void deliverVehicle() {
        Vehicle vehicle = createVehicle();
        vehicle.start();
        System.out.println("Vehicle delivered!");
    }
}

// Concrete factories
class CarFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

class MotorcycleFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Motorcycle();
    }
}

// Usage
VehicleFactory factory = new CarFactory();
factory.deliverVehicle(); // Car engine started! Vehicle delivered!
```

**Key Benefits**:
- Eliminates need to bind application-specific classes
- Promotes loose coupling
- Easy to extend with new product types

**Common Use Cases**: Creating UI components, database drivers, file parsers

---

### Abstract Factory (Creational)
**What**: Creates families of related objects without specifying their concrete classes  
**Like**: A furniture store that sells complete room sets - you get matching chair, table, and sofa  
**When**: You need to create groups of related objects that work together

```java
// Abstract products
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// Windows family
class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Windows button");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering Windows checkbox");
    }
}

// Mac family
class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Mac button");
    }
}

class MacCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering Mac checkbox");
    }
}

// Abstract Factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete factories
class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// Usage
GUIFactory factory = new WindowsFactory();
Button button = factory.createButton();
Checkbox checkbox = factory.createCheckbox();
button.render();    // Rendering Windows button
checkbox.render();  // Rendering Windows checkbox
```

**Key Benefits**:
- Ensures product families are used together
- Easy to switch between product families
- Promotes consistency among products

**Common Use Cases**: Cross-platform UI components, database drivers for different databases

---

### Builder (Creational)
**What**: Constructs complex objects step by step  
**Like**: Building a custom computer - you choose CPU, RAM, storage piece by piece  
**When**: Creating objects with many optional parameters or complex construction logic

```java
// Product class
class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String graphicsCard;
    private boolean hasWifi;
    
    // Private constructor - only Builder can create
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.hasWifi = builder.hasWifi;
    }
    
    @Override
    public String toString() {
        return String.format("Computer{cpu='%s', ram='%s', storage='%s', " +
                           "graphicsCard='%s', hasWifi=%s}", 
                           cpu, ram, storage, graphicsCard, hasWifi);
    }
    
    // Static nested Builder class
    public static class ComputerBuilder {
        // Required parameters
        private String cpu;
        private String ram;
        
        // Optional parameters
        private String storage = "256GB SSD";
        private String graphicsCard = "Integrated";
        private boolean hasWifi = true;
        
        // Constructor with required parameters
        public ComputerBuilder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }
        
        // Optional parameter methods (fluent interface)
        public ComputerBuilder storage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public ComputerBuilder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        
        public ComputerBuilder hasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }
        
        // Build method
        public Computer build() {
            return new Computer(this);
        }
    }
}

// Usage
Computer gamingPC = new Computer.ComputerBuilder("Intel i9", "32GB")
    .storage("1TB NVMe")
    .graphicsCard("RTX 4080")
    .hasWifi(true)
    .build();

Computer officePC = new Computer.ComputerBuilder("Intel i5", "16GB")
    .build(); // Uses defaults for optional parameters

System.out.println(gamingPC);
System.out.println(officePC);
```

**Key Benefits**:
- Readable object construction
- Immutable objects
- Flexible parameter handling

**Common Use Cases**: Configuration objects, SQL query builders, test data creation

---

### Prototype (Creational)
**What**: Creates objects by cloning existing instances  
**Like**: Photocopying a document - you get an exact copy without rewriting  
**When**: Creating objects is expensive or you need copies of existing objects

```java
// Prototype interface
interface Shape extends Cloneable {
    Shape clone();
    void draw();
}

// Concrete prototype
class Circle implements Shape {
    private int radius;
    private String color;
    
    public Circle(int radius, String color) {
        this.radius = radius;
        this.color = color;
    }
    
    // Copy constructor for deep cloning
    public Circle(Circle other) {
        this.radius = other.radius;
        this.color = other.color;
    }
    
    @Override
    public Shape clone() {
        return new Circle(this); // Deep clone
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing " + color + " circle with radius " + radius);
    }
    
    // Getters and setters
    public void setRadius(int radius) { this.radius = radius; }
    public void setColor(String color) { this.color = color; }
}

// Prototype registry (optional)
class ShapeCache {
    private static Map<String, Shape> shapeMap = new HashMap<>();
    
    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return cachedShape != null ? cachedShape.clone() : null;
    }
    
    public static void loadCache() {
        Circle circle = new Circle(10, "Red");
        shapeMap.put("1", circle);
        
        Circle largeCircle = new Circle(20, "Blue");
        shapeMap.put("2", largeCircle);
    }
}

// Usage
ShapeCache.loadCache();

Shape clonedShape1 = ShapeCache.getShape("1");
Shape clonedShape2 = ShapeCache.getShape("2");

clonedShape1.draw(); // Drawing Red circle with radius 10
clonedShape2.draw(); // Drawing Blue circle with radius 20
```

**Key Benefits**:
- Avoids expensive object creation
- Reduces subclassing
- Dynamic object creation

**Common Use Cases**: Object caching, configuration objects, game objects

---

## Structural Patterns
*These patterns deal with object composition and relationships*

### Adapter (Structural)
**What**: Allows incompatible interfaces to work together  
**Like**: A power adapter that lets you plug a US device into a European outlet  
**When**: You need to use an existing class with an incompatible interface

```java
// Target interface (what client expects)
interface MediaPlayer {
    void play(String audioType, String fileName);
}

// Adaptee (existing class with incompatible interface)
class AdvancedAudioPlayer {
    public void playMp3(String fileName) {
        System.out.println("Playing MP3 file: " + fileName);
    }
    
    public void playMp4(String fileName) {
        System.out.println("Playing MP4 file: " + fileName);
    }
}

// Adapter class
class MediaAdapter implements MediaPlayer {
    private AdvancedAudioPlayer advancedPlayer;
    
    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("mp3") || audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer = new AdvancedAudioPlayer();
        }
    }
    
    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            advancedPlayer.playMp3(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer.playMp4(fileName);
        }
    }
}

// Client class
class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    
    @Override
    public void play(String audioType, String fileName) {
        // Built-in support for WAV
        if (audioType.equalsIgnoreCase("wav")) {
            System.out.println("Playing WAV file: " + fileName);
        }
        // Use adapter for other formats
        else if (audioType.equalsIgnoreCase("mp3") || audioType.equalsIgnoreCase("mp4")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}

// Usage
AudioPlayer player = new AudioPlayer();
player.play("wav", "song.wav");    // Playing WAV file: song.wav
player.play("mp3", "song.mp3");    // Playing MP3 file: song.mp3
player.play("mp4", "movie.mp4");   // Playing MP4 file: movie.mp4
```

**Key Benefits**:
- Reuse existing classes with incompatible interfaces
- Separation of concerns
- Single Responsibility Principle

**Common Use Cases**: Third-party library integration, legacy system integration

---

### Decorator (Structural)
**What**: Adds new functionality to objects dynamically without changing their structure  
**Like**: Adding toppings to a pizza - each topping adds functionality without changing the base  
**When**: You want to add behavior to objects without subclassing

```java
// Component interface
interface Coffee {
    String getDescription();
    double getCost();
}

// Concrete component
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple coffee";
    }
    
    @Override
    public double getCost() {
        return 2.0;
    }
}

// Base decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return coffee.getCost();
    }
}

// Concrete decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", milk";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", sugar";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.2;
    }
}

class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", whip";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.7;
    }
}

// Usage
Coffee coffee = new SimpleCoffee();
System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

// Add milk
coffee = new MilkDecorator(coffee);
System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

// Add sugar
coffee = new SugarDecorator(coffee);
System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

// Add whip
coffee = new WhipDecorator(coffee);
System.out.println(coffee.getDescription() + " - $" + coffee.getCost());
// Output: Simple coffee, milk, sugar, whip - $3.4
```

**Key Benefits**:
- Flexible alternative to subclassing
- Composition over inheritance
- Runtime behavior modification

**Common Use Cases**: GUI components, I/O streams, middleware layers

---

### Facade (Structural)
**What**: Provides a simplified interface to a complex subsystem  
**Like**: A hotel concierge - you ask for "dinner reservation" instead of calling restaurant directly  
**When**: You want to hide the complexity of a system behind a simple interface

```java
// Complex subsystem classes
class CPU {
    public void freeze() { System.out.println("CPU: Freezing..."); }
    public void jump(long position) { System.out.println("CPU: Jumping to " + position); }
    public void execute() { System.out.println("CPU: Executing..."); }
}

class Memory {
    public void load(long position, String data) {
        System.out.println("Memory: Loading '" + data + "' at " + position);
    }
}

class HardDrive {
    public String read(long lba, int size) {
        System.out.println("HardDrive: Reading " + size + " bytes from " + lba);
        return "bootloader";
    }
}

// Facade class
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    
    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }
    
    // Simplified interface
    public void startComputer() {
        System.out.println("Starting computer...");
        cpu.freeze();
        memory.load(0, hardDrive.read(0, 1024));
        cpu.jump(0);
        cpu.execute();
        System.out.println("Computer started successfully!");
    }
}

// Usage
ComputerFacade computer = new ComputerFacade();
computer.startComputer(); // Simple call hides complex operations
```

**Key Benefits**:
- Simplifies complex interfaces
- Reduces dependencies
- Provides a single entry point

**Common Use Cases**: API wrappers, database access layers, complex library interfaces

---

### Composite (Structural)
**What**: Composes objects into tree structures to represent part-whole hierarchies  
**Like**: A file system - folders can contain files or other folders  
**When**: You need to represent hierarchical structures and treat individual objects and compositions uniformly

```java
// Component interface
interface FileSystemComponent {
    void showDetails();
    long getSize();
}

// Leaf component
class File implements FileSystemComponent {
    private String name;
    private long size;
    
    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public void showDetails() {
        System.out.println("File: " + name + " (Size: " + size + " bytes)");
    }
    
    @Override
    public long getSize() {
        return size;
    }
}

// Composite component
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();
    
    public Directory(String name) {
        this.name = name;
    }
    
    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }
    
    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }
    
    @Override
    public void showDetails() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }
    
    @Override
    public long getSize() {
        return components.stream()
                .mapToLong(FileSystemComponent::getSize)
                .sum();
    }
}

// Usage
Directory root = new Directory("root");
Directory documents = new Directory("documents");
Directory photos = new Directory("photos");

File readme = new File("readme.txt", 1024);
File photo1 = new File("vacation.jpg", 2048);
File photo2 = new File("family.jpg", 1536);

documents.addComponent(readme);
photos.addComponent(photo1);
photos.addComponent(photo2);

root.addComponent(documents);
root.addComponent(photos);

root.showDetails();
System.out.println("Total size: " + root.getSize() + " bytes");
```

**Key Benefits**:
- Uniform treatment of individual and composite objects
- Recursive composition
- Simplified client code

**Common Use Cases**: File systems, GUI components, organizational structures

---

### Proxy (Structural)
**What**: Provides a placeholder or surrogate for another object to control access to it  
**Like**: A security guard at a building - controls who can enter  
**When**: You need to control access to an object, add caching, or provide lazy initialization

```java
// Subject interface
interface Image {
    void display();
}

// Real subject
class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // Expensive operation
    }
    
    private void loadFromDisk() {
        System.out.println("Loading image: " + filename);
        // Simulate expensive loading operation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public void display() {
        System.out.println("Displaying image: " + filename);
    }
}

// Proxy
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    
    public ProxyImage(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        // Lazy initialization
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

// Usage
Image image1 = new ProxyImage("photo1.jpg");
Image image2 = new ProxyImage("photo2.jpg");

System.out.println("Images created (not loaded yet)");

image1.display(); // Loads and displays
image1.display(); // Just displays (already loaded)

image2.display(); // Loads and displays
```

**Key Benefits**:
- Controls access to objects
- Lazy initialization
- Caching and performance optimization

**Common Use Cases**: Virtual proxies, protection proxies, remote proxies

---

## Behavioral Patterns
*These patterns focus on communication between objects and the assignment of responsibilities*

### Observer (Behavioral)
**What**: Defines a one-to-many dependency between objects so that when one object changes state, all dependents are notified  
**Like**: A newspaper subscription - when a new issue is published, all subscribers get notified  
**When**: You need to notify multiple objects about state changes

```java
// Observer interface
interface Observer {
    void update(String message);
}

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(String message);
}

// Concrete subject
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String news;
    
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public void setNews(String news) {
        this.news = news;
        notifyObservers(news);
    }
}

// Concrete observers
class NewsChannel implements Observer {
    private String name;
    
    public NewsChannel(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println(name + " received news: " + message);
    }
}

class Newspaper implements Observer {
    private String name;
    
    public Newspaper(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println(name + " will print: " + message);
    }
}

// Usage
NewsAgency agency = new NewsAgency();

NewsChannel cnn = new NewsChannel("CNN");
NewsChannel bbc = new NewsChannel("BBC");
Newspaper times = new Newspaper("Times");

agency.attach(cnn);
agency.attach(bbc);
agency.attach(times);

agency.setNews("Breaking: New Java version released!");
// Output:
// CNN received news: Breaking: New Java version released!
// BBC received news: Breaking: New Java version released!
// Times will print: Breaking: New Java version released!
```

**Key Benefits**:
- Loose coupling between subject and observers
- Dynamic subscription/unsubscription
- Broadcast communication

**Common Use Cases**: Event handling, model-view architectures, publish-subscribe systems

---

### Strategy (Behavioral)
**What**: Defines a family of algorithms, encapsulates each one, and makes them interchangeable  
**Like**: Different payment methods at checkout - credit card, PayPal, cash  
**When**: You have multiple ways to perform a task and want to switch between them dynamically

```java
// Strategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String holderName;
    
    public CreditCardPayment(String cardNumber, String holderName) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending in " + 
                         cardNumber.substring(cardNumber.length() - 4));
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal account: " + email);
    }
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " in cash");
    }
}

// Context class
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public void checkout(double amount) {
        if (paymentStrategy == null) {
            System.out.println("Please select a payment method");
            return;
        }
        paymentStrategy.pay(amount);
    }
}

// Usage
ShoppingCart cart = new ShoppingCart();

// Pay with credit card
cart.setPaymentStrategy(new CreditCardPayment("1234567890123456", "John Doe"));
cart.checkout(100.50);

// Switch to PayPal
cart.setPaymentStrategy(new PayPalPayment("john@example.com"));
cart.checkout(75.25);

// Switch to cash
cart.setPaymentStrategy(new CashPayment());
cart.checkout(50.00);
```

**Key Benefits**:
- Runtime algorithm selection
- Easy to add new strategies
- Eliminates conditional statements

**Common Use Cases**: Sorting algorithms, compression algorithms, payment processing

---

### Command (Behavioral)
**What**: Encapsulates a request as an object, allowing you to parameterize clients with different requests  
**Like**: A remote control - each button encapsulates a command for the TV  
**When**: You want to queue operations, support undo, or decouple sender from receiver

```java
// Command interface
interface Command {
    void execute();
    void undo();
}

// Receiver (the object that performs the work)
class Light {
    private boolean isOn = false;
    
    public void turnOn() {
        isOn = true;
        System.out.println("Light is ON");
    }
    
    public void turnOff() {
        isOn = false;
        System.out.println("Light is OFF");
    }
    
    public boolean isOn() {
        return isOn;
    }
}

// Concrete commands
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOn();
    }
    
    @Override
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOff();
    }
    
    @Override
    public void undo() {
        light.turnOn();
    }
}

// No-op command for empty slots
class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }
    
    @Override
    public void undo() {
        // Do nothing
    }
}

// Invoker
class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;
    
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    
    public void onButtonPressed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }
    
    public void offButtonPressed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    public void undoButtonPressed() {
        undoCommand.undo();
    }
}

// Usage
RemoteControl remote = new RemoteControl();

Light livingRoomLight = new Light();
Light kitchenLight = new Light();

LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);

LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
remote.setCommand(1, kitchenLightOn, kitchenLightOff);

remote.onButtonPressed(0);  // Light is ON
remote.offButtonPressed(0); // Light is OFF
remote.undoButtonPressed(); // Light is ON (undo)
```

**Key Benefits**:
- Decouples sender from receiver
- Supports undo operations
- Macro commands (combining multiple commands)

**Common Use Cases**: GUI actions, macro recording, queuing systems

---

### State (Behavioral)
**What**: Allows an object to alter its behavior when its internal state changes  
**Like**: A vending machine - behaves differently when it has coins vs. when it's empty  
**When**: An object's behavior depends on its state and must change at runtime

```java
// State interface
interface VendingMachineState {
    void insertCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispense(VendingMachine machine);
}

// Context class
class VendingMachine {
    private VendingMachineState waitingState;
    private VendingMachineState hasCoinsState;
    private VendingMachineState dispensingState;
    
    private VendingMachineState currentState;
    private int productCount = 5;
    
    public VendingMachine() {
        waitingState = new WaitingState();
        hasCoinsState = new HasCoinsState();
        dispensingState = new DispensingState();
        
        currentState = waitingState;
    }
    
    public void insertCoin() {
        currentState.insertCoin(this);
    }
    
    public void selectProduct() {
        currentState.selectProduct(this);
    }
    
    public void dispense() {
        currentState.dispense(this);
    }
    
    // State transition methods
    public void setState(VendingMachineState state) {
        this.currentState = state;
    }
    
    public VendingMachineState getWaitingState() { return waitingState; }
    public VendingMachineState getHasCoinsState() { return hasCoinsState; }
    public VendingMachineState getDispensingState() { return dispensingState; }
    
    public int getProductCount() { return productCount; }
    public void releaseProduct() { 
        if (productCount > 0) {
            productCount--;
            System.out.println("Product dispensed! Remaining: " + productCount);
        }
    }
}

// Concrete states
class WaitingState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin inserted. Please select a product.");
        machine.setState(machine.getHasCoinsState());
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Please insert a coin first.");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please insert a coin first.");
    }
}

class HasCoinsState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin already inserted. Please select a product.");
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        if (machine.getProductCount() > 0) {
            System.out.println("Product selected. Dispensing...");
            machine.setState(machine.getDispensingState());
            machine.dispense();
        } else {
            System.out.println("Out of stock. Returning coin.");
            machine.setState(machine.getWaitingState());
        }
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please select a product first.");
    }
}

class DispensingState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Please wait, dispensing in progress.");
    }
    
    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Please wait, dispensing in progress.");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        machine.releaseProduct();
        machine.setState(machine.getWaitingState());
    }
}

// Usage
VendingMachine machine = new VendingMachine();

machine.selectProduct();  // Please insert a coin first.
machine.insertCoin();     // Coin inserted. Please select a product.
machine.selectProduct();  // Product selected. Dispensing...
                         // Product dispensed! Remaining: 4

machine.insertCoin();     // Coin inserted. Please select a product.
machine.insertCoin();     // Coin already inserted. Please select a product.
machine.selectProduct();  // Product selected. Dispensing...
                         // Product dispensed! Remaining: 3
```

**Key Benefits**:
- Eliminates complex conditional logic
- Makes state transitions explicit
- Easy to add new states

**Common Use Cases**: State machines, game character states, UI component states

---

### Template Method (Behavioral)
**What**: Defines the skeleton of an algorithm in a base class, letting subclasses override specific steps  
**Like**: A recipe - the steps are the same, but ingredients can vary  
**When**: You have an algorithm with invariant parts and variant parts

```java
// Abstract class with template method
abstract class DataProcessor {
    // Template method - defines the algorithm skeleton
    public final void process() {
        readData();
        processData();
        writeData();
    }
    
    // Steps that subclasses must implement
    protected abstract void readData();
    protected abstract void processData();
    protected abstract void writeData();
    
    // Hook method - optional override
    protected void validateData() {
        System.out.println("Default validation performed");
    }
}

// Concrete implementations
class CSVProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from CSV file");
    }
    
    @Override
    protected void processData() {
        System.out.println("Processing CSV data");
    }
    
    @Override
    protected void writeData() {
        System.out.println("Writing processed data to CSV file");
    }
}

class XMLProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from XML file");
    }
    
    @Override
    protected void processData() {
        System.out.println("Processing XML data");
    }
    
    @Override
    protected void writeData() {
        System.out.println("Writing processed data to XML file");
    }
    
    @Override
    protected void validateData() {
        System.out.println("XML schema validation performed");
    }
}

class JSONProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from JSON file");
    }
    
    @Override
    protected void processData() {
        System.out.println("Processing JSON data");
    }
    
    @Override
    protected void writeData() {
        System.out.println("Writing processed data to JSON file");
    }
}

// Usage
DataProcessor csvProcessor = new CSVProcessor();
DataProcessor xmlProcessor = new XMLProcessor();
DataProcessor jsonProcessor = new JSONProcessor();

System.out.println("Processing CSV:");
csvProcessor.process();

System.out.println("\nProcessing XML:");
xmlProcessor.process();

System.out.println("\nProcessing JSON:");
jsonProcessor.process();
```

**Key Benefits**:
- Code reuse through inheritance
- Controls algorithm structure
- Allows customization of specific steps

**Common Use Cases**: Frameworks, data processing pipelines, test frameworks

---

### Chain of Responsibility (Behavioral)
**What**: Passes requests along a chain of handlers until one handles it  
**Like**: Customer service escalation - starts with level 1, then level 2, then manager  
**When**: You want to give multiple objects a chance to handle a request

```java
// Handler interface
abstract class SupportHandler {
    protected SupportHandler nextHandler;
    
    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    
    public abstract void handleRequest(String issue, int priority);
}

// Concrete handlers
class Level1Support extends SupportHandler {
    @Override
    public void handleRequest(String issue, int priority) {
        if (priority <= 1) {
            System.out.println("Level 1 Support: Handling '" + issue + "'");
        } else if (nextHandler != null) {
            System.out.println("Level 1 Support: Escalating '" + issue + "'");
            nextHandler.handleRequest(issue, priority);
        }
    }
}

class Level2Support extends SupportHandler {
    @Override
    public void handleRequest(String issue, int priority) {
        if (priority <= 2) {
            System.out.println("Level 2 Support: Handling '" + issue + "'");
        } else if (nextHandler != null) {
            System.out.println("Level 2 Support: Escalating '" + issue + "'");
            nextHandler.handleRequest(issue, priority);
        }
    }
}

class ManagerSupport extends SupportHandler {
    @Override
    public void handleRequest(String issue, int priority) {
        if (priority <= 3) {
            System.out.println("Manager: Handling '" + issue + "'");
        } else {
            System.out.println("Manager: Issue '" + issue + "' requires executive attention");
        }
    }
}

// Usage
SupportHandler level1 = new Level1Support();
SupportHandler level2 = new Level2Support();
SupportHandler manager = new ManagerSupport();

// Build the chain
level1.setNextHandler(level2);
level2.setNextHandler(manager);

// Test different priority levels
level1.handleRequest("Password reset", 1);        // Level 1 handles
level1.handleRequest("Software bug", 2);          // Escalated to Level 2
level1.handleRequest("System crash", 3);          // Escalated to Manager
level1.handleRequest("Data breach", 4);           // Requires executive attention
```

**Key Benefits**:
- Decouples sender and receiver
- Dynamic chain composition
- Single Responsibility Principle

**Common Use Cases**: Event handling, middleware processing, validation chains

---

### Iterator (Behavioral)
**What**: Provides a way to access elements of a collection without exposing its underlying representation  
**Like**: A TV remote - you can go through channels without knowing how they're stored  
**When**: You need to traverse a collection without exposing its internal structure

```java
// Iterator interface
interface Iterator<T> {
    boolean hasNext();
    T next();
}

// Aggregate interface
interface Iterable<T> {
    Iterator<T> createIterator();
}

// Concrete aggregate
class BookCollection implements Iterable<String> {
    private String[] books;
    private int index = 0;
    
    public BookCollection(int size) {
        books = new String[size];
    }
    
    public void addBook(String book) {
        if (index < books.length) {
            books[index++] = book;
        }
    }
    
    @Override
    public Iterator<String> createIterator() {
        return new BookIterator();
    }
    
    // Inner class implementing iterator
    private class BookIterator implements Iterator<String> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < index && books[currentIndex] != null;
        }
        
        @Override
        public String next() {
            if (hasNext()) {
                return books[currentIndex++];
            }
            throw new java.util.NoSuchElementException();
        }
    }
}

// Alternative implementation using Java's built-in Iterator
class PlaylistCollection implements java.lang.Iterable<String> {
    private List<String> songs = new ArrayList<>();
    
    public void addSong(String song) {
        songs.add(song);
    }
    
    @Override
    public java.util.Iterator<String> iterator() {
        return songs.iterator();
    }
}

// Usage
BookCollection library = new BookCollection(5);
library.addBook("Design Patterns");
library.addBook("Clean Code");
library.addBook("Effective Java");

System.out.println("Books in library:");
Iterator<String> bookIterator = library.createIterator();
while (bookIterator.hasNext()) {
    System.out.println("- " + bookIterator.next());
}

// Using Java's built-in iterator
PlaylistCollection playlist = new PlaylistCollection();
playlist.addSong("Song 1");
playlist.addSong("Song 2");
playlist.addSong("Song 3");

System.out.println("\nSongs in playlist:");
for (String song : playlist) {
    System.out.println("♪ " + song);
}
```

**Key Benefits**:
- Uniform traversal interface
- Supports multiple traversal algorithms
- Separates collection from traversal logic

**Common Use Cases**: Collection frameworks, database result sets, tree traversal

---

### Visitor (Behavioral)
**What**: Lets you define new operations on objects without changing their classes  
**Like**: A tax inspector visiting different types of buildings - each building accepts the visitor differently  
**When**: You need to perform operations on objects of different types without modifying their classes

```java
// Visitor interface
interface ShapeVisitor {
    void visit(Circle circle);
    void visit(Rectangle rectangle);
    void visit(Triangle triangle);
}

// Element interface
interface Shape {
    void accept(ShapeVisitor visitor);
}

// Concrete elements
class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

class Rectangle implements Shape {
    private double width, height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

class Triangle implements Shape {
    private double base, height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    public double getBase() { return base; }
    public double getHeight() { return height; }
    
    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}

// Concrete visitors
class AreaCalculator implements ShapeVisitor {
    @Override
    public void visit(Circle circle) {
        double area = Math.PI * circle.getRadius() * circle.getRadius();
        System.out.println("Circle area: " + String.format("%.2f", area));
    }
    
    @Override
    public void visit(Rectangle rectangle) {
        double area = rectangle.getWidth() * rectangle.getHeight();
        System.out.println("Rectangle area: " + area);
    }
    
    @Override
    public void visit(Triangle triangle) {
        double area = 0.5 * triangle.getBase() * triangle.getHeight();
        System.out.println("Triangle area: " + area);
    }
}

class PerimeterCalculator implements ShapeVisitor {
    @Override
    public void visit(Circle circle) {
        double perimeter = 2 * Math.PI * circle.getRadius();
        System.out.println("Circle perimeter: " + String.format("%.2f", perimeter));
    }
    
    @Override
    public void visit(Rectangle rectangle) {
        double perimeter = 2 * (rectangle.getWidth() + rectangle.getHeight());
        System.out.println("Rectangle perimeter: " + perimeter);
    }
    
    @Override
    public void visit(Triangle triangle) {
        // Assuming equilateral triangle for simplicity
        double perimeter = 3 * triangle.getBase();
        System.out.println("Triangle perimeter: " + perimeter);
    }
}

// Usage
List<Shape> shapes = Arrays.asList(
    new Circle(5),
    new Rectangle(4, 6),
    new Triangle(3, 4)
);

AreaCalculator areaCalculator = new AreaCalculator();
PerimeterCalculator perimeterCalculator = new PerimeterCalculator();

System.out.println("Calculating areas:");
for (Shape shape : shapes) {
    shape.accept(areaCalculator);
}

System.out.println("\nCalculating perimeters:");
for (Shape shape : shapes) {
    shape.accept(perimeterCalculator);
}
```

**Key Benefits**:
- Adds new operations without modifying existing classes
- Groups related operations together
- Easy to add new visitors

**Common Use Cases**: Compiler design, document processing, syntax tree operations

---

### Mediator (Behavioral)
**What**: Defines how objects interact with each other through a mediator object  
**Like**: An air traffic control tower - planes don't communicate directly, they go through the tower  
**When**: You want to reduce coupling between communicating objects

```java
// Mediator interface
interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}

// Abstract colleague
abstract class User {
    protected ChatMediator mediator;
    protected String name;
    
    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }
    
    public abstract void send(String message);
    public abstract void receive(String message);
    
    public String getName() {
        return name;
    }
}

// Concrete mediator
class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();
    
    @Override
    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getName() + " joined the chat room");
    }
    
    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receive(message);
            }
        }
    }
}

// Concrete colleagues
class ChatUser extends User {
    public ChatUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void send(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }
    
    @Override
    public void receive(String message) {
        System.out.println(name + " receives: " + message);
    }
}

class ModeratorUser extends User {
    public ModeratorUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }
    
    @Override
    public void send(String message) {
        System.out.println("[MODERATOR] " + name + " sends: " + message);
        mediator.sendMessage("[MODERATOR] " + message, this);
    }
    
    @Override
    public void receive(String message) {
        System.out.println("[MODERATOR] " + name + " receives: " + message);
    }
}

// Usage
ChatMediator chatRoom = new ChatRoom();

User john = new ChatUser(chatRoom, "John");
User jane = new ChatUser(chatRoom, "Jane");
User admin = new ModeratorUser(chatRoom, "Admin");

chatRoom.addUser(john);
chatRoom.addUser(jane);
chatRoom.addUser(admin);

john.send("Hello everyone!");
admin.send("Welcome to the chat room!");
jane.send("Thanks for the welcome!");
```

**Key Benefits**:
- Reduces coupling between objects
- Centralizes communication logic
- Easy to understand and maintain

**Common Use Cases**: Chat systems, GUI components, workflow systems

---

### Memento (Behavioral)
**What**: Captures and externalizes an object's internal state for later restoration  
**Like**: A save game feature - you can save your progress and restore it later  
**When**: You need to provide undo functionality or save/restore object states

```java
// Memento class
class EditorMemento {
    private final String content;
    private final int cursorPosition;
    
    public EditorMemento(String content, int cursorPosition) {
        this.content = content;
        this.cursorPosition = cursorPosition;
    }
    
    public String getContent() {
        return content;
    }
    
    public int getCursorPosition() {
        return cursorPosition;
    }
}

// Originator class
class TextEditor {
    private String content;
    private int cursorPosition;
    
    public TextEditor() {
        this.content = "";
        this.cursorPosition = 0;
    }
    
    public void type(String text) {
        content = content.substring(0, cursorPosition) + text + 
                 content.substring(cursorPosition);
        cursorPosition += text.length();
    }
    
    public void setCursorPosition(int position) {
        if (position >= 0 && position <= content.length()) {
            this.cursorPosition = position;
        }
    }
    
    public String getContent() {
        return content;
    }
    
    public int getCursorPosition() {
        return cursorPosition;
    }
    
    // Create memento
    public EditorMemento save() {
        return new EditorMemento(content, cursorPosition);
    }
    
    // Restore from memento
    public void restore(EditorMemento memento) {
        this.content = memento.getContent();
        this.cursorPosition = memento.getCursorPosition();
    }
    
    public void printStatus() {
        System.out.println("Content: '" + content + "'");
        System.out.println("Cursor at position: " + cursorPosition);
        System.out.println("---");
    }
}

// Caretaker class
class EditorHistory {
    private Stack<EditorMemento> history = new Stack<>();
    
    public void backup(EditorMemento memento) {
        history.push(memento);
    }
    
    public EditorMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
    
    public boolean canUndo() {
        return !history.isEmpty();
    }
}

// Usage
TextEditor editor = new TextEditor();
EditorHistory history = new EditorHistory();

editor.type("Hello");
editor.printStatus();

// Save state
history.backup(editor.save());

editor.type(" World");
editor.printStatus();

// Save another state
history.backup(editor.save());

editor.type("!");
editor.setCursorPosition(5);
editor.type(" Beautiful");
editor.printStatus();

// Undo to previous state
if (history.canUndo()) {
    System.out.println("Undoing...");
    editor.restore(history.undo());
    editor.printStatus();
}

// Undo again
if (history.canUndo()) {
    System.out.println("Undoing again...");
    editor.restore(history.undo());
    editor.printStatus();
}
```

**Key Benefits**:
- Provides undo/redo functionality
- Preserves encapsulation
- Simplifies originator class

**Common Use Cases**: Text editors, games, database transactions, configuration management

---

## Summary

Design patterns are powerful tools that help you write better, more maintainable code. Here are the key takeaways:

### When to Use Each Category:
- **Creational**: When you need flexible object creation
- **Structural**: When you need to compose objects and classes
- **Behavioral**: When you need to define object interactions

### Most Commonly Used Patterns:
1. **Singleton** - For global access to single instances
2. **Factory Method** - For flexible object creation
3. **Observer** - For event-driven programming
4. **Strategy** - For algorithm selection
5. **Decorator** - For adding behavior dynamically

### Remember:
- Don't force patterns where they're not needed
- Patterns solve specific problems - understand the problem first
- Start simple and refactor to patterns when complexity grows
- Good software design is more important than using patterns

Practice these patterns in small projects to understand them better. The more you use them, the more natural they'll become in your problem-solving toolkit!
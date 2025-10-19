	
Chapter1: Introduction

- The first thing you should understand :[[[JAVA][Beginning Java EE 7].pdf#page=8&selection=17,0,17,12|[JAVA][Beginning Java EE 7], page 8]]
- Chapter one is pure gold
- priority of development descriptors [[[JAVA][Beginning Java EE 7].pdf#page=15&selection=19,11,19,56|[JAVA][Beginning Java EE 7], page 15]]

Chapter 2 : Context and dependency injection

[[[JAVA][Beginning Java EE 7].pdf#page=30&selection=23,96,23,100|[JAVA][Beginning Java EE 7], page 30]]
Here are clean, structured notes on **JNDI (Java Naming and Directory Interface):**

---

 Java Naming and Directory Interface (JNDI)

Overview

- JNDI is a **Java API** that provides naming and directory functionality.
    
- It allows applications to look up resources and objects by name, instead of hardcoding configurations.
    
- Commonly used in Java EE (now Jakarta EE) for accessing container-managed resources.
    

---

Key Idea

- Acts as a **directory service**: applications can request resources by a logical name.
    
- The container or application server manages the actual resource and binds it to a JNDI name.
    
- This decouples application code from environment-specific details (e.g., database URLs, credentials).
    

---

 Use Cases

JNDI is typically used to look up:

- **Database connections (DataSource)**
    
- **Enterprise JavaBeans (EJBs)**
    
- **JMS (Java Messaging Service) destinations** – queues and topics
    
- **Mail sessions**
    
- **Environment entries or configuration values**
    

---

Example (Without JNDI)

```java
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/mydb", "user", "password"
);
```

- Hardcodes connection details into the application.
    
- Makes the application less portable and harder to configure.
    

---

Example (With JNDI)

```java
Context ctx = new InitialContext();
DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
Connection conn = ds.getConnection();
```

- Looks up the resource by its JNDI name (`"java:comp/env/jdbc/MyDB"`).
    
- The container provides the actual database connection settings.
    
- Application is portable and easier to maintain.
    

---

 Benefits

1. **Decoupling** – Application code does not depend on specific configuration details.
    
2. **Portability** – Same code works across environments (development, testing, production).
    
3. **Security** – Sensitive information (like passwords) is stored in the server configuration, not in code.
    
4. **Centralized Configuration** – Administrators configure resources once in the container, all applications can reuse them.
    

---
 Summary

- JNDI provides a standard way for Java applications to access resources by name.
    
- It enables indirection between application code and resource configuration.
    
- Commonly used for databases, EJBs, JMS, mail, and other container-managed resources.
    

---

[[[JAVA][Beginning Java EE 7].pdf#page=30&selection=29,0,33,86|[JAVA][Beginning Java EE 7], page 30]]



- Enterprise JavaBeans (EJBs): Server-side components used to encapsulate business logic. They run inside an EJB container, which provides services like transactions, security, and concurrency management.
    
- Servlets: Java classes that handle HTTP requests and responses. They are the foundation of web applications in Java EE, running inside a servlet container such as Tomcat.
    
- Java Message Service (JMS): A Java API for sending and receiving messages between distributed systems. It enables asynchronous communication through message queues (point-to-point) or topics (publish-subscribe).
    
- JDBC DataSource: A factory for database connections managed by the container. Instead of creating database connections manually, applications use a DataSource object, which can provide connection pooling and better resource management.
    
- JMS factories or destinations:
    
    - JMS ConnectionFactory: An object used to create connections to a JMS provider (like a messaging server).
        
    - JMS Destination: A queue or topic that represents the target or source of JMS messages.
        
- Resource injection: A process where the container automatically provides a reference to a resource (like a DataSource, JMS destination, or EJB) into an application component. This is usually done via annotations (e.g., @Resource, @Inject) instead of performing a JNDI lookup manually.
    
- Life cycle management of resources: The container manages the creation, initialization, availability, and cleanup of resources. This ensures resources are efficiently pooled, reused, and released when no longer needed

[[[JAVA][Beginning Java EE 7].pdf#page=31&selection=22,0,32,96|[JAVA][Beginning Java EE 7], page 31]]

- Stateful: A component that maintains internal data (state) across multiple interactions with a client. Each client may have a different stateful instance.
    
- Contextual: A component whose lifecycle is tied to a specific context (such as request, session, or application). The container ensures that the correct instance is provided depending on the active context.
    
- Scope: Defines the lifecycle and visibility of a bean instance. CDI provides predefined scopes such as request, session, application, and conversation.
    
- Request scope: A bean lives only during a single HTTP request. It is created when the request begins and destroyed when the request ends.
    
- Session scope: A bean lives during the entire lifetime of an HTTP session. All requests from the same client in that session share the same instance.
    
- Application scope: A bean is created once for the whole application and shared across all clients and sessions. It behaves like a singleton managed by the container.
    
- Conversation scope: A bean lives during a “conversation,” which is longer than a request but shorter than a session. It allows state to be maintained across multiple requests until the conversation ends.
    
- Stateless components: Components that do not maintain client-specific state between calls. Each client call is independent. Stateless session beans are an example.
    
- Singleton: A component where only one instance exists for the whole application. All clients share this single instance (e.g., Servlets, CDI beans with application scope, or explicitly declared singletons).
    
- Client: In this context, a client can be another bean or component that depends on or uses the bean.


[[[JAVA][Beginning Java EE 7].pdf#page=32&selection=9,0,16,112|[JAVA][Beginning Java EE 7], page 32]]

- Interceptors: Special classes or methods that can be applied to beans to intercept method calls. They allow adding cross-cutting concerns (like logging, security, or transactions) without modifying the business logic code.
    
- Contextual life-cycle management: A mechanism where the container manages the creation, initialization, and destruction of beans depending on their scope. Beans do not need to handle their own lifecycle.
    
- Injection: The process of the container providing a bean with its required dependencies at runtime, without the bean knowing the exact implementation details.
    
- Event notifications: A communication mechanism where beans can fire events, and other beans can listen to them. This decouples event producers from consumers.
    
- Decorators: Special CDI beans that wrap and extend the behavior of other beans without changing their code. They are used to modify or enhance business logic.
    
- Typesafe: Refers to using compile-time checked constructs instead of relying on String-based identifiers. This reduces errors because the compiler validates types and annotations.
    
- Qualifiers: Annotations used to distinguish between different implementations of the same type when performing dependency injection.
    
- Stereotypes: Annotations that group multiple CDI annotations into a single reusable annotation. They simplify bean definition and configuration.
    
- Interceptor bindings: Annotations that declare which interceptors should be applied to which beans or methods. They link business methods to interceptor logic in a typesafe way.
    
- XML descriptors: Deployment configuration files used in Java EE to provide settings. With CDI, their usage is reduced and reserved mainly for deployment-specific details.

concrete class:
A **concrete class** is a normal class in Java that has a complete implementation.

Key points:

- It can be **instantiated** (you can create objects from it using `new`).
    
- It provides implementations for all of its methods.
    
- Unlike abstract classes or interfaces, it does not leave any methods unimplemented.
    
- It can extend another class or implement interfaces.
    

Example of a concrete class:

```java
class Car {
    void drive() {
        System.out.println("Car is driving");
    }
}

Car myCar = new Car(); // concrete class can be instantiated
myCar.drive();
```

Non-concrete examples for comparison:

- **Abstract class**: Cannot be instantiated directly; may contain abstract methods without implementations.
    
- **Interface**: Defines method signatures but no implementations (before Java 8 default methods).
    

So, a concrete class is simply a fully defined, usable class from which you can create objects.


Annotation to use while dependency ejection [[[JAVA][Beginning Java EE 7].pdf#page=35&selection=17,0,17,20|[JAVA][Beginning Java EE 7], page 35]]
- @inject, 
- @Default
- @Qualifier,
- @Qualifier with members,
- @Alternatives
- @Produces: used to inject normal pojos wihout bean.xml
- Injection point : when point of injection should be known ex loggers
- @Despose

Scopes

add section

add about serializable class in java.io
- @ApplicationScoped
- @RequestScoped
- @ConversationScoped
- @Dependent

=>@ Named [[[JAVA][Beginning Java EE 7].pdf#page=50&selection=17,0,22,43|[JAVA][Beginning Java EE 7], page 50]]

=>way to use a service in jsf [[[JAVA][Beginning Java EE 7].pdf#page=50&selection=68,0,70,3|[JAVA][Beginning Java EE 7], page 50]]


=>Interceptor : some thing to execute something while in the process of calling CDI bean. [[[JAVA][Beginning Java EE 7].pdf#page=50&selection=86,0,86,12|[JAVA][Beginning Java EE 7], page 50]]

=> Defination of invocation context interface [[[JAVA][Beginning Java EE 7].pdf#page=52&selection=69,0,69,44|[JAVA][Beginning Java EE 7], page 52]]

![[Pasted image 20250920125815.png]]

=> Creating a common interceptor [[[JAVA][Beginning Java EE 7].pdf#page=53&selection=48,0,48,29|[JAVA][Beginning Java EE 7], page 53]]

=> Channing the interceptor [[[JAVA][Beginning Java EE 7].pdf#page=56&selection=90,0,90,35|[JAVA][Beginning Java EE 7], page 56]]

=> ok but .. .Interceptor Binding Interceptors are defined in their ow

=> prioritizing interceptor [[[JAVA][Beginning Java EE 7].pdf#page=59&selection=17,0,17,33|[JAVA][Beginning Java EE 7], page 59]]

=> Decorators [[[JAVA][Beginning Java EE 7].pdf#page=60&selection=20,0,20,20|[JAVA][Beginning Java EE 7], page 60]]

=> Events (this is the shit we are talking about ) [[[JAVA][Beginning Java EE 7].pdf#page=61&selection=27,0,29,45|[JAVA][Beginning Java EE 7], page 61]]

In this chapter you have learned the difference between a POJO, a Managed Bean, and a CDI Bean and which services apply to which component model. Dependency Injection (JSR 330) and Contexts and Dependency Injection (JSR 299) both bring a standard, portable, and typesafe support for dependency injection to Java EE. CDI adds extra features such as scopes and contexts but also enhanced interceptors, decorators, and events. In fact, CDI natively implements several design patterns like the bridge (with alternatives), the observer/observable (with events), the decorator, the factory (with producers), and of course interception and injection. Interceptors are the AOP-like mechanism of Java EE, allowing the container to invoke cross-cutting concerns on your application. Interceptors are easy to use, are powerful, and can be chained together or prioritized to apply several concerns to your beans. CDI, being a vertical specification, is used in other Java EE specifications. In fact, the next chapters of this book will use some of these CDI services in some way



Chapter 3: Bean validation.

need of bean validation : [[[JAVA][Beginning Java EE 7].pdf#page=75&selection=31,0,36,27|[JAVA][Beginning Java EE 7], page 75]]

wrting contrains [[[JAVA][Beginning Java EE 7].pdf#page=77&selection=8,0,8,19|[JAVA][Beginning Java EE 7], page 77]]

constrain compositn : using multiple contains [[[JAVA][Beginning Java EE 7].pdf#page=81&selection=18,0,19,20|[JAVA][Beginning Java EE 7], page 81]]

Method level validaator : [[[JAVA][Beginning Java EE 7].pdf#page=87&selection=31,0,35,37|[JAVA][Beginning Java EE 7], page 87]]

constain validator constrain [[[JAVA][Beginning Java EE 7].pdf#page=90&selection=35,0,41,17|[JAVA][Beginning Java EE 7], page 90]]

chapter 4:


so basicaly jpa is like a specification for being able to speaking with the db without actualy using the sql , lke we do whiel uisng direct jdbc 

some of the famous implementation of jpa is Heberntis\

Intro to  the annotation [[[JAVA][Beginning Java EE 7].pdf#page=112&selection=9,18,10,37|[JAVA][Beginning Java EE 7], page 112]]

Chapter 4: Object relational mapping

- jpa @table annotation
- jap secondary table.
- diff ways to create composit key via jpa
- other basic annotaion including temporal,transient,enumerated,
- @elementt collection
- xml based  mapping
- types of relational mapping
- Fetching relationship and ordering them
- In hertance mapping
Chapter 6:Managing persistant  objects

- intro duces JPQL as alternative to SQL
- intro to entity manger
- entity manger in application and container manged application
- intro to persistent context
- performing operation with entity mager
- wrtting querites in JPQL
- intro to criteria API
- native queries
- calche api
- types of locking
- entity life cycle
- callback for entity manager
- callback listner

Chapter 7: ejb

- intro to ejbs
- process and embaded container
- service provided by an ejb container
- ejb and ejb lie
- history of ejb
- Remote, Local, and No-Interface Views

|Interface|Used For|Scope|Extends|Typical Client|
|---|---|---|---|---|
|**Remote Interface**|Business methods|Different JVM / machine|`EJBObject`|Remote client|
|**Local Interface**|Business methods|Same JVM|`EJBLocalObject`|Servlet / EJB in same app|
|**Remote Home Interface**|Bean creation/finding|Different JVM / machine|`EJBHome`|Remote client|
|**Local Home Interface**|Bean creation/finding|Same JVM|`EJBLocalHome`|Local client|

- diff web services interface
- Jndi
- stateless beans
- stateful beans
- singleton beans
- concurrency while using singleton beans
- deploying ejbs
- invoking ejbs






# 📝 Java `java.lang.Thread`

---

## 🔹 What is a Thread?

* A **thread** is the smallest unit of execution in a Java program.
* In Java, threads are represented by the class **`java.lang.Thread`**.
* Each program starts with at least one thread → the **main thread**.
* You can create additional threads to run tasks in parallel.

---

## 🔹 Why Use Threads?

* To execute tasks **concurrently** (multiple things happening at the same time).
* To improve responsiveness (UI, servers).
* To utilize **multi-core CPUs** better.
* To handle **background tasks** without blocking the main program.

---

## 🔹 Ways to Create a Thread

1. **Extending `Thread` class**

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running...");
    }
}
new MyThread().start();
```

2. **Implementing `Runnable`**

```java
class MyTask implements Runnable {
    public void run() {
        System.out.println("Task running...");
    }
}
new Thread(new MyTask()).start();
```

3. **Using Lambda (Java 8+)**

```java
new Thread(() -> System.out.println("Lambda running...")).start();
```

---

## 🔹 Important Methods in `Thread`

| Method                            | Description                                                |
| --------------------------------- | ---------------------------------------------------------- |
| `start()`                         | Starts a new thread and calls its `run()` method.          |
| `run()`                           | Code to be executed by the thread.                         |
| `sleep(ms)`                       | Pauses execution for given milliseconds.                   |
| `join()`                          | Waits for another thread to finish.                        |
| `interrupt()`                     | Signals a thread to stop waiting/sleeping.                 |
| `setName()` / `getName()`         | Sets/gets thread name.                                     |
| `setPriority()` / `getPriority()` | Sets/gets priority.                                        |
| `isAlive()`                       | Checks if thread is still running.                         |
| `setDaemon(true)`                 | Makes thread a background daemon thread.                   |
| `currentThread()`                 | Returns the **Thread object** representing current thread. |

---

## 🔹 Thread Lifecycle (States)

A thread can be in one of these states (`Thread.State`):

1. **NEW** → created but not started.
2. **RUNNABLE** → ready to run, waiting for CPU.
3. **RUNNING** → currently executing.
4. **WAITING** → waiting indefinitely for another thread.
5. **TIMED\_WAITING** → waiting for a specific time.
6. **BLOCKED** → waiting for a lock/resource.
7. **TERMINATED** → finished execution.

---

## 🔹 Stack Traces with Threads

### 📌 What is a Stack Trace?

* A **stack trace** shows the **call history (method calls)** of a thread at a given moment.
* Each entry is a `StackTraceElement` with:

    * Class name
    * Method name
    * File name
    * Line number

---

### 📌 Getting the Current Thread’s Stack Trace

```java
StackTraceElement[] stack = Thread.currentThread().getStackTrace();
for (StackTraceElement e : stack) {
    System.out.println(e);
}
```

**Example Output:**

```
java.lang.Thread.getStackTrace(Thread.java:1600)
MyApp.processData(MyApp.java:25)
MyApp.main(MyApp.java:10)
```

---

### 📌 Getting Another Thread’s Stack Trace

```java
Thread t = Thread.currentThread();
for (StackTraceElement e : t.getStackTrace()) {
    System.out.println(e);
}
```

---

### 📌 Getting All Threads’ Stack Traces

```java
Map<Thread, StackTraceElement[]> allTraces = Thread.getAllStackTraces();
for (Map.Entry<Thread, StackTraceElement[]> entry : allTraces.entrySet()) {
    System.out.println("Thread: " + entry.getKey().getName());
    for (StackTraceElement e : entry.getValue()) {
        System.out.println("   " + e);
    }
}
```

---

### 📌 Use Cases of Stack Traces

* **Error Handling** → JVM prints stack trace automatically when an exception is thrown.
* **Debugging** → See which method/class called the current method.
* **Monitoring** → Inspect what all threads are doing at runtime.
* **Profiling** → Analyze program flow, identify performance bottlenecks.
* **Connector use case** → log which class/method invoked a common utility.

---

## 🔹 Example Program

```java
public class ThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("Current thread: " + Thread.currentThread().getName());

            // Print stack trace
            for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
                System.out.println("  " + e);
            }
        });

        t1.setName("Worker-1");
        t1.start();
    }
}
```

**Output:**

```
Current thread: Worker-1
  java.lang.Thread.getStackTrace(Thread.java:1600)
  ThreadDemo.lambda$main$0(ThreadDemo.java:6)
  java.lang.Thread.run(Thread.java:834)
```

---

## 🔹 Summary

* `java.lang.Thread` represents a unit of execution in Java.
* Threads allow **concurrent and parallel programming**.
* Key methods: `start()`, `run()`, `sleep()`, `join()`, `currentThread()`.
* Each thread has a **call stack**, which can be inspected using `getStackTrace()`.
* Stack traces are invaluable for **debugging, logging, and monitoring**.

---

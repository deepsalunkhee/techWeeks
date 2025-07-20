## 🟦 What

### **What is a Servlet?**

* A **Servlet** is a **Java class** that runs on the server.
* It handles **HTTP requests** (like form submissions) and returns **HTTP responses**.
* Think of it as the **controller** or **backend brain** of a Java web application.

### **What is JSP (JavaServer Pages)?**

* JSP is a **template language** that combines HTML + Java code.
* It is used to **generate dynamic web pages**.
* Think of JSP as the **frontend view layer**, rendered on the server.

---

## 🟨 Why

### **Why use Servlets?**

* To **process business logic**, validate data, access databases, handle sessions, etc.
* They are powerful and scalable for enterprise-level backend development.

### **Why use JSP?**

* To **separate the presentation layer (UI)** from the backend logic.
* It simplifies the process of writing HTML with embedded Java logic.

---

## 🟩 When

### **When were they introduced?**

* **Servlets:** 1997
* **JSP:** 1999

These became mainstream in **early 2000s** enterprise web apps before React, Angular, and REST APIs became popular.

### **When to use today?**

* Mostly in **legacy systems**.
* Still relevant for learning Java EE fundamentals.
* Rarely used in **modern full-stack apps**, which use REST APIs + frontend frameworks.

---

## 🟧 How

### **How does JSP-Servlet flow work?**

1. **JSP loads first:** User sees a `login.jsp` page.
2. **Form submitted to a Servlet:** The form `action="LoginServlet"` sends data to `LoginServlet.java`.
3. **Servlet processes data:** It validates login, interacts with DB, and sets session data.
4. **Servlet forwards to JSP:** After processing, it sends the user to `home.jsp` using:

   ```java
   RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
   rd.forward(request, response);
   ```

### **How does communication happen?**

* **JSP → Servlet:** via `form action`, AJAX `fetch()` call, or URL.
* **Servlet → JSP:** via `RequestDispatcher.forward()` or `response.sendRedirect()`.

### **How does session management work?**

* When a user logs in, a **session** is created on the server.
* A **session ID** is sent to the user's browser as a **cookie**.
* The server uses this ID to keep track of that user across pages.
* Ends when: user logs out / session times out / browser is closed.

```java
HttpSession session = request.getSession();
session.setAttribute("username", "deep");
```

---

## 🟫 Legacy vs Modern Comparison

| Feature       | Early 2000s (JSP/Servlet) | Modern Web (React + APIs)  |
| ------------- | ------------------------- | -------------------------- |
| UI Rendering  | Server-side via JSP       | Client-side via React      |
| Data Handling | Servlets/JSP              | RESTful APIs + JSON        |
| Architecture  | Monolithic WAR/EAR        | Microservices, CI/CD       |
| Popular Tools | JSTL, Struts, JSF         | React, Spring Boot, Docker |

---

## 🟪 Bonus: Good Learning Resources 🌐

* [Oracle JSP & Servlet Docs](https://docs.oracle.com/javaee/)
* [W3Schools JSP Tutorial](https://www.w3schools.com/jsp/)
* [TutorialsPoint JSP](https://www.tutorialspoint.com/jsp/index.htm)
* [JavaTpoint JSP](https://www.javatpoint.com/jsp-tutorial)
* [Baeldung (for Java concepts)](https://www.baeldung.com/)


# Jakarta EE 10 Independent Modules with EAR Assembly

This tutorial guides you through creating a Jakarta EE 10 project with three independent Maven projects: an EJB module, a WAR module, and an EAR module that packages them together for deployment on WildFly.

## Prerequisites

- Java 21 JDK installed
- Maven 3.8+ installed
- WildFly 27+ (supports Jakarta EE 10)

## Project Overview

We'll create three separate projects:
1. **ejb-project**: Contains the EJB business logic
2. **war-project**: Contains the web layer (REST API)
3. **ear-project**: Packages both EJB and WAR into an Enterprise Archive

## Step 1: Create the EJB Project

Create a new directory for the EJB project:

```bash
mkdir ejb-project
cd ejb-project
```

### Create EJB Project Structure

```bash
mkdir -p src/main/java/com/example/ejb
mkdir -p src/main/resources/META-INF
```

### Create EJB pom.xml

Create `pom.xml` in the `ejb-project` directory:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>ejb-project</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>ejb</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <jakarta.ee.version>10.0.0</jakarta.ee.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>jakarta.platform</groupId>
            <artifactId>jakarta.jakartaee-api</artifactId>
            <version>${jakarta.ee.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-ejb-plugin</artifactId>
                <version>3.2.1</version>
                <configuration>
                    <ejbVersion>4.0</ejbVersion>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Create Sample EJB Bean

Create `src/main/java/com/example/ejb/GreetingService.java`:

```java
package com.example.ejb;

import jakarta.ejb.Stateless;

@Stateless
public class GreetingService {
    
    public String greet(String name) {
        return "Hello, " + name + "! Welcome to Jakarta EE 10.";
    }
    
    public String getServerInfo() {
        return "Running on Jakarta EE 10 with Java 21";
    }
}
```

### Create beans.xml

Create `src/main/resources/META-INF/beans.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee 
       https://jakarta.ee/xml/ns/jakartaee/beans_4_0.xsd"
       version="4.0"
       bean-discovery-mode="all">
</beans>
```

### Build and Install EJB Project

```bash
mvn clean install
```

This will create `target/ejb-project-1.0-SNAPSHOT.jar` and install it to your local Maven repository.

## Step 2: Create the WAR Project

Go back to the parent directory and create the WAR project:

```bash
cd ..
mkdir war-project
cd war-project
```

### Create WAR Project Structure

```bash
mkdir -p src/main/java/com/example/web
mkdir -p src/main/webapp/WEB-INF
mkdir -p src/main/resources
```

### Create WAR pom.xml

Create `pom.xml` in the `war-project` directory:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>war-project</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <jakarta.ee.version>10.0.0</jakarta.ee.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>jakarta.platform</groupId>
            <artifactId>jakarta.jakartaee-api</artifactId>
            <version>${jakarta.ee.version}</version>
            <scope>provided</scope>
        </dependency>
        
        <!-- Dependency on the EJB project -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>ejb-project</artifactId>
            <version>1.0-SNAPSHOT</version>
            <type>ejb</type>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.4.0</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Create Servlet

Create `src/main/java/com/example/web/GreetingServlet.java`:

```java
package com.example.web;

import com.example.ejb.GreetingService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GreetingServlet", urlPatterns = {"/greet"})
public class GreetingServlet extends HttpServlet {
    
    @EJB
    private GreetingService greetingService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }
        
        String greeting = greetingService.greet(name);
        String serverInfo = greetingService.getServerInfo();
        
        request.setAttribute("greeting", greeting);
        request.setAttribute("serverInfo", serverInfo);
        request.setAttribute("name", name);
        
        request.getRequestDispatcher("/greeting.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
```

### Create web.xml

Create `src/main/webapp/WEB-INF/web.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee 
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">
    
    <display-name>Jakarta EE WAR Project</display-name>
    
</web-app>
```

### Create JSP Pages

Create `src/main/webapp/index.jsp`:

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Jakarta EE 10 Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        form {
            margin: 20px 0;
        }
        input[type="text"] {
            padding: 10px;
            width: 300px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Jakarta EE 10</h1>
        <p>Enter your name to receive a greeting from the EJB service:</p>
        
        <form action="greet" method="post">
            <input type="text" name="name" placeholder="Enter your name" required>
            <button type="submit">Greet Me</button>
        </form>
        
        <hr>
        <p><small>Built with JSP, Servlets, EJB, and Java 21</small></p>
    </div>
</body>
</html>
```

Create `src/main/webapp/greeting.jsp`:

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Greeting Response</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #28a745;
        }
        .info {
            background-color: #e7f3ff;
            padding: 15px;
            border-left: 4px solid #007bff;
            margin: 20px 0;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${greeting}</h1>
        
        <div class="info">
            <strong>Server Information:</strong><br>
            ${serverInfo}
        </div>
        
        <p>You entered: <strong>${name}</strong></p>
        
        <a href="index.jsp">Back to Home</a>
    </div>
</body>
</html>
```

### Build and Install WAR Project

```bash
mvn clean install
```

This will create `target/war-project-1.0-SNAPSHOT.war` and install it to your local Maven repository.

## Step 3: Create the EAR Project

Go back to the parent directory and create the EAR project:

```bash
cd ..
mkdir ear-project
cd ear-project
```

### Create EAR pom.xml

Create `pom.xml` in the `ear-project` directory:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>ear-project</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>ear</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- EJB module -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>ejb-project</artifactId>
            <version>1.0-SNAPSHOT</version>
            <type>ejb</type>
        </dependency>
        
        <!-- WAR module -->
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>war-project</artifactId>
            <version>1.0-SNAPSHOT</version>
            <type>war</type>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-ear-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <version>10</version>
                    <defaultLibBundleDir>lib</defaultLibBundleDir>
                    <modules>
                        <ejbModule>
                            <groupId>com.example</groupId>
                            <artifactId>ejb-project</artifactId>
                        </ejbModule>
                        <webModule>
                            <groupId>com.example</groupId>
                            <artifactId>war-project</artifactId>
                            <contextRoot>/myapp</contextRoot>
                        </webModule>
                    </modules>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Build the EAR Project

```bash
mvn clean package
```

This will create `target/ear-project-1.0-SNAPSHOT.ear` that contains both the EJB JAR and WAR files.

## Step 4: Verify the EAR Contents

You can verify the contents of the EAR file:

```bash
jar tf target/ear-project-1.0-SNAPSHOT.ear
```

You should see:
```
META-INF/
META-INF/MANIFEST.MF
ejb-project-1.0-SNAPSHOT.jar
war-project-1.0-SNAPSHOT.war
META-INF/application.xml
META-INF/maven/
...
```

## Step 5: Download and Configure WildFly

Download WildFly 27 or later:

```bash
# Download WildFly
wget https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.zip

# Extract
unzip wildfly-27.0.1.Final.zip
cd wildfly-27.0.1.Final
```

## Step 6: Deploy to WildFly

### Option 1: Manual Deployment

Start WildFly:

```bash
./bin/standalone.sh  # On Linux/Mac
# or
bin\standalone.bat   # On Windows
```

Copy the EAR file to the deployments directory:

```bash
cp /path/to/ear-project/target/ear-project-1.0-SNAPSHOT.ear \
   ./standalone/deployments/
```

WildFly will automatically deploy the application. Watch the console for deployment messages.

### Option 2: Using Maven WildFly Plugin

Add the WildFly Maven plugin to the EAR project's `pom.xml` in the `<build><plugins>` section:

```xml
<plugin>
    <groupId>org.wildfly.plugins</groupId>
    <artifactId>wildfly-maven-plugin</artifactId>
    <version>4.2.0.Final</version>
    <configuration>
        <filename>ear-project-1.0-SNAPSHOT.ear</filename>
    </configuration>
</plugin>
```

Start WildFly, then deploy from the ear-project directory:

```bash
mvn wildfly:deploy
```

To redeploy after changes:

```bash
mvn wildfly:redeploy
```

To undeploy:

```bash
mvn wildfly:undeploy
```

## Step 7: Test the Application

Once deployed, access the application:

- Homepage: `http://localhost:8080/myapp/`
- Servlet directly: `http://localhost:8080/myapp/greet?name=YourName`

Enter your name in the form on the homepage, and the servlet will call the EJB service and display the greeting on the JSP page.

You can also test the servlet directly in your browser:

```
http://localhost:8080/myapp/greet?name=Developer
```

## Project Structure Summary

Your final project structure with three independent folders:

```
ejb-project/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/example/ejb/
        │       └── GreetingService.java
        └── resources/
            └── META-INF/
                └── beans.xml

war-project/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/example/web/
        │       └── GreetingServlet.java
        └── webapp/
            ├── index.jsp
            ├── greeting.jsp
            └── WEB-INF/
                └── web.xml

ear-project/
└── pom.xml
```

## Making Changes and Rebuilding

When you make changes to the code:

### If you change the EJB project:
```bash
cd ejb-project
mvn clean install
cd ../ear-project
mvn clean package
# Then redeploy the EAR
```

### If you change the WAR project:
```bash
cd war-project
mvn clean install
cd ../ear-project
mvn clean package
# Then redeploy the EAR
```

### If you change both:
```bash
cd ejb-project
mvn clean install
cd ../war-project
mvn clean install
cd ../ear-project
mvn clean package
# Then redeploy the EAR
```

## Build Script (Optional)

Create a `build-all.sh` script in the parent directory of all three projects:

```bash
#!/bin/bash

echo "Building EJB project..."
cd ejb-project
mvn clean install
if [ $? -ne 0 ]; then
    echo "EJB build failed!"
    exit 1
fi

echo "Building WAR project..."
cd ../war-project
mvn clean install
if [ $? -ne 0 ]; then
    echo "WAR build failed!"
    exit 1
fi

echo "Building EAR project..."
cd ../ear-project
mvn clean package
if [ $? -ne 0 ]; then
    echo "EAR build failed!"
    exit 1
fi

echo "All builds completed successfully!"
echo "EAR file location: ear-project/target/ear-project-1.0-SNAPSHOT.ear"
```

Make it executable:

```bash
chmod +x build-all.sh
```

Run it:

```bash
./build-all.sh
```

## Troubleshooting

**Module Not Found**: If the EAR project can't find the EJB or WAR artifacts, ensure you've run `mvn install` on those projects first to add them to your local Maven repository.

**ClassNotFoundException**: Make sure the EJB dependency in the WAR project has `<scope>provided</scope>` since the EJB classes will be available in the EAR.

**Port Already in Use**: If port 8080 is already in use, modify WildFly's port in `standalone/configuration/standalone.xml`.

**Deployment Fails**: Check WildFly logs in `standalone/log/server.log` for detailed error messages.

**Context Root Issues**: The context root is configured in the EAR project's pom.xml. Change `<contextRoot>/myapp</contextRoot>` to your preferred path.

## Next Steps

You now have a working Jakarta EE 10 application with independent EJB, WAR, and EAR projects. You can extend it by:
- Adding JPA entities and database connectivity to the EJB project
- Implementing additional EJB services (Singleton, Message-Driven Beans)
- Creating more REST endpoints or JSF pages in the WAR project
- Adding shared libraries in the EAR's lib directory
- Implementing security with Jakarta Security
- Adding Jakarta Messaging for asynchronous processing


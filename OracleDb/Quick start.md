# Oracle Database Notes for SDE 1

## Table of Contents

1. [Database Fundamentals](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#database-fundamentals)
2. [SQL Language Categories](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#sql-language-categories)
3. [Data Manipulation Language (DML)](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#data-manipulation-language-dml)
4. [Data Definition Language (DDL)](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#data-definition-language-ddl)
5. [Data Control Language (DCL)](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#data-control-language-dcl)
6. [Querying Data](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#querying-data)
7. [Joins](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#joins)
8. [Subqueries](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#subqueries)
9. [Aggregate Functions](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#aggregate-functions)
10. [String Functions](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#string-functions)
11. [Date and Time Functions](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#date-and-time-functions)
12. [Conditional Expressions](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#conditional-expressions)
13. [Set Operations](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#set-operations)
14. [Transaction Control](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#transaction-control)
15. [Oracle-Specific Functions](https://claude.ai/chat/a1972d82-cd15-41d0-9e3c-36c0e2f1cd1f#oracle-specific-functions)

---

## Database Fundamentals

### What is a Database?

A database is an organized collection of structured information stored electronically in a computer system. Oracle Database is a relational database management system (RDBMS) that uses SQL (Structured Query Language) for data manipulation.

### Key Concepts

- **Table**: A collection of related data entries consisting of rows and columns
- **Row (Record)**: A single entry in a table
- **Column (Field)**: A specific attribute of data in a table
- **Primary Key**: A unique identifier for each row in a table
- **Foreign Key**: A field that links to the primary key of another table

---

## SQL Language Categories

SQL commands are categorized into different types based on their functionality:

### 1. DML (Data Manipulation Language)

- Used to manipulate data within existing database objects
- Commands: `SELECT`, `INSERT`, `UPDATE`, `DELETE`

### 2. DDL (Data Definition Language)

- Used to define and manage database structures
- Commands: `CREATE`, `ALTER`, `DROP`, `TRUNCATE`

### 3. DCL (Data Control Language)

- Used to control access to data
- Commands: `GRANT`, `REVOKE`

### 4. TCL (Transaction Control Language)

- Used to manage transactions
- Commands: `COMMIT`, `ROLLBACK`, `SAVEPOINT`

---

## Data Manipulation Language (DML)

### SELECT - Retrieving Data

**Theory**: The SELECT statement is used to query and retrieve data from one or more tables.

```sql
-- Basic syntax
SELECT column1, column2, ...
FROM table_name;

-- Select all columns
SELECT * FROM employees;

-- Select specific columns
SELECT first_name, last_name, salary
FROM employees;

-- Select with alias
SELECT first_name AS "First Name", 
       last_name AS "Last Name"
FROM employees;
```

### INSERT - Adding Data

**Theory**: The INSERT statement adds new records to a table.

```sql
-- Insert single row
INSERT INTO employees (employee_id, first_name, last_name, salary)
VALUES (101, 'John', 'Doe', 50000);

-- Insert multiple rows
INSERT INTO employees (employee_id, first_name, last_name, salary)
VALUES 
    (102, 'Jane', 'Smith', 55000),
    (103, 'Bob', 'Johnson', 48000);

-- Insert from another table
INSERT INTO employees_backup
SELECT * FROM employees WHERE department_id = 10;
```

### UPDATE - Modifying Data

**Theory**: The UPDATE statement modifies existing records in a table.

```sql
-- Update single column
UPDATE employees 
SET salary = 60000 
WHERE employee_id = 101;

-- Update multiple columns
UPDATE employees 
SET salary = 65000, 
    department_id = 20 
WHERE employee_id = 101;

-- Update with condition
UPDATE employees 
SET salary = salary * 1.1 
WHERE department_id = 10;
```

### DELETE - Removing Data

**Theory**: The DELETE statement removes records from a table.

```sql
-- Delete specific records
DELETE FROM employees 
WHERE employee_id = 101;

-- Delete with condition
DELETE FROM employees 
WHERE salary < 30000;

-- Delete all records (use with caution)
DELETE FROM employees;
```

---

## Data Definition Language (DDL)

### CREATE - Creating Objects

**Theory**: CREATE statement is used to create database objects like tables, indexes, views.

```sql
-- Create table with constraints
CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE,
    salary NUMBER(8,2) CHECK (salary > 0),
    hire_date DATE DEFAULT SYSDATE,
    department_id NUMBER,
    CONSTRAINT fk_dept FOREIGN KEY (department_id) 
        REFERENCES departments(department_id)
);

-- Create index
CREATE INDEX idx_emp_last_name ON employees(last_name);

-- Create view
CREATE VIEW high_salary_emp AS
SELECT employee_id, first_name, last_name, salary
FROM employees
WHERE salary > 75000;
```

### ALTER - Modifying Structure

**Theory**: ALTER statement modifies existing database objects.

```sql
-- Add column
ALTER TABLE employees 
ADD (phone_number VARCHAR2(20));

-- Modify column
ALTER TABLE employees 
MODIFY (salary NUMBER(10,2));

-- Drop column
ALTER TABLE employees 
DROP COLUMN phone_number;

-- Add constraint
ALTER TABLE employees 
ADD CONSTRAINT chk_salary CHECK (salary > 0);

-- Drop constraint
ALTER TABLE employees 
DROP CONSTRAINT chk_salary;
```

### DROP - Removing Objects

**Theory**: DROP statement permanently removes database objects.

```sql
-- Drop table
DROP TABLE employees;

-- Drop index
DROP INDEX idx_emp_last_name;

-- Drop view
DROP VIEW high_salary_emp;
```

### TRUNCATE - Removing All Data

**Theory**: TRUNCATE removes all rows from a table quickly without logging individual row deletions.

```sql
-- Truncate table (faster than DELETE, cannot be rolled back)
TRUNCATE TABLE employees;
```

---

## Data Control Language (DCL)

### GRANT - Giving Permissions

**Theory**: GRANT statement provides specific privileges to users or roles.

```sql
-- Grant select permission
GRANT SELECT ON employees TO user1;

-- Grant multiple permissions
GRANT SELECT, INSERT, UPDATE ON employees TO user1;

-- Grant all permissions
GRANT ALL ON employees TO user1;
```

### REVOKE - Removing Permissions

**Theory**: REVOKE statement removes previously granted privileges.

```sql
-- Revoke specific permission
REVOKE SELECT ON employees FROM user1;

-- Revoke multiple permissions
REVOKE SELECT, INSERT ON employees FROM user1;
```

---

## Querying Data

### WHERE Clause - Filtering Data

**Theory**: WHERE clause filters records based on specified conditions.

```sql
-- Basic conditions
SELECT * FROM employees WHERE salary > 50000;
SELECT * FROM employees WHERE department_id = 10;
SELECT * FROM employees WHERE first_name = 'John';

-- Multiple conditions
SELECT * FROM employees 
WHERE salary > 50000 AND department_id = 10;

SELECT * FROM employees 
WHERE salary > 50000 OR department_id = 20;

-- NOT operator
SELECT * FROM employees WHERE NOT department_id = 10;

-- Inequality operator
SELECT * FROM employees WHERE salary <> 50000;
```

### ORDER BY - Sorting Results

**Theory**: ORDER BY clause sorts the result set in ascending or descending order.

```sql
-- Sort ascending (default)
SELECT * FROM employees ORDER BY salary;

-- Sort descending
SELECT * FROM employees ORDER BY salary DESC;

-- Sort by multiple columns
SELECT * FROM employees 
ORDER BY department_id, salary DESC;
```

### GROUP BY - Grouping Data

**Theory**: GROUP BY clause groups rows with the same values and is often used with aggregate functions.

```sql
-- Group by single column
SELECT department_id, COUNT(*) as emp_count
FROM employees
GROUP BY department_id;

-- Group by multiple columns
SELECT department_id, job_id, AVG(salary) as avg_salary
FROM employees
GROUP BY department_id, job_id;
```

### HAVING - Filtering Groups

**Theory**: HAVING clause filters grouped results (used after GROUP BY).

```sql
-- Filter grouped results
SELECT department_id, COUNT(*) as emp_count
FROM employees
GROUP BY department_id
HAVING COUNT(*) > 5;

-- Multiple conditions in HAVING
SELECT department_id, AVG(salary) as avg_sal
FROM employees
GROUP BY department_id
HAVING AVG(salary) > 50000 AND COUNT(*) > 3;
```

---

## Joins

### Theory

A JOIN combines rows from two or more tables based on a related column. Oracle supports various types of joins.

### INNER JOIN

**Theory**: Returns only rows that have matching values in both tables.

```sql
-- Basic INNER JOIN
SELECT e.first_name, e.last_name, d.department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.department_id;

-- Alternative syntax (Oracle traditional)
SELECT e.first_name, e.last_name, d.department_name
FROM employees e, departments d
WHERE e.department_id = d.department_id;
```

### LEFT OUTER JOIN

**Theory**: Returns all rows from the left table and matching rows from the right table.

```sql
SELECT e.first_name, e.last_name, d.department_name
FROM employees e
LEFT OUTER JOIN departments d ON e.department_id = d.department_id;
```

### RIGHT OUTER JOIN

**Theory**: Returns all rows from the right table and matching rows from the left table.

```sql
SELECT e.first_name, e.last_name, d.department_name
FROM employees e
RIGHT OUTER JOIN departments d ON e.department_id = d.department_id;
```

### FULL OUTER JOIN

**Theory**: Returns rows when there's a match in either table.

```sql
SELECT e.first_name, e.last_name, d.department_name
FROM employees e
FULL OUTER JOIN departments d ON e.department_id = d.department_id;
```

### CROSS JOIN

**Theory**: Returns the Cartesian product of both tables (every row from first table with every row from second table).

```sql
SELECT e.first_name, d.department_name
FROM employees e
CROSS JOIN departments d;
```

### SELF JOIN

**Theory**: Joins a table with itself.

```sql
-- Find employees and their managers
SELECT e.first_name AS employee, m.first_name AS manager
FROM employees e, employees m
WHERE e.manager_id = m.employee_id;
```

---

## Subqueries

### Theory

A subquery is a query nested inside another query. It can be used in SELECT, FROM, WHERE, and HAVING clauses.

### IN Operator with Subquery

```sql
-- Find employees in specific departments
SELECT * FROM employees
WHERE department_id IN (
    SELECT department_id FROM departments 
    WHERE location_id = 1700
);
```

### ANY Operator

```sql
-- Find employees with salary greater than any employee in department 10
SELECT * FROM employees
WHERE salary > ANY (
    SELECT salary FROM employees WHERE department_id = 10
);
```

### ALL Operator

```sql
-- Find employees with salary greater than all employees in department 10
SELECT * FROM employees
WHERE salary > ALL (
    SELECT salary FROM employees WHERE department_id = 10
);
```

### EXISTS

```sql
-- Find departments that have employees
SELECT * FROM departments d
WHERE EXISTS (
    SELECT 1 FROM employees e 
    WHERE e.department_id = d.department_id
);
```

---

## Aggregate Functions

### Theory

Aggregate functions perform calculations on a set of values and return a single value.

```sql
-- COUNT - Count number of rows
SELECT COUNT(*) FROM employees;
SELECT COUNT(commission_pct) FROM employees; -- Excludes NULLs

-- SUM - Calculate total
SELECT SUM(salary) FROM employees;

-- AVG - Calculate average
SELECT AVG(salary) FROM employees;

-- MIN - Find minimum value
SELECT MIN(salary) FROM employees;

-- MAX - Find maximum value
SELECT MAX(salary) FROM employees;

-- Combined with GROUP BY
SELECT department_id, 
       COUNT(*) as emp_count,
       AVG(salary) as avg_salary,
       SUM(salary) as total_salary
FROM employees
GROUP BY department_id;
```

---

## String Functions

### Theory

String functions are used to manipulate character data.

```sql
-- CONCAT - Concatenate strings
SELECT CONCAT(first_name, last_name) AS full_name FROM employees;
SELECT first_name || ' ' || last_name AS full_name FROM employees; -- Oracle concatenation

-- SUBSTR - Extract substring
SELECT SUBSTR(first_name, 1, 3) AS first_three FROM employees;

-- LENGTH - Get string length
SELECT first_name, LENGTH(first_name) AS name_length FROM employees;

-- UPPER/LOWER - Change case
SELECT UPPER(first_name) AS upper_name FROM employees;
SELECT LOWER(first_name) AS lower_name FROM employees;

-- TRIM - Remove spaces
SELECT TRIM(' ' FROM first_name) AS trimmed_name FROM employees;

-- REPLACE - Replace characters
SELECT REPLACE(first_name, 'a', 'A') AS replaced_name FROM employees;

-- INSTR - Find position of substring
SELECT INSTR(first_name, 'a') AS position FROM employees;
```

---

## Date and Time Functions

### Theory

Oracle provides extensive date and time functions for temporal data manipulation.

```sql
-- Current date and time
SELECT SYSDATE FROM dual; -- Current date and time
SELECT CURRENT_DATE FROM dual;
SELECT CURRENT_TIMESTAMP FROM dual;

-- Date arithmetic
SELECT hire_date, 
       hire_date + 30 AS thirty_days_later,
       hire_date - 30 AS thirty_days_earlier
FROM employees;

-- EXTRACT - Get specific parts of date
SELECT EXTRACT(YEAR FROM hire_date) AS hire_year FROM employees;
SELECT EXTRACT(MONTH FROM hire_date) AS hire_month FROM employees;

-- ADD_MONTHS - Add months to date
SELECT ADD_MONTHS(hire_date, 6) AS six_months_later FROM employees;

-- MONTHS_BETWEEN - Calculate months between dates
SELECT MONTHS_BETWEEN(SYSDATE, hire_date) AS months_employed FROM employees;

-- TO_CHAR - Format dates
SELECT TO_CHAR(hire_date, 'DD-MON-YYYY') AS formatted_date FROM employees;
SELECT TO_CHAR(hire_date, 'Day, Month DD, YYYY') AS full_date FROM employees;

-- TO_DATE - Convert string to date
SELECT TO_DATE('25-DEC-2023', 'DD-MON-YYYY') AS christmas FROM dual;

-- TRUNC - Truncate date
SELECT TRUNC(hire_date, 'MONTH') AS first_of_month FROM employees;
SELECT TRUNC(hire_date, 'YEAR') AS first_of_year FROM employees;
```

---

## Conditional Expressions

### CASE Statement

**Theory**: CASE provides conditional logic similar to if-else statements.

```sql
-- Simple CASE
SELECT first_name, salary,
    CASE 
        WHEN salary > 80000 THEN 'High'
        WHEN salary BETWEEN 50000 AND 80000 THEN 'Medium'
        ELSE 'Low'
    END AS salary_grade
FROM employees;

-- Searched CASE with multiple conditions
SELECT first_name, salary, department_id,
    CASE 
        WHEN salary > 80000 AND department_id = 90 THEN 'Executive High'
        WHEN salary > 80000 THEN 'High'
        WHEN salary > 50000 THEN 'Medium'
        ELSE 'Entry Level'
    END AS employee_category
FROM employees;
```

### BETWEEN Operator

**Theory**: BETWEEN checks if a value lies within a range (inclusive).

```sql
-- Salary range
SELECT * FROM employees 
WHERE salary BETWEEN 40000 AND 70000;

-- Date range
SELECT * FROM employees 
WHERE hire_date BETWEEN TO_DATE('01-JAN-2020', 'DD-MON-YYYY') 
                    AND TO_DATE('31-DEC-2021', 'DD-MON-YYYY');

-- NOT BETWEEN
SELECT * FROM employees 
WHERE salary NOT BETWEEN 40000 AND 70000;
```

---

## Set Operations

### Theory

Set operations combine results from multiple queries.

### UNION

**Theory**: Combines results from two queries, removing duplicates.

```sql
SELECT first_name, last_name FROM employees WHERE department_id = 10
UNION
SELECT first_name, last_name FROM employees WHERE department_id = 20;

-- UNION ALL (includes duplicates)
SELECT first_name, last_name FROM employees WHERE department_id = 10
UNION ALL
SELECT first_name, last_name FROM employees WHERE department_id = 20;
```

### INTERSECT

**Theory**: Returns common rows from both queries.

```sql
SELECT employee_id FROM employees WHERE salary > 50000
INTERSECT
SELECT employee_id FROM employees WHERE department_id = 10;
```

### MINUS

**Theory**: Returns rows from first query that are not in second query.

```sql
SELECT employee_id FROM employees WHERE department_id = 10
MINUS
SELECT employee_id FROM employees WHERE salary > 60000;
```

---

## Transaction Control

### Theory

Transactions ensure data consistency and allow you to group multiple DML operations.

```sql
-- Start transaction (implicit in Oracle)
BEGIN
    -- DML operations
    INSERT INTO employees (employee_id, first_name, last_name) 
    VALUES (201, 'Alice', 'Wonder');
    
    UPDATE employees SET salary = salary * 1.1 WHERE department_id = 10;
    
    DELETE FROM employees WHERE employee_id = 100;
    
    -- Save changes
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        -- Undo changes if error occurs
        ROLLBACK;
END;
/

-- Using SAVEPOINT
BEGIN
    INSERT INTO employees VALUES (202, 'Bob', 'Builder', 45000);
    SAVEPOINT sp1;
    
    UPDATE employees SET salary = 50000 WHERE employee_id = 202;
    SAVEPOINT sp2;
    
    DELETE FROM employees WHERE employee_id = 202;
    
    -- Rollback to specific savepoint
    ROLLBACK TO sp2; -- This will undo the DELETE but keep the UPDATE
    
    COMMIT;
END;
/
```

---

## Oracle-Specific Functions

### NVL Function

**Theory**: Replaces NULL with a specified value.

```sql
-- Replace NULL commission with 0
SELECT first_name, NVL(commission_pct, 0) AS commission
FROM employees;

-- Replace NULL with string
SELECT first_name, NVL(manager_id, 'No Manager') AS manager
FROM employees;
```

### NVL2 Function

**Theory**: Returns different values based on whether expression is NULL or not.

```sql
-- If commission exists, show 'Has Commission', else 'No Commission'
SELECT first_name, 
       NVL2(commission_pct, 'Has Commission', 'No Commission') AS comm_status
FROM employees;

-- Calculate total compensation
SELECT first_name, salary,
       NVL2(commission_pct, salary + (salary * commission_pct), salary) AS total_comp
FROM employees;
```

### DECODE Function

**Theory**: Oracle's version of CASE statement.

```sql
-- Convert department_id to department name
SELECT first_name,
       DECODE(department_id, 
              10, 'Administration',
              20, 'Marketing', 
              30, 'Purchasing',
              'Other') AS dept_name
FROM employees;
```

### ROWNUM and ROWID

```sql
-- ROWNUM - Assigns sequential numbers to rows
SELECT ROWNUM, first_name, salary
FROM employees
WHERE ROWNUM <= 5; -- Top 5 rows

-- Pagination using ROWNUM
SELECT * FROM (
    SELECT ROWNUM rn, first_name, salary
    FROM employees
    WHERE ROWNUM <= 20
) WHERE rn > 10; -- Rows 11-20

-- ROWID - Unique identifier for each row
SELECT ROWID, first_name FROM employees WHERE ROWNUM = 1;
```

---

## Best Practices for SDE 1

### 1. Query Optimization

```sql
-- Use indexes effectively
CREATE INDEX idx_emp_dept_sal ON employees(department_id, salary);

-- Use EXISTS instead of IN for better performance
SELECT * FROM departments d
WHERE EXISTS (SELECT 1 FROM employees e WHERE e.department_id = d.department_id);
```

### 2. Error Handling

```sql
-- Always handle potential errors in PL/SQL
DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM employees WHERE department_id = 999;
    DBMS_OUTPUT.PUT_LINE('Count: ' || v_count);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No data found');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
```

### 3. Data Integrity

```sql
-- Always use constraints
CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    email VARCHAR2(100) UNIQUE NOT NULL,
    salary NUMBER CHECK (salary > 0),
    hire_date DATE DEFAULT SYSDATE NOT NULL
);
```

### 4. Efficient Joins

```sql
-- Use appropriate join types
-- Use table aliases for readability
SELECT e.first_name, d.department_name, l.city
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN locations l ON d.location_id = l.location_id;
```

---

## PL/SQL Programming

### Theory

PL/SQL (Procedural Language extension to SQL) is Oracle's procedural programming language that combines SQL with procedural constructs. It allows you to write stored procedures, functions, triggers, and packages.

### Key Benefits of PL/SQL:

- **Performance**: Reduces network traffic by executing multiple SQL statements as a single block
- **Error Handling**: Robust exception handling mechanisms
- **Modularity**: Code can be organized into reusable procedures and functions
- **Security**: Controlled access to data through stored procedures

---

### PL/SQL Block Structure

**Theory**: Every PL/SQL program is written in blocks with a specific structure.

```sql
DECLARE
    -- Declaration section (optional)
    -- Variables, constants, cursors, exceptions
BEGIN
    -- Execution section (mandatory)
    -- Program logic and SQL statements
EXCEPTION
    -- Exception handling section (optional)
    -- Error handling code
END;
/
```

### Basic PL/SQL Block Example

```sql
DECLARE
    v_employee_name VARCHAR2(50);
    v_salary NUMBER;
BEGIN
    SELECT first_name, salary
    INTO v_employee_name, v_salary
    FROM employees
    WHERE employee_id = 100;
    
    DBMS_OUTPUT.PUT_LINE('Employee: ' || v_employee_name || 
                        ', Salary: ' || v_salary);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Employee not found');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
```

---

### Variables and Data Types

**Theory**: Variables store data that can be manipulated during program execution.

```sql
DECLARE
    -- Scalar variables
    v_employee_id NUMBER(6);
    v_first_name VARCHAR2(20);
    v_salary NUMBER(8,2);
    v_hire_date DATE;
    v_is_manager BOOLEAN := FALSE;
    
    -- Constants
    c_max_salary CONSTANT NUMBER := 100000;
    c_company_name CONSTANT VARCHAR2(30) := 'Oracle Corp';
    
    -- %TYPE attribute - inherits column data type
    v_emp_name employees.first_name%TYPE;
    v_emp_salary employees.salary%TYPE;
    
    -- %ROWTYPE attribute - inherits entire row structure
    v_employee_rec employees%ROWTYPE;
    
BEGIN
    -- Assign values
    v_employee_id := 100;
    v_first_name := 'John';
    v_salary := 50000;
    v_hire_date := SYSDATE;
    
    -- Using %ROWTYPE
    SELECT * INTO v_employee_rec
    FROM employees
    WHERE employee_id = 100;
    
    DBMS_OUTPUT.PUT_LINE('Employee: ' || v_employee_rec.first_name);
END;
/
```

---

### Control Structures

### IF-THEN-ELSE Statements

**Theory**: Conditional logic to execute different code blocks based on conditions.

```sql
DECLARE
    v_salary NUMBER;
    v_bonus NUMBER;
BEGIN
    SELECT salary INTO v_salary
    FROM employees
    WHERE employee_id = 100;
    
    -- Simple IF-THEN
    IF v_salary > 50000 THEN
        v_bonus := v_salary * 0.1;
    END IF;
    
    -- IF-THEN-ELSE
    IF v_salary > 80000 THEN
        v_bonus := v_salary * 0.15;
    ELSE
        v_bonus := v_salary * 0.05;
    END IF;
    
    -- IF-THEN-ELSIF-ELSE
    IF v_salary > 100000 THEN
        v_bonus := v_salary * 0.2;
    ELSIF v_salary > 75000 THEN
        v_bonus := v_salary * 0.15;
    ELSIF v_salary > 50000 THEN
        v_bonus := v_salary * 0.1;
    ELSE
        v_bonus := v_salary * 0.05;
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('Bonus: ' || v_bonus);
END;
/
```

### CASE Statements

```sql
DECLARE
    v_grade CHAR(1) := 'B';
    v_description VARCHAR2(20);
BEGIN
    -- Simple CASE
    CASE v_grade
        WHEN 'A' THEN v_description := 'Excellent';
        WHEN 'B' THEN v_description := 'Good';
        WHEN 'C' THEN v_description := 'Average';
        ELSE v_description := 'Poor';
    END CASE;
    
    DBMS_OUTPUT.PUT_LINE('Grade: ' || v_grade || ', Description: ' || v_description);
END;
/
```

---

### Loops

### Basic LOOP

**Theory**: Basic loop structure that continues until EXIT condition is met.

```sql
DECLARE
    v_counter NUMBER := 1;
BEGIN
    LOOP
        DBMS_OUTPUT.PUT_LINE('Counter: ' || v_counter);
        v_counter := v_counter + 1;
        
        EXIT WHEN v_counter > 5; -- Exit condition
        -- Alternative: IF v_counter > 5 THEN EXIT; END IF;
    END LOOP;
END;
/
```

### WHILE LOOP

```sql
DECLARE
    v_counter NUMBER := 1;
BEGIN
    WHILE v_counter <= 5 LOOP
        DBMS_OUTPUT.PUT_LINE('While Counter: ' || v_counter);
        v_counter := v_counter + 1;
    END LOOP;
END;
/
```

### FOR LOOP

```sql
BEGIN
    -- Numeric FOR loop
    FOR i IN 1..5 LOOP
        DBMS_OUTPUT.PUT_LINE('For Counter: ' || i);
    END LOOP;
    
    -- Reverse FOR loop
    FOR i IN REVERSE 1..5 LOOP
        DBMS_OUTPUT.PUT_LINE('Reverse Counter: ' || i);
    END LOOP;
END;
/
```

---

### Cursors

**Theory**: Cursors are pointers to SQL result sets that allow row-by-row processing of query results.

### Implicit Cursors

**Theory**: Oracle automatically creates implicit cursors for single-row SELECT INTO statements.

```sql
DECLARE
    v_employee_name VARCHAR2(50);
    v_salary NUMBER;
BEGIN
    SELECT first_name, salary
    INTO v_employee_name, v_salary
    FROM employees
    WHERE employee_id = 100;
    
    -- SQL cursor attributes
    IF SQL%FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Employee found: ' || v_employee_name);
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('Rows processed: ' || SQL%ROWCOUNT);
END;
/
```

### Explicit Cursors

**Theory**: Explicitly declared cursors for processing multiple rows.

```sql
DECLARE
    -- Cursor declaration
    CURSOR emp_cursor IS
        SELECT employee_id, first_name, salary
        FROM employees
        WHERE department_id = 10;
        
    -- Cursor variables
    v_emp_id employees.employee_id%TYPE;
    v_name employees.first_name%TYPE;
    v_salary employees.salary%TYPE;
BEGIN
    -- Open cursor
    OPEN emp_cursor;
    
    -- Fetch data
    LOOP
        FETCH emp_cursor INTO v_emp_id, v_name, v_salary;
        EXIT WHEN emp_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_emp_id || 
                           ', Name: ' || v_name || 
                           ', Salary: ' || v_salary);
    END LOOP;
    
    -- Close cursor
    CLOSE emp_cursor;
END;
/
```

### Cursor FOR Loop

**Theory**: Simplified way to process cursor results.

```sql
DECLARE
    CURSOR emp_cursor IS
        SELECT employee_id, first_name, salary, department_id
        FROM employees
        WHERE salary > 50000;
BEGIN
    FOR emp_rec IN emp_cursor LOOP
        DBMS_OUTPUT.PUT_LINE('Employee: ' || emp_rec.first_name || 
                           ', Salary: ' || emp_rec.salary ||
                           ', Dept: ' || emp_rec.department_id);
    END LOOP;
END;
/
```

### Parameterized Cursors

```sql
DECLARE
    CURSOR emp_cursor(p_dept_id NUMBER, p_min_salary NUMBER) IS
        SELECT employee_id, first_name, salary
        FROM employees
        WHERE department_id = p_dept_id
        AND salary >= p_min_salary;
BEGIN
    FOR emp_rec IN emp_cursor(10, 40000) LOOP
        DBMS_OUTPUT.PUT_LINE('Employee: ' || emp_rec.first_name || 
                           ', Salary: ' || emp_rec.salary);
    END LOOP;
END;
/
```

---

### Exception Handling

**Theory**: Exception handling manages runtime errors gracefully.

### Predefined Exceptions

```sql
DECLARE
    v_employee_name VARCHAR2(50);
    v_salary NUMBER;
BEGIN
    SELECT first_name, salary
    INTO v_employee_name, v_salary
    FROM employees
    WHERE department_id = 999; -- Non-existent department
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No employee found in specified department');
        
    WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.PUT_LINE('More than one employee found');
        
    WHEN VALUE_ERROR THEN
        DBMS_OUTPUT.PUT_LINE('Data type conversion error');
        
    WHEN ZERO_DIVIDE THEN
        DBMS_OUTPUT.PUT_LINE('Division by zero error');
        
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
        DBMS_OUTPUT.PUT_LINE('Error code: ' || SQLCODE);
END;
/
```

### User-Defined Exceptions

```sql
DECLARE
    -- Declare custom exception
    invalid_salary EXCEPTION;
    
    v_salary NUMBER;
BEGIN
    SELECT salary INTO v_salary
    FROM employees
    WHERE employee_id = 100;
    
    -- Raise custom exception
    IF v_salary < 0 THEN
        RAISE invalid_salary;
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('Valid salary: ' || v_salary);
    
EXCEPTION
    WHEN invalid_salary THEN
        DBMS_OUTPUT.PUT_LINE('Error: Salary cannot be negative');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error occurred');
END;
/
```

### RAISE_APPLICATION_ERROR

```sql
DECLARE
    v_salary NUMBER;
BEGIN
    SELECT salary INTO v_salary
    FROM employees
    WHERE employee_id = 100;
    
    IF v_salary > 200000 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Salary exceeds maximum limit of 200000');
    END IF;
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
```

---

### Stored Procedures

**Theory**: Stored procedures are named PL/SQL blocks that can accept parameters and be called from other programs.

### Creating Procedures

```sql
-- Simple procedure without parameters
CREATE OR REPLACE PROCEDURE display_employee_count
IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM employees;
    DBMS_OUTPUT.PUT_LINE('Total employees: ' || v_count);
END display_employee_count;
/

-- Execute procedure
BEGIN
    display_employee_count;
END;
/
```

### Procedures with Parameters

```sql
-- Procedure with IN parameters
CREATE OR REPLACE PROCEDURE increase_salary(
    p_employee_id IN NUMBER,
    p_percentage IN NUMBER
)
IS
    v_current_salary NUMBER;
BEGIN
    -- Get current salary
    SELECT salary INTO v_current_salary
    FROM employees
    WHERE employee_id = p_employee_id;
    
    -- Update salary
    UPDATE employees
    SET salary = salary * (1 + p_percentage/100)
    WHERE employee_id = p_employee_id;
    
    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('Salary increased from ' || v_current_salary || 
                        ' by ' || p_percentage || '%');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_employee_id || ' not found');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating salary: ' || SQLERRM);
END increase_salary;
/

-- Execute with parameters
BEGIN
    increase_salary(100, 10); -- Increase employee 100's salary by 10%
END;
/
```

### Procedures with OUT Parameters

```sql
-- Procedure with OUT parameters
CREATE OR REPLACE PROCEDURE get_employee_details(
    p_employee_id IN NUMBER,
    p_first_name OUT VARCHAR2,
    p_last_name OUT VARCHAR2,
    p_salary OUT NUMBER,
    p_department_name OUT VARCHAR2
)
IS
BEGIN
    SELECT e.first_name, e.last_name, e.salary, d.department_name
    INTO p_first_name, p_last_name, p_salary, p_department_name
    FROM employees e
    JOIN departments d ON e.department_id = d.department_id
    WHERE e.employee_id = p_employee_id;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_first_name := NULL;
        p_last_name := NULL;
        p_salary := NULL;
        p_department_name := NULL;
END get_employee_details;
/

-- Execute procedure with OUT parameters
DECLARE
    v_first_name VARCHAR2(50);
    v_last_name VARCHAR2(50);
    v_salary NUMBER;
    v_dept_name VARCHAR2(50);
BEGIN
    get_employee_details(100, v_first_name, v_last_name, v_salary, v_dept_name);
    
    IF v_first_name IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('Employee: ' || v_first_name || ' ' || v_last_name);
        DBMS_OUTPUT.PUT_LINE('Salary: ' || v_salary);
        DBMS_OUTPUT.PUT_LINE('Department: ' || v_dept_name);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Employee not found');
    END IF;
END;
/
```

---

### Functions

**Theory**: Functions are similar to procedures but must return a value and can be used in SQL statements.

### Creating Functions

```sql
-- Function to calculate annual salary
CREATE OR REPLACE FUNCTION get_annual_salary(p_employee_id NUMBER)
RETURN NUMBER
IS
    v_monthly_salary NUMBER;
    v_annual_salary NUMBER;
BEGIN
    SELECT salary INTO v_monthly_salary
    FROM employees
    WHERE employee_id = p_employee_id;
    
    v_annual_salary := v_monthly_salary * 12;
    
    RETURN v_annual_salary;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
    WHEN OTHERS THEN
        RETURN -1; -- Error indicator
END get_annual_salary;
/

-- Using function in SQL
SELECT employee_id, first_name, salary,
       get_annual_salary(employee_id) AS annual_salary
FROM employees
WHERE department_id = 10;

-- Using function in PL/SQL
DECLARE
    v_annual_sal NUMBER;
BEGIN
    v_annual_sal := get_annual_salary(100);
    DBMS_OUTPUT.PUT_LINE('Annual Salary: ' || v_annual_sal);
END;
/
```

### Function with Complex Logic

```sql
-- Function to determine employee grade
CREATE OR REPLACE FUNCTION get_employee_grade(p_employee_id NUMBER)
RETURN VARCHAR2
IS
    v_salary NUMBER;
    v_years_of_service NUMBER;
    v_grade VARCHAR2(10);
BEGIN
    SELECT salary, ROUND(MONTHS_BETWEEN(SYSDATE, hire_date)/12)
    INTO v_salary, v_years_of_service
    FROM employees
    WHERE employee_id = p_employee_id;
    
    IF v_salary > 80000 AND v_years_of_service > 5 THEN
        v_grade := 'Senior';
    ELSIF v_salary > 50000 AND v_years_of_service > 2 THEN
        v_grade := 'Mid-Level';
    ELSE
        v_grade := 'Junior';
    END IF;
    
    RETURN v_grade;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'Unknown';
END get_employee_grade;
/
```

---

### Packages

**Theory**: Packages group related procedures, functions, variables, and other constructs into a single unit.

### Package Specification

```sql
-- Package specification (header)
CREATE OR REPLACE PACKAGE employee_pkg
IS
    -- Public constants
    c_max_salary CONSTANT NUMBER := 200000;
    
    -- Public variables
    g_total_employees NUMBER;
    
    -- Public procedure declarations
    PROCEDURE hire_employee(
        p_first_name VARCHAR2,
        p_last_name VARCHAR2,
        p_salary NUMBER,
        p_department_id NUMBER
    );
    
    PROCEDURE fire_employee(p_employee_id NUMBER);
    
    -- Public function declarations
    FUNCTION get_department_average_salary(p_department_id NUMBER) 
        RETURN NUMBER;
        
    FUNCTION employee_exists(p_employee_id NUMBER) 
        RETURN BOOLEAN;
END employee_pkg;
/
```

### Package Body

```sql
-- Package body (implementation)
CREATE OR REPLACE PACKAGE BODY employee_pkg
IS
    -- Private procedure (not visible outside package)
    PROCEDURE log_activity(p_message VARCHAR2)
    IS
    BEGIN
        INSERT INTO activity_log (log_date, message)
        VALUES (SYSDATE, p_message);
    END log_activity;
    
    -- Public procedure implementation
    PROCEDURE hire_employee(
        p_first_name VARCHAR2,
        p_last_name VARCHAR2,
        p_salary NUMBER,
        p_department_id NUMBER
    )
    IS
        v_employee_id NUMBER;
    BEGIN
        -- Validate salary
        IF p_salary > c_max_salary THEN
            RAISE_APPLICATION_ERROR(-20001, 'Salary exceeds maximum limit');
        END IF;
        
        -- Get next employee ID
        SELECT employees_seq.NEXTVAL INTO v_employee_id FROM dual;
        
        -- Insert new employee
        INSERT INTO employees (employee_id, first_name, last_name, salary, 
                             department_id, hire_date)
        VALUES (v_employee_id, p_first_name, p_last_name, p_salary, 
                p_department_id, SYSDATE);
                
        -- Log activity
        log_activity('Employee hired: ' || p_first_name || ' ' || p_last_name);
        
        COMMIT;
        
        DBMS_OUTPUT.PUT_LINE('Employee hired successfully with ID: ' || v_employee_id);
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error hiring employee: ' || SQLERRM);
    END hire_employee;
    
    -- Public function implementation
    FUNCTION get_department_average_salary(p_department_id NUMBER) 
        RETURN NUMBER
    IS
        v_avg_salary NUMBER;
    BEGIN
        SELECT AVG(salary) INTO v_avg_salary
        FROM employees
        WHERE department_id = p_department_id;
        
        RETURN NVL(v_avg_salary, 0);
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END get_department_average_salary;
    
    FUNCTION employee_exists(p_employee_id NUMBER) 
        RETURN BOOLEAN
    IS
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count
        FROM employees
        WHERE employee_id = p_employee_id;
        
        RETURN (v_count > 0);
    END employee_exists;
    
    -- Package initialization (runs once when package is first loaded)
BEGIN
    SELECT COUNT(*) INTO g_total_employees FROM employees;
    DBMS_OUTPUT.PUT_LINE('Employee package initialized. Total employees: ' || g_total_employees);
END employee_pkg;
/
```

### Using Packages

```sql
-- Using package components
DECLARE
    v_avg_salary NUMBER;
    v_exists BOOLEAN;
BEGIN
    -- Call package procedure
    employee_pkg.hire_employee('John', 'Doe', 55000, 10);
    
    -- Call package function
    v_avg_salary := employee_pkg.get_department_average_salary(10);
    DBMS_OUTPUT.PUT_LINE('Average salary in dept 10: ' || v_avg_salary);
    
    -- Access package variable
    DBMS_OUTPUT.PUT_LINE('Total employees: ' || employee_pkg.g_total_employees);
    
    -- Use package function in conditional
    v_exists := employee_pkg.employee_exists(100);
    IF v_exists THEN
        DBMS_OUTPUT.PUT_LINE('Employee 100 exists');
    END IF;
END;
/
```

---

### Triggers

**Theory**: Triggers are special stored procedures that execute automatically in response to database events.

### DML Triggers

```sql
-- BEFORE INSERT trigger
CREATE OR REPLACE TRIGGER trg_employee_audit
    BEFORE INSERT OR UPDATE ON employees
    FOR EACH ROW
BEGIN
    -- Log the change
    IF INSERTING THEN
        INSERT INTO employee_audit (employee_id, action, action_date, old_salary, new_salary)
        VALUES (:NEW.employee_id, 'INSERT', SYSDATE, NULL, :NEW.salary);
    ELSIF UPDATING THEN
        INSERT INTO employee_audit (employee_id, action, action_date, old_salary, new_salary)
        VALUES (:NEW.employee_id, 'UPDATE', SYSDATE, :OLD.salary, :NEW.salary);
    END IF;
    
    -- Validate salary increase
    IF UPDATING THEN
        IF :NEW.salary < :OLD.salary THEN
            RAISE_APPLICATION_ERROR(-20002, 'Salary cannot be decreased');
        END IF;
        
        IF :NEW.salary > :OLD.salary * 1.5 THEN
            RAISE_APPLICATION_ERROR(-20003, 'Salary increase cannot exceed 50%');
        END IF;
    END IF;
    
    -- Set audit fields
    :NEW.last_modified_date := SYSDATE;
    :NEW.last_modified_by := USER;
END;
/

-- AFTER DELETE trigger
CREATE OR REPLACE TRIGGER trg_employee_delete_audit
    AFTER DELETE ON employees
    FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, action, action_date, old_salary, new_salary)
    VALUES (:OLD.employee_id, 'DELETE', SYSDATE, :OLD.salary, NULL);
END;
/
```

### Statement-Level Triggers

```sql
-- Statement-level trigger
CREATE OR REPLACE TRIGGER trg_prevent_weekend_changes
    BEFORE INSERT OR UPDATE OR DELETE ON employees
BEGIN
    IF TO_CHAR(SYSDATE, 'DY') IN ('SAT', 'SUN') THEN
        RAISE_APPLICATION_ERROR(-20004, 'No employee changes allowed on weekends');
    END IF;
END;
/
```

---

### Collections

**Theory**: Collections are ordered groups of elements of the same data type.

### Associative Arrays (Index-by Tables)

```sql
DECLARE
    TYPE salary_table_type IS TABLE OF NUMBER INDEX BY VARCHAR2(30);
    salary_table salary_table_type;
    
    v_employee_name VARCHAR2(30);
BEGIN
    -- Populate the collection
    salary_table('John Doe') := 50000;
    salary_table('Jane Smith') := 60000;
    salary_table('Bob Johnson') := 55000;
    
    -- Access elements
    DBMS_OUTPUT.PUT_LINE('John Doe salary: ' || salary_table('John Doe'));
    
    -- Iterate through collection
    v_employee_name := salary_table.FIRST;
    WHILE v_employee_name IS NOT NULL LOOP
        DBMS_OUTPUT.PUT_LINE(v_employee_name || ': ' || salary_table(v_employee_name));
        v_employee_name := salary_table.NEXT(v_employee_name);
    END LOOP;
END;
/
```

### Nested Tables

```sql
DECLARE
    TYPE number_table_type IS TABLE OF NUMBER;
    number_table number_table_type;
BEGIN
    -- Initialize the collection
    number_table := number_table_type();
    
    -- Add elements
    FOR i IN 1..5 LOOP
        number_table.EXTEND;
        number_table(i) := i * 10;
    END LOOP;
    
    -- Display elements
    FOR i IN 1..number_table.COUNT LOOP
        DBMS_OUTPUT.PUT_LINE('Element ' || i || ': ' || number_table(i));
    END LOOP;
END;
/
```

---

### Best Practices for PL/SQL (SDE 1 Level)

### 1. Error Handling

```sql
-- Always include proper exception handling
CREATE OR REPLACE PROCEDURE safe_update_salary(
    p_employee_id NUMBER,
    p_new_salary NUMBER
)
IS
BEGIN
    UPDATE employees
    SET salary = p_new_salary
    WHERE employee_id = p_employee_id;
    
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Employee ID not found: ' || p_employee_id);
    END IF;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE; -- Re-raise the exception
END;
/
```

### 2. Use Cursors Efficiently

```sql
-- Use cursor attributes to check status
DECLARE
    CURSOR emp_cursor IS SELECT employee_id, salary FROM employees;
BEGIN
    FOR emp_rec IN emp_cursor LOOP
        -- Process each employee
        UPDATE employees 
        SET salary = salary * 1.1 
        WHERE employee_id = emp_rec.employee_id;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('Updated ' || SQL%ROWCOUNT || ' employees');
    COMMIT;
END;
/
```

### 3. Bulk Operations for Performance

```sql
-- Use BULK COLLECT for better performance
DECLARE
    TYPE emp_id_array IS TABLE OF employees.employee_id%TYPE;
    TYPE salary_array IS TABLE OF employees.salary%TYPE;
    
    v_emp_ids emp_id_array;
    v_salaries salary_array;
BEGIN
    SELECT employee_id, salary
    BULK COLLECT INTO v_emp_ids, v_salaries
    FROM employees
    WHERE department_id = 10;
    
    FOR i IN 1..v_emp_ids.COUNT LOOP
        -- Process bulk data
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_emp_ids(i) || ', Salary: ' || v_salaries(i));
    END LOOP;
END;
/
```

### 4. Dynamic SQL (When Necessary)

```sql
-- Use EXECUTE IMMEDIATE for dynamic SQL
DECLARE
    v_sql VARCHAR2(1000);
    v_table_name VARCHAR2(30) := 'employees';
    v_count NUMBER;
BEGIN
    v_sql := 'SELECT COUNT(*) FROM ' || v_table_name;
    EXECUTE IMMEDIATE v_sql INTO v_count;
    
    DBMS_OUTPUT.PUT_LINE('Count from ' || v_table_name || ': ' || v_count);
END;
/
```

This comprehensive PL/SQL section covers all essential concepts that an SDE 1 should master for effective database programming and application development in Oracle environments.
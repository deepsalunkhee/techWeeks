

##### Theory

A **JOIN** is used to combine rows from two or more tables based on a related column.  
In Oracle, you can use the standard SQL joins like `INNER JOIN`, `LEFT OUTER JOIN`, `RIGHT OUTER JOIN`, and `FULL OUTER JOIN`.

##### Types of Joins:

- **INNER JOIN**: Returns rows when there's a match in both tables.
    
- **LEFT OUTER JOIN**: Returns all rows from the left table, matched rows from the right.
    
- **RIGHT OUTER JOIN**: Returns all rows from the right table, matched rows from the left.
    
- **FULL OUTER JOIN**: Returns rows when there is a match in one of the tables.
    
- **CROSS JOIN**: Returns Cartesian product of the tables.
    

##### Code Syntax

```sql
-- INNER JOIN
SELECT a.column1, b.column2
FROM table1 a
INNER JOIN table2 b ON a.common_col = b.common_col;

-- LEFT OUTER JOIN
SELECT a.column1, b.column2
FROM table1 a
LEFT OUTER JOIN table2 b ON a.common_col = b.common_col;

-- RIGHT OUTER JOIN
SELECT a.column1, b.column2
FROM table1 a
RIGHT OUTER JOIN table2 b ON a.common_col = b.common_col;

-- FULL OUTER JOIN
SELECT a.column1, b.column2
FROM table1 a
FULL OUTER JOIN table2 b ON a.common_col = b.common_col;
```

##### Example

```sql
-- Tables: employees(emp_id, dept_id), departments(dept_id, dept_name)

SELECT e.emp_id, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;
```

---

#### `<>` Operator

##### Theory

The `<>` operator checks for **inequality**. It is used to compare two values where the condition is **true** if the values are **not equal**.

##### Code Syntax

```sql
SELECT * FROM employees WHERE salary <> 50000;
```

##### Example

```sql
-- Get all employees who are not earning 50000
SELECT emp_name FROM employees WHERE salary <> 50000;
```

---

#### `GROUP BY`

##### Theory

The `GROUP BY` clause groups rows that have the same values in specified columns into summary rows, like `SUM()`, `AVG()`, `COUNT()`, etc.

##### Code Syntax

```sql
SELECT column_name, aggregate_function(column_name)
FROM table_name
GROUP BY column_name;
```

##### Example

```sql
-- Count number of employees in each department
SELECT dept_id, COUNT(*) AS emp_count
FROM employees
GROUP BY dept_id;
```

---

#### `NVL`

##### Theory

The `NVL` function replaces `NULL` with a specified value. Useful for handling `NULL` in calculations.

##### Code Syntax

```sql
NVL(expression, replacement_value)
```

##### Example

```sql
-- Replace NULL salary with 0
SELECT emp_name, NVL(salary, 0) AS salary
FROM employees;
```

---

#### `NVL2`

##### Theory

`NVL2(expr1, expr2, expr3)` returns:

- `expr2` if `expr1` is **not null**
    
- `expr3` if `expr1` is **null**
    

##### Code Syntax

```sql
NVL2(expr1, value_if_not_null, value_if_null)
```

##### Example

```sql
-- Check if commission is given, if yes show 'YES', else 'NO'
SELECT emp_name, NVL2(commission, 'YES', 'NO') AS has_commission
FROM employees;
```

---

#### `CASE`

##### Theory

`CASE` is used for conditional logic similar to `if-else`. It returns a value when a condition is met.

##### Code Syntax

```sql
SELECT column,
  CASE
    WHEN condition1 THEN result1
    WHEN condition2 THEN result2
    ELSE default_result
  END AS alias
FROM table;
```

##### Example

```sql
-- Categorize employees based on salary
SELECT emp_name,
  CASE
    WHEN salary > 80000 THEN 'High'
    WHEN salary BETWEEN 50000 AND 80000 THEN 'Medium'
    ELSE 'Low'
  END AS salary_category
FROM employees;
```

---

#### `BETWEEN`

##### Theory

The `BETWEEN` operator selects values within a range. The range is inclusive.

##### Code Syntax

```sql
SELECT * FROM table
WHERE column BETWEEN value1 AND value2;
```

##### Example

```sql
-- Select employees with salary between 40000 and 70000
SELECT emp_name FROM employees
WHERE salary BETWEEN 40000 AND 70000;
```

---

#### DDL (Data Definition Language)

##### Theory

DDL is used to define and manage **database structures** like tables, indexes, and views.

##### Common DDL Commands

- `CREATE`: To create objects (tables, views, etc.)
    
- `ALTER`: To modify existing structures
    
- `DROP`: To delete objects
    
- `TRUNCATE`: To remove all rows quickly without logging
    

##### Example

```sql
-- Create table
CREATE TABLE students (
  student_id NUMBER,
  student_name VARCHAR2(100)
);

-- Alter table to add column
ALTER TABLE students ADD (dob DATE);

-- Drop table
DROP TABLE students;

-- Truncate table
TRUNCATE TABLE students;
```

---

#### DML (Data Manipulation Language)

##### Theory

DML is used to manipulate data stored in database objects like tables.

##### Common DML Commands

- `INSERT`: To add data
    
- `UPDATE`: To modify data
    
- `DELETE`: To remove data
    
- `MERGE`: UPSERT operation (insert/update)
    

##### Example

```sql
-- Insert data
INSERT INTO students (student_id, student_name) VALUES (1, 'John');

-- Update data
UPDATE students SET student_name = 'Johnny' WHERE student_id = 1;

-- Delete data
DELETE FROM students WHERE student_id = 1;
```

---


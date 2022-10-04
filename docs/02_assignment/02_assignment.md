# Assignment 2 - Category-Partition

- Which functions have you selected for testing and why.
- What is the purpose of each function.
- Step-by-step of the ‘Category-Partition’ algorithm for each function.
- Brief description of the unit tests generated for each category.
- Brief description of the outcome of each unit test and whether any test results in a failure (and why).

## Test 1 - project.Project.pause 

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

---

## Test 2 - project.Project.setSecondsToday

### Why test this function?
*"Never trust user input"*.  
Unit tests not only improve the product quality, but also increases its security.
The test tries to focus on these two aspects and therefore, given that the user can 
modify the `secondsToday` field using the GUI, the question that raises is:
> Is the user able to crash the program with some input? 

### Function Description
```java 
public void setSecondsToday(int secondsToday); 
```
By the name of the function, it's clear that it changes
the value of a variable called `secondsToday`, which handles the value of the column
`Today Time` in the program GUI. 

### Category-Partition
1. **Parameters/Input**:
- `seconds` - the function input, representing the time. 
- `expected` - a variable containing the expected value.
2. **Characteristics of each parameter** :  Given that `seconds` is an integer, it can assume the following values:
- `seconds` is positive
- `seconds` is 0
- `seconds` is negative
3. **Constraints**: The only constraint valid in this test in the maximum value of integer. 
4. **Unit test**: The possible values tested for the `seconds` parameter are:
- `seconds` is maximum positive number valid;
- `seconds` is a positive number higher than 60;
- `seconds` is 60; 
- `seconds` is a positive number less than 60; 
- `seconds` is 0; 
- `seconds` is the minimum negative number valid; 
- `seconds` is a negative number less than -60; 
- `seconds` is -60;
- `seconds` is a negative number higher than -60; 

Since negative numbers are not possible, it's expected that negative numbers are handled
 and are equals to zero. 
### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

```java 
@ParameterizedTest
@MethodSource("genSetSecondsToday")
public void setSecondsTodayTest(int seconds, int expected);
```

The `setSecondsTodayTest` is a `@ParameterizedTest` that receives the input from a
function called `genSetSecondsToday`, which is defined as below: 

```java
public static Stream<Arguments> genSetSecondsToday(); 
```

The test passes for all the given inputs. 
It's important highlight that although the tests don't have a wild variate of inputs, quantity is 
not a synonym of quality: the inputs tests specific cases that approaches scenarios where 
the test might fail. Adding more input indistinctly is not recommended. 

--- 

## Test 3 - project.ProjectTime.parseSeconds

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

--- 

## Test 4 - project.ProjectTime.parseDate

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

--- 

## Test 5 - project.ProjectTableModel.isCellEditable

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

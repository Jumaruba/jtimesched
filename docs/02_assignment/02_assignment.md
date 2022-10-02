# Assignment 2 - Category-Partition

- Which functions have you selected for testing and why.
- What is the purpose of each function.
- Step-by-step of the ‘Category-Partition’ algorithm for each function.
- Brief description of the unit tests generated for each category.
- Brief description of the outcome of each unit test and whether any test results in a failure (and why).

## Test 1 - project.Project.pause 

### Why test this function?
> TODO

### Function Description
```java
public static int parseSeconds(String strTime);
```
Converts a time String in the format 'h+:mm:ss' to the equivalent time in seconds. Throws a `ParseException` if the format is not the expected.

### Category-Partition
1. **Parameters/Input**: `strTime`, a String representing the time
2. **Characteristics of each parameter**: Considering the requirements, the parameter `strTime` must respect a specific time format ('h+:mm:ss') in order for the conversion to work correctly.
3. **Constraints**: Given that the parameter is a String, it can assume infinite value, either respecting the requested format or not. Therefore, for the exceptional behavior we will consider only the cases where `strTime` is null, empty or has another format different from the accepted one. 
Likewise, for the valid cases (when the parameter respects the format) we will take into account the domain of time itself in order to define our corner cases, testing if the conversion works well with time Strings that have seconds, minutes, hours and more hours than a single day.
4.**Unit Tests**: This function only has one parameter (`strTime`), so we are going to test it considering the possible value of this parameter:
- `strTime` is null;
- `strTime`is an empty String;
- `strTime` has a different format than the one expected i.e. "0:90:05", "6-54", "2";
- `strTime` has the expected format (h+:mm:ss):
  - `strTime` is 0 i.e. "0:00:00";
  - `strTime` only has seconds i.e. "0:00:05";
  - `strTime` has minutes and seconds i.e. "0:15:48";
  - `strTime` has at least 1 hour but no more than 24, minutes and seconds i.e. 6:15:48;
  - `strTime` has more than 24 i.e. "36:15:48";

### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

We decided to use one test for each partition that correspond to a valid input and a single test for all the invalid inputs:
- To test all the invalid parameters described above we used a `@ParametrizedTest` with a `@MethodSource` that feeds all the parameters to be tested. As expected, the test passes for all the invalid inputs, throwing a `ParseException`, except when the input is `null`. In that case, the test fails because a a `NullPointerException` is thrown. We believe that this exception should be handled in order to avoid unexpected exceptions.
- All the other tests that test valid parameters we tested in separate `@Test` functions.

---

## Test 2 - project.Project.setSecondsToday

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

--- 

## Test 3 - project.ProjectTime.parseSeconds

### Why test this function?

### Function Description

### Category-Partition

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

# Assignment 2 - Category-Partition

- Which functions have you selected for testing and why.
- What is the purpose of each function.
- Step-by-step of the ‘Category-Partition’ algorithm for each function.
- Brief description of the unit tests generated for each category.
- Brief description of the outcome of each unit test and whether any test results in a failure (and why).

## Test 1 - project.Project.pause 

### Why test this function?


### Function Description
```java

```


### Category-Partition
1. **Parameters/Input**: 
2. **Characteristics of each parameter**: 
3. **Constraints**:
4. **Unit Tests**:

### Unit Tests & Outcome
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
This application allows the user to manually change the overall time of a task and the time spent doing that same task in the current day. When that manually configuration is performed, this function is used to convert the input of the user into the correspondent time in seconds, which is then used to update the state of the application. For this reason, it is crucial that this function handles the user input as expected, either by performing the right conversion when possible or by throwing the expected exception. 

### Function Description
```java
public static int parseSeconds(String strTime) throws ParseException;
```
Converts a time `String` in the format "\d+:[0-5]?\d:[0-5]?\d" (hours:minutes:seconds) to the equivalent time in seconds. Throws a `ParseException` if the format is not the expected.

### Category-Partition
1. **Parameters/Input**: `strTime`, a `String` representing the time.
2. **Characteristics of each parameter**: Considering the requirements, the parameter `strTime` must respect a specific time format "\d+:[0-5]?\d:[0-5]?\d" in order for the conversion to work correctly.
3. **Constraints**: Given that the parameter is a `String`, it can assume infinite values, either respecting the requested format or not. Therefore, for the exceptional behavior we will consider only the cases where `strTime` is null, empty or has another format different from the accepted one. 
Likewise, for the valid cases (when the parameter respects the format) we will take into account the domain of time itself in order to define our corner cases, testing if the conversion works well with time Strings that have seconds, minutes, hours and more hours than a single day.
4.**Input combinations / Tests**: This function only has one parameter (`strTime`), so we are going to test it considering the possible values of this parameter:
  - `strTime` doesn't have the expected format ("\d+:[0-5]?\d:[0-5]?\d"):
    - `strTime` is null;
    - `strTime`is an empty `String`;
    - `strTime` in not null nor an empty `String`, but it doesn't respect the format:
      - `strTime` doesn't respect the seconds domain e.g. "0:07:60";
      - `strTime` doesn't respect the minutes domain e.g. "0:90:05";
      - `strTime` has a seconds value with more than 2 digits e.g. "0:6:054";
      - `strTime` has a minutes value with more than 2 digits e.g. "0:006:54";
      - `strTime` doesn't have hours e.g. "6:54";
      - `strTime` doesn't have hours and minutes e.g. "2".
  - `strTime` has the expected format:
    - `strTime` is 0 i.e. "0:00:00";
    - `strTime` only has seconds e.g. "0:00:05";
    - `strTime` has minutes and seconds e.g. "0:15:48";
    - `strTime` the seconds value has 1 digit e.g. "0:00:5";
    - `strTime` the minutes value has 1 digit e.g. "0:15:8";
    - `strTime` the seconds and minutes values have 1 digit e.g. "0:5:8";
    - `strTime` has at least 1 hour but no more than 24 e.g. 6:15:48;
    - `strTime` has more than 24 hours e.g. "36:15:48".

### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

To test the invalid parameters described above, we used the `@ParametrizedTest` `parseSecondsInvalidTest` with a `@MethodSource` that feeds all the invalid parameters to be tested, mainly `null`, empty string and all the other cases that don't respect the expected format. The test passes for all the invalid inputs, throwing a `ParseException`, except when the input is `null`. In that case, the test fails because a `NullPointerException` is thrown. We believe that this exception should be handled in order to avoid unexpected behavior i.e. by throwing a `ParseException` instead.

Even though the specification considered above does not tolerate formats such as "06:54", we believe that this method could be improved in order to support such cases.

To test the other partitions, we created the `@ParametrizedTest` `parseSecondsValidTest` with a `@MethodSource` that feeds all the valid parameters described above. The test passed successfully for all the valid values, returning the number of seconds that correspond to the `String` parameter.

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

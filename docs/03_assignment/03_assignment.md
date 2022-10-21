# Assignment 3 - Boundary testing

## Test 1 - project.ProjectTime.parseSeconds

### Why test this function?
This application allows the user to manually change the overall time of a task and the time spent doing that same task in the current day. When that manually configuration is performed, this function is used to convert the input of the user into the correspondent time in seconds, which is then used to update the state of the application. For this reason, it is crucial that this function handles the user input as expected, either by performing the right conversion when possible or by throwing the expected exception. 

As checked while executing the software, some fields are not supposed to be
**edited/changed** while the timer is counting: `Time Overall` and `Time Today`.
However, it's still necessary to check if the function works as expected in other scenarios
inside the domain.

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
4. **Input combinations / Tests**: This function only has one parameter (`strTime`), so we are going to test it considering the possible values of this parameter. 
    ![](https://i.imgur.com/PHfTDQ2.png)  
    All the categories correspond to a case where:
    - `strTime` is `null`;
    - `strTime` is empty;
    - `strTime`doesn't respect the expected format;
    - `strTime` has the expected format.  
&emsp;

    To define the categories, we deal with the seconds, minutes and hours like they were different parameters (`seconds`, `minutes` and `hours`). 

    First, if we consider their time domains, we know that:
    - 0 <= `seconds` < 60
    - 0 <= `minutes` < 60
    - 0 <= `hours`
&emsp;

    As we want to make sure this function is able to covert a time `String`, even when it exceeds the duration of a day (24 hours), we will consider the cases where:
    - 0 <= `hours` <= 24
    - 24 < `hours`
&emsp;

    Then, if we take into account the regular expression, we can identify the following conditions:
    - 0 < `seconds.len` < 3 *i.e. we can have "1:2:3" or "1:2:03", but not "1:2:003"*
    - 0 < `minutes.len` < 3 *i.e. we can have "1:2:3" or "1:02:3", but not "1:002:3"*
    - 0 < `hours.len` *i.e. we can have "1:2:3","01:2:3", "001:2:3", ...*
&emsp;

    Finally, we want to test if the expression works without hours, minutes or seconds, that is, if it accepts formats like: "04:05" or "5". From the function's description, we know that this cases should be invalid, which leads us to the following conditions:
    - `hours` component exists *i.e: "4:4" is invalid*
    - `minutes` component exists *i.e: "4" is invalid*
&emsp;

    Having all of these conditions in mind, we started by defining the categories for each "parameter". 
    &nbsp;&nbsp;&nbsp;&nbsp;

    > *Note*: We decided to treat each invalid case separately *i.e.* we don't combine invalid lengths with valid values and vice versa, nor do we combine multiple invalid values, because each format error alone must be enough for the function to fail. Otherwise, the number of tests to perform would be unnecessarily large.

    - **Categories for `seconds`**:
      - Valid:
        - **S1**: `seconds` >= 0 AND `seconds` < 60 AND 0 < `seconds.len` < 3 
      - Invalid:
        - **S2**: `seconds` >= 60
        - **S3**: `seconds` < 0
        - **S4**: `seconds.len` <= 0 (there is no negative length so this category only includes the case where `seconds.len` == 0)
        - **S5**: `seconds.len` >= 3
&emsp;

    - **Categories for `minutes`:**
        - Valid:
          - **M1**: `minutes` >= 0 AND `minutes` < 60 AND 0 < `minutes.len` < 3 
        - Invalid:
          - **M2**: `minutes` >= 60
          - **S3**: `minutes` < 0
          - **M4**: `minutes.len` <= 0 (there is no negative length so this category only includes the case where `minutes.len` == 0)
          - **M5**: `minutes.len` >= 3
          - **M6**: `minutes` component doesn't exist *e.g. "4"*
&emsp;

    - **Categories for `hours`:**
      - Valid:
        - **H1**: `hours` >= 0 AND `hours` <= 24 AND `hours.len` > 0
        - **H2**: `hours` > 24 (`hours.len` is necessarily higher than 0)
      - Invalid:
        - **H3**: `hours` < 0
        - **H4**: `hours.len` <= 0 (there is no negative length so this category only includes the case where `hours.len` == 0)
        - **H5**: `hours` component doesn't exist *e.g. "4:4"*

&nbsp;&nbsp;&nbsp;&nbsp;
: By combining all the categories above, we arrive to the following categories:      
    - **E1**: `strTime` is `null`;  
    - **E2**: `strTime` is an empty `String`;  
    - `strTime` has a valid format:  
      - **E3** (**S1** + **M1** + **H1**):   
        (`seconds` >= 0, `seconds` < 60 AND 0 < `seconds.len` < 3) AND     
        (`minutes` >= 0 AND `minutes` < 60 AND 0 < `minutes.len` < 3 ) AND  
        (`hours` >= 0 AND `hours` <= 24 AND `hours.len` > 0)    
        *e.g. "22:15:48"*   
      - **E4** (**S1** + **M1** + **H2**):  
        (`seconds` >= 0, `seconds` < 60 AND 0 < `seconds.len` < 3) AND  
        (`minutes` >= 0 AND `minutes` < 60 AND 0 < `minutes.len` < 3 ) AND  
        (`hours` > 24)    
        *e.g. "36:15:48"*  
    - `strTime` is not `null` nor empty, but it doesn't respect the format: Any category that results from combining at least one of the categories that represent an invalid format for the `hours`, `minutes` or `seconds` will also represent an invalid format here. Therefore, to reduce the number of tests, we will consider each invalid format separately, as we explained in the note above.  
        - **E5** (**S2**): `seconds` >= 60 *e.g. "0:07:60"*;  
        - **E6** (**M2**): `minutes` >= 60 *e.g. "0:90:05"*  
        - **E7** (**S3**): `seconds` < 0 *e.g. "2:02:-1"*;  
        - **E8** (**M3**): `minutes` < 0 *e.g. "2:&#8203;-1:05"*  
        - **E9** (**H3**): `hours` < 0 *e.g. "-1:09:05"*  
        - **E10** (**S4**): `seconds.len` <= 0 *e.g. "0:07:"* (there is no negative length so this category only includes the case where `seconds.len` == 0)  
        - **E11** (**M4**): `minutes.len` <= 0 *e.g. "0::02"* (there is no negative length so this category only includes the case where `minutes.len` == 0)  
        - **E12** (**H4**): `hours.len` <= 0 *e.g. ":6:54"* (there is no negative length so this category only includes the case where `hours.len` == 0)  
        - **E13** (**S5**): `seconds.len` >= 3 *e.g. "0:6:054"*  
        - **E14** (**M5**): `minutes.len` >= 3 *e.g. "0:007:05"*        
        - **E15** (**M6**): `minutes`component doesn't exist *e.g. "2"*  
        - **E16** (**H5**): `hours` component doesn't exist *e.g. "6:54"*  

### Boundary Analysis
> Note: the category to which each on-point an off-point belongs is identified between parentheses.

**Boundary Analyses for E1**: `strTime` is `null`
- On-point: `null` (**E1**)
- Off-point: empty string (**E2**)
Given that a `null` value, in this case, would be caused by passing an uninitialized String to `strTime`, we consider that the value that is closer to the boundary (`null`) corresponds to passing the most basic initialized String(`new String()`) to `strTime`, which results in an empty String. 
As we are dealing with Strings, even though the condition represents an equality, we only consider one off-point (empty String), as it a subjective analysis. 

**Boundary Analysis for E2**: `strTime` is an empty `String`
- On-point: empty string (**E2**)
- Off-point: "0:0:0" (time is zero) (**E3**)
Based on the logic of the previous boundary analysis (E3), the minimum change that needs to be made in order to create a valid string is to fill the `strTime`with the initial value of the counting seconds.

**Boundary Analysis for E3 (S1 + M1 + H1)**:
Category **E3** combines categories **S1**, **M1** and **H1**, resulting in a logical conjunction of the conditions that characterize a valid `strTime` format. For this reason, we decided to perform the Boundary Analysis on **S1**, **M1** and **H1** individually.
- **Boundary Analysis for S1**: `seconds` >= 0 AND `seconds` < 60 AND 0 < `seconds.len` < 3 
  - `seconds` >= 0
    - On-point: "0:0:0" (**E3**)
    - Off-point: "0:0:-1" (**E7**)
  - `seconds` < 60
    - On-point: "0:0:60" (**E5**)
    - Off-point: "0:0:59" (**E3**)
  - `seconds.len` > 0
    - On-point: "0:0:" (**E10**)
    - Off-point: "0:0:0" (**E3**)
  - `seconds.len` < 3 
    - On-point: "0:0:000" (**E13**)
    - Off-point: "0:0:00" (**E3**)
- **Boundary Analysis for M1**: `minutes` >= 0 AND `minutes` < 60 AND 0 < `minutes.len` < 3 
  - `minutes` >= 0
    - On-point: "0:0:0" (**E3**)
    - Off-point: "0:&#8203;-1:0" (**E8**)
  - `minutes` < 60
    - On-point: "0:60:0" (**E6**)
    - Off-point: "0:59:0" (**E3**)
  - `minutes.len` > 0
    - On-point: "0::0" (**E11**)
    - Off-point: "0:0:0" (**E3**)
  - `minutes.len` < 3 
    - On-point: "0:000:0" (**E14**)
    - Off-point: "0:00:0" (**E3**)
- **Boundary Analysis for H1**: `hours` >= 0 AND `hours` <= 24 AND `hours.len` > 0
  - `hours` >= 0
    - On-point: "0:0:0" (**E3**)
    - Off-point: "-1:0:0" (**E9**)
  - `hours` <= 24
    - On-point: "24:0:0" (**E3**)
    - Off-point: "25:0:0" (**E4**)
  - `hours.len` > 0
    - On-point: "0:0:0" (**E3**)
    - Off-point: ":0:0" (**E12**)
  
**Boundary Analysis for E4 (S1 + M1 + H2)**:
Similarly to the last category, **E4** combines categories **S1**, **M1** and **H2**, which led us to perform the Boundary Analysis on each of this categories individually. As we already performed this analysis for **S1** and **M1**, the only one left is **H2**.
- **Boundary Analysis for H2**: `hours` > 24
  - `hours` > 24
    - On-point: "24:0:0" (**E3**)
    - Off-point: "25:0:0" (**E4**)

**Boundary Analysis for E5**: `seconds` >= 60
- On-point: "0:0:60" (**E5**)
- Off-point: "0:0:59" (**E3**)

**Boundary Analysis for E6**: `minutes` >= 60
- On-point: "0:60:0" (**E6**)
- Off-point: "0:59:0" (**E3**)

**Boundary Analysis for E7**: `seconds` < 0
- On-point: "0:0:0" (**E3**)
- Off-point: "0:0:-1" (**E7**)

**Boundary Analysis for E8**: `minutes` < 0
- On-point: "0:0:0" (**E3**)
- Off-point: "0:&#8203;-1:0" (**E8**)

**Boundary Analysis for E9**: `hours` < 0
- On-point: "0:0:0" (**E3**)
- Off-point: "-1:0:0" (**E9**)

**Boundary Analysis for E10**: `seconds.len` <= 0
- On-point: "0:0:" (**E10**)
- Off-point: "0:0:0" (**E3**)

**Boundary Analysis for E11**: `minutes.len` <= 0
- On-point: "0::0" (**E11**)
- Off-points: "0:0:0" (**E3**)

**Boundary Analysis for E12**: `hours.len` <= 0
- On-point: ":0:0" (**E12**)
- Off-points: "0:0:0" (**E3**)

**Boundary Analysis for E13**: `seconds.len` >= 3
- On-point: "0:0:000" (**E13**)
- Off-point: "0:0:00" (**E3**)

**Boundary Analysis for E14**: `minutes.len` >= 3
- On-point: "0:000:0" (**E14**)
- Off-point: "0:00:0" (**E3**)

**Boundary Analysis for E15**: `minutes` component doesn't exist
- On-point: "0" (**E15**)
- Off-point: "0:0" (**E16**)

**Boundary Analysis for E16**: `hours` component doesn't exist
- On-point: "0:0" (**E16**)
- Off-point: "0:0:0" (**E3**)

### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

**Category-Partition Tests**
To test the invalid parameters described above, we used the `@ParametrizedTest` `parseSecondsInvalidTest` with a `@MethodSource` that feeds all the invalid parameters to be tested, mainly `null`, empty string and all the other cases that don't respect the expected format. The test passes for all the invalid inputs, throwing a `ParseException`, except when the input is `null`. In that case, the test fails because a `NullPointerException` is thrown. We believe that this exception should be handled in order to avoid unexpected behavior i.e. by throwing a `ParseException` instead.

Even though the specification considered above does not tolerate formats such as "06:54" and "2", we believe that this method could be improved in order to support such cases.

To test the other partitions, we created the `@ParametrizedTest` `parseSecondsValidTest` with a `@MethodSource` that feeds all the valid parameters described above. The test passes successfully for all the valid values, returning the number of seconds that correspond to the `String` parameter.

**Boundary Tests**
Similarly, to test the boundaries, mainly the on-points and off-points, we started by excluding the ones that were already tested in the category-partition tests, such as the `null` and empty String. We then divided them in valid and invalid values, that is, we separated the ones that were supposed to make the function work as expected and the ones that were expected to cause a `ParseException`.
After that, we created 2 `@ParametrizedTests`, one for the invalid values - `parseSecondsInvalidBoundariesTest`; and another for the valid values - `parseSecondsValidBoundariesTest`.

The first test uses the `parseSecondsInvalidBoundaries` `@MethodSource` to feed the test function with all the invalid boundaries described above: "0:0:-1", "0:&#8203;-1:0", "-1:0:0", "0:0:60", "0:60:0", "0:0:", "0::0", ":0:0", "0", "0:0", "0:0:000", "0:000:0". As this values were expected to generate a `ParseException`, `assertThrows` was used to verify if the method handled the values correctly.
The second test uses the `parseSecondsIValidBoundaries` `@MethodSource` to feed the test function with all the valid boundaries: "0:0:00", "0:0:59", "0:59:0", "24:0:0", "25:0:0"; and verifies if they generate a return value that matches the correspondent time in seconds.

Both tests passed successfully for all the input values, which means that the method handles the boundaries values correctly. However, as we had already concluded in the category-partition tests, the `null` boundary generates a `NullPointerException` instead of a `ParseException` because it is not directly handled by the function.

---  

## Test 2 - project.ProjectTime.formatSeconds

### Why test this function?
Given the character of the application, whose main feature is to track the time of tasks and projects, it is essential that the functions used to format the time in a human readable way work as expected. Furthermore, this method is used at least 15 times in 5 different classes of the source code: `JTimeSchedFrame`, `TimeCellComponent`, ... and therefore it may generate multiple points of failure if it doesn't work as expected.

### Function Description
```java
public static String formatSeconds(int s);
```
Converts the number of seconds to the equivalent time in hours, minutes and seconds, in the format 'hh:mm:ss'.

### Category-Partition
1. **Parameters/Input**: `s`, the number of seconds;
2. **Characteristics of each parameter**: Given that `s` is an integer, it can assume the following values:
- `s` is positive
- `s` is 0
- `s` is negative
3. **Constraints**: Given that the parameter represents the number of seconds, we can restrict the number of cases to test by having in mind that the expected output must respect the domain of the minutes and seconds. We want the number of seconds to always be less than 60 (1 minute = 60 seconds), and the number of minutes to always be less than 60 (3600 seconds = 60 minutes = 1 hour), so we may test cases where we only have seconds, where we have at least a minute, at least an hour or at least a day. Also, given that negative times are invalid, we only need to test this case with one value as the output should be the same. Thus, the partitions are:
4. **Input combinations / Tests**: Given that we only have a single parameter we only need to test the function for the different possible values of `s`:
- E1: s < 0;
- E2: s >= 0 and s < 60; 
- E3: s >= 60 and s < 3600;
- E4: s >= 3600 and s < 86400;  
- E5: s >= 86400; 

### Boundary testing 

Considering the partitions defined in the previous section, let's define the **on-points** and **off-points** and make sure that these are covered by the tests. 

- {s < 0, s >= 0}: 
	- on-point: 0 (E2)
	- off-point: -1 (E1)
- {s < 60, s >= 60}:
	- on-point: 60 (E3)
	- off-point: 59 (E2)
- {s < 3600, s >= 3600}:
	- on-point: 3600 (E4)
	- off-point: 3599 (E3) 
- {s < 86400, s >= 86400}:
	- on-point: 86400 (E5)
	- off-point: 86399 (E4)

### Unit Tests & Outcome

#### Category-partition Tests
To test the combinations that were identified above, we used the `ParametrizedTest` `formatSecondsTest`, which can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

```java 
@ParameterizedTest
@MethodSource("genFormatSecondsPartition");
public void formatSecondsPartitionTest(int s, String expected);
```

The method `genFormatSecondsPartition` was used as the source of parameters to test, and respective expected return values.
The test passed for all the possible values of `s`, except for the last one (negative numbers). Given that no exception handlers (`try, catch`) are used in the source code to handle a possible exception generated by this function, we were expecting a return value of "0:00:00", considering that 0 is the closest valid value. However, this function fails the test and returns "0:00:-2", which means that it does not handle negative numbers correctly. In this case, we don't expect the input to be negative, however it would be a good practice to handle this case either by throwing an Exception or by returning the value "0:00:00" if we don't want to stop the application on such a case. 

#### Boundary value Tests 
```java
@ParameterizedTest
@MethodSource("genFormatSecondsBoundary")
public void formatSecondsBoundary(int s, String expected);
```

As well as tested in the partition tests, the test fails for negative values, such as -1.

--- 

## Test 3 - project.ProjectTableModel.isCellEditable

### Why test this function?

As checked while executing the software, some fields are not supposed to be **edited/changed** while the timer is counting: `Time Overall` and `Time Today`. However, it's still necessary to check if the function works as expected in other scenarios inside the domain.

### Function Description

```java 
 public boolean isCellEditable(int row, int column); 
```

- The first argument is the row/position of the project inside the `arPrj`.
- The column argument is the identification of the column, which can be:

```java
  COLUMN_ACTION_DELETE=0;
  COLUMN_CHECK=1;
  COLUMN_TITLE=2;
  COLUMN_COLOR=3;
  COLUMN_CREATED=4;
  COLUMN_TIMEOVERALL=5;
  COLUMN_TIMETODAY=6;
  COLUMN_ACTION_STARTPAUSE=7;
  COLUMN_COUNT=8;
```
### Category-Partition

1. **Parameters/Input**:

- `row`;
- `column`;
- `isRunning`;

2. **Characteristics of each parameter**: Let's identify the ranges of each parameter and explain them. 
- `row`: is an integer, thus belongs to the range [-Integer.MIN_VALUE`, `Integer.MAX_VALUE`]. It represents the project position in the rows;
- `column`: is an integer and belongs to the range [-Integer.MIN_VALUE`, `Integer.MAX_VALUE`]. It represents the column identification number;
- `isRunning`: can be true or false. **It's not received by the function**, but we set it at the creation of the `Project` class which is added to the  `ProjectTableModel`. It defines if the counter is running or not; 

3. **Constraints**: 

While the timer is not counting (`isRunning == false`), all the columns are editable except:
 - `COLUMN_ACTION_DELETE` *(col == 0)*; 
 - `COLUMN_ACTION_STARTPAUSE` *(col == 7)*; 
 - `COLUMN_COUNT` *(col == 8)*; 

Therefore, when `isRunning` is false, the constraints that make the function return true are: 
 - `col >= 1 and col <= 6`

If the program is running (`isRunning == true`), however, we noticed that, in the GUI, the following columns are not editable: 
 - `COLUMN_ACTION_DELETE` *(col == 0)*; 
 - `COLUMN_TIMEOVERALL` *(col == 5)*; 
 - `COLUMN_TIMETODAY` *(col == 6)*; 
 - `COLUMN_ACTION_STARTPAUSE` *(col == 7)*, 
 - `COLUMN_COUNT` *(col == 8)*. 
 
Then, when `isRunning` is true, the constraints that makes the function return true are: 
 - `col >= 1 and col <= 4`

Some important notes are: 
- It wasn't possible to know for sure which column `COLUMN_COUNT` refers to in the GUI. Thus, it was considered that this column can't be modified by the user and, for this reason, it's not editable. 
- The columns `COLUMN_ACTION_DELETE` and `COLUMN_ACTION_STARTPAUSE` are not editable, since these columns trigger control actions and are not related to personalization. Therefore, they are considered not editable. 

To test the function with some consistency, the `row` parameter needs to be set to a fixed value, since all tests will be executed against a specific `row` position.  
However, some values of `row` should not be accepted, should not crash the program and must return false: negative values and a `row` position that doesn't exist. Thus, given an arbitrary number *n* of *projects* available in the program, we have the following constraints over `row`: 
- `row > -1 and row < n` 

4. **Input combination/ Unit test**: 
Let's analyse the partitions of each input: 
- `isRunning`: false 
	- E1: col < 1;
	- E2: col >= 1 and col <= 6;
	- E3: col > 6; 
- `isRunning`: true
	- E4: col < 1;
	- E5: col >= 1 and col <= 4; 
	- E6: col > 4; 

- `row`, with n == 1: 
	- E7: row <= -1; 
	- E8: row > - 1 and row < n; 
	- E9: row >= n; 

> Note: we didn't test the combination of `row` and `col` parameters in the tests, since the invalid test cases of `row` (E7, E9) should fail regardless of the row value. 

### Boundary testing 

Considering the partitions defined in the previous section, let's define the on-points and off-points, making sure these points are covered by the tests. 

- `isRunning == false`: 
	- {col < 1, col >=  1}: 
		- on-point: 1 (E2) 
		- off-point: 0 (E1) 
	- {col > 6, col <= 6}: 
		- on-point: 6 (E1) 
		- off-point: 7 (E2) 

- `isRunning == true`: 
	- {col < 1, col >=  1}: 
		- on-point: 1 (E5) 
		- off-point: 0 (E4) 
	- {col <= 4, col > 4}: 
		- on-point: 4 (E5)
		- off-point: 5 (E6)

- `row`, with n == 1: 
	- {row <= -1, row > -1}: 
		- on-point: -1 (E7) 
		- off-point: 0 (E8) 
	- {row < 1, row >= 1} 
		- on-point: 1 (E9)
		- off-point: 0 (E9)  

### Unit Tests and Outcome

The tests used for this function can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTableModelTest.java).

Before starting each test the following function is executed: 
```java
@BeforeEach
public void initProjectTableModel();
```

It initializes a static variable called `ProjectTableModel projectTableModel` and adds a `Project` 
to it. This variable contains the target method to be tested. 

After finishing each test the following function is executed: 
```java
  @AfterEach
  public void setNullProjectTableModel(){
    projectTableModel = null;
  }
```

#### Testing `col` parameter

The main function around the tests is the following: 
```java
public void isEditableTemplate(boolean isRunning, int column, boolean expected){
    // Given
    int row = 0;
    projectTableModel.getProjectAt(0).setRunning(isRunning);
    // When
    boolean cellEditable = projectTableModel.isCellEditable(row, column);
    // Then
    Assertions.assertEquals(cellEditable, expected);
}
```

Each test calls this function, which receives as parameter:
- a `boolean isRunning`, which tells if the counter is running or not;
- the `column` of the row and the expected result value. 

All the tests look really similar: they are all `@ParameterizedTest`, which receive the column as parameter through a `@ValueSource`, and,  internally, they only call the `isEditableTemplate`.

All tests regarding the `col` variable pass. 

####  Testing `row` parameter 
The goal of this test is to make sure that the function `isCellEditable` doesn't return an `IndexOutOfBoundsException`. 
Many tests failed, since the program probably doesn't handle this scenario. 

Both the `row` and `col` tests includes the off and on-points discussed in the boundary tests section. 


> Note: in the code, we decided to test both boundary and category-partition values in the same function, therefore there is no separation between them. 

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

As checked while executing the software, some fields are not supposed to be
**edited/changed** while the timer is counting: `Time Overall` and `Time Today`.
However, it's still necessary to check if the function works as expected in other scenarios
inside the domain.

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

While the timer is not counting, all the columns are editable except `COLUMN_ACTION_DELETE`, 
`COLUMN_ACTION_STARTPAUSE`, `COLUMN_COUNT`. 

If the program is running, however, it was analysed in the GUI that the
`COLUMN_TIMEOVERALL` and `COLUMN_TIMETODAY` columns are not editable.

In addition to that, it wasn't possible to know for sure which column  `COLUMN_COUNT`
refers to in the GUI. Thus, it was considered that this column can't be modified by the user and for this 
reason, not editable. 

The columns `COLUMN_ACTION_DELETE` and `COLUMN_ACTION_START_PAUSE` are not 
editable, since these columns trigger control actions and are not related to personalization. Therefore,
they are considered not editable. 

### Category-Partition

1. **Parameters/Input**:

- `row`: an `int`, representing the project position in the row;
- `column`: an `int`, representing the column identification number;
- `isRunning`: a `boolean` that defines if the program will be counting during the test.

2. **Characteristics of each parameter**: Given that the `column` is an integer, it can be any integer in the range
   [`Integer.MIN_VALUE`, `Integer.MAX_VALUE`]. To simplify the test, the row only infers to the first project in
   the `arProj` list,
   since the goal is not to check if the function can retrieve the project from a position
   correctly, but verify if a cell is editable or not.

The `isRunning` input varies between `false` and `positive`.

3. **Constraints**: The only constraint valid in this test is the maximum and minimum value of integer.
4. **Input combination/ Unit test**: The tests analysis 7 situations:

- `isRunning = false`
    - `column: [1,6]`. All the types of column should be editable, except `COLUMN_COUNT`.
    - `column: {0,7,8}`. As explained before, these columns are not considered editable and the 
  function should return false upon receiving a `column` parameter with one of these values. 
    - `column: [-Integer.MIN_VALUE, -1]`. No negative value should be accepted editable.
    - `column: [9, Integer.MAX_VALUE]`. All numbers higher than 7 should not be modified,
      since the column doesn't exist.
- `isRunning = true`
    - `column: [1,4]`. Can be modified while the counter is counting.
    - `column: [5,8] U [0,0]`. These columns are blocked to be modified while the counter is in execution 
  or are not editable by default.
    - `column: [-Integer.MIN_VALUE, -1]`. No negative value should be accepted editable.
    - `column: [9, Integer.MAX_VALUE]`. All numbers higher than 7 should not be modified.

### Unit Tests and Outcome

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

Each test, call this function, which receives as parameter a `boolean isRunning`, which tells if the 
counter is running or not, the `colummn` of the row and the expected result value. 

All the tests look really similar: they are all `@ParameterizedTest`, which receives the column 
as parameter and by a `@ValueSource` and internally they only call the `isEditableTemplate`. 
```java
@ParameterizedTest
@ValueSource(ints = {
      ProjectTableModel.COLUMN_CHECK,
      ProjectTableModel.COLUMN_TITLE,
      ProjectTableModel.COLUMN_COLOR,
      ProjectTableModel.COLUMN_CREATED,
      ProjectTableModel.COLUMN_TIMEOVERALL,
      ProjectTableModel.COLUMN_TIMETODAY})
@Tag("not_running")
public void isCellEditableRunningFalse1(int column) {
isEditableTemplate(false, column, true);
}
```

All tests passed with success. 

Although these might look simple, the lack of documentation overcomplicated the 
process of understanding of the function, since this is a `BlackBox` test approach. The meaning and 
the goal of the function had to be deducted and the function had to be executed in isolation, 
in order to distinguish by tries and mistakes the meaning of the parameters.

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

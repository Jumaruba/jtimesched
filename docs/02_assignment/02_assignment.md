# Assignment 2 - Category-Partition

## Test 1 - project.ProjectSerializer.addXmlElement

### Why test this function?

This application persists the task's information in an XML file, allowing the user to keep his tasks from one usage of the tool to another. Given that the application tracks both the overall and the current day's active time of each task, it is essential that the information is correctly stored and retrieved when we shut down the app and relaunch it later, in the same or in different days. For this reason, we decided to test the function that is mostly used to write the information in XML format. In the `writeXml` method of the `ProjectSerializer` class, the static method `addXmlElement` is called 9 times for each project, in order to add the details about each project to an XML element. Therefore, it is essential that this function does what is expected, otherwise, when the data is restored with the `readXml` method, it could fail to be restored or load incorrect information about the projects.

### Function Description

```java
protected static void addXmlElement(TransformerHandler hd, String element, AttributesImpl atts, Object data) throws SAXException;
```

Adds to the `hd` transformer an xml element with the tag `element`, the attributes `atts` and the data `data`, represented as a `String`. A `SAXException` is thrown when an error occurs when generating the Xml.

e.g. The following XML content would be the result of adding an element named "note" with the attribute "author" and the data "This is a note".

```XML
<?xml version="1.0" encoding="UTF-8"?>
<note author="John Doe">This is a note</note>
```

### Category-Partition

1. **Parameters/Input**:
  - `hd`, a `TransformerHandler` used to build the Xml;
  - `element`, a `String` representing the name of the element (tag);
  - `atts`, a `AttributesImpl` instance representing the attributes of the `element`;
  - `data`, the content of the `element`;

2. **Characteristics of each parameter**:
  - the `element` and `data` parameters must respect [Xml's syntactic rules](https://www.w3schools.com/xml/xml_syntax.asp). Given that an element may be empty, `data` may be `null`. However, if `data` is not `null`, it must be an object that implements the `toString()` method, because the content of an Xml element is textual. The name of the element must not be empty;
  - the existence of attributes (`atts`) must not be mandatory, as Xml elements don't always have them;
  - the `TranformerHandler` `hd` may be either "new" i.e. when no elements were added yet; or it may already have elements i.e. when we wish to add multiple nested elements. If it is `null`, no element can, of course, be added.

3. **Constraints**: Given that, in the particular case of this application, we only want to store simple details about each `Project`, the `data` parameter just needs to tolerate numeric values (to store times), `String` values (for the title, notes, among others), and, of course, it must support `null` values, in the case of empty Xml elements.
4. **Input combinations / Tests**: By combining the characteristics of the four parameters we get the following combinations:
  - new `hd` (doesn't store the representation of any other elements), attributes and no data;
  - new `hd`, attributes and textual data;
  - new `hd`, attributes and numeric data;
  - new `hd`, no attributes and no data;
  - non-empty `hd`, with or without attributes and data, because we only need to check the case where the `TransformerHandler` already stores the representation of other Xml elements i.e. to build an element with multiple child elements;
  - `null` `hd`, with or without attributes and data, because it should give an exception either way;
  - unnamed element, with or without attributes and data, because it should give an exception either way;
  - `null` element, with or without attributes and data, because it should give an exception either way.

### Unit Tests & Outcome

The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectSerializerTest.java).

The first 5 tests described above, which represent cases in which the method is used with valid inputs, achieve the expected results i.e. they all pass. To check if the Xml was being generated as expected, we decided to use a `StringWriter` and test if its value was the expected Xml represented by the parameters.

The 6th test, where the function is called with a `null` `TransformerHandler` throws a `NullPointerException` as expected. This function doesn't have a return value, it changes the content of the `TransformerHandler` that is passed in the arguments. Therefore, the `TransformerHandler` must be initialized before using this function, which facilitates its usage to build an Xml document with nested elements.

The remaining tests, which test the usage of an empty or `null` `element`, fail. We were expecting to get a `SAXException`, given that an XML element/tag must not be empty. However, the function throws a `NullPointerException` when the element is `null`, and it also accepts elements without a name, which is invalid. Considering that this parameters disrespect the syntax of XML, we believe that the code should be modified to throw a `SAXException` in these cases.

---

## Test 2 - project.Project.setSecondsToday

### Why test this function?
*"Never trust user input"*.  
Unit tests not only improve the product quality, but also increase its security. The test tries to focus on these two aspects and therefore, given that the user can modify the `secondsToday` field using the GUI, the question that raises is:
> Is the user able to crash the program with some input? 

### Function Description
```java 
public void setSecondsToday(int secondsToday); 
```
By the name of the function, it's clear that it changes the value of a variable called `secondsToday`, which handles the value of the column `Today Time` in the program GUI. 

### Category-Partition
1. **Parameters/Input**:
- `seconds` - the function input, representing the time.
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

Since negative numbers are not possible, it's expected that negative numbers are handled and are equal to zero. 

### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTest.java).

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
4. **Input combinations / Tests**: This function only has one parameter (`strTime`), so we are going to test it considering the possible values of this parameter:
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

## Test 4 - project.ProjectTime.formatSeconds

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
3. **Constraints**: Given that the parameter represents the number of seconds, we can restrict the number of cases to test by having in mind that the expected output must respect the domain of the minutes and seconds. We want the number of seconds to always be less than 60 (1 minute = 60 seconds) and the number of minutes to always be less than 60 (3600 seconds = 60 minutes = 1 hour), so we may test cases where we only have seconds, where we have at least a minute, at least an hour or at least a day. Also, given that negative times are invalid, we only need to test this case with one value as the output should be the same.
4. **Input combinations / Tests**: Given that we only have a single parameter we only need to test the function for the different possible values of `s`:
- `s` is positive and lower than 60 (1 minute);
- `s` is positive, higher or equal to 60 (1 minute) and lower than 3600 (1 hour);
- `s` is positive and higher or equal to 3600 (1 hour) and lower than 86400 (1 day);
- `s` is higher or equal to 86400 (1 day);
- `s` is 0;
- `s` is negative.

### Unit Tests & Outcome
To test the combinations that were identified above, we used the `ParametrizedTest` `formatSecondsTest`, which can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectTimeTest.java).

```java 
@ParameterizedTest
@MethodSource("genFormatSeconds");
public void formatSecondsTest(int s, String expected);
```

The method `genFormatSeconds` was used as the source of parameters to test, and respective expected return values.
The test passed for all the possible values of `s`, except for the last one (negative numbers). Given that no exception handlers (`try, catch`) are used in the source code to handle a possible exception generated by this function, we were expecting a return value of "0:00:00", considering that 0 is the closest valid value. However, this function fails the test and returns "0:00:-1", which means that it does not handle negative numbers correctly. In this case, we don't expect the input to be negative, however it would be a good practice to handle this case either by throwing an Exception or by returning the value "0:00:00" if we don't want to stop the application on such a case. 

--- 

## Test 5 - project.ProjectTableModel.isCellEditable

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

While the timer is not counting, all the columns are editable except `COLUMN_ACTION_DELETE`, 
`COLUMN_ACTION_STARTPAUSE`, `COLUMN_COUNT`. 

If the program is running, however, it was analysed in the GUI that the
`COLUMN_TIMEOVERALL` and `COLUMN_TIMETODAY` columns are not editable.

In addition to that, it wasn't possible to know for sure which column  `COLUMN_COUNT` refers to in the GUI. Thus, it was considered that this column can't be modified by the user and for this reason, not editable. 

The columns `COLUMN_ACTION_DELETE` and `COLUMN_ACTION_START_PAUSE` are not editable, since these columns trigger control actions and are not related to personalization. Therefore, they are considered not editable. 

### Category-Partition

1. **Parameters/Input**:

- `row`: an `int`, representing the project position in the row;
- `column`: an `int`, representing the column identification number;
- `isRunning`: a `boolean` that defines if the program will be counting during the test.

2. **Characteristics of each parameter**: Given that the `column` is an integer, it can be any integer in the range [`Integer.MIN_VALUE`, `Integer.MAX_VALUE`]. To simplify the test, the row only infers to the first project in the `arProj` list, since the goal is not to check if the function can retrieve the project from a position correctly, but verify if a cell is editable or not.

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
counter is running or not, the `colummn` of the row and there expected result value. 

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

Although these might look simple, the lack of documentation overcomplicated the process of understanding the function, since this is a `BlackBox` test approach. The meaning and the goal of the function had to be deducted and the function had to be executed in isolation, to distinguish by tries and mistakes the meaning of the parameters.

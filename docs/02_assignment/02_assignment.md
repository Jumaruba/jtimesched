# Assignment 2 - Category-Partition

- Which functions have you selected for testing and why.
- What is the purpose of each function.
- Step-by-step of the ‘Category-Partition’ algorithm for each function.
- Brief description of the unit tests generated for each category.
- Brief description of the outcome of each unit test and whether any test results in a failure (and why).

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
  - the existence of attributes (`atts`) must not be compulsive, as Xml elements don't always have them;
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

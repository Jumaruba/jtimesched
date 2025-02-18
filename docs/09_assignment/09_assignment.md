# Assignment 9 - Mutation Testing

## Setup

In order to perform mutation testing in JTimeSched using the PIT library, it was necessary to adapt our test suite, in particular to:

- update the source code to make sure that all the tests developed in previous assignments successfully passed;
- exclude tests that exercise the project's main function because, even though they run successfully in previous assignments, they are not supported by the PIT library - probably because they require the application to be launched.

With this in mind, we started by updating the source code of some functions that failed under certain conditions. Namely:

- `addXmlElement`: this function did not handle null and empty strings in the `element` attribute correctly, which was leading the `unnamedElementTest` and `nullElementTest` to fail. In order for these tests to pass, we added a condition to `adXmlElement` that verifies these scenarios and throws a `SAXException` if they occur;
- `parseSeconds`: this method led the `parseSecondsInvalidTest` to fail because it did not throw a `ParseException` when a null string was passed as a parameter. We added an if statement that verifies this scenario and throws the appropriate exception when it occurs;
- `formatSeconds`: the `formatSecondsBoundary` and `formatSecondsPartitionTest` were failing when the parameter was a negative number because the `formatSeconds` method did not handle negative numbers correctly. Therefore, we added a condition that checks if the parameter is lower than 0 and returns "0:00:00" if that is the case;
- `isCellEditable`: tests `testPartitionE7` and `testPartitionE9` were failing because the function `isCellEditable` did not handle the case where the project row parameter was out of bounds. To fix this, we now verify if the row is smaller than 0 or higher or equal to the number of projects in the table and return `false` if that is the case.

Having all previous tests successfully passing, we then set up the `pitest` configuration.
First, we excluded the `JTimeSchedAppTest` from the test classes, so that the `testConfFolder` test is not executed. We also decided to exclude the class `JTimeSchedApp` from mutation because, apart from the main function (which launches the App and thus is not testable with PIT library), the lack of dependency injection i.e. most of the variables are created inside the function instead of being provided in the parameters; the usage of private methods and the calls to static functions make it extremely hard to test with unit tests. Moreover, we excluded the classes of the `gui` package from mutation as well. The `excludedClasses`and `excludedTestClasses` shown on the excerpt of the pom.xml file reflect these decisions.

```xml
<plugin>
  <groupId>org.pitest</groupId>
  <artifactId>pitest-maven</artifactId>
  <version>1.10.0</version>
    <configuration>
      <excludedClasses>
        <param>de.dominik_geyer.jtimesched.gui.*</param>
        <param>de.dominik_geyer.jtimesched.gui.table.*</param>
        <param>de.dominik_geyer.jtimesched.JTimeSchedApp*</param>
      </excludedClasses>
      <excludedTestClasses>
        <param>de.dominik_geyer.jtimesched.JTimeSchedAppTest</param>
      </excludedTestClasses>
    </configuration>
    ...
</plugin>
```

## Initial Mutation Score

The initial report shows a mutation coverage of 82% and a test strength of 83%.
![](./images/report_initial_all.png)

Additionally, if we analyze the mutation coverage for each package individually, we verify that the `misc` package has 100% line and mutation coverage and test strength. Therefore, we will focus on improving the mutation score for the `project` package.
![](./images/report_initial_misc.png)

Breaking down the coverage by class, we see that the mutation score of the classes varies from 75% (`ProjectTableModel`) and 94% (`ProjectTime`), which means that there are still non-killed mutants to be addressed. However, as we will see, not all the reported mutants should be considered (there are some equivalent mutants).
![](./images/report_initial_project.png)

### Non-killed mutants

The class with more non-killed mutants is, therefore, the `ProjectSerializer`, with 11/70 mutants that survived the tests developed in previous assignments. However, it is also the class for which more mutations were generated. Moreover, 9/36 mutants survived the tests developed for the methods of the `ProjectTableModel` class, and 6/43 mutations survived the unit tests developed for the `Project` class. Finally, only one mutant resisted the tests developed for the methods of the `ProjectTime` class.

Some of these mutants are, however, equivalent, as we will see in the [next section](#equivalent-mutants). The other non-killed mutants and the tests developed to handle them will be further explained in the scope of each class in the [Description of the tests](#description-of-the-tests) section of this report.

## Irrelevant mutations 

### Project 
The *PiTest* has generated 6 non-killed mutants, where 2 of them are not relevant. 

#### Mutant 1 and 2
Before explaining the equivalent mutants, it's important to highlight that a possible mutation in the `Project` class is the removal of `e.printStackTrace()` calls. As expected, the tests continue to pass even if this line of code is removed. As the `e.printStackTrace()` is called for debug, log proposals and yet is a java standard function, we decided to ignore this mutation test. 

![](./images/project_getSecondsOverall_printStackTrace.png) 
![](./images/project_getSecondsToday_printStackTrace.png) 

## Equivalent mutants

### Project
The *PiTest* has generated 6 non-killed mutant tests. Where 3 of them are equivalent. 

#### Mutant 1,2 and 3

The conditions `if (secondsOverall < 0)` and `if (secondsToday < 0)`  in the functions `setSecondsOverall`, `setSecondsToday` and `adjustSecondsToday` fail the mutations test, by surviving, when the conditions boundary is changed. 
However, changing the `<` for ` <=` is an equivalent mutant, since it behaves as the original program. 

![](./images/project_secondsToday.png)

### ProjectSerializer

#### Mutant 1
The `WriteXml()`, contains some non-killed mutants. One of them, is related to the `atts.clear()` statement. 

![](./images/projectSerializer_writeXml_2.png) 

The `atts.clear()` is responsible for cleaning the list of attributes in a `AttributesImpl` object. However, notice that `[1]` has the same attributes of `[2]`. Although the values of the attributes are different, the `atts.clear()` isn't necessary, since the values of each attribute are replaced by new ones in `[2]`. Thus, there's no way we can change the test code so that the `atts.clear()` becomes necessary. 

#### Mutant 2 
This mutation regards the `WriteXml()` function and excludes the following line from the source code:  

![](./images/projectSerializer_writeXml_3.png) 

As the [documentation](https://www.ibm.com/docs/en/sdi/7.1.1?topic=parsers-xml-sax-parser) says, the default encoding for the `XML SAX Parser` is UTF-8, which means that the specification of the encoding isn't necessary. Thus, there are no changes that could kill the mutation. 

#### Mutant 3
The mutation exercised removes the following line from the code: 

![](./images/projectSerializer_writeXml_4.png)

The default indentation of SAXTransformerFactory apparently depends on the java implementation according to the [documentation](https://docs.oracle.com/javase/7/docs/api/javax/xml/transform/TransformerFactory.html#setAttribute(java.lang.String,%20java.lang.Object)). It was tested that the default indentation for java 11 and 17 is 4 spaces, which makes the mutation in these versions equivalent, since removing it doesn't affect the program. 

#### Mutant 4 and 5
The mutation exercised here removes the `out.flush()` and the `out.close()` statements. 
Closing a file is [essential to minimize the virtual machine resources](https://www.ibm.com/docs/en/zvm/7.2?topic=io-closing-files) required to run the program and helps keep files and directories available for other users. It is not mandatory, and its absence is acceptable. Thus the `out.close()` is equivalent. 
The `out.flush()` operation [is not mandatory](https://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-java-streams), but it ensures that all the stream's data is written. As it works as an assurance, we considered this an equivalent mutation.
![](./images/projectSerializer_writeXml_7_8.png)

#### Mutant 6
The mutation exercised removes the `hd.startDocument()` statement from the program. 
It wasn't possible to gather enough information about this function, as the [documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.xml/org/xml/sax/ContentHandler.html#startDocument()) only says that it receives a notification of the beginning of a document. As the tests survive after removing the statement and there are no visible alterations on the program, we considered this an equivalent mutation.
![](./images/projectSerializer_writeXml_9.png) 

#### Mutant 7
The mutation exercised removes the `hd.endDocument()` statement from the program. 
It wasn't possible to gather enough information about this function, as the [documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.xml/org/xml/sax/ContentHandler.html#endDocument()) only says that it receives a notification of the end of a document. As the tests survive after removing the statement and there are no visible alterations on the program, we considered this an equivalent mutation.
![](./images/projectSerializer_writeXml_6.png)

### ProjectTime
The condition `if (s < 0)` of the `formatSeconds` method fails for one of the mutations, namely when the conditional boundary is changed. This happens because this mutation is equivalent i.e., if the operator is changed to `<=` the return value of the method `formatSeconds` is exactly the same for any parameter `s` equal or lower than 0.

![](./images/projecttime_formatSeconds.png)


## Description of the tests

###  Project 
There are 6 mutants that survived the tests developed for the `Project` class in the `getElapsedSeconds` method. 

![](./images/project_getElapsedSeconds.png)

The *PiTest* mutates `getElapsedSeconds` by **replacing the return condition for 0 (zero)**. 

In fact, the test created to verify this method (`runningElapsedSecondsTest()`) expects that it returns a zero value, since the `timeStart` is not set and its default value is 0 (zero) as well: 

```java
  @Test
  public void runningElapsedSecondsTest() {
    // Given
    Project project = new Project();
    project.setRunning(true);
    // When and Then
    try {
      Assertions.assertEquals(0, project.getElapsedSeconds());
    } catch (ProjectException e) {
      fail("Exception shouldn't be thrown");
    }
  }
```

Thus, we have set a value for the `startTime` variable which is equivalent of three days before the test execution: 

```java
@Test
public void runningElapsedSecondsTest() {
    // Given
+   long DAY_IN_MS = 1000 * 60 * 60 * 24;
    Project project = new Project();
    project.setRunning(true);
+   project.setTimeStart(
+       new Date(System.currentTimeMillis() - (3 * DAY_IN_MS)));
    // When and Then
    try {
+       Assertions.assertEquals(259200, project.getElapsedSeconds());
-       Assertions.assertEquals(0, project.getElapsedSeconds());
    } catch (ProjectException e) {
        fail("Exception shouldn't be thrown");
    }
}
```

On this way, the test is always expecting to have `259200` as return value and passes the mutation test.   

**Preconditions**:   
- The project is running;   
- The project has the `TimeStart` of 3 days.    

**Inputs**: None  
**Outcome**: The test passes successfully and the mutant is killed. 

![](./images/project_getElapsedSeconds_KILLED.png) 

### ProjectTableModel

There are 9 mutants that survive the tests developed for the `ProjectTableModel` class:

![](./images/projectTableModel/non-killed/all.png)

#### Mutant 1

The first mutant was introduced when we fixed the source code of the `isCellEditable` method to avoid failing tests:

![](./images/projectTableModel/non-killed/01.png)

**Mutation**: `replaced boolean return with true for de/dominik_geyer/jtimesched/project/ProjectTableModel::isCellEditable`

This mutant replaced the boolean return with `true` and still managed to survive the existent tests. Therefore, we improved the `testPartitionE7` and `testPartitionE9` parameterized tests to verify that the method returns false when the row index is out of bounds. For that, we used the `testRowParameter` function, which uses the `assertEquals` to verify if the given row leads to a "false" return value.

**Preconditions**: The project table model has a single project.  

**Inputs**:
- **`testPartitionE7`**: -2, -1;
- **`testPartitionE9`**: 1, 2.

**Outcome**: The tests pass successfully and the mutant is killed:
  ![](./images/projectTableModel/fix/01.png)

#### Mutant 2

![](./images/projectTableModel/non-killed/02.png)

**Mutation**: `setValueAt : negated conditional`

This mutant shows that the condition that verifies if the project is checked or not is not exercised. Therefore, we had to find a way to verify the output of the logger when the `setValueAt` function is called to set the value of the `ProjectTableMode.COLUMN_CHECK` as true or false.
For that, we created the tests `testCheckProject` and `testUncheckProject`. Furthermore, to be able to test the output of the logger, we created a custom `Handler` that stores the last message that is published in a class field:

```java
class LogHandler extends Handler {
 private String lastMessage = "";

 public String getMessage() {
   return lastMessage;
 }

 public void close() {}

 public void flush() {}

 @Override
 public void publish(LogRecord record) {
   lastMessage = record.getMessage();
 }
}
```

Then, in each of the tests, we associate the handler with the logger `l`, which was already initialized in the function annotated with `@BeforeEach`, namely `initProjectTableModel` (explained in assignment 5):

```java
    LogHandler handler = new LogHandler();
    l.addHandler(handler);
    l.setLevel(Level.INFO);
```

The `initProjectTableModel` also initializes the `ProjectTableModel` instance with a single project. After that, in each of the tests, we set the parameter `row` to 0 (the first and only project of the Table), the `col` to `ProjectTableModel.COLUMN_CHECK` and the value to `true` or `false`, to check or uncheck the checkbox of that table cell. After calling the `setValueAt` function with these parameters, we then assert that the output of the logger is the expected:

```java
// In testCheckProject
String expected = "Set check for project 'project'";
Assertions.assertEquals(expected, handler.getMessage());

// In testUncheckProject
String expected = "Unset check for project 'project'";
Assertions.assertEquals(expected, handler.getMessage());
```

**Preconditions**: The project table model has a single project.

**Inputs**:
- **`testCheckProject`**:
  - `value` = `true`;
  - `row` = 0;
  - `col` = `ProjectTableModel.COLUMN_CHECK`.
- **`testUncheckProject`**:
  - `value` = `false`;
  - `row` = 0;
  - `col` = `ProjectTableModel.COLUMN_CHECK`.

**Outcome**: The tests pass successfully and the mutant is killed:
  ![](./images/projectTableModel/fix/02.png)

#### Mutants 3 & 4

![](./images/projectTableModel/non-killed/03_04.png).

**Mutations**: `setValueAt : negated conditional` (both mutations are of the same type)

These mutants showed that our test suit was lacking a test to verify the log generated when the method `setValueAt` is used to update the value of the `ProjectTableModel.COLUMN_TIMETODAY` or `ProjectTableModel.COLUMN_TIMEOVERALL`. When one of these columns is edited, the `oldSeconds` variable is used in the function to store the previous value of the respective attribute (`secondsToday` or `secondsOverall`) so that the log shows what the old value was and what is the new one. To test if the output was the expected when the function was called to edit the `ProjectTableModel.COLUMN_TIMETODAY` column, we used the test `testSetTimeTodayColumn`. To test the other column, we used the test `testSetTimeOverallColumn`.

In both tests, we start by setting the `LogHandler` as described in the previous tests. Then, we set `secondsOverall` and `secondsToday` class properties of the only project of the `ProjectTableModel`(initialized in `initProjectTableModel`). We used different values for each property so that we could easily verify the old value outputted in the log. We called the `setValueAt` method with the inputs described below and asserted that the log matched what was expected:

```java
// testSetTimeOverall
 String expected = "Manually set time overall for project 'project' from 0:00:10 to 0:00:15";
    Assertions.assertEquals(expected, handler.getMessage());

// testSetTimeToday
String expected =
        "Manually set time today for project 'project' from 0:00:05 to 0:00:15";
Assertions.assertEquals(expected, handler.getMessage());
```

**Preconditions**: The project table model has a single project with `secondsOverall` = 10 and `secondsToday` = 5.

**Inputs**:
- **`testSetTimeTodayColumn`**:
  - `value` = 15;
  - `row` = 0;
  - `col` = `ProjectTableModel.COLUMN_TIMETODAY`.
- **`testSetTimeOverallColumn`**:
  - `value` = 15;
  - `row` = 0;
  - `col` = `ProjectTableModel.COLUMN_TIMEOVERALL`.

**Outcome**: The tests pass successfully and the mutants are both killed:
  ![](./images/projectTableModel/fix/03_04.png)

#### Mutant 5

![](./images/projectTableModel/non-killed/05.png).

**Mutation**: `setValueAt : removed call to de/dominik_geyer/jtimesched/project/ProjectTableModel::fireTableRowsUpdated`

The mutation shown above survived because none of the tests performed for the `setValueAt` method was verifying if the `fireTableRowsUpdated` method was called to notify row listeners of the update. To solve this, we created the test `testFireTableRowsUpdated`, where we used a `Mockito.spy` to create a spy of the `ProjectTableModel` and then used `Mockito.verify` to check if the method was invoked precisely once with the correct row:
```java
Mockito.verify(projectTableModelSpy).fireTableRowsUpdated(row, row);
``` 

**Preconditions**: The project table model has a single project.

**Inputs**: 
- `row` = 0;
- `col` = `ProjectTableModel.COLUMN_TITLE`;
- `value` = "test".

**Outcome**: The test passes successfully and the mutant is killed:
  ![](./images/projectTableModel/fix/05.png)

#### Mutant 7 - 8

![](./images/projectTableModel/non-killed/06_07_08.png).

**Mutations**: 
- `addProject : Replaced integer subtraction with addition`
- `addProject : Replaced integer subtraction with addition`
- `addProject : removed call to de/dominik_geyer/jtimesched/project/ProjectTableModel::fireTableRowsInserted`

In the `addProject` method, 3 mutants survived our tests because we were not verifying it the method `fireTableRowsInserted` was being called to notify the listeners that a new row was inserted in the projects table. To test this, we updated the test `testAddProject` to use a spy of the `ProjectTableModel` and added an additional verification (with `Mockito.verify`) to check if the method was being called exactly once with the correct parameters:
```java
Mockito.verify(projectTableModelSpy).fireTableRowsInserted(1, 1);
```

**Preconditions**: The project table model has a single project.

**Inputs**: a `Project` instance with the name "Project1";

**Outcome**: The tests pass successfully and the mutants are killed:
  ![](./images/projectTableModel/fix/06_08.png)

#### Mutant 9

![](./images/projectTableModel/non-killed/09.png).

**Mutations**: `removed call to de/dominik_geyer/jtimesched/project/ProjectTableModel::fireTableRowsDeleted`

This mutation is similar to the one above but now in the `removeProject` method, where we must verify if the `fireTableRowsDeleted` method is being called with the correct parameters. To do so, we followed the same approach as above by updating the test `testRemoveProject` method and verifying with `Mockito.verify` if the method was called exactly once with the correct parameters:
```java
Mockito.verify(projectTableModelSpy).fireTableRowsDeleted(0, 0);
```

**Preconditions**: The project table model has a single project.

**Inputs**: `row` = 0.

**Outcome**: The test passes successfully and the mutant is killed:
  ![](./images/projectTableModel/fix/09.png)

#### Final Mutation Score

After performing new tests and improving tests from other assignments we achieved a mutation score of 100% for the `ProjectTableModel` class.
![](./images/projectTableModel/fix/all_report.png)
![](./images/projectTableModel/fix/all.png)

### ProjectSerializer 

There are 11 mutants that survived the tests developed for the `ProjectSerializer`. 

#### Mutant 1 
The mutation exercises the removal of the following line: 

![](./images/projectSerializer_writeXml_5.png) 

A new test was created in order to kill this mutant: `writeXmlIndentationTest`. This test writes a project in a file and the output file is expected to match a string which contains indentation. 

**Inputs**: The project XML path and the path.
**Outcome**: The test passes successfully and the mutant is killed. 

![](./images/projectSerializer_identation.png)


#### Mutant 2, 3 and 4

In the function `readXml()`, although the `set` statements are removed from the function, the test still passes: 

![](./images/projectSerialiazer_readXml.png)

Since the test `readXml_1()` and `readXml_2()` only assess default values, the value of the variables `secondsOverall`, `secondsToday` and `quoteOverall` of a project (the `p` variable) will be correct even if the sets are removed. 

Thus, the `readXml_2()` test was changed so that value read from the xml file isn't the default one. This was done performing the following assertions:

```java
Assertions.assertEquals(600, projectList.get(1).getQuotaToday());
Assertions.assertEquals(10, projectList.get(1).getSecondsOverall());
Assertions.assertEquals(10, projectList.get(1).getSecondsToday());
```

And consequently modifying the second project in the Xml input to:

```xml
<project>
    <title>New project</title>
    <notes>A nice note</notes>
    <created>1668006000250</created>
    <started>1668006000250</started>
    <running>no</running>
    <checked>yes</checked>
    <time overall="10" today="10"/>
    <quota overall="800" today="600"/>
    <color red="122" green="194" blue="229" alpha="255"/>
</project>
```

**Inputs**: the project XML filepath `"docs/05_assignment/inputDir/projectTest"`   
**Outcome**: The test pass successfully and the mutant is killed.  

With this modifications, the mutant tests are killed.

![](./images/projectSerialiazer_readXml_KILLED.png) 
___ 

## Final Mutation Score

The final report shows a mutation coverage of 93% and a test strength of 93%.
![](./images/report_final_all.png)

Breaking down the coverage by class, we see that the mutation score as also improved.
![](./images/report_final_project.png)
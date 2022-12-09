# Assignment 9 - Mutation Testing

## Setup

In order to perform mutation testing in JTimeSched using the PIT library it was necessary to adapt our test suite, in particular to:
- update the source code to make sure that all the tests developed in previous assignments successfully passed;
- exclude tests that exercise the main function of the project, because, even though they run successfully in previous assignments, they are not supported by the PIT library - probably because they require the application to be launched.

With this in mind, we started by updating the source code of some of the functions that failed under certain conditions. Namely:
- `addXmlElement`: this function did not handle null and empty strings in the `element` attribute correctly, which was leading the `unnamedElementTest` and `nullElementTest` to fail. In order for this tests to pass, we added a condition to `adXmlElement` that verifies these scenarios and throws a `SAXException` if they occur;
- `parseSeconds`: this method led the `parseSecondsInvalidTest` to fail because it did not throw a `ParseException` when a null string was passed as parameter. We added an if statement that verifies this scenario and throws the appropriate exception when it occurs;
- `formatSeconds`: the `formatSecondsBoundary` and `formatSecondsPartitionTest` were failing when the parameter was a negative number because the `formatSeconds` method did not handle negative numbers correctly. Therefore, we added a condition that checks if the parameter is lower than 0 and returns "0:00:00" if that is the case;
- `isCellEditable`: tests `testPartitionE7` and `testPartitionE9` were failing because the function `isCellEditable` did not handle case where the project row parameter was out of bounds. To fix this, we now verify if the row is smaller than 0 or higher or equal to the number of project in the table and return `false` if that is the case.


Having all previous tests successfully passing, we then set up the `pitest` configuration.
First, we excluded the `JTimeSchedAppTest` from the test classes, so that the `testConfFolder` test is not executed. We also decided to exclude the class `JTimeSchedApp` from mutation because, apart from the main function (which launches the App and thus is not testable with PIT library), the lack of dependency injection i.e. most of the variables are created inside the function instead of being provided in the parameters; and the usage of private methods and the calls to static functions make it extremely hard to test with unit tests. Moreover, we excluded the classes of the `gui` package from mutation as well. The `excludedClasses`and `excludedTestClasses` of the excerpt of the pom.xml file reflect these decisions.

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

Additionally, if we analyze the mutation coverage for each package individually we verify that the `misc` package has 100% line and mutation coverage and test strength. Therefore, we will focus on improving the mutation score for the `project` package.
![](./images/report_initial_misc.png)

Breaking down the coverage by class, we see that the mutation score of the classes varies from 75% (`ProjectTableModel`) and 94% (`ProjectTime`), which means that there are still non-killed mutants to be addressed. However, as we will see, not all the reported mutants should be considered (there are some equivalent mutants).
![](./images/report_initial_project.png)


### Non-killed mutants
The class with more non-killed mutants is therefore the `ProjectSerializer`, with 12/70 mutants that survive the tests developed in previous assignments. However, it is also the class for which more mutations were generated. Moreover, 9/36 mutants survive the tests developed for the methods of the `ProjectTableModel` class and 6/43 mutations survive the unit tests developed for the `Project` class. Finally, only 1 mutant resists the tests developed for the methods of the `ProjectTime` class.

Some of these mutants are, however, equivalent, as we will see in the [next section](#equivalent-mutants). The other non-killed mutants and the tests developed to handle them will be further explained in the scope of each class in the [Description of the tests](#description-of-the-tests) section of this report.

## Irrelevant mutations 

### Project 
The *PiTest* has generated 6 non-killed mutant tests, where 2 of them are not relevant. 

#### Mutant 1 and 2
Before explaining the equivalent mutants, it's important to highlight a possible mutation in the `Project` class is the removal of `e.printStackTrace()` calls. As expected, the tests continues to pass even if this line of code is removed. As the `e.printStackTrace()` is called for debug, log proposals and yet is a java standard function, we decided to ignore this mutation test. 

![](./images/project_getSecondsOverall_printStackTrace.png) 
![](./images/project_getSecondsToday_printStackTrace.png) 


## Irrelevant mutations 

### Project 

There are 9 mutant that survived the tests developed for the `ProjectSerializer`. Where x of them are irrelevant. 

#### Mutant 1
The `WriteXml()`, contains some non-killed mutant tests. One of them, is related to the `atts.clear()` statement. 

![](./images/projectSerializer_writeXml_2.png) 

The `atts.clear()` is responsible for cleaning the list of attributes in a `AttributesImpl` object. However, notice that `[1]` has the same attributes of `[2]`. Although the values of the attributes are different, the `atts.clear()` isn't necessary, since the values of each attribute are replaced by new ones in `[2]`. Thus, there's no way we can change the test code so that the `atts.clear()` becomes necessary. 

#### Mutant 2 
This mutation regards the `WriteXml()` function and excludes the following line from the source code:  

![](./images/projectSerializer_writeXml_3.png) 

As the [documentation](https://www.ibm.com/docs/en/sdi/7.1.1?topic=parsers-xml-sax-parser) says, the default enconding for the `XML SAX Parser` is UTF-8, which means that the speficication of the enconding isn't necessary. Thus, there are no changes that could kill the mutation. 

#### Mutant 3 and 4 

The mutation regards the `WriteXml()` function and excludes the following lines from the source code: 

![](./images/projectSerializer_writeXml_4.png)
![](./images/projectSerializer_writeXml_5.png) 

These statements are responsible for beautifying the output xml text, which means that this is not a critical piece of code and doesn't affect the program execution. Thus, it doesn't make sense testing or verifying it in our test functions. 

#### Mutant 5 
![](./images/projectSerializer_writeXml_6.png) 

#### Mutant 6 and 7 
![](./images/projectSerializer_writeXml_7_8.png) 

#### Mutant 8 
![](./images/projectSerializer_writeXml_9.png) 
## Equivalent mutants
<!-- for each class -->

### Project
The *PiTest* has generated 6 non-killed mutant tests. Where 3 of them are equivalent. 

#### Mutant 3,4 and 5

The conditions `if (secondsOverral < 0)` and `if (secondsToday < 0)`  in the functions `setSecondsOverral`, `setSecondsToday` and `adjustSecondsToday` fails the mutations test, by surviving, when the conditions boundary is changed. 
However, changing the `<` for ` <=` is an equivalent mutant, since it behaves as the original program and it's expected and will not be treated. 

![](./images/project_secondsToday.png)


### ProjectTime

### ProjectTableModel

### ProjectSerializer

### PlainTextFormatter

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
**Outcome**:   The tests pass sucecssfully and the mutant is killed. 

![](./images/project_getElapsedSeconds_KILLED.png) 

### ProjectTime 

### ProjectTableModel 

### ProjectSerializer 
There are 9 mutant that survived the tests developed for the `ProjectSerializer`. 

#### Mutant 9 

In the function `readXml()`, although the `set` statments are removed from the function, the test still passes: 

![](./images/projectSerialiazer_readXml.png)

Since the test `readXml_1()` and `readXml_2()` only assess default values, the value of the variables `secondsOverall`, `secondsToday` and `quoteOverall` of a project (the `p` variable) will be correct even if the sets are removed. 

Thus, the `readXml_2()` test was changed so that value read from the xml file isn't the default one. This was done performing the following assertions:

```java
Assertions.assertEquals(600, projectList.get(1).getQuotaToday());
Assertions.assertEquals(10, projectList.get(1).getSecondsOverall());
Assertions.assertEquals(10, projectList.get(1).getSecondsToday());
```

And consequently modifing the second project in the Xml input to:

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



### PlainTextFormatter 

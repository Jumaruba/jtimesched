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

## Equivalent mutants
<!-- for each class -->

### Project

### ProjectTime

### ProjectTableModel

### ProjectSerializer

### PlainTextFormatter

## Description of the tests 

###  Project 

### ProjectTime 

### ProjectTableModel 
There are 9 mutants that survive the tests developed for the `ProjectTableModel` class.

#### Mutant 1
The first one was introduced by the changes made to the `isCellEditable` function to avoid failing tests:
![](./images/project_table_model1.png).
This mutant replaced this boolean return by `true` and still managed to survive the existent tests. Therefore, we improved the `testPartitionE7` and `testPartitionE9` parameterized tests to verify that, when the index is out of bounds, the method return false. For that, we used the `testRowParameter` function.
**Preconditions**:
- The project table model has a single project.
**Inputs**:
- `testPartitionE7`: -2, -1;
- `testPartitionE9`: 1, 2.
**Outcome**
- The tests pass successfully and the mutant is killed:
![](./images/project_table_model1_fix.png)

#### Mutant 2
<!-- Set and uncheck -->

#### Mutants 3 & 4
![](./images/project_table_model_2.png).
<!-- overall vs today set log-->

#### Mutant 5 to 7
<!-- na addProject -->

#### Mutant 8
<!--na removeProject -->

## Mutant 9
<!-- call tah fireTable no fim da setValueAt -->

### ProjectSerializer 

### PlainTextFormatter 
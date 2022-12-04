# Assignment 9 - Mutation Testing

## Initial Mutation Score

![](./images/report_initial_all.png)
![](./images/report_initial_report.png)
![](./images/report_initial_misc.png)
<!-- 
- Adicionalmente dizer que mudamos o codigo de testes anteriores para não shaver testes a falhar?
    - ProjectSerializer :: unnamedElementTest e nullElementTest() :: falhavam porque addXmlElement nao lidava com strings nulas ou vazias no element. Adicionamos um if que verifica isto e dá SAXException se falhar
    - ProjectTime :: parseSecondsInvalidTest :: adicionamos um if para dar throw de ParseException se o parameter for null
    - ProjectTime :: formatSecondsBoundary e formatSecondsPartitionTest :: devolvemos 0:00:00 para numeros negativos
- Explicar porque razão não incluímos os testes e a class JTimeSched 
-->

### Non-killed mutants
<!--Explain which classes have more non-killed mutants -->

## Equivalent mutants
<!-- for each class -->

### Project

Before explaining the equivalent mutants, it's important to highlight a possible mutation in the `Project` class is the removal of `e.printStackTrace()` calls. As expected, the tests continues to pass even if this line of code is removed. However, as the `e.printStackTrace()` is called for debug and log proposals, we decided to ignore this mutation test.

The conditions `if (secondsOverral < 0)` and `if (secondsToday < 0)`  in the functions `setSecondsOverral`, `setSecondsToday` and `adjustSecondsToday` fails the mutations test, by surviving, when the conditions boundary is changed. 
However, changing the `<` for ` <=` is an equivalent mutant, since it behaves as the original program and it's expected and will not be treated. 

![](./images/project_secondsToday.png)




### ProjectTime

### ProjectTableModel

### ProjectSerializer

### PlainTextFormatter

## Description of the tests 

###  Project 

In the `Project` class a mutation test has survived in the `getElapsedSeconds` method: 

![](./images/project_getElapsedSeconds.png)

The mutation test that has failed is replacing the return value for zero. 

In fact, the test created to verify this function expects that the function returns a zero value, since the `timeStart` is not set: 

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
    long DAY_IN_MS = 1000 * 60 * 60 * 24;
    Project project = new Project();
    project.setRunning(true);
    project.setTimeStart(
        new Date(System.currentTimeMillis() - (3 * DAY_IN_MS)));
    // When and Then
    try {
        Assertions.assertEquals(259200, project.getElapsedSeconds());
    } catch (ProjectException e) {
        fail("Exception shouldn't be thrown");
    }
}
```

On this way, the test is always expecting to have `259200` as return value and passes the mutation test. 


### ProjectTime 

### ProjectTableModel 

### ProjectSerializer 

### PlainTextFormatter 
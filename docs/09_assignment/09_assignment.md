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

### ProjectTime

### ProjectTableModel

### ProjectSerializer

### PlainTextFormatter

## Description of the tests 

###  Project 

### ProjectTime 

### ProjectTableModel 

### ProjectSerializer 
There are 9 mutant that survived the tests developed for the `ProjectSerializer`. 

#### Mutant1 

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

#### Mutant 2 
The `WriteXml()`, contains some non-killed mutant tests. One of then, is related to the `atts.clear()` statement. 

![](./images/projectSerializer_writeXml_2.png) 

The `atts.clear()` is responsible for cleaning the list of attributes in a `AttributesImpl` object. However, notice that `[1]` has the same attributes of `[2]`. Although the values of the attributes are different, the `atts.clear()` isn't necessary, since the values are replaced by new ones in `[2]`. Thus, there's no way we can change the test code so that the `atts.clear()` becomes necessary. 

#### Mutant 3 
This mutation regards the `WriteXml()` function and excludes the following line from the source code:  

![](./images/projectSerializer_writeXml_3.png) 

As the [documentation](https://www.ibm.com/docs/en/sdi/7.1.1?topic=parsers-xml-sax-parser) says, the default enconding for the `XML SAX Parser` is UTF-8, which means that the speficication of the enconding isn't necessary. Thus, there are no changes that could kill the mutation. 

#### Mutant 4 and 5 

The mutation regards the `WriteXml()` function and excludes the following lines from the source code: 

![](./images/projectSerializer_writeXml_4.png)
![](./images/projectSerializer_writeXml_5.png) 

These statements are responsible for beautifying the output xml text, which means that this is not a critical piece of code and doesn't affect the program execution. Thus, it doesn't make sense testing or verifying it in our test functions. 

#### Mutant 6 
![](./images/projectSerializer_writeXml_6.png) 

#### Mutant 7 and 8 
![](./images/projectSerializer_writeXml_7_8.png) 

#### Mutant 9 
![](./images/projectSerializer_writeXml_9.png) 

### PlainTextFormatter 

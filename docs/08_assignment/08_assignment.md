# Assignment 8 - Dataflow Testing

## parseSeconds 

### What is the purpose of this function and why should it be tested ?

This application allows the user to manually change the overall time of a task and the time spent doing that same task in the current day. When that manually configuration is performed, this function is used to convert the input of the user into the correspondent time in seconds, which is then used to update the state of the application. For this reason, it is crucial that this function handles the user input as expected, either by performing the right conversion when possible or by throwing the expected exception. 

### Dataflow Testing
<!-- for each variable. We are interesting in seeing a tabular summary for each variable, as the one presented in lecture #9 and all paths for each coverage criteria: all-defs, all-c-uses, all-p-uses, and all-uses.-->
#### Dataflow diagram 

#### All-defs 

#### All-c-uses 

#### All-p-uses 

#### All-uses 

### Unit Tests and Outcome
<!-- for each coverage criteria -->

#### All-defs 

#### All-c-uses 

#### All-p-uses 

#### All-uses 

## startXmlElement

### What is the purpose of this function and why should it be tested ?

The `startXmlElement` method of the `ProjectSerializer` class adds a starting element to a `TransformerHandler`, a class that, in the context of this project, is used to parse and build the XML document that stores the state of the application before exiting. This method is used to initialize each XML tag of the configuration file, namely the "projects" tag and the "project" tag of each task. Therefore, it is crucial to ensure that this method does not generate any unexpected behavior. Otherwise, the current tasks and their process may be lost.

#### Dataflow diagram 

![](./images/diagram_startXmlElement.png)

#### Dataflow table 

![](./images/tables_startXmlElement.png)

#### All-defs 
![](./images/alldefs-startXmlElement.png)

#### All-c-uses 
![](./images/allcuses_startXmlElement.png)
#### All-p-uses 
![](./images/allpuses_startXmlElement.png)

#### All-uses 
![](./images/alluses_startXmlElement.png)

### Unit Tests
<!-- for each coverage criteria -->
#### All-defs 
**hd**
- **all_defs::pairId_0**: To exercise the path `<0,1,2>`, the JVM must verify the condition inside the function as 
  **true**. In other words, the `atts` parameter passed as an argument to `startXml` must be `null`. Thus, we have 
  reused the test function `public void startXmlElementNoAtts()` from previous assignments to exercise the path. The `hd` variable is passed to the function as a parameter and contains only one definition. It only needs to be declared with any acceptable value to pass through the path `<0,1,3>`.  

  ```java
  SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
  TransformerHandler hd = tf.newTransformerHandler();
  ```` 

**element**
- **all_defs::pairId_1**: To exercise the path `<0,1,3>`, the JVM must verify the condition inside the function as **false**. In other words, the `atts` parameter passed as an argument to `startXml` must be **not** null. Thus, we have reused the test function `public void startXmlElementWithAtts()` from previous assignments to exercise the path. It's in a similar situation to `hd` parameter. Thus, it only needs to be declared with any acceptable value.

**atts**
- **all_defs::pairId_3**:  To follow the path `<0,1,3>`, the condition `(atts == null)` must be false. Then `atts` 
  must have a value **different from null**. Thus, we have reused the test function `public void startXmlElementWithAtts()` from previous assignments to exercise the path. 

- **all_defs::pairId_4**: This test is similar to **all_defs::pairId_1**. To be declared in `2`, `atts` must be `null`. Thus, we have reused the test function `public void startXmlElementNoAtts()` from previous assignments to exercise the path. 

#### All-c-uses
To avoid some repetition, this section might reference some tests from the **all-defs** section, since they are the same. 

**hd**
- **all_c_uses::pairId_1**: Uses the test described in **all_defs::pairId_1**. 
**element**: 
- **all_c_uses::pairId_1**: Uses the test described in **all_defs::pairId_1**. 
**atts**: 
- **all_c_uses::pairId_3**: Uses the test described in **all_defs::pairId_3**: 
- **all_c_uses::pairId_4**: Uses the test described in **all_defs::pairId_4**: 


#### All-p-uses
As well as in **All-c-uses** section, this section might reference some tests from the previous sections since they are the same. 

**atts**: 
- **all_c_uses::pairId_1**: For the condition to return **true**, the `atts` must be `null`. Thus, we have reused the test function `public void startXmlElementNoAtts()` from previous assignments to exercise the path. 

- **all_c_uses::pairId_2**: To follow the path `<0,1,3>` the condition `(atts == null)` must be false. In this case, it's necessary to declare the variable and parse it as a parameter to the function.  Thus, we have reused the test function `public void startXmlElementWithAtts()` from previous assignments to exercise the path. 
 
#### All-uses

The **All-uses** criteria uses tests from the **All-c-uses** and **All-p-uses**, since, in our case, $all\_p\_uses\cup all\_c\_uses = all\_uses$. 

**hd**
  - **all_c_uses::pairId_1**: Uses the test described in **all_defs::pairId_1**. 
**element**: 
  - **all_c_uses::pairId_1**: Uses the test described in **all_defs::pairId_1**. 
**atts**: 
  - **all_c_uses::pairId_1**: Uses the test described in **all_defs::pairId_1**: 
  - **all_c_uses::pairId_2**: Uses the test described in **all_p_uses::pairId_2** 
  - **all_c_uses::pairId_3**: Uses the test described in **all_defs::pairId_3**: 
  - **all_c_uses::pairId_4**: Uses the test described in **all_defs::pairId_4**: 

> Note: We are aware that the path `<2,3>` isn't a clean path, since the `atts` is passed as parameter in path node 
> `0`. However, this matter went into discussion, and we decided to add this path, since the fact that this is not a clean-path doesn't change the necessity of testing it. 

## getSecondsToday 

### What is the purpose of this function and why should it be tested ?
<!-- Why test this function? AKA copy paste --> 

#### Dataflow diagram 

#### All-defs 

#### All-c-uses 

#### All-p-uses 

#### All-uses 

### Unit Tests
<!-- for each coverage criteria -->

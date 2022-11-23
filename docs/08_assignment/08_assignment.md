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

<!-- xuliane -->

### What is the purpose of this function and why should it be tested ?
<!-- Why test this function? AKA copy paste -->
### Dataflow diagram 

![](./images/diagram_startXmlElement.png)

### Dataflow table 

![](./images/tables_startXmlElement.png)

### All-defs 
![](./images/alldefs-startXmlElement.png)

### All-c-uses 
![](./images/allcuses_startXmlElement.png)
### All-p-uses 
![](./images/allpuses_startXmlElement.png)

### All-uses 
![](./images/alluses_startXmlElement.png)

### Unit Tests
<!-- for each coverage criteria -->
#### All-defs 
- **hd**
  - **all_defs::pairId_0**:   

  ```java
  SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
  TransformerHandler hd = tf.newTransformerHandler();
  ```` 

    This value is parsed to the function as a parameter and contains only one definition. It only needs to be declared with any acceptable value in order to pass through the patht `<0,1,3>`. Following the path defined in pair id 1 or 2 depends on the `atts` variable. 

- **element**
  - **all_defs::pairId_1**: 
  ```java
  String element = new String("element"); 
  ```

  It's in a similar situation of `hd` parameter. Thus, it only needs to be declared with any acceptable value. 

- **atts**

  - **all_defs::pairId_1**: 

  ```java 
  AttributesImpl atts = null; 
  ```

  To the condition return **true**, the `atts` must be `null`. 
  - **all_defs::pairId_3**:  

  ```java
  AttributesImpl atts = new AttributesImpl();
  ```

  To follow the path `<0,1,3>` the condition `(atts == null)` must be false. Then `atts` must have a value different from null. 
    - **all_defs::pairId_4**: 

    ```java
      AttributesImpl atts = null; 
    ```
    This test is similar to **all_defs::pairId_1**. To be declared in `2`, `atts` must be `null`. 
#### All-c-uses
To avoid some repetition, this section might reference some tests from the **all-defs** section, since they are the same. 

- **hd**
  - **all_c_uses::PairId_1**:   
  Uses the test described in **all_defs::pairId_1**. 
- **element**: 
  - **all_c_uses::pairId_1**:   
  Uses the test described in **all_defs::pairId_1**. 
- **atts**: 
  - **all_c_uses::pairId_3**:  
  Uses the test described in **all_defs::pairId_3**: 
  - **all_c_uses::pairId_4**:   
  Uses the test described in **all_defs::pairId_4**: 


#### All-p-uses
As well as in **All-c-uses** section, this section might reference some tests from the previous sections, since they are the same. 

- **atts**: 
  - **all_c_uses::pairId_1**:  
  Uses the test described in **all_defs::pairId_1**: 
  - **all_c_uses_pairId_2**:    
  ```java
  AttributesImpl atts = new AttributesImpl();
  ```   

  To follow the path `<0,1,3>` the condition `(atts == null)` must be false. In this case it's necessary to declare the variable and parse it as a parameter to the function.  
#### All-uses

The **All-uses** criteria uses tests from the **All-c-uses** and **All-p-uses**, once, in our case, $all\_p\_uses\cup all\_c\_uses = all\_uses$. 

- **hd**
  - **all_c_uses::PairId_1**:   
  Uses the test described in **all_defs::pairId_1**. 
- **element**: 
  - **all_c_uses::pairId_1**:   
  Uses the test described in **all_defs::pairId_1**. 
- **atts**: 
  - **all_c_uses::pairId_1**:  
  Uses the test described in **all_defs::pairId_1**: 
  - **all_c_uses_pairId_2**:    
  Uses the test described in **all_p_uses::pairId_2** 
  - **all_c_uses::pairId_3**:  
  Uses the test described in **all_defs::pairId_3**: 
  - **all_c_uses::pairId_4**:   
  Uses the test described in **all_defs::pairId_4**: 


## getSecondsToday 

### What is the purpose of this function and why should it be tested ?
<!-- Why test this function? AKA copy paste --> 

### Dataflow diagram 

### All-defs 

### All-c-uses 

### All-p-uses 

### All-uses 

### Unit Tests
<!-- for each coverage criteria -->
#### All-defs 
**seconds**:
- **all_defs::pairId_1**: To test this path, the `seconds` variable must be defined, which requires the class attribute `secondsToday` to also be defined. Furthermore, the condition of node 2 (`isRunning`) must return true. Thus, the project must be running.
Having this in mind, we must set the `secondsToday` to any integer and the `isRunning` flag to true. 
- **all_defs::pairId_3**: In node 3, the `seconds` variable is redefined and used (added to the result of `getElapsedSeconds`). To reach this node, the conditions of the previous case must hold. ?...e mais...?

**secondsToday**:
- **all_defs::pairId_1**: To test this path, the attribute `secondsToday` of the `Project` instance must exist, so that it can be copied to the `seconds` variable.

**e**:
- **all_defs::pairId_1**: To reach node 5, the conditions to reach node 3 must also hold. Furthermore, the `getElapsedSeconds()` method must throw a `ProjectException`.

#### All-uses (and All-c-uses) 
**seconds**:
- **all_uses::pairId_1**: Already described in **all_defs::pairId_1** of the `seconds` variable.
- **all_uses::pairId_2**: To test this path, the `seconds` variable must be defined, which requires the class attribute `secondsToday` to also be defined. Furthermore, the condition of node 2 (`isRunning`) must return false. Thus, the project must not be running.
Having this in mind, we must set the `secondsToday` to any integer and the `isRunning` flag to false. The return value must be equivalent to the value of `secondsToday`.
- **all_uses::pairId_3**: Already described in **all_defs::pairId_3** of the `seconds` variable.
- **all_uses::pairId_4**: To reach node 3, where `seconds` is redefined, the `seconds` variable must have been created, which requires the class attribute `secondsToday` to also exist. Furthermore, the condition of node 2 (`isRunning`) must return true. Thus, the project must be running. The, the function `getSecondsToday` must not throw an exception, so that it goes directly to the return node (7).

**secondsToday**:
- **all_uses::pairId_1**: Already described in **all_defs::pairId_1** of the `secondsToday` variable.

**e**:
- **all_uses::pairId_1**:  Already described in **all_defs::pairId_1** of the variable `e`.

# Assignment 8 - Dataflow Testing

## parseSeconds 

### What is the purpose of this function and why should it be tested ?

This application allows the user to manually change the overall time of a task and the time spent doing that same task in the current day. When that manually configuration is performed, this function is used to convert the input of the user into the correspondent time in seconds, which is then used to update the state of the application. For this reason, it is crucial that this function handles the user input as expected, either by performing the right conversion when possible or by throwing the expected exception. 

### Dataflow Testing
<!-- for each variable. We are interesting in seeing a tabular summary for each variable, as the one presented in lecture #9 and all paths for each coverage criteria: all-defs, all-c-uses, all-p-uses, and all-uses.-->
#### Dataflow diagram 

![](./images/diagram_parseSeconds.png)

#### All-defs 

![](./images/alldefs_parseSeconds.png)

#### All-c-uses
![](./images/allcuses_parseSeconds.png)
    
#### All-p-uses 

![](./images/allpuses_parseSeconds.png)

#### All-uses 
![](./images/alluses_parseSeconds.png)

### Unit Tests and Outcome
<!-- for each coverage criteria -->

#### All-defs 
- m
    - **all_defs::pairId_1**: this test uses the `parseSecondsInvalidTest` created in a previous assignment. This is a parameterized test, which receives as argument a value that makes the if condition `true` and passes through the path `<2,3,4>`. The if condition can be checked below: 

```java
if (!m.matches()) throw new ParseException("Invalid seconds-string", 0);
```
  
Since it returns a `Exception` the test assesses an `assertThrows`. The parameters tested can be checked below: 
  
```java
  "",
  "0:07:60",
  "0:90:05",
  "0:6:054",
  "0:007:05",
  "6:54",
  "2",
  "0:07:",
  "0::02",
  ":6:54",
  "2:02:-1",
  "2:-1:05",
  "-1:09:05",
  null
```

In this test, the value of the `strTime` input is changed so that the condition returns a true value.  

#### All-c-uses 

- m 
  - **all_c_uses::pairId_3** , **all_c_uses::pairId_4**, **all_c_uses::pairId_5**: these situations follow the respective paths: `<2,3,5>`, `<2,3,5,6>`, `<2,3,5,6,7>` since paths of situations **all_c_uses::pairId_3** and **all_c_uses::pairId_4** are contained in the **all_c_uses::pairId_5**. Which means, that a test that verifies the path of **pairId_5** also will test the paths of ids 3 and 4. The tests applied were created in a previous assignments, and the input contains valid strings. Valid strings are the ones that matches the regex expression `"%d:%02d:%02d"`. An example of test is the `formatSecondsPartitionTest`. This test makes an `assertEquals` and verifies the output against the expected value. The inputs are: 
  
  
  
#### All-p-uses 
- m 
  - **all_p_uses::pairId_1**: to test this path, we must have a situation where the if condition, discussed in the `all-defs` section, is `true`. For this motive, the value of `strTime` must be valid, so that the if condition returns the desired value. For this reason, the test for this section is the same of **all_def::pairId_1**. 
  - **all_p_uses::pairId_2**: to test this path, we must have a situation where the if condition is `false`. In this context, the value of the `strTime` variable must be invalid. For this motive, the tests of this case were developed in the previous assignment in the function test called `parseSecondsInvalidTest`. The values for this test can be checked below: 
```java 
"",
"0:07:60",
"0:90:05",
"0:6:054",
"0:007:05",
"6:54",
"2",
"0:07:",
"0::02",
":6:54",
"2:02:-1",
"2:-1:05",
"-1:09:05",
null;
```
  
#### All-uses 
- m: All uses tests are all the possible pairIds. Thus: 
  - **all_uses::PairId_1**: uses the test described in **all_defs::pairId_1**; 
  - **all_uses::PairId_2**: uses the test described in **all_p_uses::pairId_2**; 
  - **all_uses::PairId_3**: uses the test described in **all_c_uses::pairId_3**; 
  - **all_uses::PairId_4**: uses the test described in **all_c_uses::pairId_4**; 
  - **all_uses::PairId_5**: uses the test described in **all_c_uses::pairId_5**; 
  
## startXmlElement

### What is the purpose of this function and why should it be tested ?
<!-- Why test this function? AKA copy paste -->
#### Dataflow diagram 

#### All-defs 

#### All-c-uses 

#### All-p-uses 

#### All-uses 

### Unit Tests
<!-- for each coverage criteria -->


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

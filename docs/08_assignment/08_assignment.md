# Assignment 8 - Dataflow Testing

## parseSeconds 

### What is the purpose of this function and why should it be tested ?

This application allows the user to manually change the overall time of a task and the time spent doing that same task in the current day. When that manual configuration is performed, this function is used to convert the input of the user into the correspondent time in seconds, which is then used to update the state of the application. For this reason, it is crucial that this function handles the user input as expected, either by performing the right conversion when possible or by throwing the expected exception. 

### Dataflow Testing

#### Dataflow diagram 

![](./images/diagram_parseSeconds.png)

#### Dataflow table
![](./images/table_parseSeconds.png)

#### All-defs 

![](./images/alldefs_parseSeconds.png)

#### All-c-uses
![](./images/allcuses_parseSeconds.png)
    
#### All-p-uses 

![](./images/allpuses_parseSeconds.png)

#### All-uses 
![](./images/alluses_parseSeconds.png)

### Unit Tests and Outcome

#### All-defs 
**m**
- **all_defs::pairId_1**: this test uses the `parseSecondsInvalidTest` created in a previous assignment. This is a `ParameterizedTest`, which receives as argument a value that makes the if condition `true` and passes through the path `<2,3,4>`. The if condition can be checked below: 

```java
if (!m.matches()) throw new ParseException("Invalid seconds-string", 0);
```
  
Since it returns a `Exception` the test uses an `assertThrows`. The parameters tested can be checked below: 
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

**strTime**
- **all_defs::pairId_1**: To exercise the path `<0,2>`, the `strTime` parameter must be initialized with a valid or invalid argument as shown above. However, here we are only worried about the first usage of this variable, in node 2, where we must assure that this input does not cause any undesired effect when creating the `Matcher` instance. In fact, in past assignments, we had already created multiple tests: `parseSecondsInvalidBoundariesTest`, `parseSecondsValidBoundariesTest`, `parseSecondsInvalidTest` and `parseSecondsValidTest` that exercise this path by setting the `strTime` parameter with different values. However, here it would be enough to test it with `null` and with a non-null value (e.g. ""), because the `matcher` function accepts any `CharSequence`, and a `String` variable, which is the case of `strTime` is also a `CharSequence`. The `parseSecondsInvalidTest` `ParametrizedTest` already tests these cases and, in fact, by using a `assertThrows` to check if a `ParseException` is thrown for these invalid values, shows that the null value generates an unexpected `NullPointerException` instead. To sum up:
  - **Inputs**: null, any String (e.g. empty String);
  - **Outcome**: fails for null, passes for empty String.

**hours**
- **all_defs::pairId_1**: Even though the `hours` variable is only defined in node 5, it is not possible to start executing the function on this node, at least in Java. Therefore, we must assure that the function reaches this node, which requires that the condition in node 3 evaluates to false i.e. a valid time String is used. That scenario was already exercised in the `parseSecondsValidTest` and `parseSecondsValidBoundaries` in previous assignments, where different valid values are used and the output time is verified with `assertEquals`. If we were to create a specific test only to test this path, we believe that it would suffice to test a valid time string with only hours, like "24:0:0", so that the return value of node 8 could be accessed only based on the conversion from hours to seconds. To sum up:
  - **Inputs**: "24:0:0" - one of the inputs tested in `parseSecondsValidTest`;
  - **Outcome**: success.

**minutes**
- **all_defs::pairId_1**: This case is similar to the previous one. However it refers the path `<6,8>`. This path was already exercised in the tests of the previous assignments where we use valid time Strings as input - `parseSecondsValidTest` and `parseSecondsValidBoundaries`. If we were to create a specific test only to test this path, we believe that it would suffice to test a valid time string with only minutes, like "0:59:0", so that the return value of node 8 could be accessed only based on the conversion from minutes to seconds. To sum up:
  - **Inputs**: "0:59:0" - one of the inputs tested in `parseSecondsValidTest`;
  - **Outcome**: success.

**seconds**
- **all_defs::pairId_1**: This case is similar to the previous one. However it refers the path `<7,8>`. This path was already exercised in the tests of the previous assignments where we use valid time Strings as input - `parseSecondsValidTest` and `parseSecondsValidBoundaries`. If we were to create a specific test only to test this path, we believe that it would suffice to test a valid time string with only seconds, like "0:0:59", so that the return value of node 8 could be accessed only based on the seconds conversion. To sum up:
  - **Inputs**: "0:0:59" - one of the inputs tested in `parseSecondsValidTest`;
  - **Outcome**: success.

#### All-c-uses 

**m** 
- **all_c_uses::pairId_3** , **all_c_uses::pairId_4**, **all_c_uses::pairId_5**: these cases follow the respective paths: `<2,3,5>`, `<2,3,5,6>`, `<2,3,5,6,7>`. Since the paths **all_c_uses::pairId_3** and **all_c_uses::pairId_4** are contained in the **all_c_uses::pairId_5**, a test that verifies the path of **pairId_5** will also test the paths of ids 3 and 4. The tests applied were created in a previous assignments, and the input consists of valid strings. Valid strings are the ones that match the regex expression `"%d:%02d:%02d"`. An example of a test is the `formatSecondsPartitionTest`. This test makes an `assertEquals` and verifies the output against the expected value. The inputs are: 

**strTime**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `strTime`.

**hours**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `hours`.

**minutes**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `minutes`.

**seconds**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `seconds`.
  
  
#### All-p-uses 
**m** 
- **all_p_uses::pairId_1**: to test this path, we must have a scenario where the if condition, discussed in the `all-defs` section, is `true`. For this reason, the value of `strTime` must be valid, so that the if condition returns the desired value. Therefore, the test for this section is the same of **all_def::pairId_1**. 
- **all_p_uses::pairId_2**: to test this path, we must have a scenario where the if condition is `false`. In this context, the value of the `strTime` variable must be invalid. For this reason, the tests of this case were developed in the previous assignment in the function test called `parseSecondsInvalidTest`. The values for this test can be checked below: 
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

**strTime**
- **all_p_uses::pairId_1**: `strTime` is not used inside any predicate of a branch, thus there are no p-uses.

**hours**
- **all_p_uses::pairId_1**: `hours` is not used inside any predicate of a branch, thus there are no p-uses.

**minutes**
- **all_p_uses::pairId_1**: `minutes` is not used inside any predicate of a branch, thus there are no p-uses.  

 **seconds**
- **all_p_uses::pairId_1**: `seconds` is not used inside any predicate of a branch, thus there are no p-uses.

#### All-uses 
**m**: All-uses tests are all the possible pairIds. Thus: 
- **all_uses::PairId_1**: uses the test described in **all_defs::pairId_1**; 
- **all_uses::PairId_2**: uses the test described in **all_p_uses::pairId_2**; 
- **all_uses::PairId_3**: uses the test described in **all_c_uses::pairId_3**; 
- **all_uses::PairId_4**: uses the test described in **all_c_uses::pairId_4**; 
- **all_uses::PairId_5**: uses the test described in **all_c_uses::pairId_5**; 

**strTime**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `strTime`.

**hours**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `hours`.

**minutes**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `minutes`.

**seconds**
- **all_c_uses::pairId_1**: Already described in `all_defs::pairId_1` of variable `seconds`.

  
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

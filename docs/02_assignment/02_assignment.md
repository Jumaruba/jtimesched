# Assignment 2 - Category-Partition

- Which functions have you selected for testing and why.
- What is the purpose of each function.
- Step-by-step of the ‘Category-Partition’ algorithm for each function.
- Brief description of the unit tests generated for each category.
- Brief description of the outcome of each unit test and whether any test results in a failure (and why).

## Test 1 - project.ProjectSerializer.addXmlElement


### Why test this function?

This application persists the data regarding the tasks in an XML file, allowing the user to keep his tasks from one use to another. Given that the application tracks both the overall and the current day's active time of each task, it is essential that the information is correctly stored and retrieved when we shut down the app and relaunch it later in the same or in different days. For this reason, we decided to test the function that is mostly used to write the information in XML format. In the `writeXml` method of the `ProjectSerializer` class, the static method `addXmlElement` is called 9 times for each project, in order to add the details about each project to an XML element. Therefore, it is essential that this function does what is expected, otherwise, when the data is restored with the `readXml` method, it could fail to be restored or load incorrect information about the projects.

### Function Description
```java
protected static void addXmlElement(TransformerHandler hd, String element, AttributesImpl atts, Object data);
```

Adds an xml element with the tag `element`, the attributes `atts` and the data `data` to `hd`.

e.g. The following XML content would be the result of adding an element named "note" with the attribute "author" and the data "This is a note".
```XML
<?xml version="1.0" encoding="UTF-8"?>
<note author="John Doe">This is a note</note>
```

### Category-Partition
1. **Parameters/Input**:
- `hd`, a `TransformerHandler` used to build the XMl;
- `element`, a `String` representing the name of the element (tag);
- `atts`, a `AttributesImpl` representing the attributes of the `element`;
- `data`, the content of the `element`, which may be text or other elements;
2. **Characteristics of each parameter**: Considering the requirements, ... 
> TODO
3. **Constraints**: 
> TODO
4. **Unit Tests**: 
> TODO

### Unit Tests & Outcome
The tests implemented can be found [here](../../src/test/java/de/dominik_geyer/jtimesched/project/ProjectSerializerTest.java).

> TODO
---

## Test 2 - project.Project.setSecondsToday

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

--- 

## Test 3 - project.ProjectTime.parseSeconds

### Why test this function?

### Function Description

### Category-Partition

### Unit Tests

### Outcome

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

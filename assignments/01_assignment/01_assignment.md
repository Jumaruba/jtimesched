# 01 Assignment - Static testing 

## Description 

> Brief description of your project. For example, What is it? How is the source-code organized?


JTimeSched is a lightweight time tracking tool, which allows the user to track the time of tasks and projects.
With its simple GUI, JTimeSched only provides the essential features for tracking the time spent on specific tasks, including the ability to create and delete tasks and to track the elapsed time on the current day and the overall elapsed time. Additionally, it allows the user to categorize the tasks and sort them by title, category, date created, time overall, time today, or current state.

The project is organized in the following structure: 
```
.
├── assets
├── conf (generated upon build)
├── dist
├── launcher
├── pom.xml
├── src
└── target
```

- The `launcher` contains the files to execute the program; 
- The `src` contains the source code of the application, which will be explained in more details next; 
- The `dist` folder contains a script to generate the project distribution; 
- The `conf` folder is generated upon build and stores the serialized tasks and data of the application.

### The source code 
The source code is organized in three main folders: 

- `gui`: responsible for rendering the application; 
- `misc`: contains some utils code, namely a class containing one function to format date time; 
- `project`: contains the classes required to represent a task/project and to format their time.

It also includes the application's entry class: `JTimeSchedApp`.

## What is static testing? And why is important and relevant? 

Static testing is a type of verification, in other words, it checks if the software works correctly, by verifying if the systems contains faults (bugs). 

There're four main reasons for testing an application: 
- Improves the client satisfaction; 
- Increases the software quality; 
- Improves the security;
- There's a certain cost-benefit, since fixing faults (bugs) in early project costs less than finding it later. 

The static verification, by it self, provides the following advantages:
- Detect early warnings, which might prevent some faults;  
- Detect faults early; 
- Improves the maintainability of the code. Some tools, such as the `checkstyle` improve the maintainability, by standardizing the style of the code;
- Detect dependencies and some inconsistencies. 

To sum up, the static testing can be categorized in **manual examination** and **automated analysis**. The manual examination consists in analysing the code manually by doing reviews, while the automated analysis uses tools to automate the process. 

## Testing tools

> Brief description of the static testing tool used in this assignment and how was it configured for your project. Your description must explain, e.g., why did you enabled or disabled any default configuration or bug pattern.

### BugSpot

#### Description

TODO

#### Report produced 

> Brief description of the report produced by the static testing tool. 

**=== BUGS FOUND ===**

**1) BAD_PRACTICE :: RV_RETURN_VALUE_IGNORED_BAD_PRACTICE**

Originally the code was written as follows: 
```java
    if (!dirConf.isDirectory()) 
        dirConf.mkdir();
```

The code was modified to use the returned value and exit the program in case it isn't possible to initialize the folder, since it is necessary to store information. 
```java
Boolean directoryCreated = false; 
if (!dirConf.isDirectory()) {
    directoryCreated = dirConf.mkdir();
    if (!directoryCreated){
        System.err.println("Not able to create directory");
        System.exit(1); 
    }
}
```

**2) STYLE :: SF_SWITCH_NO_DEFAULT**

The code was written as follows: 
```java
switch (column) {
    case ProjectTableModel.COLUMN_ACTION_DELETE:
        if (e.getClickCount() == 2)
            handleDelete(tstm, prj, row);
        break;
    case ProjectTableModel.COLUMN_ACTION_STARTPAUSE:
        handleStartPause(prj);
        break;
    }
```

The `SF_SWITCH_NO_DEFAULT` complains about the missing default condition in the code. The code was modified to satisfy this conditions: 
```java
switch (column) {
    case ProjectTableModel.COLUMN_ACTION_DELETE:
        if (e.getClickCount() == 2)
            handleDelete(tstm, prj, row);
        break;
    case ProjectTableModel.COLUMN_ACTION_STARTPAUSE:
        handleStartPause(prj);
        break;
    default: 
        System.err.println("Unknown option for mouse event");
        break;
}
```

**3) PERFORMANCE :: DM_NUMBER_CTOR**

The code was written as follows:
```java
tf.setAttribute("indent-number", Integer.valueOf(4));
```

The code was modified in order to get a better performance. The constructor has actually been deprecated given that it is rarely appropriate to use it. As suggested in the documentation: ["The static factory valueOf(int) is generally a better choice, as it is likely to yield significantly better space and time performance"](https://docs.oracle.com/javase/9/docs/api/java/lang/Integer.html#Integer-int-); and that's the reason why we decided to use `Integer.valueOf()` instead:
```java
tf.setAttribute("indent-number", Integer.valueOf(4));
```
Similar modifications were performed for all the cases where the Integer, Long or Boolean constructors were used.

**4) MALICIOUS_CODE :: EI_EXPOSE_REP2** 

The code was written as follows: 
```java 
@Override
public Component getTableCellEditorComponent(JTable table,
        Object value, boolean isSelected, int row, int column) {
    this.oldDate = (Date)value;
    String strDate = ProjectTime.formatDate(this.oldDate);
    this.tfEdit.setText(strDate);
    
    return this.tfEdit;
}
```

The value returned is a reference to a mutable object. According to the [documentation](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#EI_EXPOSE_REP2): 
> Returning a reference to a mutable object value stored in one of the object's fields exposes the internal representation of the object.  

The code was modified so that a copy of the `tfEdit` is returned. The field is copied by a new function called : 
```java
@Override
public Component getTableCellEditorComponent(JTable table,
        Object value, boolean isSelected, int row, int column) {
    this.oldDate = (Date)value;
    String strDate = ProjectTime.formatDate(this.oldDate);
    this.tfEdit.setText(strDate);

    return this.getTfEditCopy();
}

public Component getTfEditCopy(){
    // Returns a copy of the object, to not expose data. 
    JTextField tfEditCopy = (JTextField) this.getComponent();
    tfEditCopy.setHorizontalAlignment(SwingConstants.CENTER);
    tfEditCopy.setText(this.tfEdit.getText());
    return tfEditCopy;
}
```

**5) STYLE :: DLS_DEAD_LOCAL_STORE** 

Previously the code was written as such: 
```java 


```

> Brief description of the 5x2 randomly selected bugs.

#### Bug fixing 

> Brief description of how those 5x2 bugs were addressed/fixed. Tip: provide examples before and after fixing those bugs.


### PMD

#### Description
TODO

#### Report produced 

> Brief description of the report produced by the static testing tool. 

**=== BUGS FOUND ===**

**1) ClassWithOnlyPrivateConstructorsShouldBeFinal**


## Components 

- Diana Freitas :: up201806230
- Juliane Marubayashi :: up201800175 


--- 
To bundle the project’s source code and all its dependencies, you must run mvn package.
This command should create the
    target/jtimesched-1.2-SNAPSHOT-jar-with-dependencies.jar

which could then be executed as
    java -jar target/jtimesched-1.2-SNAPSHOT-jar-with-dependencies.jar

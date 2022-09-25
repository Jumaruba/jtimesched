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
Similar modifications were performed for all the cases where the Integer or Long constructors were used.
For the same reason, when the Boolean constructor was used it was modified from `new Boolean(true)` to `Boolean.TRUE` and from `new Boolean(false)` to `Boolean.FALSE`.

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

**5) BAD_PRACTICE :: DM_EXIT** 

Previously the code was written as such: 
```java 
// load project-file
try {
    this.loadProjects();
} catch (FileNotFoundException e) {
    JTimeSchedApp.getLogger().info("Projects file does not exist, starting with empty projects file.");
} catch (Exception e) {
    e.printStackTrace();
    JTimeSchedApp.getLogger().severe("Error loading projects file: " + e.getMessage());
    
    JOptionPane.showMessageDialog(this,
            "An error occurred while loading the projects file.\n" +
            "Details: \"" + e.getMessage() + "\"\n\n" +
            "Please correct or remove the file '" + JTimeSchedApp.PRJ_FILE + "' " +
            "(or replace it with the backup file '" + JTimeSchedApp.PRJ_FILE_BACKUP + "', if present).\n\n" +
            "JTimeSched will quit now to avoid data corruption.",
            "Error loading projects file",
            JOptionPane.ERROR_MESSAGE);
    System.exit(1);
}
```

This code contains a line with `System.exit(1)`. This is considered bad practice by the SpotBugs, since it shuts down the entire machine code. And this makes impossible for the code being called by another. 


The code was rewritten to throw an exception: 

```java
// load project-file
try {
    this.loadProjects();
} catch (FileNotFoundException e) {
    JTimeSchedApp.getLogger().info("Projects file does not exist, starting with empty projects file.");
} catch (Exception e) {
    e.printStackTrace();
    JTimeSchedApp.getLogger().severe("Error loading projects file: " + e.getMessage());
    
    JOptionPane.showMessageDialog(this,
            "An error occurred while loading the projects file.\n" +
            "Details: \"" + e.getMessage() + "\"\n\n" +
            "Please correct or remove the file '" + JTimeSchedApp.PRJ_FILE + "' " +
            "(or replace it with the backup file '" + JTimeSchedApp.PRJ_FILE_BACKUP + "', if present).\n\n" +
            "JTimeSched will quit now to avoid data corruption.",
            "Error loading projects file",
            JOptionPane.ERROR_MESSAGE);
    
    throw new RuntimeException("Error loading projects file: " + e.getMessage()); 
}
```

On this way, the program will finish with an Exception. 


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

`ProjectTime` is a class that is only used to format and parse the time, and therefore it only uses private constructors and static methods. For this reason, it should be final.

The original code was the following:
```java
public class ProjectTime {
	private static final String fmtDate = "yyyy-MM-dd";
	
	private ProjectTime() {}
	
	public static String formatSeconds(int s) {
		return String.format("%d:%02d:%02d", s/3600, (s%3600)/60, (s%60));
	}
    ...
}
```

And we made the class final:
```java
public final class ProjectTime {
	private static final String fmtDate = "yyyy-MM-dd";
	
	private ProjectTime() {}
	
	public static String formatSeconds(int s) {
		return String.format("%d:%02d:%02d", s/3600, (s%3600)/60, (s%60));
	}
    ...
}
```

**2) UseEqualsToCompareStrings**

To compare strings ...
```java
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if (e.getActionCommand() == NOCOLOR) {
			this.selectedColor = null;
		} else if (e.getActionCommand() == CHOOSER) {
			Color chosenColor = JColorChooser.showDialog(ColorDialog.this,
					"Choose a custom color",
					ColorDialog.this.currentColor);
			
			if (chosenColor != null)
				this.selectedColor = chosenColor;
			else
				this.selectedColor = this.currentColor;
		} else {
			this.selectedColor = btn.getBackground();
		}
		
		this.setVisible(false);
		this.dispose();
	}
```

Which was fixed by replacing the `==` with the use of `equals()`:
```java
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if (e.getActionCommand().equals(NOCOLOR)) {
			this.selectedColor = null;
		} else if (e.getActionCommand().equals(CHOOSER)) {
			Color chosenColor = JColorChooser.showDialog(ColorDialog.this,
					"Choose a custom color",
					ColorDialog.this.currentColor);
			
			if (chosenColor != null)
				this.selectedColor = chosenColor;
			else
				this.selectedColor = this.currentColor;
		} else {
			this.selectedColor = btn.getBackground();
		}
		
		this.setVisible(false);
		this.dispose();
	}
```

When fixing this error, a new bug was reported: `LiteralsFirstInComparisons`; because literals should come first in all String comparisons, avoiding the possibility of NullPointerExceptions. If the object that may be null is instead used as the parameter, no exception will occur if it is null, it will just return false.
For this reason, to fix the new bugs, we changed the code to the following:
```java
@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if (NOCOLOR.equals(e.getActionCommand())) {
			this.selectedColor = null;
		} else if (CHOOSER.equals(e.getActionCommand())) {
			Color chosenColor = JColorChooser.showDialog(ColorDialog.this,
					"Choose a custom color",
					ColorDialog.this.currentColor);
			
			if (chosenColor != null)
				this.selectedColor = chosenColor;
			else
				this.selectedColor = this.currentColor;
		} else {
			this.selectedColor = btn.getBackground();
		}
		
		this.setVisible(false);
		this.dispose();
	}
```

## Components 

- Diana Freitas :: up201806230
- Juliane Marubayashi :: up201800175 


--- 
To bundle the project’s source code and all its dependencies, you must run mvn package.
This command should create the
    target/jtimesched-1.2-SNAPSHOT-jar-with-dependencies.jar

which could then be executed as
    java -jar target/jtimesched-1.2-SNAPSHOT-jar-with-dependencies.jar

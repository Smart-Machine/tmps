# *SOLID Principles*


## Author: Radu Calin 

----

## Objectives:

&ensp; &ensp; __1. Study and understand the SOLID Principles.__

&ensp; &ensp; __2. Choose a domain, define its main classes/models/entities and choose the appropriate instantiation mechanisms.__

&ensp; &ensp;__3. Create a sample project that respects SOLID Principles.__


## Used SOLID Principles:

### Single Responsibility Principle (SRP):

This principle states that a class should have only one reason to change, 
meaning it should have only one responsibility or job.

### Open/Closed Principle (OCP):

The Open/Closed Principle states that software entities (e.g., classes, modules) should be open for extension but closed for modification.
This means you should be able to add new functionality to a system without altering existing code.

### Liskov Substitution Principle (LSP):

The Liskov Substitution Principle states that objects of a derived class should be able to replace objects 
of the base class without affecting the correctness of the program.

### Interface Segregation Principle (ISP):

This principle suggests that clients should not be forced to depend on interfaces they do not use.

### Dependency Inversion Principle (DIP):

The Dependency Inversion Principle emphasizes high-level modules (e.g., abstractions) should not depend on low-level modules (e.g., concrete implementations). 
Both should depend on abstractions.

## Implementation:
### Overview
An instance of the application (`TermEditorCLI`) is invoked in the main thread. `TermEditorCLI` is responsible for taking user input, the creation of the appropriate instances in accordance to the user's input, helper message, etc. 
Depending on the provided option by the user, one of the following three classes will be invoked: `TermEditorCreateCommand/TermEditorEditCommand/TermEditorDeleteCommand`. 
These classes are responsible for taking user input about the file, and invoking the respective method from the `TermEditorFileManager`.  
The file manager class is responsible for file I/O and metadata, also functions like conversion from one `LocalTime` to `FileTime` are part of this class. 
The `TermEditorFile` is the file type, and the `TermEditorBashFile` is a child of it, with custom permissions and functionality.

### DIP
One of the attributes in the `TermEditorCLI` is the following one:
```java
private ITermEditorCommand termEditorCommand;
```  
By the use of the `getTermEditorCommandInstance` function in accordance to the given option, an instance of the attribute is retured as follows:
```java
...
switch (option) {
    case CREATE:
        return new TermEditorCreateCommand(file);
    case EDIT:
        return new TermEditorEditCommand(file);
    case DELETE:
        return new TermEditorDeleteCommand(file);
...
``` 
Afterwards the `execute` method is called, as follows:
```java
termEditorCommand.execute();
```
This is an example of the Dependency Inversion Principle, where for the class `TermEditorCLI` the specifics of the `execute` method isnt't important.

### ISP
The file class depends on two separated interfaces, as follows:
```java
public class TermEditorFile implements ITermEditorFile, ITermEditorFileTimeMetadata ...
```
where
```java
public interface ITermEditorFile {
    public String getContent();

    public void setContent(String content);

    public String getFileName();

    public void setFileName(String fileName);

    public String getFileExtenstion();

    public void setFileExtension(String fileExtension);
}
```

```java
public interface ITermEditorFileTimeMetadata {
    public LocalDateTime getCreationTime();

    public void setCreationTime(LocalDateTime creationTime);

    public LocalDateTime getLastModificationDate();

    public void setLastModificationDate(LocalDateTime lastModificationDate);

}
```
The separation is made due to the functionality they reflect. 

### LSP
The `TermEditorCLI` also has the following attribute:
```java
private TermEditorFile file;
```
And depending on the file extension the right instance is invoked:
```java
...
switch (fileExtenstion) {
    case "sh":
        file = new TermEditorBashFile(filename);
        break;
    default:
        file = new TermEditorFile(filename);
        break;
}
...
```
Due to the implicite upcasting and dynamic binding, the LSP is present in this example.

### OCP
The inheritance of the `TermEditorBashFile` that overrided the parent class (`TermEditorFile`) method in order to add extra functionality is an example of OCP.

### SRP
The user input separation, first for the option and different parameters, second for the file content itself, and it all separated from the file I/O is a great example of the SRP.

## Results:
By the use of the makefile the following output is met:
```
Building...
Output:
Options:
	1. Create
	2. Edit
	3. Delete
Option := 1
Filename := main.sh
Enter file content below (Ctrl+D to save and exit):
echo "Hello, World"
File main.sh created.
Cleaning...
removed 'lab1/Main.class'
removed 'lab1/enums/Option.class'
removed 'lab1/models/ITermEditorCommand.class'
removed 'lab1/models/ITermEditorCommandFile.class'
removed 'lab1/models/ITermEditorFile.class'
removed 'lab1/models/ITermEditorFileTimeMetadata.class'
removed 'lab1/models/TermEditorBashFile.class'
removed 'lab1/models/TermEditorCLI$1.class'
removed 'lab1/models/TermEditorCLI.class'
removed 'lab1/models/TermEditorCreateCommand.class'
removed 'lab1/models/TermEditorDeleteCommand.class'
removed 'lab1/models/TermEditorEditCommand.class'
removed 'lab1/models/TermEditorFile.class'
removed 'lab1/models/TermEditorFileManager.class'
```
In this example a file is created. The file has the extension `.sh`, which means that the program changed it's permissions, and now the file is executable, and when run the following is printed:
```
$ main.sh
Hello, World
``` 
In order to edit the file, the second option should be chosen, as follows:
```
Building...
Output:
Options:
	1. Create
	2. Edit
	3. Delete
Option := 2
Filename := main.sh
The content of file:
#!/bin/bash
echo "Hello, World"
Enter the edit file content below (Ctrl+D to save and exit):
echo "hi"
File main.sh edited.
Cleaning...
removed 'lab1/Main.class'
removed 'lab1/enums/Option.class'
removed 'lab1/models/ITermEditorCommand.class'
removed 'lab1/models/ITermEditorCommandFile.class'
removed 'lab1/models/ITermEditorFile.class'
removed 'lab1/models/ITermEditorFileTimeMetadata.class'
removed 'lab1/models/TermEditorBashFile.class'
removed 'lab1/models/TermEditorCLI$1.class'
removed 'lab1/models/TermEditorCLI.class'
removed 'lab1/models/TermEditorCreateCommand.class'
removed 'lab1/models/TermEditorDeleteCommand.class'
removed 'lab1/models/TermEditorEditCommand.class'
removed 'lab1/models/TermEditorFile.class'
removed 'lab1/models/TermEditorFileManager.class'
```
Which means, 
```
$ main.sh
hi
```
And when the file is no longer needed we can delete it like this:
```
Building...
Output:
Options:
	1. Create
	2. Edit
	3. Delete
Option := 3
Filename := main.sh
File main.sh deleted.
Cleaning...
removed 'lab1/Main.class'
removed 'lab1/enums/Option.class'
removed 'lab1/models/ITermEditorCommand.class'
removed 'lab1/models/ITermEditorCommandFile.class'
removed 'lab1/models/ITermEditorFile.class'
removed 'lab1/models/ITermEditorFileTimeMetadata.class'
removed 'lab1/models/TermEditorBashFile.class'
removed 'lab1/models/TermEditorCLI$1.class'
removed 'lab1/models/TermEditorCLI.class'
removed 'lab1/models/TermEditorCreateCommand.class'
removed 'lab1/models/TermEditorDeleteCommand.class'
removed 'lab1/models/TermEditorEditCommand.class'
removed 'lab1/models/TermEditorFile.class'
removed 'lab1/models/TermEditorFileManager.class'
```
We successfully deleted the file:
```
$ ls -la lab1/output
total 0
drwxr-xr-x. 1 uni uni  0 Sep 29 05:09 .
drwxr-xr-x. 1 uni uni 70 Sep 29 05:09 ..
```

## Conclusion:
By following these SOLID principles, we can create code that is easier to understand, 
maintain, and extend. These principles contribute to the development of robust and 
flexible software systems that are less prone to bugs and easier to adapt to changing requirements.
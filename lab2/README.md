# Topic: *Creational Design Patterns*
## Author: *Radu Calin*
------
## Objectives:
&ensp; &ensp; __1. Study and understand the Creational Design Patterns.__

&ensp; &ensp; __2. Choose a domain, define its main classes/models/entities and choose the appropriate instantiation mechanisms.__

&ensp; &ensp; __3. Use some creational design patterns for object instantiation in a sample project.__

## Theory:
&ensp; &ensp; Creational design patterns are a category of design patterns that focus on the process of object creation. They provide a way to create objects in a flexible and controlled manner, while decoupling the client code from the specifics of object creation. Creational design patterns address common problems encountered in object creation, such as how to create objects with different initialization parameters, how to create objects based on certain conditions, or how to ensure that only a single instance of an object is created. There are several creational design patterns that have their own strengths and weaknesses. Each of it is best suited for solving specific problems related to object creation. By using creational design patterns, developers can improve the flexibility, maintainability, and scalability of their code.

&ensp; &ensp; Some examples of this kind of design patterns are:

   * Singleton
   * Builder
   * Prototype
   * Object Pooling
   * Factory Method
   * Abstract Factory

## Implementation:
**Singleton Pattern**
There should be only one instance of the `TermEditorCLI` class, which starts the application. There are two functions to achieve the implementation of **Singleton Pattern**:

```java
    public static TermEditorCLI getInstance() {
        if (instance == null) {
            instance = new TermEditorCLI();
        }
        return instance;
    }
```
The `getInstance` function is responsible for creating the single instance of the app.

```java
    public void init(Scanner scanner, String[] args) {
        if (TermEditorCLI.scanner == null) {
            TermEditorCLI.scanner = scanner;
        }
        if (TermEditorCLI.args == null) {
            TermEditorCLI.args = args;
        }
    }

```
The `init` function is responsible for assigning the parameters of the application.

The reason behind separating the logic of instantiation into two functions is keeping the functions pure, as there might be cases when we need only the instance of the class and not the creation of a new one. 

**Factory Method**
In the `TermEditorCLI` class, in the dependence of the user's chosen option, the app is creating a Command Executor.
```java

    public TermEditorCommandExecutor getTermEditorCommandExecutorInstance(Option option, String fileName) throws Exception {
        switch (option) {
            case CREATE:
                return new TermEditorCreateCommandExecutor(fileName);
            case EDIT:
                return new TermEditorEditCommandExecutor(fileName);
            case DELETE:
                return new TermEditorDeleteCommandExecutor(fileName);
            case INVALID:
                throw new Exception("Invalid option for TermEditorCommand instance was given");
            default:
                throw new Exception("No option for TermEditorCommand instance was matched");
        }
    }
```

In the case of `CREATE` option, the following instance of the following class is created:
```java
    public class TermEditorCreateCommand implements ITermEditorCommand {
        private Scanner scanner;
        private String fileName;
        private TermEditorFileManager fileManager;

        public TermEditorCreateCommand(String fileName) {
            this.fileName = fileName;
            this.scanner = new Scanner(System.in);
        }

        public void execute() {
            StringBuilder userInput = new StringBuilder();

            System.out.println("Enter file content below (Ctrl+D to save and exit):");
            while (scanner.hasNextLine()) {
                userInput.append(scanner.nextLine()).append("\n");
            }

            fileManager = new TermEditorFileManager(fileName);
            fileManager.setFileContent(userInput.toString());
            fileManager.createFile();
        }
    }
```
The `TermEditorCreateCommand` is responsible for the creation of a new file, by the use of `TermEditorFileManager`.

**Builder**
In the `TermEditorFileManager`, we are creating a `FileBuilder` in the dependence of the file extension:
```java
    switch (fileExtension) {
        case "sh":
            fileBuilder = (TermEditorFileBuilder) new TermEditorBashFileBuilder();
            break;
        case "py":
            fileBuilder = (TermEditorFileBuilder) new TermEditorPythonFileBuilder();
            break;
        default:
            fileBuilder = new TermEditorFileBuilder();
            break;
    }
```
Following the case of the file creation:
```java
    public void createFile() {
        fileBuilder.setFileName(fileName);
        fileBuilder.setFileExtension(fileExtension);
        fileBuilder.setFileContent(fileContent);
        fileBuilder.setFileCreationTime(convertLocalDateTimeToFileTime(LocalDateTime.now()));
        fileBuilder.setFileLastModificationDate(convertLocalDateTimeToFileTime(LocalDateTime.now()));
        fileBuilder.build();
    }
```


## Conclusion:
**Singleton Pattern:**
The Singleton pattern ensures that a class has only one instance throughout an application's life. It's used when a single point of control or global access is needed, but it should be used sparingly to avoid global state and tight coupling.

**Factory Pattern:**
The Factory pattern abstracts object creation by providing an interface for creating objects. Subclasses or implementing classes can change the type of objects produced. This promotes loose coupling, enhancing flexibility and maintainability.

**Builder Pattern:**
The Builder pattern helps create complex objects by separating the construction process from the actual representation.
It's useful for objects with optional parameters, making code more readable and maintainable.


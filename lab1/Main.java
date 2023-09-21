package lab1;

import lab1.models.TermEditorCLI;
import java.util.Scanner;

/*
 * S -> Single responsability (Each class has a single responsability)
 * O -> Open/Closed           (Open for extension, closed for modification)
 * L -> Liskov Substitution   (The substitution with a subtype should not brake the logic)
 * I -> Interface Segregation (Large interfaces should be split into smaller once)
 * D -> Dependency Inversion  (Depend on abstractions, like interfaces)
 */

/*
 * File -> a class that has all the data related to the file, the extansion, metadata and so on
 * FileInvoker -> a class that manages the File class, adds the content and metadata
 * TermEditorCLI -> a class that prints the help message, takes user input, and so on
 * TermEditorCommand -> an optional class/interface that will encapsulate the common logci for the following command classes
 * TermEditorCreateCommand -> a class that creates a new file
 * TermEditorEditCommand -> a class that edits the contents of an existing file
 * TermEditorDeleteCommand -> a class that deletes an existing file
 */

public class Main {
    private static TermEditorCLI termEditorCLI;

    public static void main(String[] args) {
        termEditorCLI = new TermEditorCLI(new Scanner(System.in), args);
        termEditorCLI.start();
    }

}

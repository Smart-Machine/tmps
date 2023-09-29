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

public class Main {
    private static TermEditorCLI termEditorCLI;

    public static void main(String[] args) {
        termEditorCLI = new TermEditorCLI(new Scanner(System.in), args);
        termEditorCLI.start();
    }

}

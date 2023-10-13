package lab2;

import lab2.models.TermEditorCLI;
import java.util.Scanner;

public class Main {
    private static TermEditorCLI termEditorCLI;

    public static void main(String[] args) {
        termEditorCLI = TermEditorCLI.getInstance();
        termEditorCLI.init(new Scanner(System.in), args);
        termEditorCLI.start();
    }

}

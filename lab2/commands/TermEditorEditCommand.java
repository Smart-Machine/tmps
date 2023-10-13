package lab2.commands;

import java.util.Scanner;

import lab2.models.TermEditorFileManager;

public class TermEditorEditCommand implements ITermEditorCommand {
    private Scanner scanner;
    private String fileName;
    private TermEditorFileManager fileManager;

    public TermEditorEditCommand(String fileName) {
        this.fileName = fileName;
        this.scanner = new Scanner(System.in);
    }

    public void execute() {
        StringBuilder userInput = new StringBuilder();

        fileManager = new TermEditorFileManager(fileName);
        fileManager.readFile();

        System.out.println("Enter the edit file content below (Ctrl+D to save and exit):");
        while (scanner.hasNextLine()) {
            userInput.append(scanner.nextLine()).append("\n");
        }

        fileManager.setFileContent(userInput.toString());
        fileManager.createFile();
    }

}

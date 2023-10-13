package lab2.commands;

import java.util.Scanner;

import lab2.models.TermEditorFileManager;

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

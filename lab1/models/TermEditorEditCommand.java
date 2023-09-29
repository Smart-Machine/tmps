package lab1.models;

import java.util.Scanner;

public class TermEditorEditCommand implements ITermEditorCommand, ITermEditorCommandFile {
    private TermEditorFile file;
    private TermEditorFileManager fileManager;
    private Scanner scanner;

    public TermEditorEditCommand(TermEditorFile file) {
        this.file = file;
        this.fileManager = new TermEditorFileManager(file);
        this.scanner = new Scanner(System.in);
    }

    public TermEditorFile getFile() {
        return this.file;
    }

    public void setFile(TermEditorFile file) {
        this.file = file;
    }

    public void execute() {
        String fileContent;
        StringBuilder userInput = new StringBuilder();

        fileManager.readFile();

        System.out.println("Enter the edit file content below (Ctrl+D to save and exit):");
        while (scanner.hasNextLine()) {
            userInput.append(scanner.nextLine()).append("\n");
        }
        fileContent = userInput.toString();
        file.setContent(fileContent);

        fileManager.editFile();
    }

}

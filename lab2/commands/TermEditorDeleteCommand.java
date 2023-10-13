package lab2.commands;

import lab2.models.TermEditorFileManager;

public class TermEditorDeleteCommand implements ITermEditorCommand {
    private String fileName;
    private TermEditorFileManager fileManager;

    public TermEditorDeleteCommand(String fileName) {
        this.fileName = fileName;
    }

    public void execute() {
        fileManager = new TermEditorFileManager(fileName);
        fileManager.deleteFile();
    }
}

package lab1.models;

public class TermEditorDeleteCommand implements ITermEditorCommand, ITermEditorCommandFile {
    private TermEditorFile file;
    private TermEditorFileManager fileManager;

    public TermEditorDeleteCommand(TermEditorFile file) {
        this.file = file;
        this.fileManager = new TermEditorFileManager(file);
    }

    public TermEditorFile getFile() {
        return this.file;
    }

    public void setFile(TermEditorFile file) {
        this.file = file;
    }

    public void execute() {
        fileManager.deleteFile();
    }
}

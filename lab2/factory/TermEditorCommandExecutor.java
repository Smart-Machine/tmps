package lab2.factory;

import lab2.commands.ITermEditorCommand;

public abstract class TermEditorCommandExecutor {
    private String fileName;    

    public TermEditorCommandExecutor(String fileName) {
        this.fileName = fileName;
    }
    
    public void executeCommand() {
        ITermEditorCommand command = createCommand(fileName);
        command.execute();
    }

    public abstract ITermEditorCommand createCommand(String fileName);
}

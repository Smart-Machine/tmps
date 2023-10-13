package lab2.factory;

import lab2.commands.ITermEditorCommand;
import lab2.commands.TermEditorCreateCommand;

public class TermEditorCreateCommandExecutor extends TermEditorCommandExecutor {
    
    public TermEditorCreateCommandExecutor(String fileName) {
        super(fileName);
    }

    @Override
    public ITermEditorCommand createCommand(String fileName) {
        return new TermEditorCreateCommand(fileName);
    }
}

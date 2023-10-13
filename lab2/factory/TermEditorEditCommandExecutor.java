package lab2.factory;

import lab2.commands.ITermEditorCommand;
import lab2.commands.TermEditorEditCommand;

public class TermEditorEditCommandExecutor extends TermEditorCommandExecutor {

    public TermEditorEditCommandExecutor(String fileName) {
        super(fileName);
    }

    @Override
    public ITermEditorCommand createCommand(String fileName) {
        return new TermEditorEditCommand(fileName);
    }

}

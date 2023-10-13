package lab2.factory;

import lab2.commands.ITermEditorCommand;
import lab2.commands.TermEditorDeleteCommand;

public class TermEditorDeleteCommandExecutor extends TermEditorCommandExecutor {

    public TermEditorDeleteCommandExecutor(String fileName) {
        super(fileName);
    }

    @Override
    public ITermEditorCommand createCommand(String fileName) {
        return new TermEditorDeleteCommand(fileName);
    }

}

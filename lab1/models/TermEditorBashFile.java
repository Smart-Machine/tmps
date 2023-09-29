package lab1.models;

public class TermEditorBashFile extends TermEditorFile {

    public TermEditorBashFile(String fileName) {
        super(fileName);
    }

    @Override
    public void setContent(String content) {
        content = "#!/bin/bash\n" + content;
        super.setContent(content);
    }
}

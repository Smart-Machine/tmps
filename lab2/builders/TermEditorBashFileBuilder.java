package lab2.builders;

public class TermEditorBashFileBuilder extends TermEditorFileBuilder {
    @Override
    public void setFileContent(String fileContent) {
        super.setFileContent("#!/bin/bash\n" + fileContent);
    }
}

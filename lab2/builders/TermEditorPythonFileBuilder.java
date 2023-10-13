package lab2.builders;

public class TermEditorPythonFileBuilder extends TermEditorFileBuilder {
    @Override
    public void setFileContent(String fileContent) {
        super.setFileContent("#!/usr/bin/env python3\n" + fileContent);
    }
}
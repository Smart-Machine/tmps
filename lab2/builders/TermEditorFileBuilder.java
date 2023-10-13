package lab2.builders;

import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.io.FileWriter;
import java.io.IOException;

import lab2.files.TermEditorFile;

public class TermEditorFileBuilder implements ITermEditorFile, ITermEditorFileTimeMetadata {
    private String fileName;
    private String fileExtension;
    private String fileContent;
    private FileTime fileCreationTime;
    private FileTime fileLastModificationDate;

    public TermEditorFile build() {
        TermEditorFile file = new TermEditorFile(fileName, fileExtension, fileContent, fileCreationTime, fileLastModificationDate);

        try {
            if (file.getFileCreationTime() != null) {
                Files.setAttribute(file.getFileOutput().toPath(), "basic:creationTime", fileCreationTime);
            }
            if (file.getFileLastModificationDate() != null) {
                Files.setAttribute(file.getFileOutput().toPath(), "basic:lastModifiedTime", fileLastModificationDate);
            }

            if (file.getFileContent() != null) {
                FileWriter fileWriter = new FileWriter(file.getFileOutput());
                if (file.getFileExtension().equals("sh") || file.getFileExtension().equals("py")) {
                    file.getFileOutput().setExecutable(true);
                }
                fileWriter.write(file.getFileContent());
                System.out.println("File " + file.getFileName() + " created.");
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
            System.exit(-1);
        }

        return file;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public void setFileCreationTime(FileTime fileCreationTime) {
        this.fileCreationTime = fileCreationTime;
    }

    @Override
    public void setFileLastModificationDate(FileTime fileLastModificationDate) {
        this.fileLastModificationDate = fileLastModificationDate;
    }

}

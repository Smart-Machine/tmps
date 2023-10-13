package lab2.files;

import java.nio.file.attribute.FileTime;

import java.io.File;

public class TermEditorFile {
    private String fileName;
    private String fileExtension;
    private String filePath;
    private String fileContent;
    private FileTime fileCreationTime;
    private FileTime fileLastModificationDate;
    private File fileOutput;

    public TermEditorFile(String fileName, String fileExtension, String fileContent, FileTime fileCreationTime,
            FileTime fileLastModificationDate) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileContent = fileContent;
        this.fileCreationTime = fileCreationTime;
        this.fileLastModificationDate = fileLastModificationDate;

        this.filePath = "lab2/output/";
        this.fileOutput = new File(this.filePath + this.fileName);
        try {
            fileOutput.createNewFile();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.getStackTrace();
            System.exit(-1);
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public String getFileContent() {
        return this.fileContent;
    }

    public FileTime getFileCreationTime() {
        return this.fileCreationTime;
    }

    public FileTime getFileLastModificationDate() {
        return this.fileLastModificationDate;
    }

    public File getFileOutput() {
        return this.fileOutput;
    }

}

package lab2.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDateTime;

import lab2.files.TermEditorFile;
import lab2.builders.TermEditorFileBuilder;
import lab2.builders.TermEditorBashFileBuilder;
import lab2.builders.TermEditorPythonFileBuilder;

public class TermEditorFileManager {
    private TermEditorFile file;
    private String fileName;
    private String fileExtension;
    private String fileContent;
    private TermEditorFileBuilder fileBuilder;

    public TermEditorFileManager(String fileName) {
        this.fileName = fileName;
        try {
            this.fileExtension = fileName.split("\\.")[1];
            switch (fileExtension) {
                case "sh":
                    fileBuilder = (TermEditorFileBuilder) new TermEditorBashFileBuilder();
                    break;
                case "py":
                    fileBuilder = (TermEditorFileBuilder) new TermEditorPythonFileBuilder();
                    break;
                default:
                    fileBuilder = new TermEditorFileBuilder();
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            fileBuilder = new TermEditorFileBuilder();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(-1);
        }
    }

    public TermEditorFile getFile() {
        return this.file;
    }

    public void setFile(TermEditorFile file) {
        this.file = file;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void readFile() {
        try {
            Scanner scannerFileReader = new Scanner(new File("lab2/output/" + fileName));
            scannerFileReader.useDelimiter("\\Z");
            System.out.println("The content of file:");
            System.out.println(scannerFileReader.next());
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file");
            System.out.println("ERROR: " + e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public FileTime convertLocalDateTimeToFileTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return FileTime.from(instant);
    }

    public void createFile() {
        fileBuilder.setFileName(fileName);
        fileBuilder.setFileExtension(fileExtension);
        fileBuilder.setFileContent(fileContent);
        fileBuilder.setFileCreationTime(convertLocalDateTimeToFileTime(LocalDateTime.now()));
        fileBuilder.setFileLastModificationDate(convertLocalDateTimeToFileTime(LocalDateTime.now()));
        fileBuilder.build();
    }

    public void deleteFile() {
        fileBuilder.setFileName(fileName);
        fileBuilder.setFileExtension(fileExtension);
        TermEditorFile file = fileBuilder.build();
        if (file.getFileOutput().delete()) {
            System.out.println("File " + file.getFileName() + " deleted.");
        } else {
            System.out.println("ERROR: Failed to delete the file " + file.getFileName());
        }
    }
}

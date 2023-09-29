package lab1.models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TermEditorFileManager implements ITermEditorCommandFile {
    private String filePath;
    private TermEditorFile file;

    public TermEditorFileManager(TermEditorFile file) {
        this.file = file;
        this.filePath = "lab1/output/";
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public TermEditorFile getFile() {
        return this.file;
    }

    public void setFile(TermEditorFile file) {
        this.file = file;
    }

    public void readFile() {
        try {
            Scanner scannerFileReader = new Scanner(new File(filePath + file.getFileName()));
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
        try {
            File fileOutput = new File(filePath + file.getFileName());

            try {
                fileOutput.createNewFile();
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
                e.getStackTrace();
                System.exit(-1);
            }

            Files.setAttribute(fileOutput.toPath(), "basic:creationTime",
                    convertLocalDateTimeToFileTime((file.getCreationTime())));
            Files.setAttribute(fileOutput.toPath(), "basic:lastModifiedTime",
                    convertLocalDateTimeToFileTime(file.getLastModificationDate()));

            FileWriter fileWriter = new FileWriter(fileOutput);
            if (file.getFileExtenstion().equals("sh")) {
                fileOutput.setExecutable(true);
            }
            fileWriter.write(file.getContent());
            System.out.println("File " + file.getFileName() + " created.");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void editFile() {
        try {
            File fileOutput = new File(filePath + file.getFileName());

            Files.setAttribute(fileOutput.toPath(), "basic:creationTime",
                    convertLocalDateTimeToFileTime((file.getCreationTime())));
            Files.setAttribute(fileOutput.toPath(), "basic:lastModifiedTime",
                    convertLocalDateTimeToFileTime(file.getLastModificationDate()));

            FileWriter fileWriter = new FileWriter(fileOutput);
            fileWriter.write(file.getContent());
            System.out.println("File " + file.getFileName() + " edited.");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void deleteFile() {
        File fileOutput = new File(filePath + file.getFileName());
        if (fileOutput.delete()) {
            System.out.println("File " + file.getFileName() + " deleted.");
        } else {
            System.out.println("ERROR: Failed to delete the file " + file.getFileName());
        }
    }
}

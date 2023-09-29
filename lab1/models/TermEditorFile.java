package lab1.models;

import java.time.LocalDateTime;

public class TermEditorFile implements ITermEditorFile, ITermEditorFileTimeMetadata {
    private String content;
    private String fileName;
    private String fileExtenstion;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationDate;

    public TermEditorFile(String fileName) {
        this.fileName = fileName;
        this.creationTime = LocalDateTime.now();
        this.lastModificationDate = LocalDateTime.now();

        try {
            this.fileExtenstion = fileName.split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.fileExtenstion = "txt";
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(-1);
        }
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtenstion() {
        return this.fileExtenstion;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtenstion = fileExtension;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}

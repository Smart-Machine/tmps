package lab2.builders;

import java.nio.file.attribute.FileTime;

public interface ITermEditorFileTimeMetadata {
    public void setFileCreationTime(FileTime fileCreationTime);

    public void setFileLastModificationDate(FileTime fileLastModificationDate);
}

package lab1.models;

import java.time.LocalDateTime;

public interface ITermEditorFileTimeMetadata {
    public LocalDateTime getCreationTime();

    public void setCreationTime(LocalDateTime creationTime);

    public LocalDateTime getLastModificationDate();

    public void setLastModificationDate(LocalDateTime lastModificationDate);

}

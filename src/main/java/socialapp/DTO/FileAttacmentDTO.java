package socialapp.DTO;

import lombok.Data;
import socialapp.File.FileAttachment;

@Data
public class FileAttacmentDTO {

    private String name;

    private String fileType;

    public FileAttacmentDTO(FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
        this.fileType = fileAttachment.getFileType();
    }

}

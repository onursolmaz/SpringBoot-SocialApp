package socialapp.DTO;

import lombok.Data;
import socialapp.Shared.FileType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDTO {

    @NotNull
    @Size(min = 4,max = 16)
    private String displayName;
    @FileType(types = {"jpeg", "png"})
    private String image;
}

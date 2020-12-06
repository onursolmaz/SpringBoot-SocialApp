package socialapp.DTO;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class TweetSubmitDTO {

    @Size(min = 1,max = 250)
    private String content;

    private long attachmentId;

}

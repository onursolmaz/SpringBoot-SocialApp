package socialapp.Error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // user login olurken gerek yok bu parametreye
public class ApiError {
    private int status;
    private String message;
    private String path;
    private Long timestamp=new Date().getTime();

    private Map<String, String> validationErrors;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

}

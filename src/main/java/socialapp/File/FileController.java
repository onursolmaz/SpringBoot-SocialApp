package socialapp.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import socialapp.Services.FileService;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/api/tweet-attachments")

   FileAttachment saveTweetAttachment(MultipartFile file) {
        return fileService.saveTweetAttachment(file);
    }
}

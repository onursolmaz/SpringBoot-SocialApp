package socialapp.Services;


import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.Configuration.AppConfiguration;

import socialapp.Entity.UserEntity;
import socialapp.File.FileAttachment;
import socialapp.Repositories.FileAttachmentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {

    AppConfiguration appConfiguration;

    Tika tika;

    FileAttachmentRepository fileAttachmentRepository;

    public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
        super();
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getProfileStoragePath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64Encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64Encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public void deleteProfileImage(String oldImage) {
        if (oldImage == null)
            return;
        deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImage));
    }

    public void deleteAttachmentFile(String oldImage) {
        if (oldImage == null)
            return;
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImage));

    }

    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String detectType(String base64) {
        byte[] base64Encoded = Base64.getDecoder().decode(base64);
        return detectType(base64Encoded);
    }

    public String detectType(byte[] arr) {
        return tika.detect(arr);

    }

    public FileAttachment saveTweetAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
            fileType = detectType(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);

        return fileAttachmentRepository.save(attachment);

    }

    // 1 saatde bir çalıştır ve bir tweete ait olmayan dosyaları(image) sil
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanupStorage() {
        Date twentFoursAge = new Date(System.currentTimeMillis() - (10 * 1000*60*60));
        List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndTweetIsNull(twentFoursAge);
        for (FileAttachment file : filesToBeDeleted) {
            //delete file
            deleteAttachmentFile(file.getName());
            //delete from table
            fileAttachmentRepository.deleteById(file.getId());
        }

        }

        public void deleteAllStoredFileForUser(UserEntity inDB){
            deleteProfileImage(inDB.getImage());
            List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByTweetUserEntity(inDB);

            for (FileAttachment file : filesToBeRemoved) {
                deleteAttachmentFile(file.getName());

            }

        }
}


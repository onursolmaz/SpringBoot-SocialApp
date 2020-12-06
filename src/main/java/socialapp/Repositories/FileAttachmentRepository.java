package socialapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import socialapp.File.FileAttachment;


import socialapp.Entity.UserEntity;


import java.util.Date;
import java.util.List;


public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {

    List<FileAttachment> findByDateBeforeAndTweetIsNull(Date date); // verdiğimizden tarihden itibaren null olan
                                                                    // dosyaları sil.


    List<FileAttachment> findByTweetUserEntity(UserEntity user);  // user ait olan tweetlerin fileattachment ları

}

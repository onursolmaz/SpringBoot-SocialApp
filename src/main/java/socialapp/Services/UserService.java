package socialapp.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.DTO.UserUpdateDTO;
import socialapp.Entity.UserEntity;
import socialapp.Error.NotFoundException;
import socialapp.Repositories.UserRepository;

import java.io.*;


@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FileService fileService;
    @Autowired
    public final TweetService tweetService;


    public void save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public UserEntity findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }

    public Page<UserEntity> getUsers(Pageable page, UserEntity user) {
        if (user != null)
            return userRepository.findByUsernameNot(user.getUsername(), page);
        return userRepository.findAll(page);
    }

    public UserEntity getByUsername(String username) {
      UserEntity inDb=userRepository.findByUsername(username);
      if(inDb==null)
          throw new NotFoundException();
      return inDb;
    }

    public UserEntity uptadeUser(String username, UserUpdateDTO updatedUser) {
       UserEntity inDB=getByUsername(username);
       inDB.setDisplayName(updatedUser.getDisplayName());
       if(updatedUser.getImage()!=null){
           String oldImage=inDB.getImage();
           try {
               String storedFileName= fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
               inDB.setImage(storedFileName);
           }catch (IOException e){
               e.printStackTrace();
           }
           fileService.deleteProfileImage(oldImage);

       }
          // inDB.setImage(updatedUser.getImage());
       return userRepository.save(inDB); // primary key e bakarak otamatik uptade olacağını anlıyor
    }



    public void delete(UserEntity user) {
 //       tweetService.deleteOfUserTweets(user); bi-directional ilişki yapıp cascade=CascadeType.REMOVA dan sonra gerek kalmadı
        fileService.deleteAllStoredFileForUser(user);
        userRepository.delete(user);
    }

}

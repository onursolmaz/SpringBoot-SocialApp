package socialapp;


import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import socialapp.DTO.TweetSubmitDTO;
import socialapp.Entity.UserEntity;
import socialapp.Services.TweetService;
import socialapp.Services.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;



@SpringBootApplication
public class SocialappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialappApplication.class, args);

    }

//    @Bean
//    CommandLineRunner createInitialUsers(UserService userService, TweetService tweetService) {
//        return (args) -> {
//            for(int i = 1; i<=25;i++) {
//                UserEntity user = new UserEntity();
//                user.setUsername("user"+i);
//                user.setDisplayName("display"+i);
//                user.setPassword("P4ssword");
//                userService.save(user);
//                for(int j = 1;j<=20;j++) {
//                    TweetSubmitDTO hoax = new TweetSubmitDTO();
//                    hoax.setContent("hoax (" +j + ") from user ("+i+")");
//                    tweetService.save(hoax, user);
//                }
//            }
//        };
//    }



}
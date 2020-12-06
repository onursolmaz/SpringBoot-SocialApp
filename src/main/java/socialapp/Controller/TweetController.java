package socialapp.Controller;



import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.DTO.TweetDTO;
import socialapp.DTO.TweetSubmitDTO;
import socialapp.Entity.Tweet;
import socialapp.Entity.UserEntity;
import socialapp.Services.TweetService;
import socialapp.Shared.CurrentUser;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TweetController {

    @Autowired
    public TweetService tweetService;



    @PostMapping("/api/tweets")
    String save(@Valid @RequestBody TweetSubmitDTO tweet, @CurrentUser UserEntity user) {

        tweetService.save(tweet, user);

        return "Tweet is saved";

    }



    @GetMapping("/api/tweets")
    Page<TweetDTO> getTweets(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page,@CurrentUser UserEntity loggedInUser) {
        return tweetService.getTweets(page).map(hoax -> {
            return new TweetDTO(hoax, loggedInUser);
        });
    }


    @GetMapping("/api/{username}/tweets")
    Page<TweetDTO> getUserTweets(@PathVariable String username, @PageableDefault(sort = "id", direction = Direction.DESC) Pageable page,@CurrentUser UserEntity loggedInUser) {
        return tweetService.getUserTweets(username, page).map(hoax -> {
            return new TweetDTO(hoax, loggedInUser);
        });
    }

    @GetMapping({"/api/tweets/{id:[0-9]+}", "/api/{username}/tweets/{id:[0-9]+}"})
    ResponseEntity<?> getTweetsRelative(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page,
                                        @PathVariable Long id,
                                        @PathVariable(required = false) String username,
                                        @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
                                        @RequestParam(name = "direction", defaultValue = "before") String direction,
                                        @CurrentUser UserEntity user){
       final UserEntity loggedInUser=user;

        if (count) {
            Long newTweetCount = tweetService.getNewTweetsCount(id, username);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newTweetCount);
            return ResponseEntity.ok(response);
        }
        if (direction.equals("after")) {

            List<Tweet> newTweets = tweetService.getNewTweets(id, username, page.getSort());
            List<TweetDTO> newTweetDTO = newTweets.stream().map(hoax -> {
                return new TweetDTO(hoax, loggedInUser);
            }).collect(Collectors.toList());

            return ResponseEntity.ok(newTweetDTO);
        }
        return ResponseEntity.ok(tweetService.getOldTweets(id, username, page).map(hoax -> {
            return new TweetDTO(hoax, loggedInUser);
        }));
    }


    @DeleteMapping("/api/tweets/{id:[0-9]+}")
    ResponseEntity<?> deleteTweet(@PathVariable Long id, @CurrentUser UserEntity loggedInUser){
        if(!tweetService.delete(id,loggedInUser)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        return ResponseEntity.ok("Tweet Silinidi");

    }

    @PostMapping("/api/tweets/{tweetId:[0-9]+}")
    public void like (@PathVariable Long tweetId,@CurrentUser UserEntity loggedInUser){
        tweetService.like(tweetId,loggedInUser);
    }

    @DeleteMapping("/api/tweets/delete/{tweetId:[0-9]+}")
    public void deleteLike(@PathVariable Long tweetId,@CurrentUser UserEntity loggedInUser){
        tweetService.deleteLike(tweetId,loggedInUser);
    }


    // getTweetsRelative fonksiyonuyle gerek kalmadı

//    @GetMapping("/api/{username}/tweets/{id:[0-9]+}")
//    ResponseEntity<?> getUserTweetsRelative(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page, @PathVariable Long id, @PathVariable String username,
//                                            @RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
//                                            @RequestParam(name = "direction", defaultValue = "before") String direction) {
//        if (count) {
//            Long newTweetCount = service.getNewTweetsCountOfUser(id, username);
//            Map<String, Long> response = new HashMap<>();
//            response.put("count", newTweetCount);
//            return ResponseEntity.ok(response);
//        }
//        if (direction.equals("after")) {
//            List<Tweet> newTweets = service.getNewTweetsOfUser(id, username, page.getSort());
//            List<TweetDTO> newTweetDTO = newTweets.stream().map(TweetDTO::new).collect(Collectors.toList());
//
//            return ResponseEntity.ok(newTweetDTO);
//        }
//
//        return ResponseEntity.ok(service.getUserOldTweets(id, username, page).map(TweetDTO::new));
//    }


}

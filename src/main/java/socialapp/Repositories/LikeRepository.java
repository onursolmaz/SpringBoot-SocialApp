package socialapp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialapp.Entity.Like;
import socialapp.Entity.UserEntity;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Like findByTweetIdAndUser(Long tweetId,UserEntity user);



}

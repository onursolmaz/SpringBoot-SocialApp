package socialapp.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import socialapp.Entity.Tweet;
import socialapp.Entity.UserEntity;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet,Long>, JpaSpecificationExecutor<Tweet> {

        Page<Tweet> findByUserEntity(UserEntity user, Pageable page);

        List<Tweet> findByUserEntity(UserEntity user);

        // Aşağıdaki queryler spefication kullanamadan önce kullanılıyordu

//        Page<Tweet> findByIdLessThan(Long id,Pageable page);

//        Page<Tweet> findByIdLessThanAndUserEntity(Long id,UserEntity user,Pageable page);

//        Long countByIdGreaterThan(Long id);

//        Long countByIdGreaterThanAndUserEntity(Long id,UserEntity user);

//        List<Tweet> findByIdGreaterThan(Long id, Sort sort );

//        List<Tweet> findByIdGreaterThanAndUserEntity(Long id, UserEntity user,Sort sort);



}

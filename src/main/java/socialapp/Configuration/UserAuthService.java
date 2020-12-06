package socialapp.Configuration;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.Entity.UserEntity;
import socialapp.Repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService {

 private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity inDb=userRepository.findByUsername(username);
        if(inDb==null)
            throw new UsernameNotFoundException("User not found");
        return inDb;
    }
}

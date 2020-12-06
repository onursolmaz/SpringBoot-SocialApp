package socialapp.Entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import socialapp.Validator.UniqueUsername;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import socialapp.Validator.OneWordUsername;
import javax.persistence.*;
import java.util.List;


@Data
@Entity
@RequiredArgsConstructor
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue
    private Long id;


    @NotNull(message = "{socialApp.constraints.username.NotNull.message}")
    @Size(min = 4, max = 16)


    @Size(min = 4, max = 16)
    @UniqueUsername(message = "{socialApp.constraints.username.UniqueUsername.message}")
    @OneWordUsername
    @NotNull(message = "{socialApp.constraints.username.NotNull.message}")
    private String username;

    @NotNull(message = "{socialApp.constraints.displayName.NotNull.message}")
    @Size(min = 4, max = 16)
    private String displayName;
    private String image;

    @NotNull(message = "{socialApp.constraint.password.NotNull.message}")
    @Size(min = 8, max = 255)
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "Must be at least one uppercase letter, one lowercase letter and one number")
    private String password;


//    public UserDTO mapToUserDTO(UserEntity user) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(user.getUsername());
//        userDTO.setDisplayName(user.getDisplayName());
//        return userDTO;
//    }

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.REMOVE)
    List<Tweet> tweets;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Like> likes;





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_user");
    }

    @Override                                                   // security, authorization için
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

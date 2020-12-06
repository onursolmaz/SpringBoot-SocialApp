package socialapp.DTO;

import lombok.Getter;
import lombok.Setter;

import socialapp.Entity.UserEntity;


import socialapp.Entity.Like;
import socialapp.Entity.UserEntity;

import java.util.List;


@Getter
@Setter
public class UserDTO {

    private String username;
    private String displayName;
    private String image;


    public UserDTO(UserEntity user) {
        this.setUsername(user.getUsername());
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }

}

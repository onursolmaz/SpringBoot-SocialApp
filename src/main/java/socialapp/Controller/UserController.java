package socialapp.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import socialapp.DTO.UserDTO;
import socialapp.DTO.UserUpdateDTO;
import socialapp.Entity.UserEntity;


import socialapp.Repositories.UserRepository;

import socialapp.Services.UserService;
import socialapp.Shared.CurrentUser;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    public final UserService userService;
    @Autowired
    public final UserRepository userRepository;

    @PostMapping("/api/users")
    public void createUser(@Valid @RequestBody UserEntity user) {
        userService.save(user);

    }


    @PostMapping("/api/auth")
    UserDTO handleAuthentication(@CurrentUser UserEntity user) {
        return new UserDTO(user);
        //   return user.mapToUserDTO(user);   maplemek için farklı bir yol

    }

    @GetMapping("/api/users")
    public Page<UserDTO> getUsers(Pageable page, @CurrentUser UserEntity user) {
        return userService.getUsers(page, user).map(UserDTO::new);     // tek tek bütün User objeclerini UserDTO un constructor ına yolluyor (UserEntity alan constructor)

    }

    @GetMapping("/api/users/{username}")
    UserDTO getUser(@PathVariable String username) {
        UserEntity user = userService.getByUsername(username);
        return new UserDTO(user);
    }

    @PutMapping("/api/users/{username}")
    @PreAuthorize("#username==principal.username") // request authorization kontrolü, # ile fonksiyona gelen paremetrelere erişiyoruz.
    UserDTO uptadeUser(@Valid @RequestBody UserUpdateDTO updatedUser, @PathVariable String username) {
//        if(!loggedInUser.equals(username)) {
//            ApiError error=new ApiError(403,"can not change","/api/users/"+username); // bu kısmı spring security yapıyor.
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
//        }


        UserEntity user = userService.uptadeUser(username, updatedUser);
        return new UserDTO(user);
    }

    @DeleteMapping("/api/users")
    public String deleteUser(@CurrentUser UserEntity user){
        userService.delete(user);
        return "User is deleted";
    }


}

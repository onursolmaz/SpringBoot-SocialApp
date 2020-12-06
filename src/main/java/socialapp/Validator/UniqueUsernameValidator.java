package socialapp.Validator;

import lombok.RequiredArgsConstructor;
import socialapp.Entity.UserEntity;
import socialapp.Repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

  public final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity user=userRepository.findByUsername(value);
        if(user==null){
            return true;
        }
        return false;
    }
}

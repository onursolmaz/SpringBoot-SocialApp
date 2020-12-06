package socialapp.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneWordUsernameValidator implements ConstraintValidator<OneWordUsername,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if( value==null || value.contains(" "))
            return false;
        return true;
    }
}

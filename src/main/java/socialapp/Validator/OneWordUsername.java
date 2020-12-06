package socialapp.Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {OneWordUsernameValidator.class})
public @interface OneWordUsername {

    String message() default "Username can not have space";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

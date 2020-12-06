package socialapp.Shared;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {FileTypeValidator.class})
public @interface FileType {

    String message() default "{socialApp.constraint.FileType.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] types();


}

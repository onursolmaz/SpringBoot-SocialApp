package socialapp.Shared;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import socialapp.Services.FileService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    FileService fileService;
    String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) // file seçmeden direk request geldiyse kabul et
            return true;
        String fileType = fileService.detectType(value);
        for (String supportedType : this.types) {
            if (fileType.contains(supportedType))
                return true;
        }
        String supportedTypes = Arrays.stream(this.types).collect(Collectors.joining(", "));
//        String supportedTypes = "";
//        for (int i = 0; i < this.types.length; i++) {      => another using
//            supportedTypes -= this.types[i] + ",";
//        }
        context.disableDefaultConstraintViolation();
        context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("types", supportedTypes)
                .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();  // kendi constraint mesajımızı üretmek için

        return false;


    }
}

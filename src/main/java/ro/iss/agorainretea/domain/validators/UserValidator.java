package ro.iss.agorainretea.domain.validators;

import org.springframework.stereotype.Component;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.exceptions.ValidationException;

@Component
public class UserValidator implements Validator<User>{

    @Override
    public void validate(User entity) {
        StringBuilder errs = new StringBuilder();

        if(entity.getFamilyName().isEmpty())
            errs.append("Family name can't be empty!\n");
        if(entity.getName().isEmpty())
            errs.append("Name can't be empty!\n");
        if(entity.getPassword().isEmpty())
            errs.append("Password can't be empty!\n");
        if(entity.getEmail().isEmpty())
            errs.append("Email can't be empty!\n");

        if(!errs.isEmpty())
            throw new ValidationException(errs.toString());
    }
}

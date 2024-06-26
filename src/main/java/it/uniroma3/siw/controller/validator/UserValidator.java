package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 50;
    final Integer MIN_NAME_LENGTH = 2;

    @Autowired
    private UserService userService;

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        String name = user.getName().trim();
        String surname = user.getSurname().trim();
        String email = user.getEmail().trim();
        String groupName = user.getGroupName().trim();


        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
            errors.rejectValue("name", "nome.size");

        if (surname.length() < MIN_NAME_LENGTH || surname.length() > MAX_NAME_LENGTH)
            errors.rejectValue("surname", "cognome.size");

        if (!isEmailValid(email)) {
            errors.rejectValue("email", "email.invalid");
        }
        System.out.println("------->" + user.getId());

        if (user.getId() == null || !user.getEmail().equals(this.userService.getUser(user.getId()).getEmail())) {
            if (this.userService.findByEmail(email) != null)
                errors.rejectValue("email", "email.duplication");
        }

        if (groupName.length() < MIN_NAME_LENGTH || groupName.length() > MAX_NAME_LENGTH)
            errors.rejectValue("groupName", "groupName.size");

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    private boolean isEmailValid(String email) {
        String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }
}
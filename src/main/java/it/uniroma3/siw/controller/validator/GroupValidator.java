package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {

    private static final int MAX_STRING_LENGTH = 50;
    private static final int MIN_STRING_LENGTH = 2;

    @Autowired
    private GroupService groupService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Group group = (Group) target;
        String groupName = group.getName().trim();

        if (groupName.length() < MIN_STRING_LENGTH || groupName.length() > MAX_STRING_LENGTH) {
            errors.rejectValue("name", "name.size");
        }

        // Validate if group already exists
        if (this.groupService.alreadyExists(group)) {
            errors.reject("group.duplicate");
        }
    }
}

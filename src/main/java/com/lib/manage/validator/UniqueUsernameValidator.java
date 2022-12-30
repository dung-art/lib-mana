package com.lib.manage.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lib.manage.entity.User;
import com.lib.manage.repository.IUserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {
    private IUserRepository userRepository;

    public UniqueUsernameValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = userRepository.findByUsername(email);
        if(user!=null){
            return false;
        }
        return true;
    }
}
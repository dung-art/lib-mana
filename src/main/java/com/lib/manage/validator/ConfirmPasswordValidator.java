package com.lib.manage.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lib.manage.dto.PasswordForm;

public class ConfirmPasswordValidator implements ConstraintValidator<PasswordConfirm, PasswordForm> {

    @Override
    public boolean isValid(PasswordForm passwordForm, ConstraintValidatorContext context) {
        if(passwordForm.getPassword().equals(passwordForm.getConfirmPassword())){
            return true;
        }
        return false;
    }
}
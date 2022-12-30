package com.lib.manage.dto;
import com.lib.manage.validator.PasswordConfirm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordForm {
    private String username;
    private String currentPassword;
    @PasswordConfirm
    private PasswordForm passwordForm;
}
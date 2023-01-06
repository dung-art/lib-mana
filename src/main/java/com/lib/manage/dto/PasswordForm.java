package com.lib.manage.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lib.manage.constant.Constants;
import com.lib.manage.validator.FieldMatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword")
public class PasswordForm {
	@NotNull
	@Pattern(regexp = Constants.PATTERN_PASSWORD)
    private String password;

	@NotNull
	@Pattern(regexp = Constants.PATTERN_PASSWORD)
    private String confirmPassword;
}

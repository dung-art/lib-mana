package com.lib.manage.dto.request.account;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lib.manage.constant.Constants;
import com.lib.manage.validator.FieldMatch;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldMatch(first = "newPassword", second = "confirmNewPassword")
public class ResetPasswordRequest {
  @NotNull
  private String accountId;
  
  @NotNull
  @Pattern(regexp = Constants.PATTERN_PASSWORD)
  private String newPassword;
  
  @NotNull
  @Pattern(regexp = Constants.PATTERN_PASSWORD)
  private String confirmNewPassword;
}
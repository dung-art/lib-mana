package com.lib.manage.models.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityConfigParam {
  private String tokenPrefix;
  private String secretKey;
  private String issuer;
  private int accessTokenExpirationSecond;
  private int refreshTokenExpirationSecond;
  private int resetPasswordTokenExpirationSecond;
}
package com.lib.manage.dto.response.account;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSuccessResponse {
  private String accessToken;
  private String refreshToken;
  private LocalDateTime expiresIn;
//  private String id;
//  private String username;
//  private String fullname;
//  private String email;
//  private String pic;
//  private String[] roles;
//  private String language;
//  private String companyName;
//  private String phone;
}

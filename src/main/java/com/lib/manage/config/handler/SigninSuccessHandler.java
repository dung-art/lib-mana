package com.lib.manage.config.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.manage.config.JwtTokenService;
import com.lib.manage.dto.response.account.AuthSuccessResponse;
import com.lib.manage.models.account.SecurityConfigParam;

public class SigninSuccessHandler implements AuthenticationSuccessHandler {
  private ObjectMapper om = new ObjectMapper();;

  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private SecurityConfigParam securityParam;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    LocalDateTime issueDate = LocalDateTime.now();
    LocalDateTime aExpireDate = issueDate;
    LocalDateTime rExpireDate = issueDate;
    aExpireDate.plusSeconds(securityParam.getAccessTokenExpirationSecond());
    rExpireDate.plusSeconds(securityParam.getRefreshTokenExpirationSecond());
    String authToken = null;
		try {
			authToken = jwtTokenService.generateToken(authentication, issueDate, aExpireDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    String refreshToken = jwtTokenService.generateRefreshToken(authentication, issueDate, rExpireDate);
    AuthSuccessResponse ret = new AuthSuccessResponse();
    ret.setAccessToken(authToken);
    ret.setRefreshToken(refreshToken);
    ret.setExpiresIn(aExpireDate);
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter writer = response.getWriter();
    writer.write(om.writeValueAsString(ret));
  }
  
}
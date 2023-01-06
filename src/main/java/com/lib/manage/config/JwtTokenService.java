package com.lib.manage.config;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.lib.manage.models.account.CustomAccountDetails;

public interface JwtTokenService {

	boolean validateToken(String authToken);
//	  String generateAccessToken(CustomAccountDetails user, LocalDateTime issueDate, LocalDateTime expireDate);
//	  String generateRefreshToken(CustomAccountDetails user, LocalDateTime issueDate, LocalDateTime expireDate);
//	  Authentication getAuthentication(HttpServletRequest request);
//	boolean validateToken(String authToken);

	String generateToken(CustomAccountDetails account, LocalDateTime issueDate, LocalDateTime expireDate) throws Exception;

	Authentication getAuthentication(HttpServletRequest request);

	String getUserNameFromJwtToken(String token);

	String generateRefreshToken(Authentication authentication, LocalDateTime issueDate, LocalDateTime expireDate);
}

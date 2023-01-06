package com.lib.manage.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.lib.manage.config.handler.SigninFailureHandler;
import com.lib.manage.config.handler.SigninSuccessHandler;
import com.lib.manage.config.handler.SignoutSuccessHandler;
import com.lib.manage.models.account.SecurityConfigParam;
import com.lib.manage.service.CustomAccountDetailsService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SecurityBeanConfig {
	  @Bean
	  @ConfigurationProperties(prefix = "app.security.jwt")
	  SecurityConfigParam applicationConfig() {
	    return new SecurityConfigParam();
	  }

	  @Bean
	  @Autowired
	  JwtTokenService jwtService(SecurityConfigParam securityParam, TokenService tokenService) {
	    return new JwtTokenServiceImpl(securityParam, tokenService);
	  }

	  @Bean
	  @Autowired
	  JwtAuthenticationFilter jwtAuthFilter(JwtTokenService jwtService) {
	    return new JwtAuthenticationFilter(jwtService);
	  }

	    @Bean
	    public RestAuthenticationEntryPoint restServicesEntryPoint() {
	        return new RestAuthenticationEntryPoint();
	    }
	  @Bean
	  AuthenticationFailureHandler signinFailureHandler() {
	    return new SigninFailureHandler();
	  }

	  @Bean
	  AuthenticationSuccessHandler signinSuccessHandler() {
	    return new SigninSuccessHandler();
	  }

	  @Bean
	  LogoutSuccessHandler signoutSuccessHandler() {
	    return new SignoutSuccessHandler();
	  }

	  @Bean
	  UserDetailsService userDetailService() {
	    return new CustomAccountDetailsService();
	  }

	  @Bean
	  AuditorAware<String> auditorProvider() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null) {
	      return () -> Optional.ofNullable(authentication.getName());
	    } else {
	    	return null;
	    }
	  }
}

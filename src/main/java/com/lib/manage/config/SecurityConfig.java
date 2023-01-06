package com.lib.manage.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.lib.manage.config.handler.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	  @Autowired
	  private AuthenticationSuccessHandler loginSuccessHandler;
	  @Autowired
	  private AuthenticationFailureHandler loginFailureHandler;
	  @Autowired
	  private LogoutSuccessHandler logoutSuccessHandler;
	  @Autowired
	  private JwtAuthenticationFilter jwtAuthenticationFilter;
//	  @Autowired
	  private UserDetailsService userDetailService;
	  @Value("${app.api.allowed-origins}")
	  private String[] allowedOrigin;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
				// vô hiệu hóa csrf vì sẽ không sử dụng đăng nhập biểu mẫu
		httpSecurity.csrf().disable();
	    httpSecurity.cors().configurationSource(new CorsConfigSource());
	    httpSecurity.sessionManagement().sessionFixation().newSession().maximumSessions(1).maxSessionsPreventsLogin(true);
		// cấp mọi quyền cho mọi yêu cầu đối với /login
	    httpSecurity.authorizeRequests().and().formLogin().loginProcessingUrl("/lbm/v1/account/login")
        .usernameParameter("username").passwordParameter("password").successHandler(loginSuccessHandler)
        .failureHandler(loginFailureHandler);
	    httpSecurity.authorizeRequests().and().logout().logoutUrl("/lbm/v1/account/logout")
        .logoutSuccessHandler(logoutSuccessHandler);
	    //				// đối với mọi thứ khác, người dùng phải được xác thực
//				.anyRequest().authenticated().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		httpSecurity.authorizeRequests()
//		.antMatchers("/admin/**")
//		.hasRole("ADMIN")
//		.antMatchers("/protected/**")
//		.hasRole("USER");
		// thêm bộ lọc tùy chỉnh trước UsernamePasswordAuthenticationFilter trong chuỗi
		// bộ lọc
	    httpSecurity.authorizeRequests().and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler());
	    httpSecurity.userDetailsService(userDetailService);
		return httpSecurity.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.antMatchers("/public/*", "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
				.antMatchers("/actuator").antMatchers("/auth/v1/login")
				.antMatchers("/swagger-ui", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs");
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);
		return authenticationManagerBuilder.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder).and().build();
	}
	
	 class CorsConfigSource implements CorsConfigurationSource {
		    @Override
		    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		      CorsConfiguration cors = new CorsConfiguration();
		      String[] methodArray = { "*" };
		      String[] headerArray = { "*" };
		      cors.setAllowedOrigins(Arrays.asList(allowedOrigin));
		      cors.setAllowedMethods(Arrays.asList(methodArray));
		      cors.setAllowedHeaders(Arrays.asList(headerArray));
		      return cors;
		    }
		  }
}

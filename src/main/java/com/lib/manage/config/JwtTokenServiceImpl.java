package com.lib.manage.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.manage.constant.TokenTypeEnum;
import com.lib.manage.models.account.CustomAccountDetails;
import com.lib.manage.models.account.SecurityConfigParam;
import com.lib.manage.validator.Convert;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
	private static final String SCOPES = "scopes";
	private static final String ENABLED = "enabled";
	private static final String PRINCIPAL = "principal";
	// Thời gian có hiệu lực của chuỗi jwt
	private final long JWT_EXPIRATION = 604800000L;
	private TokenService tokenService = null;
	private JwtParser jwtParser;
	private JwtBuilder jwtBuilder;
	private ObjectMapper objectMapper;
	private SecurityConfigParam securityParam;

	public JwtTokenServiceImpl(SecurityConfigParam securityParam, TokenService tokenService) {
		this.securityParam = securityParam;
		this.tokenService = tokenService;
		this.objectMapper = new ObjectMapper();
		jwtParser = Jwts.parser().setSigningKey(securityParam.getSecretKey());
		jwtBuilder = Jwts.builder().setIssuer(securityParam.getIssuer()).signWith(SignatureAlgorithm.HS512,
				securityParam.getSecretKey());
	}

	// Tạo ra jwt từ thông tin user
	@Override
	public String generateToken(CustomAccountDetails account, LocalDateTime issueDate, LocalDateTime expireDate)
			throws Exception {
//		CustomAccountDetails accountDetails = (CustomAccountDetails) authentication.getPrincipal();
		Date now = new Date();
//		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		if (issueDate == null) {
			issueDate = Convert.convertStringDateToLocalDateTime(now.toString());
		}
		if (expireDate == null) {
			expireDate = Convert.convertStringDateToLocalDateTime(new Date(now.getTime() + JWT_EXPIRATION).toString());
		}
		Date iDate = Date.from(issueDate.atZone(ZoneId.systemDefault()).toInstant());
		Date eDate = Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant()); // Tạo chuỗi json web token từ
																						// user.
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority ga : account.getAuthorities()) {
			authorities.add(ga.getAuthority());
		}
		Claims claims = Jwts.claims();
		claims.setSubject(account.getUsername());
		claims.put(SCOPES, authorities);
		claims.put(ENABLED, account.isEnabled());
		claims.put(PRINCIPAL, account);
		String token = Jwts.builder().setClaims(claims).setSubject(account.getId()).setIssuedAt(iDate)
				.setExpiration(eDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
		if (tokenService != null) {
			tokenService.saveToken(account.getId(), TokenTypeEnum.AccessToken, token, issueDate, expireDate);
		}
		return token;
	}

	@Override
	public String generateRefreshToken(Authentication authentication, LocalDateTime issueDate,
			LocalDateTime expireDate) {
		CustomAccountDetails account = (CustomAccountDetails) authentication.getPrincipal();
		Claims claims = Jwts.claims();
		claims.setSubject(account.getUsername());
		claims.put(SCOPES, Collections.singletonList("REFRESH_TOKEN"));
		claims.put(ENABLED, account.isEnabled());
		claims.put(PRINCIPAL, account);
		// @formatter:off
	    Date iDate = Date.from(issueDate.atZone(ZoneId.systemDefault()).toInstant());
	    Date eDate = Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant());
	    String token = jwtBuilder.setId(UUID.randomUUID().toString()).setIssuedAt(iDate)
	        .setExpiration(eDate).setClaims(claims).compact();
	    if (tokenService != null) {
	      tokenService.saveToken(account.getId(), TokenTypeEnum.RefreshToken, token, issueDate, expireDate);
	    }
	    return token;
	    // @formatter:on
	}

	@Override
	public String getUserNameFromJwtToken(String token) {
		String userName = Jwts.parser().parseClaimsJws(token).getBody().getSubject();
		return userName;
	}

	@Override
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature -> Message: {a} ", e);
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token -> Message: {b}", e);
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token -> Message: {c}", e);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token -> Message: {d}", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty -> Message: {e}", e);
		}
		return false;
	}

	@Override
	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token == null || !token.startsWith(securityParam.getTokenPrefix())) {
			return null;
		}
		token = token.substring(securityParam.getTokenPrefix().length() + 1);
		Claims claims = jwtParser.parseClaimsJws(token).getBody();

		String user = claims.getSubject();
		if (user == null || user.isEmpty()) {
			return null;
		}
		Object authObj = claims.get(PRINCIPAL);
		CustomAccountDetails principal = objectMapper.convertValue(authObj, new TypeReference<CustomAccountDetails>() {
		});
		principal.setActive((Boolean) claims.get(ENABLED));
		Object scopesObj = claims.get(SCOPES);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<String> scopes = objectMapper.convertValue(scopesObj, new TypeReference<List<String>>() {
		});
		scopes.forEach(scope -> {
			authorities.add(new SimpleGrantedAuthority(scope));
			;
		});

		return new UsernamePasswordAuthenticationToken(principal, null, authorities);
	}
}
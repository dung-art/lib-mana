package com.lib.manage.config;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.lib.manage.models.account.CustomAccountDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "ABC_LIB_MAN_IS_OF_SM";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

//    // Tạo ra jwt từ thông tin user
//    public String generateToken(CustomAccountDetails accountDetails) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        // Tạo chuỗi json web token từ id của user.
//        return Jwts.builder()
//                   .setSubject(String.valueOf(accountDetails.getAccount().getId()))
//                   .setIssuedAt(now)
//                   .setExpiration(expiryDate)
//                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                   .compact();
//    }
    public String generateTokenLogin(Authentication authentication) {
        CustomAccountDetails userPrincipal = (CustomAccountDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return String.valueOf(claims.getSubject());
    }
    
    public String getUserNameFromJwtToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
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
}
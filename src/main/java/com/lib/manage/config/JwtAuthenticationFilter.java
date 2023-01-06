package com.lib.manage.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {
    @Autowired
    private JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtService) {
        this.jwtTokenService = jwtService;
      }
    
//    @Autowired
//    private AccountService accountService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, FilterChain filterChains)
//            throws ServletException, IOException {
//        try {
//        	// Lấy jwt từ request
//        	String jwt = getJwtFromRequest(request);
//            if (jwt != null && jwtTokenService.validateToken(jwt)) {
//            	 // Lấy id user từ chuỗi jwt
//            	String accountName = jwtTokenService.getUserNameFromJwtToken(jwt);
//                // Lấy thông tin người dùng từ accountName
//                UserDetails userDetails = accountService.loadUserByUsername(accountName);
//                // Nếu người dùng hợp lệ, set thông tin cho Seturity Context      
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        } catch (Exception e) {
//            logger.error("Can NOT set user authentication -> Message: {}", e);
//        }
//        filterChains.doFilter(request, response);
//    }

//    private String getJwtFromRequest(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//     // Kiểm tra xem header Authorization có chứa thông tin jwt không
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.replace("Bearer ", "");
//        }
//        return null;
//    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 try {
		      Authentication authentication = jwtTokenService.getAuthentication((HttpServletRequest) request);
		      SecurityContextHolder.getContext().setAuthentication(authentication);
		    } catch (Exception e) {
		    } finally {
		      chain.doFilter(request, response);
		    }
		  }
	}
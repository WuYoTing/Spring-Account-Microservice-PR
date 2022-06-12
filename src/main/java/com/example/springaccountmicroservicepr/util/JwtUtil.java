package com.example.springaccountmicroservicepr.util;

import com.example.springaccountmicroservicepr.pojo.dto.UserDetailsImpl;
import io.jsonwebtoken.*;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Log4j2
@Component
public class JwtUtil {

	@Value("${account.service.jwtSecret}")
	private String jwtSecret;

	@Value("${account.service.jwtExpirationMs}")
	private int jwtExpirationMs;

	/**
	 * generate a JWT from username, date, expiration, secret
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		return generateTokenFromUsername(userPrincipal.getUsername());
	}

	public String generateTokenFromUsername(String userName) {
		return Jwts.builder()
			.setHeaderParam("typ", Header.JWT_TYPE)
			.setSubject(userName)
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
			.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}


	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	public Jws<Claims> parseJwtClaims(String jwt) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
	}
}

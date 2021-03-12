package com.cos.jwtstudy1.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.cos.jwtstudy1.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

//JWT 토큰 생성 및 검증
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${property.jwt.secret}")
	private String secretKey;
	
	private long tokenValidMilisecond = 1000L * 60 * 60; //1시간
	private final UserDetailsService userDetailService;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// JWT 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		
		return Jwts.builder()
						.setClaims(claims) // 데이터
						.setIssuedAt(now)
						.setExpiration(new Date(now.getTime() + tokenValidMilisecond))
						.signWith(SignatureAlgorithm.HS256, secretKey)
						.compact();
	}
	// JWT 토큰으로 인증정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailService.loadUserByUsername(getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	//JWT 토큰에서 회원 구분 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Request의 Header에서 token파싱 : "X-AUTH-TOKEN" : jwt토큰
	public String resolverToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	//Jwt토큰의 유효성, 만료일 확인
	public boolean validateToken(String jwtToken) {
		System.out.println("validateToken -> jwtToken ::::: " + jwtToken);
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());			
		} catch (Exception e) {
			return false;
		}
	}
	
	
}

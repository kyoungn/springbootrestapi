package com.cos.jwtstudy1.config.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtstudy1.config.auth.PrincipalUserDetails;
import com.cos.jwtstudy1.model.Member;
import com.cos.jwtstudy1.model.MemberForm;
import com.cos.jwtstudy1.repository.MemberRepository;

//시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있음.
//권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음.
//만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안타요.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private MemberRepository memberRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
	}
	
	//인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게 됨.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader ::::: " + jwtHeader);

		//JWT 토큰 검증을 해서 정상적인 사용자인지 확인
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}

		// JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
		String token = jwtHeader.replace("Bearer ", "");
		
		String username = JWT.require(Algorithm.HMAC512("cos"))
											.build().verify(token)
											.getClaim("username").asString();
		
		if(username  != null) {
			Member m = memberRepository.findByUsername(username);
			PrincipalUserDetails details = new PrincipalUserDetails(MemberForm.setMember(m));
			
			// JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
			// 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		
		chain.doFilter(request, response);
//		super.doFilterInternal(request, response, chain);
	}
	
	

}

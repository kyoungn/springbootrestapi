package com.cos.jwtstudy1.config.auth.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtstudy1.config.auth.PrincipalUserDetails;
import com.cos.jwtstudy1.model.MemberForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
//스프링 시큐리에서 UsernamePasswordAuthenticationFilter가 있음.
///login 요청해서 username, password를 전송(post)
//UsernamePasswordAuthenticationFilter가 동작함.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		//1. username, password 받아서
		try {
			ObjectMapper mapper = new ObjectMapper();
			MemberForm.MemberAdd m = mapper.readValue(request.getInputStream(), MemberForm.MemberAdd.class);
			System.out.println("JwtAuthenticationFilter : 로그인 시도중1");
			UsernamePasswordAuthenticationToken token = 
					new UsernamePasswordAuthenticationToken(m.getUsername(), m.getPassword());
			System.out.println("JwtAuthenticationFilter : 로그인 시도중2");
			//2. 정상인지 로그인 시도를 해본다.authenticationManager로 로그인 시도하면 
			// PrincipalDetailsService가 호출 loadUserByUsername() 함수 실행되면 authentication을 리턴함.
			// DB에 있는 username과 password가 일치한다.
			Authentication authentication =
					authenticationManager.authenticate(token);
			System.out.println("JwtAuthenticationFilter : 로그인 시도중3");
			//  => 로그인이 되었다는 뜻
			PrincipalUserDetails pricipalDetails =
					(PrincipalUserDetails)authentication.getPrincipal();
			System.out.println("로그인완료됨 :: " + pricipalDetails.getUsername());
			
			//3. PricipalDetails를 세션에 담고(권한관리를 위함) 
			// authentication 객체가 session영역에 저장해야하고 
			// 그 방법이 return해주면 됨.
			// 리턴의 이유는 권한관리를 security가 대신해주기 때문에 편하려고 하는 거임.
			// 굳이 JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 권한 처리때문에 session에 넣어줍니다.
			return authentication;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	// attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication함수가 실행됨.
	// JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면됨.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("successfulAuthentication 실행됨 :: 인증이 완료되었다는 뜻임.");
		PrincipalUserDetails details = (PrincipalUserDetails)authResult.getPrincipal();
		

		// RAS방식은 아니구 HASH암호화방식
		String token = JWT.create()
									.withSubject("cos토큰")
									.withExpiresAt(new Date(System.currentTimeMillis() + (60000*10)))
									.withClaim("id", details.getMember().getId())
									.withClaim("username", details.getUsername())
									.sign(Algorithm.HMAC512("cos"))
									;
		
		response.setHeader("Authorization", "Bearer "+token);
//		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
}

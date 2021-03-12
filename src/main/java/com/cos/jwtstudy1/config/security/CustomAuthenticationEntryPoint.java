package com.cos.jwtstudy1.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


// 1. jwt 토큰 없이 호출한 경우
// 2. 형식에 맞지 않거나 만료된 토큰으로 호출한 경우
// 필터링의 순서로 인하여 예외처리가 안됨.
// @RestControllerAdvice, @ControllerAdvice에서 예외처리가 안됨.
// 스프링시큐리티는 스프링 앞단에서 필터링 하기때문에 해당 예외들이 전달이 안됨.

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.sendRedirect("/exception/entrypoint");
		
	}

}

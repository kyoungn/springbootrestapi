package com.cos.jwtstudy1.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final JwtTokenProvider jwtTokenProvider;
	
	@Value("${property.api.end-point}")
	private String api;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable(); // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트된다.
		http.csrf().disable(); // rest api이므로 csrf 보안이 필요없으므로 사용안함.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // jwt 토큰으로 인증하므로 세션은 필요없으므로 사용안함.
		
		http.authorizeRequests() //다음 request에 대한 사용권한 세팅
			.antMatchers("/**/signin", "/**/signup").permitAll() // 가입및 인증 주소는 누구나 접근가능
//			.antMatchers(HttpMethod.GET, "/helloworld/**").permitAll()
			.antMatchers("/v1/user/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() //그외 나머지 요청은 모두 인증된 회원만 접근 가능
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new CustomAccessDeniedHandler());
		
		http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger/**",
				"/v2/api-docs", "/swagger-resource/**");
	}
	
}

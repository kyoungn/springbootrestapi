package com.cos.jwtstudy1.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.jwtstudy1.config.auth.jwt.JwtAuthenticationFilter;
import com.cos.jwtstudy1.config.auth.jwt.JwtAuthorizationFilter;
import com.cos.jwtstudy1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

//@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final MemberRepository memberRepository;
	
//	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig --------------->>>>> ");
		http.csrf().disable();
		http.formLogin().disable();
		http.httpBasic().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
		http.addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository));
		
		http.authorizeRequests()
			.antMatchers("/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/manager/**")
			.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();

	}
}

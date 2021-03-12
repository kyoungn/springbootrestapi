package com.cos.jwtstudy1.config.auth;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cos.jwtstudy1.advice.exception.UserNotFoundException;
import com.cos.jwtstudy1.model.Member;
import com.cos.jwtstudy1.model.MemberForm;
import com.cos.jwtstudy1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
@Transactional
public class PricipalUserDetailsService implements UserDetailsService{

	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("PricipalUserDetailsService => 로그인 사용자 조회......." + username);
		Member m = Optional.ofNullable(memberRepository.findByUsername(username)).orElseThrow(UserNotFoundException::new);
		return new PrincipalUserDetails(MemberForm.setMember(m));
	}

}

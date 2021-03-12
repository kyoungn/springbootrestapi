package com.cos.jwtstudy1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.jwtstudy1.advice.exception.UserNotFoundException;
import com.cos.jwtstudy1.config.security.JwtTokenProvider;
import com.cos.jwtstudy1.model.Member;
import com.cos.jwtstudy1.model.MemberForm;
import com.cos.jwtstudy1.model.Role;
import com.cos.jwtstudy1.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
//	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Transactional
	public Member add(MemberForm.MemberAdd form) {
		Member m = Member.builder()
//				.password(bCryptPasswordEncoder.encode(form.getPassword()))
				.password(passwordEncoder.encode(form.getPassword()))
				.username(form.getUsername())
				.roles(new ArrayList<Role>())
				.build()
				;
		List<Role> roles = new ArrayList<Role>();
		for(MemberForm.MemberAdd.RoleAdd r : form.getRoles()) {
			System.out.println("----------------------------------------------");
			roles.add(Role.builder().roleName(r.getRoleName()).member(m).build());
			//m.getRoles().add(Role.builder().roleName(r.getRoleName()).member(m).build());
		}
		System.out.println(" roles.size() :::: " + roles.size());
//		m.toBuilder().roles(roles).build();
		m.getRoles().addAll(roles);
		
		return memberRepository.save(m);
	}
	
	public Member findOne(String username) {
		return Optional.ofNullable( memberRepository.findByUsername(username)).orElseThrow(UserNotFoundException::new);
	}
	
	public String signin(String username, String password) {
		Member m = Optional.ofNullable(memberRepository.findByUsername(username)).orElseThrow(UserNotFoundException::new);
		
		if(!passwordEncoder.matches(password, m.getPassword())) {
			throw new UserNotFoundException();
		}
		
		List<String> roles = new ArrayList<String>();
		m.getRoles().forEach(r -> roles.add(r.getRoleName()));
		
		return jwtTokenProvider.createToken(m.getUsername(), roles);
	}
	
}

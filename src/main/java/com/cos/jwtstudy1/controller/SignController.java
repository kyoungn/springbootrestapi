package com.cos.jwtstudy1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtstudy1.code.ResultApi;
import com.cos.jwtstudy1.model.MemberForm;
import com.cos.jwtstudy1.model.MemberForm.MemberAdd;
import com.cos.jwtstudy1.model.MemberForm.MemberInfo;
import com.cos.jwtstudy1.reponse.SingleResult;
import com.cos.jwtstudy1.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class SignController {

	private final MemberService memberService;
	private final ResultApi resultApi;
	
	@ApiOperation(value = "회원등록")
	@PostMapping("/signup")
	public SingleResult<MemberInfo> signup(@RequestBody MemberAdd form) {
		
		log.info("회원등록 정보 :: {} ", form);
		return resultApi.getSingleResult(MemberForm.setMember(memberService.add(form)));
	}
		
	@ApiOperation(value = "로그인")
	@PostMapping("/signin")
	public SingleResult<String> signin(@RequestBody MemberForm.SignInfo info) {
		String username = info.getUsername();
		String password = info.getPassword();
		
		return resultApi.getSingleResult(memberService.signin(username, password));
		
	}
}

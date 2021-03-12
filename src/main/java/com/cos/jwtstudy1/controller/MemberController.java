package com.cos.jwtstudy1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtstudy1.code.ResultApi;
import com.cos.jwtstudy1.model.Member;
import com.cos.jwtstudy1.model.MemberForm;
import com.cos.jwtstudy1.model.MemberForm.MemberInfo;
import com.cos.jwtstudy1.reponse.ListResult;
import com.cos.jwtstudy1.reponse.SingleResult;
import com.cos.jwtstudy1.repository.MemberRepository;
import com.cos.jwtstudy1.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = {"2. Member"})
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class MemberController {
	
	private final MemberService memberService;
	private final ResultApi resultApi;
//	private final ObjectMapper mapper;
//	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="X-AUTH-TOKEN", value = "로그인 성공 후 access Token", dataType = "string", paramType = "header")
	})
	@ApiOperation(value = "회원단건조회")
	@PostMapping("/member")
	public SingleResult<MemberInfo> memberInfo(@ApiParam(name = "회원명") @RequestParam String username) {
		
		log.info("회원 정보 :: {} ", username);
		return resultApi.getSingleResult(MemberForm.setMember(memberService.findOne(username)));
	} 
	
	@ApiOperation(value = "회원 전체 목록")
	@GetMapping("/members")
	public ListResult<MemberInfo> members() {
		
		List<Member> list = memberRepository.findAll();
		
		List<MemberInfo> mlist = new ArrayList<>();
		list.forEach(m -> mlist.add(MemberForm.setMember(m)));
		
		return resultApi.getListResult(mlist);
	}
	
	
	
	
}

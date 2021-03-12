package com.cos.jwtstudy1.controller.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtstudy1.advice.exception.AuthenticationEntryPointException;
import com.cos.jwtstudy1.reponse.CommonResult;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

	@GetMapping("/entrypoint")
	public CommonResult entrypoint() {
		throw new AuthenticationEntryPointException();
	}
	
	@GetMapping("/accessdenied")
	public CommonResult accessdenied() {
		throw new AuthenticationEntryPointException();
	}
}

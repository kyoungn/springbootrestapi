package com.cos.jwtstudy1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;

@Api(tags = {"1. INDEX"})
@Controller
public class IndexController {

//	@Secured("hasRole('ROLE_GENERAL')") //@PreAuthorize("hasRole('ROLE_GENERAL')") //
	@GetMapping("/")
	public String index() {
		return "index";
	}
}

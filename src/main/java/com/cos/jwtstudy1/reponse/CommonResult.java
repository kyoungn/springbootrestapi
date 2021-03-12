package com.cos.jwtstudy1.reponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
	
	@ApiModelProperty(name = "응답 성공여부")
	private boolean success;
	@ApiModelProperty(name = "응답 코드")
	private int code;
	@ApiModelProperty(name = "응답 내용")
	private String msg;
}

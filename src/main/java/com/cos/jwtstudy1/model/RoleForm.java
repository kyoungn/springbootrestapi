package com.cos.jwtstudy1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RoleForm {
	private long id;
	private String roleName;
}

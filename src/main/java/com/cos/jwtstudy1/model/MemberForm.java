package com.cos.jwtstudy1.model;

import java.util.ArrayList;
import java.util.List;

import com.cos.jwtstudy1.model.MemberForm.MemberInfo.RoleInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class  MemberForm {
	
	@Setter
	@Getter
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MemberAdd {
		@ApiModelProperty(value = "사용자명", required = true)
		private String username;
		@ApiModelProperty(value = "비밀번호", required = true)
		private String password;
		
		@ApiModelProperty(value = "역할목록", required = true)
		List<RoleAdd> roles;
		
		@Setter
		@Getter
		@Builder
		@ToString
		@AllArgsConstructor
		@NoArgsConstructor
		public static class RoleAdd {
			@ApiModelProperty(value = "역할명", required = true)
			private String roleName;
		}
	}
	
	@Setter
	@Getter
	@Builder
	@ToString
	public static class MemberInfo {
		@ApiModelProperty(value = "사용자아이디")
		private Long id;
		@ApiModelProperty(value = "사용자명")
		private String username;
		@ApiModelProperty(value = "비밀번호")
		private String password;
		
		@ApiModelProperty(value = "역할목록")
		List<RoleInfo> roles;
		
		@Setter
		@Getter
		@Builder
		@ToString
		public static class RoleInfo {
			@ApiModelProperty(value = "역할아이디")
			private Long id;
			@ApiModelProperty(value = "역할명", required = true)
			private String roleName;
		}
	}
	
	@Setter
	@Getter
	@Builder
	@ToString
	public static class SignInfo {
		@ApiModelProperty(value = "사용자명")
		private String username;
		@ApiModelProperty(value = "비밀번호")
		private String password;
	}
	
	public static MemberInfo setMember(Member member) {
		MemberForm.MemberInfo m = MemberForm.MemberInfo.builder()
												.id(member.getId())
												.password(member.getPassword())
												.username(member.getUsername())
												.roles(new ArrayList<RoleInfo>())
												.build();
		
		for(Role r : member.getRoles()) {
			m.getRoles().add(MemberForm.MemberInfo.RoleInfo.builder().id(r.getId()).roleName(r.getRoleName()).build());
		}
		
		try {
			log.info(" {} ", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(m));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return m;
	}
}

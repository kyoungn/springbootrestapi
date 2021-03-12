package com.cos.jwtstudy1.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwtstudy1.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	public Member findByUsername(String username);
}

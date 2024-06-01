package com.glowriters.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glowriters.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}

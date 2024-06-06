package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//키워드와 닉네임이 일치하는 회원을 찾아서 리턴
	@Query("SELECT m FROM Member m WHERE m.member_nickname LIKE %:keyword%")
	List<Member> findByMemberNicknameContaining(@Param("keyword") String keyword);
}

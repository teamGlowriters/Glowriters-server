package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Reportmember;

public interface ReportMemberRepository extends JpaRepository<Reportmember, Long>{
	
	//신고된 멤버중에 멤버아이디로 갯수를 리턴
	@Query("SELECT COUNT(rm) FROM Reportmember rm WHERE rm.member.member_id = :memberId")
  int countByMemberId(long memberId);
	
	//신고된 멤버중에 멤버 닉네임으로 갯수를 리턴 (안씀.)
	@Query("SELECT COUNT(rm) FROM Reportmember rm WHERE rm.member.member_nickname = :memberNickname")
  int countByMemberNickname(String memberNickname);
	
	//멤버 닉네임으로 검색하는 함수
	@Query("SELECT rm FROM Reportmember rm WHERE rm.member.member_nickname LIKE %:memberNickname%")
  List<Reportmember> findAllByMemberNickname(String memberNickname);
	
	//id를 받아서 행을 아예 삭제
	@Transactional
	@Modifying
	@Query("DELETE FROM Reportmember rm WHERE rm.reportmember_id = :reportmemberId")
	void deleteByReportmemberId(Long reportmemberId);
}

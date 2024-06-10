package com.glowriters.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Reportmember;
import com.glowriters.repository.ReportMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportMemberService {
	
	private final ReportMemberRepository reportMemberRepository;
	
	public Reportmember save(Member member, Reportmember rm) {
		rm.setMember(member);
		return reportMemberRepository.save(rm);
	}
	
	@Transactional
	public int countByMemberId(long member_id) {
		return reportMemberRepository.countByMemberId(member_id);
	}
	
	@Transactional
	public int countByMemberNickname(String member_nickname) {
		return reportMemberRepository.countByMemberNickname(member_nickname);
	}
	
	@Transactional
	public List<Reportmember> findAll(){
		return reportMemberRepository.findAll();
	}
	
	@Transactional
	public List<Reportmember> findAllByMemberNickname(String member_nickname){
		return reportMemberRepository.findAllByMemberNickname(member_nickname);
	}
	
	@Transactional
	public void deleteById(long reportmember_id) {
		reportMemberRepository.deleteByReportmemberId(reportmember_id);
	}
	
}

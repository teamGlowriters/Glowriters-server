package com.glowriters.service;

import org.springframework.stereotype.Service;

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
	
}

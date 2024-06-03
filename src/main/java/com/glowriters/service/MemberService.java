package com.glowriters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glowriters.domain.Member;
import com.glowriters.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
		@Autowired
		private final MemberRepository memberRepository;

		public MemberService(MemberRepository memberRepository) {
			this.memberRepository = memberRepository;
		}
		
		public Member save(Member member) {
			return memberRepository.save(member);
		}
		
		public Member findById(Long id) {
			Member member = memberRepository.findById(id).orElse(null);
			
			return member;
		}
		
		public List<Member> findAll() {
			return memberRepository.findAll();
		}
		
    public void delete(Long id) {
      Member member = memberRepository.findById(id).get();
      member.setMember_status(0);;
      memberRepository.save(member);
    }

}

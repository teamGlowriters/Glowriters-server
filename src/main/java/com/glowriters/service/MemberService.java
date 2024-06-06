package com.glowriters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glowriters.domain.Member;
import com.glowriters.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
		@Autowired
		private final MemberRepository memberRepository;

		public MemberService(MemberRepository memberRepository) {
			this.memberRepository = memberRepository;
		}
		
		@Transactional
		public Member save(Member member) {
			return memberRepository.save(member);
		}
		
		public Member findById(Long member_id) {
			Member member = memberRepository.findById(member_id).orElse(null);
			return member;
		}
		
		public Member findByHashCode(String hashcode) {
			List<Member> members = memberRepository.findAll();
			for (Member member : members) {
				if(member.getMember_hashcode().equals(hashcode)) {
					return member;
				}
			}
			return null;
		}
		
		public List<Member> findAll() {
			return memberRepository.findAll();
		}
		
		@Transactional
    public void delete(Long member_id) {
      Member member = memberRepository.findById(member_id).get();
      member.setMember_status(0);;
      memberRepository.save(member);
    }
}

package com.glowriters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.repository.MemberRepository;
import com.glowriters.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {

	@Autowired
	private final PostRepository postRepository;

	@Autowired
	private final MemberRepository memberRepository;

	private final MemberService memberService;

	public PostService(PostRepository postRepository, MemberRepository memberRepository, MemberService memberService) {
		this.postRepository = postRepository;
		this.memberRepository = memberRepository;
		this.memberService = memberService;
	}

//	@Transactional
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Transactional
	public Post createPost(Long memberId, Post post) {
		Member member = memberService.findById(memberId);
//		log.info("1  " + member.getMember_id());
//		log.info("2  " + member.getMember_hashcode());
//		log.info("3  " + member.getMember_email());
//		log.info("4  " + member.getMember_nickname());
//		log.info("5  " + member.getMember_profile());
//		log.info("6  " + member.getMember_comment());
//		log.info("7  " + member.getMember_status());
		
		
		post.setMember(member);
		return postRepository.save(post);
	}

}

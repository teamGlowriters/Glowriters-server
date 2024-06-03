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
	private final MemberService memberService;

	public PostService(PostRepository postRepository, MemberService memberService) {
		this.postRepository = postRepository;
		this.memberService = memberService;
	}

	@Transactional
	public Post save(long member_id, Post post) {
		Member member = memberService.findById(member_id);
		post.setMember(member);
		return postRepository.save(post);
	}
}

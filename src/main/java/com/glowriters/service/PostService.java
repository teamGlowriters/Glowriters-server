package com.glowriters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		// 현재 로그인한 사용자의 member_id를 통해 DB에서 현재 로그인한 member 객체 가지고옴
		Member member = memberService.findById(member_id);
		// post entity에 member를 넣어준 후
		post.setMember(member);
		// post save 해주면 DB 저장 완료 DB에는 알아서 member_id 컬럼이 들어감
		return postRepository.save(post);
	}
	
	@Transactional
	public Post findById(long post_id) {
		return postRepository.findById(post_id).orElse(null);
	}
	
	@Transactional
	public List<Post> findAll(){
		return postRepository.findAll();
	}
	
	@Transactional
  public List<Post> findByCategory(String category) {
      return postRepository.findByCategory(category);
  }
	
	//업데이트날짜기준 최근 cnt개의 게시물을 가져오는 함수
	@Transactional
  public List<Post> findByRecentCnt(long cnt) {
		List<Post> posts = postRepository.findAll();
    return posts.stream()
                .sorted((p1, p2) -> p2.getUpdated_date().compareTo(p1.getUpdated_date()))
                .limit(cnt)
                .collect(Collectors.toList());
  }
}

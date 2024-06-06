package com.glowriters.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
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
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Transactional
	public List<Post> findByCategory(String category) {
		return postRepository.findByCategoryOrderByCreatedDateDescJPQL(category);
	}

	// 블로거가 작성한 게시글을 가져오는 함수
	@Transactional
	public List<Post> findByMemberId(long member_id) {
		return postRepository.findAll().stream()
        .filter(post -> post.getMember().getMember_id() == member_id)
        .collect(Collectors.toList());
	}

	// 업데이트날짜 기준 최근 cnt개의 게시물을 가져오는 함수
	@Transactional
	public List<Post> findByRecentCnt(long cnt) {
		return postRepository.findAll().stream().sorted(Comparator.comparing(Post::getUpdated_date).reversed()).limit(cnt)
				.collect(Collectors.toList());
	}

	// 요일별 게시물을 가져오는 함수
	// findByUpdateDateOfWeek("Monday"); <= 이렇게 호출
	@Transactional
	public List<Post> findByUpdateDateOfWeek(String week) {
		// 문자열로 표현된 요일을 DayOfWeek 열거형 상수로 변환
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(week.toUpperCase());

		List<Post> allPosts = postRepository.findAll();

		// 요청된 요일에 해당하는 게시물만 필터링하여 반환
		return allPosts.stream().filter(post -> post.getUpdated_date().getDayOfWeek() == dayOfWeek)
				.collect(Collectors.toList());
	}

	@Transactional
	public long countPostByMemberId(long member_id) {
		return postRepository.countBymemberId(member_id);
	}
	
	@Transactional
	public List<Post> findByTitleByKeyword(String keyword){
		return postRepository.findByTitleContaining(keyword);
	}
}

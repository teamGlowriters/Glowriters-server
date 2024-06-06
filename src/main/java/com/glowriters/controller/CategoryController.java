package com.glowriters.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.glowriters.DTO.MemberViewDTO;
import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.SubscriberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor //@Autowired를 안써도됨
@Slf4j
public class CategoryController {
	private final MemberService memberService;
	private final PostService postService;
	private final PostFileSerivce postFileSerivce;
	private final SubscriberService subscriberService;

	@GetMapping("/category/category/{category}")
	public String viewCategory(@PathVariable("category") String category, Model model) {

		// Post 테이블의 category 컬럼과 url로 받은 카테고리이 같은 행만 가져오기
		List<Post> posts = postService.findByCategory(category);
		// 정보전달이 목적인 PostViewDTO 객체 생성!
		List<PostViewDTO> pvds = new ArrayList<PostViewDTO>();

		for (Post post : posts) {
			PostViewDTO pvd = new PostViewDTO();
			// member
			Member member = post.getMember();
			pvd.setMember_nickname(member.getMember_nickname());
			pvd.setMember_profile(member.getMember_profile());

			// post
			pvd.setPost_id(post.getPost_id());
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setCategory(post.getCategory());
			pvd.setCreated_date(post.getCreated_date());

			// postfile
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());
			for (Postfile postfile : postfiles) {
				pvd.setFilepath(postfile.getFilepath());
				break;
			}

			// subscriber
			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member.getMember_id()));
			pvds.add(pvd);
		}

		// 인기블로거
		List<Member> members = memberService.findAll();
		List<MemberViewDTO> mvds = new ArrayList<MemberViewDTO>();

		for (Member member : members) {
			MemberViewDTO mvd = new MemberViewDTO();
			// 인기 블로거 이름
			mvd.setMember_nickname(member.getMember_nickname());
			// 인기 블로거 프로필
			mvd.setMember_profile(member.getMember_profile());
			// 인기 블로거 구독자 수
			long subscriberCnt = subscriberService.countSubscribersByBloggerId(member.getMember_id());
			mvd.setSubscriberCount(subscriberCnt);
			// 블로거가 작성한 게시물 개수
			long postCnt = postService.countPostByMemberId(member.getMember_id());
			mvd.setPostCount(postCnt);
			mvds.add(mvd);
		}

		Collections.sort(mvds, new Comparator<MemberViewDTO>() {
			@Override
			public int compare(MemberViewDTO o1, MemberViewDTO o2) {
				return Long.compare(o2.getSubscriberCount(), o1.getSubscriberCount()); // 내림차순 정렬
			}
		});

		mvds = mvds.size() > 6 ? new ArrayList<>(mvds.subList(0, 6)) : mvds;

		model.addAttribute("pvds", pvds);
		model.addAttribute("mvds", mvds);
		model.addAttribute("category", category);

		// 사이드메뉴에서 현재 페이지가 어디인지 하이라이트 표시
		model.addAttribute("sidemenu", "category");
		return "/category/category";
	}
}

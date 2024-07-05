package com.glowriters.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowriters.DTO.MemberViewDTO;
import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.KakaoService;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.SubscriberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor // @Autowired를 안써도됨
@Slf4j
public class MainController extends BaseController {
	private final KakaoService kakaoService;
	private final PostService postService;
	private final PostFileSerivce postFileSerivce;
	private final SubscriberService subscriberService;
	private final MemberService memberService;

	// 게시물DTO를 생성하는 공통 함수
	public List<PostViewDTO> getPostViewDTO(List<Post> posts) {
		List<PostViewDTO> pvds = new ArrayList<>();

		for (Post post : posts) {
			PostViewDTO pvd = new PostViewDTO();
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());

			Member member = post.getMember();
			long member_id = member.getMember_id();
			String member_nickname = member.getMember_nickname();
			String member_profile = member.getMember_profile();
			String member_comment = member.getMember_comment();

			// 공통 부분
			pvd.setMember_nickname(member_nickname);
			pvd.setMember_profile(member_profile);
			pvd.setMember_comment(member_comment);

			pvd.setPost_id(post.getPost_id());
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setCategory(post.getCategory());
			pvd.setCreated_date(post.getCreated_date());
			pvd.setUpdated_date(post.getUpdated_date());

			pvd.setFilepath(postfiles.get(0).getFilepath()); // 가장 첫번째 이미지를 타이틀이미지로저장

			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member_id));

			String NEW = pvd.getCreated_date().isEqual(pvd.getUpdated_date()) ? "yes" : "no";
			pvd.setNEW(NEW);

//			pvd.setLikeCount(0); //아직 구현안함
			pvds.add(pvd);
		}

		return pvds;
	}

	// 블로거DTO를 생성하는 공통 함수
	public List<MemberViewDTO> getMemberViewDTO(List<Member> members) {
		List<MemberViewDTO> mvds = new ArrayList<>();

		for (Member member : members) {
			MemberViewDTO mvd = new MemberViewDTO();
			long member_id = member.getMember_id();
			String member_nickname = member.getMember_nickname();
			String member_profile = member.getMember_profile();
			String member_comment = member.getMember_comment();

			mvd.setMember_id(member_id);
			mvd.setMember_nickname(member_nickname);
			mvd.setMember_profile(member_profile);
			mvd.setMember_comment(member_comment);

			long postCount = postService.countPostByMemberId(member_id);
			mvd.setPostCount(postCount);

			long subscriberCount = subscriberService.countSubscribersByBloggerId(member_id);
			mvd.setSubscriberCount(subscriberCount);

			// 현재 블로거가 작성한 모든 게시글을가져온다.
			List<String> categorys = postService.getCategoriesByMemberIdAndPostStatus(member_id);
			// 카테고리 안에 중복된 값들이 있으므로 중복된 값을 제거해서 보내줘야함
			Set<String> categorySet = new HashSet<>(categorys);
			List<String> uniqueCategorys = new ArrayList<>(categorySet);
			mvd.setCategorys(uniqueCategorys);

			mvds.add(mvd);
		}

		return mvds;
	}

	// 웰컴페이지 담당 컨트롤러
	@RequestMapping("/")
	public String viewMain(Model model, HttpSession session) {
		// 1. 카카오 접속 url을 페이지에 주입
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

		// 2. 메인 슬라이드(전체 게시물)
		// 최근 cnt개의 게시물을 가져와서 뷰에 모델로 전달
		List<Post> recents = postService.findByRecentCnt(8);
		List<PostViewDTO> mainSlideItems = getPostViewDTO(recents);
		model.addAttribute("mainSlideItems", mainSlideItems);

		// 3. 추천블로거란
		List<Member> bloggers = memberService.findAll();
		List<MemberViewDTO> recommendedBloggers = getMemberViewDTO(bloggers);
		// 6개만 전달
		recommendedBloggers = recommendedBloggers.size() > 6 ? new ArrayList<>(recommendedBloggers.subList(0, 6))
				: recommendedBloggers;
		model.addAttribute("recommendedBloggers", recommendedBloggers);

		// 4. 푸터 슬라이드 (전체 게시물 보여줌)
		List<Post> allPosts = postService.findAll();
		List<PostViewDTO> footerSlideItems = getPostViewDTO(allPosts);
		model.addAttribute("footerSlideItems", footerSlideItems);
		model.addAttribute("footerSlideItemsCount", allPosts.size());

		return "/main/main";
	}

	// 요일별 게시물을 비동기통신으로 보여주는 컨트롤러
	@GetMapping("/weekends/{sort}")
	public String viewWeekend(Model model, @PathVariable("sort") String sort, @RequestParam("week") String week) {
//		log.warn("/weekends/" + sort);
//		log.warn("컨트롤러 호출");
		// 게시물에 대한 정보를 가져옴
		List<Post> posts = postService.findByUpdateDateOfWeek(week);
		List<PostViewDTO> pvds = getPostViewDTO(posts);

		// 가져온 게시물들을 정렬 방법에 따라 정렬
		if (sort.equals("recent"))
			pvds = pvds.stream().sorted(Comparator.comparing(PostViewDTO::getUpdated_date).reversed())
					.collect(Collectors.toList());
		else if (sort.equals("like"))
			pvds = pvds.stream().sorted(Comparator.comparing(PostViewDTO::getLikeCount).reversed())
					.collect(Collectors.toList());

		// 정렬하고나서 8개만 전달
		pvds = pvds.size() > 8 ? new ArrayList<>(pvds.subList(0, 8)) : pvds;

		model.addAttribute("weekendItems", pvds);
		return "/main/main :: #weekends";
	}

}
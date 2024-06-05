package com.glowriters.controller;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.KakaoService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.SubscriberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController extends BaseController {
	@Autowired
	private final KakaoService kakaoService;

	@Autowired
	private final PostService postService;

	@Autowired
	private final PostFileSerivce postFileSerivce;

	@Autowired
	private final SubscriberService subscriberService;

	// 웰컴페이지 담당 컨트롤러
	@GetMapping("/")
	public String viewMain(Model model, HttpSession session) {
		// 1. 카카오 접속 url을 페이지에 주입
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

		// 2. 메인 슬라이드(전체 게시물)
		List<PostViewDTO> mainSlideItems = new ArrayList<>();
		// 최근 8개의 게시물을 가져와서 뷰에 모델로 전달
		List<Post> recents = postService.findByRecentCnt(8);
		for (Post post : recents) {
			PostViewDTO pvd = new PostViewDTO();
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());

			String member_nickname = post.getMember().getMember_nickname();
			long member_id = post.getMember().getMember_id();

			pvd.setPost_id(post.getPost_id());
			pvd.setFilepath(postfiles.get(0).getFilepath()); // 가장 첫번째 이미지를 타이틀이미지로저장
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setMember_nickname(member_nickname);
			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member_id));

			mainSlideItems.add(pvd); // 게시물 하나 저장
		}
		model.addAttribute("mainSlideItems", mainSlideItems); // 전체 게시물 묶음을 전달
//		model.addAttribute("weekendItems", null);

		return "main/main";
	}

	// viewWeekend컨트롤러에서 사용할 PostViewDTO를 만듬
	public List<PostViewDTO> getPostViewDTO(String week) {
		List<PostViewDTO> pvds = new ArrayList<>(); // 모델에 담을 주체
		List<Post> posts = postService.findByUpdateDateOfWeek(week);
		for (Post post : posts) {
			// 전달할 정보를 담음
			PostViewDTO pvd = new PostViewDTO();
			pvd.setPost_id(post.getPost_id());
			pvd.setCategory(post.getCategory());
			pvd.setTitle(post.getTitle());
			// 생성날짜와 변경날짜가같으면 새로 올린 게시글이다.
			String NEW = post.getCreated_date().isEqual(post.getUpdated_date()) ? "yes" : "no";
			pvd.setNEW(NEW);
			pvd.setMember_nickname(post.getMember().getMember_nickname());
			pvd.setUpdated_date(post.getUpdated_date());

			// 게시글아이디에 해당하는 모든 이미지를 가져옴
			List<Postfile> pfs = postFileSerivce.findAllByPost(post.getPost_id());
			for (Postfile pf : pfs) {
				pvd.setFilepath(pf.getFilepath()); // 가장 첫번째 이미지를 저장하고 종료
				break;
			}
			pvds.add(pvd);
		}

		return pvds;
	}

	// 요일별 게시물을 비동기로 보여주는 컨트롤러
	@PostMapping("/weekends/{sort}")
	public String viewWeekend(Model model, @PathVariable("sort") String sort, @RequestParam("week") String week) {
		log.warn("weekends/" + sort);

		// 게시물에 대한 정보를 가져옴
		List<PostViewDTO> pvds = getPostViewDTO(week);

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
		return "main/main :: #weekends";
	}

}
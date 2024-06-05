package com.glowriters.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping("/")
	public String viewMain(Model model, HttpSession session) {
		//1. 카카오 접속 url을 페이지에 주입
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		
		//2. 메인 슬라이드(전체 게시물)
		List<PostViewDTO> mainSlideItems = new ArrayList<>();
		//최근 8개의 게시물을 가져와서 뷰에 모델로 전달
		List<Post> recents = postService.findByRecentCnt(8);
		for (Post post : recents) {
			PostViewDTO pvd = new PostViewDTO();
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());
			
			String member_nickname = post.getMember().getMember_nickname();
			long member_id = post.getMember().getMember_id();
			
			pvd.setPost_id(post.getPost_id());
			pvd.setFilepath(postfiles.get(0).getFilepath()); //가장 첫번째 이미지를 타이틀이미지로저장
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setMember_nickname(member_nickname);
			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member_id));
			
			mainSlideItems.add(pvd); //게시물 하나 저장
		}
		model.addAttribute("mainSlideItems", mainSlideItems); //전체 게시물 묶음을 전달
		
		//요일별 연재 게시물 리스트보기
		List<Post> tue = postService.findByUpdateDateOfWeek("TUESDAY");
		log.warn(" tue 갯수 = " + tue.size());
		for (Post post : tue) {
			log.warn(" tue    =   " + post.getUpdated_date());
		}
		
		
		
		return "main/main";
	}
	
	
	
	
	

}
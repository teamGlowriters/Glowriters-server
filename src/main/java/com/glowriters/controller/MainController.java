package com.glowriters.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Post;
import com.glowriters.service.KakaoService;
import com.glowriters.service.PostService;

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

	@GetMapping("/")
	public String viewMain(Model model, HttpSession session) {
		//카카오 접속 url을 페이지에 주입
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		
		//메인 슬라이드(전체 게시물)
		List<PostViewDTO> mainSlideItems = new ArrayList<>();
		
		//최근 8개의 게시물을 가져옴
		List<Post> recents = postService.findByRecentCnt(8);
		for (Post post : recents) {
			log.info(" 하나씪 출력 :  " + post.getUpdated_date());
		}
		
		return "main/main";
	}
	
	
	
	
	

}

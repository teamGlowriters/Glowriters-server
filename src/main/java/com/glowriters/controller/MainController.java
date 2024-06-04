package com.glowriters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowriters.service.KakaoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController extends BaseController {
	@Autowired
	private final KakaoService kakaoService;

	@GetMapping("/")
	public String viewMain(Model model, HttpSession session) {
		//카카오 접속 url을 페이지에 주입
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		
		//메인 슬라이드(전체 게시물)
//		List<MainSlideDTO> mainSlideItems = new ArrayList<>();
		
		
		
//		model.addAttribute("mainSlideItems", mainSlideItems);
		
		
		

		return "main/main";
	}
	
	
	
	
	

}

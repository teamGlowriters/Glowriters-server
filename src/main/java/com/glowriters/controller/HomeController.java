package com.glowriters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowriters.service.KakaoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class HomeController {
	private final KakaoService kakaoService;

	//웰컴페이지 설정
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		return "main/main";
	}
	
	
}

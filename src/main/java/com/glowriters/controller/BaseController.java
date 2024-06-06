package com.glowriters.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

//모든 컨트롤러가 상속받는 컨트롤러
//세션을 바탕으로 모델에 주입
public class BaseController {
	
	@ModelAttribute("isLogin")
	public String setLoginStatus(HttpSession session) {
    return session.getAttribute("member_id") == null ? "no" : "yes";
	}
	
	@ModelAttribute("member_id")
	public String setMember_id(HttpSession session) {
		return String.valueOf(session.getAttribute("member_id"));
	}
	
	@ModelAttribute("member_profile")
	public String setMember_profile(HttpSession session) {
		return String.valueOf(session.getAttribute("member_profile"));
	}
	
	@ModelAttribute("member_nickname")
	public String setMember_nickname(HttpSession session) {
		return String.valueOf(session.getAttribute("member_nickname"));
	}

}

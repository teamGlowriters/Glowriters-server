package com.glowriters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

//모든 컨트롤러가 상속받는 컨트롤러
//세션을 바탕으로 모델에 주입
//PostViewDTO와 MemberViewDTO를 쉽게 생성해주는 함수를 가지고 있음
@Controller
public class BaseController {
	
	//1. 모델에 값을 넣어주는 기능 (isLogin, member_id, member_profile, member_nickname)
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

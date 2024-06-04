package com.glowriters.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

//모델에 자동으로 주입할 속성들. : 상속만 받으면 사용가능
//상속받을시 모델에 "isLogin"키에  "yes" "no"로 저장해줌
//상속받을시 모델에 member_id값을 넣어줌.
public class BaseController {
	
	@ModelAttribute("isLogin")
	public String setLoginStatus(HttpSession session) {
    return session.getAttribute("member_id") == null ? "no" : "yes";
	}
	
	@ModelAttribute("member_id")
	public String setMember_id(HttpSession session) {
		return String.valueOf(session.getAttribute("member_id"));
	}

}

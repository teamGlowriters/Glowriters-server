package com.glowriters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowriters.domain.KakaoDTO;
import com.glowriters.domain.Member;
import com.glowriters.service.KakaoService;
import com.glowriters.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
	private final KakaoService kakaoService;
	private final ObjectMapper objectMapper;
	
	@Autowired
	private final MemberService memberService;
	//메인뷰에서 로그인 버튼을 눌렀을때
	@GetMapping("/oauth")
	public String callback(HttpServletRequest request) throws Exception{
		//카카오로부터 받은 인증코드를 통해 사용자정보를담은 domain객체를 생성(서비스호출)
		KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
		Member member = new Member();
		member.setMember_hashcode(String.valueOf(kakaoInfo.getId()));
		member.setMember_email(kakaoInfo.getEmail());
		member.setMember_nickname(kakaoInfo.getNickname());
		member.setMember_profile(kakaoInfo.getProfileImage());
    		
	  // member 테이블에 이미 회원으로 존재하는지 알아보기 위한 코드
		List<Member> members = memberService.findAll();
		boolean istrue = false;
		
		for (Member m : members) {
			if(m.getMember_hashcode().equals(member.getMember_hashcode())) {
				istrue = true;
				break;
			}
		}
		
		if(istrue==false) {
			memberService.save(member);
		}
		
		//로그인 정보를 세션에 담는다.		
		HttpSession session = request.getSession();
		session.setAttribute("member_id", member.getMember_id());
		
		return "/main/main";
	}
}

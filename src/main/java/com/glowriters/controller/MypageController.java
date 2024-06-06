package com.glowriters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.glowriters.domain.Member;
import com.glowriters.service.MemberService;
import com.glowriters.service.SubscriberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor //@Autowired를 안써도됨
public class MypageController extends BaseController {
	private final MemberService memberService;
	private final SubscriberService subscriberService;

	// 메인페이지에서 현재로그인한 사용자의 member_id 값을 보내줄 것이므로 url에 member_id을 받음
	@GetMapping("/mypage/mypage/{member_id}")
	public String viewMypage(@PathVariable("member_id") Long member_id, Model model) {
		// url에 입력받은 사용자 찾기
		Member member = memberService.findById(member_id);

		// view에서 타임리프로 현재 사용자의 정보를 보여줘야 하기 때문에 model에 담기
		// member라는 이름으로 member 사용
		model.addAttribute("member", member);

		long subscriberCount = subscriberService.countSubscribersByBloggerId(member_id);
		model.addAttribute("subscriberCount", subscriberCount);

		// 사이드메뉴에서 현재 페이지가 어디인지 하이라이트 표시
		model.addAttribute("sidemenu", "mypage");
		
		return "/mypage/mypage";
	}

	// 프로필편집 눌렀을때 mypage-sub.html로 가게하는 GetMapping
	@GetMapping("/mypage/mypage-sub/{member_id}")
	public String viewMypageSub(@PathVariable("member_id") Long member_id, Model model) {
		Member member = memberService.findById(member_id);
		model.addAttribute("member", member);
		return "/mypage/mypage-sub";
	}

	// mypage-sub.html에서 정보를 수정하고 난 후 저장하기를 눌렀을때 PostMapping
	@PostMapping("/mypage/mypage-sub/{member_id}")
	public String memberUpdate(@PathVariable("member_id") Long member_id, Member member) {
		// 저장하기 버튼 눌렀을때 member_id를 보냄으로써 현재 회원 정보를 업데이트할 사용자를 찾아올 수 있음
		Member updateMember = memberService.findById(member_id);
		// 업데이트할 사용자에 업데이트 정보(이름, 소개)를 setter를 통해 넣어주고
		updateMember.setMember_nickname(member.getMember_nickname());
		updateMember.setMember_comment(member.getMember_comment());
		// save 해주면 업데이트 완료!!
		memberService.save(updateMember);

		return "redirect:/mypage/mypage/{member_id}";
	}

}

package com.glowriters.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.DTO.SubscriberDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Subscriber;
import com.glowriters.service.MemberService;
import com.glowriters.service.SubscriberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubscriberController extends BaseController{
	
	private final SubscriberService subscriberService;
	
	
	@GetMapping("/subscriber/subscriber/{member_id}")
	public String viewSubscriber(@PathVariable("member_id") long member_id, Model model) {
		
		List<SubscriberDTO> suds = new ArrayList<SubscriberDTO>();
		
		// 해당 member를 구독하는 정보를 알 수 있는 행 모두 반환(status=1)
		List<Subscriber> subscribers = subscriberService.getActiveSubscribersByBloggerId(member_id);
		for (Subscriber subscriber : subscribers) {
			SubscriberDTO sud = new SubscriberDTO();
		  Member subMember = subscriber.getSubscriber();
			sud.setMember_id(subMember.getMember_id());
			sud.setMember_nickname(subMember.getMember_nickname());
			sud.setMember_comment(subMember.getMember_comment());
			sud.setMember_profile(subMember.getMember_profile());	
			
			suds.add(sud);
		}
		long subCount = subscriberService.countSubscribersByBloggerId(member_id);
		model.addAttribute("member_id", member_id);
		model.addAttribute("subCount", subCount);
		model.addAttribute("suds", suds);
		return "/subscriber/subscriber";
	}
}

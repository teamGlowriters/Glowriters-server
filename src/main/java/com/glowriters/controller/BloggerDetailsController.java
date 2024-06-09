package com.glowriters.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glowriters.DTO.MemberViewDTO;
import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.domain.Reportmember;
import com.glowriters.domain.Subscriber;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.ReplyService;
import com.glowriters.service.ReportMemberService;
import com.glowriters.service.SubscriberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BloggerDetailsController {

	private final MemberService memberService;

	private final PostService postService;

	private final PostFileSerivce postFileSerivce;

	private final SubscriberService subscriberService;

	private final ReplyService replyService;
	
	private final ReportMemberService reportMemberService;
	
	@GetMapping("/blogger-details/blogger-details/{member_id}")
	public String viewBlogger(HttpServletRequest request, @PathVariable("member_id") long member_id, Model model) {
		MemberViewDTO mvd = new MemberViewDTO();
		List<PostViewDTO> pvds = new ArrayList<PostViewDTO>();
		int memberEqualBlogger = 0;
		// 현재 로그인한 사용자의 정보
		HttpSession session = request.getSession();
		long memberId = (long) session.getAttribute("member_id");
		Member loginMember = memberService.findById(memberId);

		// 블로거에 대한 정보
		Member blogger = memberService.findById(member_id);

		if (loginMember.getMember_id() == blogger.getMember_id()) {
			memberEqualBlogger = 1;
		}
		
		// subscriber
		// 로그인한 사용자가 해당 블로거를 구독했는지
		// 구독했다면 테이블에 존재해서 1을 반환 해줄 것임
		int isSubscribe = subscriberService.checkSubscriptionStatus(blogger.getMember_id(), loginMember.getMember_id());
		if(isSubscribe == 1) {
			Subscriber subsc = subscriberService.getSubscriber(blogger.getMember_id(), loginMember.getMember_id());
			mvd.setIsSubscribe(subsc.getSubscribe_status());
		}
		
		mvd.setMember_id(blogger.getMember_id());
		mvd.setMember_nickname(blogger.getMember_nickname());
		mvd.setMember_profile(blogger.getMember_profile());
		mvd.setMember_comment(blogger.getMember_comment());
		mvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(blogger.getMember_id()));
		mvd.setPostCount(postService.countPostByMemberId(blogger.getMember_id()));
		List<String> categorys = postService.getCategoriesByMemberIdAndPostStatus(blogger.getMember_id());
		// 카테고리 안에 중복된 값들이 있으므로 중복된 값을 제거해서 보내줘야함
		Set<String> categorySet = new HashSet<>(categorys);
		List<String> uniqueCategorys = new ArrayList<>(categorySet);
		mvd.setCategorys(uniqueCategorys);

		// 블로거가 작성한 글에 대한 정보
		List<Post> posts = postService.getPostsByMemberIdAndPostStatusOrderByCreatedDateDesc(blogger.getMember_id());
		for (Post post : posts) {
			PostViewDTO pvd = new PostViewDTO();

			// post
			pvd.setPost_id(post.getPost_id());
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setCategory(post.getCategory());
			pvd.setCreated_date(post.getCreated_date());

			// postfile
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());
			for (Postfile postfile : postfiles) {
				pvd.setFilepath(postfile.getFilepath());
				break;
			}

			// replyCount
			long replyCount = replyService.getCommentCountByPostId(post.getPost_id());
			pvd.setReplyCount(replyCount);
			
			pvds.add(pvd);
		}

		model.addAttribute("memberEqualBlogger", memberEqualBlogger);
		model.addAttribute("mvd", mvd);
		model.addAttribute("pvds", pvds);
		return "/blogger-details/blogger-details";
	}
	
	// 구독하기 눌렀을 때 PostMapping
	@PostMapping("/blogger-details/blogger-details/{member_id}")
	public String subscribeEnter(HttpServletRequest request, @PathVariable("member_id") long member_id) {
		// 현재 로그인한 사용자의 정보
		HttpSession session = request.getSession();
		long memberId = (long) session.getAttribute("member_id");
		Member loginMember = memberService.findById(memberId);
		Member blogger = memberService.findById(member_id);
		
		int isSubscribe = subscriberService.checkSubscriptionStatus(blogger.getMember_id(), loginMember.getMember_id());
		// 만약 테이블에 해당 행의 정보가 없으면
		if(isSubscribe == 0) {
			// 행 생성
			subscriberService.save(blogger, loginMember);
		}else {
			// 해당 행의 정보가 있으면 해당 행 가져오기
			Subscriber subsc = subscriberService.getSubscriber(blogger.getMember_id(), loginMember.getMember_id());
			if(subsc.getSubscribe_status() == 1) {
				subscriberService.subscribeCancel(subsc);
			}else {
				subscriberService.subscribeCancelCancel(subsc);
			}
		}
		
		return "redirect:/blogger-details/blogger-details/{member_id}";
	}
	
	// 신고하기 눌렀을때 PostMapping
	@PostMapping("/blogger-details/blogger-details/{member_id1}/{member_id2}")
	public String reportMember(@PathVariable("member_id1") long member_id, Reportmember reportmember) {
		Member badMember = memberService.findById(member_id);
		reportMemberService.save(badMember, reportmember);
		return "redirect:/";
	}
}

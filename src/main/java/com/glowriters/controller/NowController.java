package com.glowriters.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.SubscriberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NowController extends BaseController{
	
	@Autowired
	private final MemberService memberService;
	
	@Autowired
	private final PostService postService;
	
	@Autowired
	private final PostFileSerivce postFileSerivce;
	
	@Autowired
	private final SubscriberService subscriberService;
	
	@GetMapping("now/now")
	public String viewNow(Model model) {
		List<Post> posts = postService.findAll();
		List<Postfile> postfiles = postFileSerivce.findAll();
		
		List<PostViewDTO> pvds = new ArrayList<PostViewDTO>();
		
		for (Post post : posts) {
			PostViewDTO pvd = new PostViewDTO();
			// member
			Member member = post.getMember();
			pvd.setMember_nickname(member.getMember_nickname());
			pvd.setMember_profile(member.getMember_profile());
			
			// post
			pvd.setPost_id(post.getPost_id());
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setCategory(post.getCategory());
			pvd.setCreate_date(post.getCreated_date());
			
			// postfile
			for (Postfile postfile : postfiles) {
				Post viewPost = postfile.getPost();
				if(viewPost.getPost_id() == post.getPost_id()) {
					pvd.setFilepath(postfile.getFilepath());
					break;
				}
			}
			
			// subscriber
			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member.getMember_id()));
			pvds.add(pvd);
		}
		model.addAttribute("pvds", pvds);
		
		return "now/now";
	}

}

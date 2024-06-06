package com.glowriters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.glowriters.domain.Post;
import com.glowriters.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor //@Autowired를 안써도됨
public class PostDetailsController {
	private final PostService postService;
	
	@GetMapping("/post-details/post-details/{post_id}")
	public String viewPostDetails(@PathVariable("post_id") long post_id, Model model) {
		Post post = postService.findById(post_id);
		model.addAttribute("post", post);
		return "/post-details/post-details";
	}
}

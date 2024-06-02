package com.glowriters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.service.PostService;



@Controller
public class WriteController {
	@Autowired
	private final PostService postService;

	public WriteController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("write/write")
	public String viewWrite() {
		return "write/write";
	}
	
	@PostMapping("write/write")
	public String writePost(Post post) {
				return null;
		
	}
	
	
	
	
	
}

package com.glowriters.controller;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class WriteController {
	@Autowired
	private final PostService postService;
	
	@Autowired
	private final MemberService memberService;

	public WriteController(PostService postService, MemberService memberService) {
		this.postService = postService;
		this.memberService = memberService;
	}

	@GetMapping("write/write")
	public String viewWrite() {
		return "write/write";
	}
	
	@PostMapping("write/write")
	public String writePost(HttpServletRequest request, @RequestParam("files") MultipartFile[] files, Post post) {
		for (MultipartFile multipartFile : files) {
			log.info(multipartFile.getOriginalFilename());
		}
		HttpSession session = request.getSession();
		long member_id = (long) session.getAttribute("member_id");
		postService.save(member_id, post);
		return null;
	}
}

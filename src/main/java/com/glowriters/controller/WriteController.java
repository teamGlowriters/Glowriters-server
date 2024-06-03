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
import com.glowriters.domain.Postfile;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
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
	
	@Autowired
	private final PostFileSerivce postFileSerivce;
	
	public WriteController(PostService postService, MemberService memberService, PostFileSerivce postFileSerivce) {
		this.postService = postService;
		this.memberService = memberService;
		this.postFileSerivce = postFileSerivce;
	}

	@GetMapping("write/write")
	public String viewWrite() {
		return "write/write";
	}
	
	@PostMapping("write/write")
	public String writePost(HttpServletRequest request, @RequestParam("files") MultipartFile[] files, Post post) {
		
		// 현재 로그인한 사용자의 정보가 세션에 담겨있음
		// 따라서 현재 로그인한 사용자가 현재 게시물을 작성하였으므로 가져와야함
		HttpSession session = request.getSession();
		// 현재 로그인한 사용자의 정보를 가져온 후 그 안에 member_id를 통해 member_id 가져오기 
		long member_id = (long) session.getAttribute("member_id");
		// 가져온후 postService.save 통해 member_id와 post 객체를 넣어준 후
		postService.save(member_id, post);
		
		// post 테이블에 save 되었으면 postfile이 있을땐 postfile 테이블에 값이 잘 들어가야함
		for (MultipartFile multipartFile : files) {
			Postfile postfile = new Postfile();
			postfile.setFilepath(multipartFile.getOriginalFilename());
			postFileSerivce.save(post, postfile);			
		}
		return null;
	}
}

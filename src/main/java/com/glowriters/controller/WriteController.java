package com.glowriters.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import jakarta.annotation.PostConstruct;
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
	
	@Value("${project.uploadpath}")
	private String uploadpath = "static/upload";

	private String absoluteUploadPath;
	
	@PostConstruct  
	public void init() {
		try {
			// 현재 작업 디렉토리 기준으로 설정된 상대 경로를 절대 경로로 변환
			File file = new File(new File("").getAbsolutePath(), "src/main/resources/" + uploadpath);
			
			if (!file.exists()) {
				file.mkdirs(); // 디렉토리가 존재하지 않으면 생성
			}
			
			absoluteUploadPath = file.getAbsolutePath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//날짜별로 폴더 생성
	public String makeDir() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String now = df.format(date);

		String path = absoluteUploadPath + "/" + now; // 경로
		File file = new File(path);

		if (!file.exists()) { // 파일이 존재하지 않으면
			file.mkdir(); // 폴더 생성
		}

		return path; // 생성된 경로 반환
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
			if(!multipartFile.isEmpty()) {
			// 실제파일명추출:image.jpg
				String origin = multipartFile.getOriginalFilename(); // 원래파일명:"C:\Users\example\Desktop\image.jpg"
				String filename = origin.substring(origin.lastIndexOf("/") + 1); 
				// 폴더 생성 - 내가만든 메소드 사용
				String filepath = makeDir();
				// 최종 저장 경로( 날짜폴더/uuid_기본파일명 으로 저장)
				String uuid = UUID.randomUUID().toString();
				String savename = filepath + "/" + uuid + "_" + filename;
				
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				String now = df.format(new Date());
				
				// 1. DB에 저장
				Postfile postfile = new Postfile();
				postfile.setFilepath("/upload/" + now + "/" + uuid + "_" + filename);
				postFileSerivce.save(post, postfile);
				

				// 2. 실제 프로젝트 폴더내에 파일 저장
				File saveFile = new File(savename);
				try {
					multipartFile.transferTo(saveFile); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	
}

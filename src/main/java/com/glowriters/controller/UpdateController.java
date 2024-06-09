package com.glowriters.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UpdateController extends BaseController {

	private final PostService postService;

	private final PostFileSerivce postFileSerivce;

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

	// 날짜별로 폴더 생성
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

	@GetMapping("update/update/{post_id}")
	public String viewWriteUpdate(@PathVariable("post_id") long post_id, Model model) {

		PostViewDTO pvd = new PostViewDTO();

		// 수정할 게시물
		Post updatePost = postService.findById(post_id);
		pvd.setPost_id(updatePost.getPost_id());
		pvd.setTitle(updatePost.getTitle());
		pvd.setContent(updatePost.getContent());

		// 수정할 게시물 사진
		List<Postfile> postfiles = postFileSerivce.findAllByPost(updatePost.getPost_id());
		List<String> imgs = new ArrayList<String>(3);
		for (Postfile postfile : postfiles) {
			imgs.add(postfile.getFilepath());
		}
		while (imgs.size() < 3)
			imgs.add("");
		pvd.setFilepaths(imgs);

		model.addAttribute("pvd", pvd);
		return "update/update";
	}
	
	@GetMapping("/update/update/delete/{post_id}")
	public String deletePost(@PathVariable("post_id") long post_id) {
		// 삭제할 게시물
		Post deletePost = postService.findById(post_id);
		postService.deletePost(deletePost);
		return "redirect:/";
	}
	
	@PostMapping("/update/update/{post_id}")
	public String updateWrite(RedirectAttributes redirectAttributes, @PathVariable("post_id") long post_id, @RequestParam("files") MultipartFile[] files,
			Post post) {
		// 수정할 게시물
		Post updatePost = postService.findById(post_id);
		List<Postfile> updatePostfiles = postFileSerivce.findAllByPost(updatePost.getPost_id());
		postService.updatePost(updatePost, post);
		postFileSerivce.delete(updatePostfiles);
		for (MultipartFile multipartFile : files) {
			if (!multipartFile.isEmpty()) {
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
		redirectAttributes.addAttribute("post_id", post_id);
		return "redirect:/post-details/post-details/{post_id}";
	}
	
	
}

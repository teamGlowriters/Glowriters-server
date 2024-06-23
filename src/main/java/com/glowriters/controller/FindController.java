package com.glowriters.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.glowriters.DTO.MemberViewDTO;
import com.glowriters.DTO.PostViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostService;
import com.glowriters.service.ReplyService;
import com.glowriters.service.SubscriberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindController extends BaseController {
	private final PostService postService;
	private final MemberService memberService;
	private final PostFileSerivce postFileSerivce;
	private final SubscriberService subscriberService;
	private final ReplyService replyService;
	
//PostViewDTO를 생성해주는 함수. 게시물리스트를 전달하면 생성해줌
	public List<PostViewDTO> getPostViewDTO(List<Post> posts) {
		List<PostViewDTO> pvds = new ArrayList<>();

		for (Post post : posts) {
			PostViewDTO pvd = new PostViewDTO();
			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());

			Member member = post.getMember();
			long member_id = member.getMember_id();
			String member_nickname = member.getMember_nickname();
			String member_profile = member.getMember_profile();
			String member_comment = member.getMember_comment();

			// 공통 부분
			pvd.setMember_nickname(member_nickname);
			pvd.setMember_profile(member_profile);
			pvd.setMember_comment(member_comment);

			pvd.setPost_id(post.getPost_id());
			pvd.setTitle(post.getTitle());
			pvd.setContent(post.getContent());
			pvd.setCategory(post.getCategory());
			pvd.setCreated_date(post.getCreated_date());
			pvd.setUpdated_date(post.getUpdated_date());

			pvd.setFilepath(postfiles.get(0).getFilepath()); // 가장 첫번째 이미지를 타이틀이미지로저장

			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member_id));

//			String NEW = pvd.getCreated_date().isEqual(pvd.getUpdated_date()) ? "yes" : "no";
			String NEW = post.getCreated_date().isEqual(post.getUpdated_date()) ? "yes" : "no"; 
			pvd.setNEW(NEW);
			
			long replyCount = replyService.getCommentCountByPostId(post.getPost_id());
			pvd.setReplyCount(replyCount);
			
			
			// pvd.setLikeCount(0); //아직 구현안함
			pvds.add(pvd);
		}
		return pvds;
	}

	// MemberViewDTO를 생성하는 함수
	public List<MemberViewDTO> getMemberViewDTO(List<Member> members) {
		List<MemberViewDTO> mvds = new ArrayList<>();

		for (Member member : members) {
			MemberViewDTO mvd = new MemberViewDTO();
			long member_id = member.getMember_id();
			String member_nickname = member.getMember_nickname();
			String member_profile = member.getMember_profile();
			String member_comment = member.getMember_comment();

			mvd.setMember_id(member_id);
			mvd.setMember_nickname(member_nickname);
			mvd.setMember_profile(member_profile);
			mvd.setMember_comment(member_comment);

			long postCount = postService.countPostByMemberId(member_id);
			mvd.setPostCount(postCount);

			long subscriberCount = subscriberService.countSubscribersByBloggerId(member_id);
			mvd.setSubscriberCount(subscriberCount);

			// 현재 블로거가 작성한 모든 게시글을가져와서 카테고리를 찾는다.
			List<Post> posts = postService.findByMemberId(member_id);
			List<String> categorys = new ArrayList<>();

			Map<String, Boolean> Allcategorys = new HashMap<>();
			Allcategorys.put("여행", false);
			Allcategorys.put("그림 웹툰", false);
			Allcategorys.put("사진 촬영", false);
			Allcategorys.put("IT 트랜드", false);
			Allcategorys.put("경제", false);
			Allcategorys.put("영화 리뷰", false);
			Allcategorys.put("뮤직(음악)", false);
			Allcategorys.put("요리 레시피", false);
			Allcategorys.put("건강 운동", false);
			Allcategorys.put("문화 예술", false);
			Allcategorys.put("사랑 이별", false);
			Allcategorys.put("반려동물", false);
			for (Post post : posts) { // 블로거가 작성한 모든 게시글에 사용된 카테고리를 체크
				Allcategorys.put(post.getCategory(), true);
			}
			for (Map.Entry<String, Boolean> item : Allcategorys.entrySet()) {
				if (item.getValue())
					categorys.add(item.getKey());
			}

			mvd.setCategorys(categorys);

			mvds.add(mvd);
		}
		return mvds;
	}

	// 이전페이지에서 검색창에 엔터누르면 이 컨트롤러가 호출됨
	@PostMapping("/search/find")
	public String viewFind(String searchInput, Model model) {
		List<Post> posts = postService.findByTitleByKeyword(searchInput);
		List<PostViewDTO> pvds = getPostViewDTO(posts);
		
		List<Member> bloggers = memberService.findByMemberNicknameByKeyword(searchInput);
		List<MemberViewDTO> mvds = getMemberViewDTO(bloggers);
		
		
		model.addAttribute("searchInput", searchInput);//검색한결과를 보여줌
		model.addAttribute("postCnt", posts.size());
		model.addAttribute("bloggerCnt", bloggers.size());

		model.addAttribute("posts", pvds);
		model.addAttribute("bloggers", mvds);
		

		return "/search/find";
	}

}

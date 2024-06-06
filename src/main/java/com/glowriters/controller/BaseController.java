package com.glowriters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

//모든 컨트롤러가 상속받는 컨트롤러
//세션을 바탕으로 모델에 주입
//PostViewDTO와 MemberViewDTO를 쉽게 생성해주는 함수를 가지고 있음
@Controller
public class BaseController {
	
	//1. 모델에 값을 넣어주는 기능 (isLogin, member_id, member_profile, member_nickname)
	@ModelAttribute("isLogin")
	public String setLoginStatus(HttpSession session) {
    return session.getAttribute("member_id") == null ? "no" : "yes";
	}
	@ModelAttribute("member_id")
	public String setMember_id(HttpSession session) {
		return String.valueOf(session.getAttribute("member_id"));
	}
	@ModelAttribute("member_profile")
	public String setMember_profile(HttpSession session) {
		return String.valueOf(session.getAttribute("member_profile"));
	}
	@ModelAttribute("member_nickname")
	public String setMember_nickname(HttpSession session) {
		return String.valueOf(session.getAttribute("member_nickname"));
	}

//	//2. PostViewDTO와 MemberViewDTO를 쉽게 생성해주는 함수들
//	private final PostService postService;
//	private final MemberService memberService;
//	private final SubscriberService subscriberService;
//	private final PostFileSerivce postFileSerivce;
//	
//	
//	//PostViewDTO를 생성해주는 함수. 게시물리스트를 전달하면 생성해줌
//	public List<PostViewDTO> getPostViewDTO(List<Post> posts) {
//		List<PostViewDTO> pvds = new ArrayList<>();
//
//		for (Post post : posts) {
//			PostViewDTO pvd = new PostViewDTO();
//			List<Postfile> postfiles = postFileSerivce.findAllByPost(post.getPost_id());
//
//			Member member = post.getMember();
//			long member_id = member.getMember_id();
//			String member_nickname = member.getMember_nickname();
//			String member_profile = member.getMember_profile();
//			String member_comment = member.getMember_comment();
//
//			// 공통 부분
//			pvd.setMember_nickname(member_nickname);
//			pvd.setMember_profile(member_profile);
//			pvd.setMember_comment(member_comment);
//
//			pvd.setPost_id(post.getPost_id());
//			pvd.setTitle(post.getTitle());
//			pvd.setContent(post.getContent());
//			pvd.setCategory(post.getCategory());
//			pvd.setCreated_date(post.getCreated_date());
//			pvd.setUpdated_date(post.getUpdated_date());
//
//			pvd.setFilepath(postfiles.get(0).getFilepath()); // 가장 첫번째 이미지를 타이틀이미지로저장
//
//			pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member_id));
//
//			String NEW = pvd.getCreated_date().isEqual(pvd.getUpdated_date()) ? "yes" : "no";
//			pvd.setNEW(NEW);
//
//			//pvd.setLikeCount(0); //아직 구현안함
//			pvds.add(pvd);
//		}
//		return pvds;
//	}
//	
//
//	//MemberViewDTO를 생성하는 함수
//	public List<MemberViewDTO> getMemberViewDTO(List<Member> members) {
//		List<MemberViewDTO> mvds = new ArrayList<>();
//
//		for (Member member : members) {
//			MemberViewDTO mvd = new MemberViewDTO();
//			long member_id = member.getMember_id();
//			String member_nickname = member.getMember_nickname();
//			String member_profile = member.getMember_profile();
//			String member_comment = member.getMember_comment();
//
//			mvd.setMember_id(member_id);
//			mvd.setMember_nickname(member_nickname);
//			mvd.setMember_profile(member_profile);
//			mvd.setMember_comment(member_comment);
//
//			long postCount = postService.countPostByMemberId(member_id);
//			mvd.setPostCount(postCount);
//
//			long subscriberCount = subscriberService.countSubscribersByBloggerId(member_id);
//			mvd.setSubscriberCount(subscriberCount);
//
//			// 현재 블로거가 작성한 모든 게시글을가져와서 카테고리를 찾는다.
//			List<Post> posts = postService.findByMemberId(member_id);
//			List<String> categorys = new ArrayList<>();
//
//			Map<String, Boolean> Allcategorys = new HashMap<>();
//			Allcategorys.put("여행", false);
//			Allcategorys.put("그림 웹툰", false);
//			Allcategorys.put("사진 촬영", false);
//			Allcategorys.put("IT 트랜드", false);
//			Allcategorys.put("경제", false);
//			Allcategorys.put("영화 리뷰", false);
//			Allcategorys.put("뮤직(음악)", false);
//			Allcategorys.put("요리 레시피", false);
//			Allcategorys.put("건강 운동", false);
//			Allcategorys.put("문화 예술", false);
//			Allcategorys.put("사랑 이별", false);
//			Allcategorys.put("반려동물", false);
//			for (Post post : posts) { //블로거가 작성한 모든 게시글에 사용된 카테고리를 체크
//				Allcategorys.put(post.getCategory(), true);
//			}
//			for(Map.Entry<String, Boolean> item : Allcategorys.entrySet()) {
//				if(item.getValue()) categorys.add(item.getKey());
//			}
//			
//			mvd.setCategorys(categorys);
//			
//			mvds.add(mvd);
//		}
//		return mvds;
//	}
}

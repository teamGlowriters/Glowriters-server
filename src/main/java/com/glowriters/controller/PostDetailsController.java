package com.glowriters.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.glowriters.DTO.PostViewDTO;
import com.glowriters.DTO.ReplyViewDTO;
import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.domain.Postlike;
import com.glowriters.domain.Reply;
import com.glowriters.repository.PostLikeRepository;
import com.glowriters.service.MemberService;
import com.glowriters.service.PostFileSerivce;
import com.glowriters.service.PostLikeService;
import com.glowriters.service.PostService;
import com.glowriters.service.ReplyService;
import com.glowriters.service.SubscriberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor // @Autowired를 안써도됨
public class PostDetailsController extends BaseController {

	private final MemberService memberService;

	private final PostService postService;

	private final PostFileSerivce postFileSerivce;

	private final SubscriberService subscriberService;
	
	private final PostLikeService postLikeService;
	
	private final ReplyService replyService;
	
	@GetMapping("/post-details/post-details/{post_id}")
	public String viewPostDetails(HttpServletRequest request, @PathVariable("post_id") long post_id, Model model) {

		// 현재 로그인한 사용자 즉 이 게시물을 보고 있는 사용자 정보 가져오기
		HttpSession session = request.getSession();
		long member_id = (long) session.getAttribute("member_id");
		Member loginMember = memberService.findById(member_id);
		model.addAttribute("member", loginMember);
		
		PostViewDTO pvd = new PostViewDTO();
		List<ReplyViewDTO> rvds = new ArrayList<ReplyViewDTO>();
		
		// 게시글
		Post post = postService.findById(post_id);
		// 게시글 작성자
		Member member = post.getMember();
		// 게시글 사진
		List<Postfile> postfiles = postFileSerivce.findAllByPost(post_id);
		
		// member
		pvd.setMember_id(member.getMember_id());
		pvd.setMember_nickname(member.getMember_nickname());
		pvd.setMember_comment(member.getMember_comment());
		pvd.setMember_profile(member.getMember_profile());
		
		// post
		pvd.setPost_id(post_id);
		pvd.setTitle(post.getTitle());
		pvd.setContent(post.getContent());
		pvd.setCategory(post.getCategory());
		pvd.setCreated_date(post.getCreated_date());

		// postfile
		for (Postfile postfile : postfiles) {
			pvd.filepaths.add(postfile.getFilepath());
		}

		// subscriberCount
		pvd.setSubscriberCount(subscriberService.countSubscribersByBloggerId(member.getMember_id()));
		
		// postlike
		// 테이블에 있는지 없는지를 먼저 봐야함
		int postlikeExist = postLikeService.checkIfPostlikeExists(loginMember.getMember_id(), post.getPost_id());
		if(postlikeExist == 1) {
			// 테이블에 있다면 해당 행을 반환
			Postlike memberPostLike = postLikeService.getPostlike(loginMember.getMember_id(), post.getPost_id());
		  // 사용자가 좋아요를 눌렀는지 아님 취소했는지를 알아와야함
			// 사용자가 좋아요를 누른 상태라면 1
			// 사용자가 좋아요를 취소한 상태라면 0
			pvd.setIsLike(memberPostLike.getStatus()); 
		}
		
		long postlikeCount = postLikeService.countPostLikes(post.getPost_id());
		pvd.setLikeCount(postlikeCount);
		
		
		// reply
		List<Reply> replyes = replyService.getRepliesByPostIdSortedByDate(post.getPost_id());
		for (Reply reply : replyes) {
			ReplyViewDTO rvd = new ReplyViewDTO();
			Member memberReply = reply.getMember();
			rvd.setMember_id(memberReply.getMember_id());
			rvd.setReply_member_nickname(memberReply.getMember_nickname());
			rvd.setReply_member_profile(memberReply.getMember_profile());
			rvd.setReply_content(reply.getReply_content());
			rvd.setReply_created_date(reply.getCreated_date());
			rvds.add(rvd);
		}
		
		// replyCount
		long replyCount = replyService.getCommentCountByPostId(post.getPost_id());
		pvd.setReplyCount(replyCount);
		
		model.addAttribute("blogger_id", member.getMember_id()); //게시글작성자의 id를 보냄
		model.addAttribute("canEdit", String.valueOf(model.getAttribute("member_id")).equals(String.valueOf(model.getAttribute("blogger_id"))));
		model.addAttribute("pvd", pvd);
		model.addAttribute("rvds", rvds);
		return "/post-details/post-details";
	}

	@PostMapping("/post-details/post-details/{post_id}")
	public String postLike(HttpServletRequest request, @PathVariable("post_id") long post_id) {
		// 현재 로그인한 사용자 즉 이 게시물을 보고 있는 사용자 정보 가져오기
		HttpSession session = request.getSession();
		long member_id = (Long) session.getAttribute("member_id");
		Member loginMember = memberService.findById(member_id);
		Post likePost = postService.findById(post_id);
		int isLike = postLikeService.checkIfPostlikeExists(loginMember.getMember_id(), likePost.getPost_id());
		
		// 좋아요를 한번도 누르지 않은 게시물이라면 테이블에 저장
		if(isLike == 0) {
		  postLikeService.save(loginMember, likePost);
		}else {
			// 좋아요를 눌렀고 한번 더 누르면 좋아요 취소 즉 테이블에서 삭제
			Postlike postlike = postLikeService.getPostlike(loginMember.getMember_id(), likePost.getPost_id());
			if(postlike.getStatus() == 1) {
				postLikeService.deleteLike(postlike);
			}else {
				postlike.setStatus(1);
				postLikeService.deleteLikeLike(postlike);
			}			
		}
		
		return "redirect:/post-details/post-details/{post_id}";
	}
	
	@PostMapping("/post-details/post-details/{member_id}/{post_id}")
	public String replyEnter(@PathVariable("member_id") long member_id, @PathVariable("post_id") long post_id, Reply reply) {
		
		// 댓글 작성자
		Member replyMember = memberService.findById(member_id);
		// 댓글 작성한 게시물
		Post replyPost = postService.findById(post_id);
		// 댓글 저장
		replyService.save(replyMember, replyPost, reply);
		
		return "redirect:/post-details/post-details/{post_id}";
	}
}

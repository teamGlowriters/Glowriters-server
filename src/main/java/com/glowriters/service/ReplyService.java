package com.glowriters.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Reply;
import com.glowriters.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;

	@Transactional
	public Reply save(Member member, Post post, Reply reply) {
		// 댓글 작성한 게시물
		reply.setPost(post);
		// 댓글 작성자
		reply.setMember(member);
		return replyRepository.save(reply);
	}
	
	@Transactional
	public Reply findbyId(long reply_id) {
		return replyRepository.findById(reply_id).orElse(null);
	}
	
	// 해당 게시물의 총 댓글
	@Transactional
  public List<Reply> getRepliesByPostIdSortedByDate(long postId) {
      return replyRepository.findByPostIdOrderByCreatedDate(postId);
  }
	
	// 댓글 개수
	@Transactional
	public long getCommentCountByPostId(long postId) {
		return replyRepository.countRepliesByPostId(postId);
	}
}

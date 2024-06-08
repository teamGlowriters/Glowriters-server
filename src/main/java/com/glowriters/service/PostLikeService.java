package com.glowriters.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postlike;
import com.glowriters.repository.PostLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;

	@Transactional
	public Postlike save(Member member, Post post) {

		Postlike postlike = new Postlike();
		// 좋아요를 누른 회원
		postlike.setMember(member);
		// 좋아요 한 게시글
		postlike.setPost(post);

		return postLikeRepository.save(postlike);
	}

	@Transactional(readOnly = true)
	public Postlike getPostlike(Long memberId, Long postId) {
		return postLikeRepository.findByMemberIdAndPostId(memberId, postId);
	}

	// 회원이 해당 게시물 좋아요를 눌렀는지 안눌렀는지 알려주는 메소드 작성
	@Transactional(readOnly = true)
	public int checkIfPostlikeExists(Long memberId, Long postId) {
		boolean exists = postLikeRepository.existsByMemberIdAndPostId(memberId, postId);
		return exists ? 1 : 0;
	}

	// 좋아요가 눌러져있고 한번 더 좋아요를 눌렀다면 좋아요 취소 즉 status = 0 <- 바껴야함
	@Transactional
	public void deleteLike(Postlike postlike) {
		// status = 0
		postlike.setStatus(0);
		postLikeRepository.save(postlike);
	}

	// 좋아요를 눌렀다가 취소하고 다시 좋아요를 누르고 싶은 상황
	public void deleteLikeLike(Postlike postlike) {
		postlike.setStatus(1);
		postLikeRepository.save(postlike);
	}

	// 좋아요 개수
	public long countPostLikes(long post_id) {
		return postLikeRepository.countByStatusAndPostId(post_id);
	}
}

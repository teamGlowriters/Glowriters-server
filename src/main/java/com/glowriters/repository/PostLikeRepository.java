package com.glowriters.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Postlike;

public interface PostLikeRepository extends JpaRepository<Postlike, Long> {

	@Query("SELECT pl FROM Postlike pl WHERE pl.member.id = :memberId AND pl.post.id = :postId")
  Postlike findByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
	
	// 회원이 보고 있는 게시물의 좋아요를 눌렀는지 안눌렀는지 구현
	@Query("SELECT COUNT(pl) > 0 FROM Postlike pl WHERE pl.member.id = :memberId AND pl.post.id = :postId")
	boolean existsByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
	
	// 해당 게시물의 좋아요 개수를 세주는 메소드 구현
	@Query("SELECT COUNT(p) FROM Postlike p WHERE p.status = 1 AND p.post.id = :postId")
  long countByStatusAndPostId(@Param("postId") long postId);
}

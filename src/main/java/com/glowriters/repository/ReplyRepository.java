package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// 해당 게시물의 총 댓글
	@Query("SELECT r FROM Reply r WHERE r.post.post_id = :postId ORDER BY r.created_date DESC")
  List<Reply> findByPostIdOrderByCreatedDate(@Param("postId") long postId);
	
	// 댓글 개수 
	@Query("SELECT COUNT(r) FROM Reply r WHERE r.post.post_id = :postId")
  int countRepliesByPostId(@Param("postId") long postId);
}
